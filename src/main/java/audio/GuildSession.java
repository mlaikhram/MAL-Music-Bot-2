package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import model.animethemes.*;
import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;
import model.mal.MALListResponse;
import model.mal.MALNode;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.AnimeThemes;
import resource.MAL;
import util.ConfigHandler;
import util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class GuildSession {

    private static final Logger logger = LoggerFactory.getLogger(GuildSession.class);


    private final Map<String, IwaUser> users;
    private final Map<Long, IwaAnime> animeBank;
    private final Map<Long, IwaTheme> themeBank;
    private final LinkedHashSet<String> recentSongs;
    private ATAudio currentSong;
    private String lastCommand;

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

    public void addUser(String username) {
        try {
            if (users.containsKey(username.toLowerCase())) {
                throw new Exception(username + " is already added!");
            } else {
                IwaUser user = new IwaUser(username);
                List<Long> newAnime = populateUser(user, "completed");
                newAnime.addAll(populateUser(user, "watching"));
                logger.info(user.getName() + " | Total completed: " + user.getMalIds("completed").size() + " | Total watching: " + user.getMalIds("watching").size());

                // TODO: update message with final counts and processing new anime message (x/y)...
                populateAnimeThemes(newAnime);

                Set<Long> emptyAnime = new HashSet<>();
                for (IwaAnime anime : animeBank.values()) {
                    if (anime.getThemeSet().isEmpty()) {
                        logger.warn(anime.getName() + " does not have any themes");
                        emptyAnime.add(anime.getMalId());
                    }
                }
                logger.info("total anime without songs: " + emptyAnime.size());
                for (long malId : emptyAnime) {
                    animeBank.remove(malId);
                }

                users.put(user.getName(), user);
                // TODO: send successful add message with stats
            }
        }
        catch (Exception e) {
            // TODO: return error message in chat + log
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeUser(String username) {
        if (users.containsKey(username.toLowerCase())) {
            users.remove(username.toLowerCase());
            // TODO: message removed user
        }
        else {
            // TODO: message user is not in list
        }
    }

    // returns a list of malIds that are not yet populated in the animeBank with theme songs
    private List<Long> populateUser(IwaUser user, String status) {
        int offset = 0;
        List<Long> newAnime = new ArrayList<>();
        MALListResponse paginatedResponse;
        do {
            paginatedResponse = MAL.getList(user.getName(), status, offset).getBody();
            for (MALNode node : paginatedResponse.getData()) {
                if (!animeBank.containsKey(node.getNode().getId())) {
                    animeBank.put(node.getNode().getId(), new IwaAnime(node.getNode()));
                    newAnime.add(node.getNode().getId());
                }
                user.addMalIds(status, node.getNode().getId());
            }
            // TODO: update message with new count of loaded entries
            offset += ConfigHandler.config().getMal().getPageLimit();
        } while (paginatedResponse.getPaging().getNext() != null);
        return newAnime;
    }

    private void populateAnimeThemes(List<Long> newAnime) {
        int page = 0;
        ATListResponse paginatedResponse;
        while (page * ConfigHandler.config().getAnimethemes().getPageLimit() < newAnime.size()) {
            ++page;
            paginatedResponse = AnimeThemes.getList(newAnime.subList((page - 1) * ConfigHandler.config().getAnimethemes().getPageLimit(), Math.min(page * ConfigHandler.config().getAnimethemes().getPageLimit(), newAnime.size())), 1).getBody();
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
            // TODO: update message with new count of processed anime entries (themes loaded)
        }
    }
}
