package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import model.animethemes.*;
import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;
import model.jikan.JikanAnimeStats;
import model.jikan.JikanProfile;
import model.mal.MALListResponse;
import model.mal.MALNode;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.AnimeThemes;
import resource.Jikan;
import resource.MAL;
import util.ConfigHandler;
import util.Constants;
import util.Embeds;

import java.util.*;
import java.util.stream.Collectors;

public class GuildSession {

    private static final Logger logger = LoggerFactory.getLogger(GuildSession.class);


    private final Map<String, IwaUser> users;
    private final Map<Long, IwaAnime> animeBank;
    private final Map<Long, IwaTheme> themeBank;
    private final LinkedHashSet<String> recentSongs;
    private ATAudio currentSong;

    public final TrackScheduler scheduler;
    private final AudioSendHandler audioSendHandler;


    public GuildSession(Guild guild, AudioPlayerManager audioPlayerManager) {
        this.users = new TreeMap<>();
        this.animeBank = new HashMap<>();
        this.themeBank = new HashMap<>();
        this.recentSongs = new LinkedHashSet<>();

        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer);
        audioPlayer.addListener(this.scheduler);
        this.audioSendHandler = new AudioPlayerSendHandler(audioPlayer);

        guild.getAudioManager().setSendingHandler(this.audioSendHandler);
    }

    public void listUsers(InteractionHook hook) {
        List<String> userlist = users.values().stream().map(IwaUser::getUsername).collect(Collectors.toList());
        userlist.add("\n*" + users.size() + " total*");
        hook.sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.List("Users", userlist)).build()).queue();
    }

    public void addUser(String username, InteractionHook hook) {
        try {
            if (users.containsKey(username.toLowerCase())) {
                throw new Exception("This user is already added!");
            } else {
                hook.sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.PendingEmbed(username, "Searching for this user...")).build()).queue();
                JikanProfile userProfile = Jikan.getProfile(username).getBody().getProfile();

                IwaUser user = new IwaUser(userProfile);

                hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.UserAddPending(user, "Populating anime list...", userProfile.getStatistics().getAnime())).build()).queue();

                List<Long> newAnime = populateUser(user, Constants.myanimelist.status.completed.toString(), userProfile.getStatistics().getAnime(), hook);
                newAnime.addAll(populateUser(user, Constants.myanimelist.status.watching.toString(), userProfile.getStatistics().getAnime(), hook));
                logger.info(user.getUsername() + " | Total completed: " + user.getMalIds("completed").size() + " | Total watching: " + user.getMalIds("watching").size());

                hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.UserAddPending(user, String.format("Gathering songs for new anime entries (%s/%s)...", 0, newAnime.size()))).build()).queue();
                populateAnimeThemes(user, newAnime, hook);

                Set<Long> emptyAnime = new HashSet<>();
                for (IwaAnime anime : animeBank.values()) {
                    if (anime.getThemeSet().isEmpty()) {
                        logger.warn(anime.getName() + " does not have any themes");
                        emptyAnime.add(anime.getMalId());
                    }
                }
                logger.info("total anime without songs: " + emptyAnime.size());

                users.put(user.getUsername().toLowerCase(), user);
                hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.UserAddComplete(user)).build()).queue();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.ErrorEmbed(username, e.getMessage())).build()).queue();
        }
    }

    public void removeUser(String username, InteractionHook hook) {
        if (users.containsKey(username.toLowerCase())) {
            users.remove(username.toLowerCase());
            hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.CompleteEmbed(username, "This user has been removed")).build()).queue();
        }
        else {
            hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.ErrorEmbed(username, "This user is not added!")).build()).queue();
        }
    }

    // returns a list of malIds that are not yet populated in the animeBank with theme songs
    private List<Long> populateUser(IwaUser user, String status, JikanAnimeStats expectedStats, InteractionHook hook) {
        int offset = 0;
        List<Long> newAnime = new ArrayList<>();
        MALListResponse paginatedResponse;
        do {
            paginatedResponse = MAL.getList(user.getUsername(), status, offset).getBody();
            for (MALNode node : paginatedResponse.getData()) {
                if (!animeBank.containsKey(node.getNode().getId())) {
                    animeBank.put(node.getNode().getId(), new IwaAnime(node.getNode()));
                    newAnime.add(node.getNode().getId());
                }
                user.addMalIds(status, node.getNode().getId());
            }
            hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.UserAddPending(user, "Populating anime list...", expectedStats)).build()).queue();
            offset += ConfigHandler.config().getMal().getPageLimit();
        } while (paginatedResponse.getPaging().getNext() != null);
        return newAnime;
    }

    private void populateAnimeThemes(IwaUser user, List<Long> newAnime, InteractionHook hook) {
        int page = 0;
        ATListResponse paginatedResponse;
        while (page * ConfigHandler.config().getAnimethemes().getPageLimit() < newAnime.size()) {
            ++page;
            int fromIndex = (page - 1) * ConfigHandler.config().getAnimethemes().getPageLimit();
            int toIndex = Math.min(page * ConfigHandler.config().getAnimethemes().getPageLimit(), newAnime.size());
            paginatedResponse = AnimeThemes.getList(newAnime.subList(fromIndex, toIndex), 1).getBody();
            for (ATAnime anime : paginatedResponse.getAnime()) {
                long malId = anime.getResources().get(0).getMalId();

                for (ATAnimeTheme animeTheme : anime.getAnimethemes()) {
                    List<String> artists = animeTheme.getSong().getArtists().stream().map(ATArtist::getName).collect(Collectors.toList());

                    for (ATAnimeThemeEntry animeThemeEntry : animeTheme.getAnimethemeentries()) {
                        // skip spoilers
                        if (!animeThemeEntry.isSpoiler()) {
                            for (ATVideo video : animeThemeEntry.getVideos()) {
                                String videoUrl = Constants.animethemes.VIDEO_URL_TEMPLATE
                                        .replace(Constants.animethemes.ANIME_SLUG, anime.getSlug())
                                        .replace(Constants.animethemes.ANIMETHEME_SLUG, animeTheme.getSlug())
                                        .replace(Constants.animethemes.VIDEO_TAGS, video.getTags());
                                IwaTheme theme = new IwaTheme(video.getId(), animeTheme.getSong().getTitle(), artists, video.getAudio().getLink(), videoUrl, malId);
                                if (animeBank.containsKey(malId)) {
                                    themeBank.putIfAbsent(theme.getId(), theme);
                                    animeBank.get(malId).addThemes(theme.getId());
                                } else {
                                    logger.error("could not find entry for " + malId + " (" + anime.getName() + ")");
                                }
                            }
                        }
                        else {
                            logger.info("skipping spoiler song for " + anime.getName() + ": " + animeTheme.getSong().getTitle());
                        }
                    }
                }
            }
            hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.UserAddPending(user, String.format("Gathering songs for new anime entries (%s/%s)...", toIndex , newAnime.size()))).build()).queue();
        }
    }
}
