package audio;

import balancer.Balancers;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.AnimeThemes;
import resource.Jikan;
import resource.MAL;
import util.ConfigHandler;
import util.Constants;
import util.Constants.AutoplayOptions;
import util.Embeds;

import java.util.*;
import java.util.stream.Collectors;

public class GuildSession {

    private static final Logger logger = LoggerFactory.getLogger(GuildSession.class);

    private final AudioPlayerManager audioPlayerManager;
    public final TrackScheduler scheduler;
    private final AudioSendHandler audioSendHandler;

    private IwaJukebox jukebox;

    private Message currentSongMessage;

    private AutoplayOptions autoplay = AutoplayOptions.DISABLED;

    public GuildSession(Guild guild) {
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer, this);
        audioPlayer.addListener(this.scheduler);
        this.audioSendHandler = new AudioPlayerSendHandler(audioPlayer);
        guild.getAudioManager().setSendingHandler(this.audioSendHandler);

        this.jukebox = new IwaJukebox();
    }

    public void setBalancer(String selectedBalancer, InteractionHook hook) {
        jukebox.setBalancer(Balancers.create(Balancers.type.valueOf(selectedBalancer)));
        displayCurrentSettings(hook);
    }

    public void setFilters(Collection<String> allowedTypes, InteractionHook hook) {
        jukebox.getFilter().setFilter(allowedTypes.stream().map(type -> Constants.myanimelist.type.valueOf(type)).collect(Collectors.toSet()));
        displayCurrentSettings(hook);
    }

    public void autoplayManager(String autoplayString, InteractionHook hook) {
        autoplay = AutoplayOptions.valueOf(autoplayString);
        displayCurrentSettings(hook);
    }

    public void displayCurrentSettings(InteractionHook hook) {
        StringSelectMenu.Builder autoplayMenuBuilder = StringSelectMenu.create(Constants.componentids.AUTOPLAY_SELECTOR);
        for (AutoplayOptions option : AutoplayOptions.values()) {
            autoplayMenuBuilder.addOption("Autoplay " + option.name().toLowerCase(), option.name());
        }
        autoplayMenuBuilder.setMaxValues(1);
        autoplayMenuBuilder.setDefaultValues(autoplay.name());

        StringSelectMenu.Builder animeTypeSelectMenuBuilder = StringSelectMenu.create(Constants.componentids.ANIME_TYPES_DROPDOWN);
        for (Constants.myanimelist.type type : Constants.myanimelist.type.values()) {
            animeTypeSelectMenuBuilder.addOption(type.name(), type.name());
        }
        animeTypeSelectMenuBuilder.setMaxValues(Constants.myanimelist.type.values().length);
        animeTypeSelectMenuBuilder.setDefaultValues(jukebox.getFilter().getAllowedTypes().stream().map(type -> type.name()).collect(Collectors.toSet()));

        StringSelectMenu.Builder animeBalancerSelectMenuBuilder = StringSelectMenu.create(Constants.componentids.ANIME_BALANCER_DROPDOWN);
        for (Balancers.type type : Balancers.type.values()) {
            animeBalancerSelectMenuBuilder.addOption(type.name(), type.name());
        }
        animeBalancerSelectMenuBuilder.setMaxValues(1);
        animeBalancerSelectMenuBuilder.setDefaultValues(jukebox.getBalancer().getType().name());

        hook.editOriginal(MessageEditBuilder.fromCreateData(new MessageCreateBuilder()
                .addActionRow(autoplayMenuBuilder.build())
                .addActionRow(animeTypeSelectMenuBuilder.build())
                .addActionRow(animeBalancerSelectMenuBuilder.build())
                .build()
        ).build()).queue();
    }

    public void stopTheme(AudioManager audioManager, Member commander, InteractionHook hook) {
        try {
            if (currentSongMessage == null) {
                throw new Exception("I'm not even playing a song right now!");
            } else if (!commander.getVoiceState().inAudioChannel() || commander.getGuild().getIdLong() != audioManager.getGuild().getIdLong() || commander.getVoiceState().getChannel().getIdLong() != audioManager.getConnectedChannel().getIdLong()) {
                throw new Exception( "You can't tell me to stop! You're not even in this voice channel!");
            } else {
                 currentSongMessage.editMessage(new MessageEditBuilder().setEmbeds(Embeds.PendingEmbed("Stopping Song", "You suck...")).build()).queue();
                scheduler.stopTrack();
                if (hook != null) {
                    hook.sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.CompleteEmbed("Stopped the Song", "You suck...")).build()).queue();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (hook != null) {
                hook.sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.ErrorEmbed("Can't stop song", e.getMessage())).build()).queue();
            }
        }
    }

    public void playTheme(AudioManager audioManager, Member commander, InteractionHook hook) {
        logger.info("attempting to play a song");
        try {
            if (currentSongMessage != null && scheduler.isPlayingTrack()) {
                stopTheme(audioManager, commander, null);
            }
            if (!commander.getVoiceState().inAudioChannel()) {
                throw new Exception("You must be in a voice channel to hear my song!");
            }
            if (commander.getGuild().getIdLong() != audioManager.getGuild().getIdLong()) {
                logger.error(String.format("user guild: %s, my guild: %s", commander.getGuild().getId(), audioManager.getGuild().getId()));
                throw new Exception("You must join a voice channel on this server!");
            }
            VoiceChannel currentChannel = commander.getVoiceState().getChannel().asVoiceChannel();
            if (!audioManager.isConnected()) {
                logger.info("opening audio connection to current channel");
                audioManager.openAudioConnection(currentChannel);
            } else if (audioManager.getConnectedChannel().getIdLong() != currentChannel.getIdLong()) {
                throw new Exception("You are in the wrong voice channel!");
            }
            hook.sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.PendingEmbed("Finding a Song", ConfigHandler.config().getRandomVoiceLine())).build()).queue(message -> {
                currentSongMessage = message;
                startAudioPlayer();
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            hook.editOriginal(new MessageEditBuilder().setEmbeds(Embeds.ErrorEmbed("Cannot play song", e.getMessage())).build()).queue();
            currentSongMessage = null;
        }
    }

    public void startAudioPlayer() {
        try {
            IwaTheme theme = jukebox.getTheme(); // TODO: figure out why exception from Balancer not being caught (when there are 0 songs to choose from)

            this.audioPlayerManager.loadItem(theme.getAudioUri(), new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    scheduler.queue(track, autoplay == AutoplayOptions.ENABLED);
                    logger.info("now playing: " + theme.getAudioUri());
                    currentSongMessage.editMessage(new MessageEditBuilder().setEmbeds(Embeds.CompleteEmbed("Guess the Song", "What could it be?")).build()).queue();
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    //
                }

                @Override
                public void noMatches() {
                    logger.error("Song uri did not match: " + theme.toString());
                    currentSongMessage.editMessage(new MessageEditBuilder().setEmbeds(Embeds.ErrorEmbed("Error Loading Song", String.format("I couldn't find the song for %s", theme.getVideoUrl()))).build()).queue();
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    exception.printStackTrace();
                    logger.error("Song failed to load: " + theme.toString());
                    currentSongMessage.editMessage(new MessageEditBuilder().setEmbeds(Embeds.ErrorEmbed("Error Loading Song", String.format("I tried to play %s but it wouldn't load!", theme.getVideoUrl()))).build()).queue();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            currentSongMessage.editMessage(new MessageEditBuilder().setEmbeds(Embeds.ErrorEmbed("Cannot play song", e.getMessage())).build()).queue();
        }
    }

    public void songEnded(AudioTrackEndReason endReason, boolean playAnotherSong) {
        logger.info("ended because " + endReason);
        logger.info("this song was " + jukebox.getLastTheme());
        logger.info("anime type: " + jukebox.getAnimeBank().get(jukebox.getLastTheme().getMalId()).getType());
        IwaTheme theme = jukebox.getLastTheme();
        List<IwaUser> users = jukebox.getUsers().stream().filter(user ->
                user.getMalIds(Constants.myanimelist.status.completed.toString()).contains(theme.getMalId()) ||
                user.getMalIds(Constants.myanimelist.status.watching.toString()).contains(theme.getMalId()))
                .collect(Collectors.toList());

        currentSongMessage.editMessage(new MessageEditBuilder().setEmbeds(Embeds.IwaTheme(theme, jukebox.getAnimeBank().get(theme.getMalId()), users)).build()).queue();

        if (playAnotherSong) {
            currentSongMessage.getChannel().sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.PendingEmbed("Finding another Song", ConfigHandler.config().getRandomVoiceLine())).build()).queue(message -> {
                currentSongMessage = message;
                startAudioPlayer();
            });
        }
        else {
            currentSongMessage = null;
        }
    }

    public void listUsers(InteractionHook hook) {
        List<String> userList = jukebox.getUsers().stream().map(IwaUser::getUsername).collect(Collectors.toList());
        userList.add("\n*" + jukebox.getUsers().size() + " total*");
        hook.sendMessage(new MessageCreateBuilder().setEmbeds(Embeds.List("Users", userList)).build()).queue();
    }

    public void addUser(String username, InteractionHook hook) {
        try {
            if (jukebox.containsUser(username)) {
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
                for (IwaAnime anime : jukebox.getAnimeBank().values()) {
                    if (anime.getThemeSet().isEmpty()) {
//                        logger.warn(anime.getName() + " does not have any themes");
                        emptyAnime.add(anime.getMalId());
                    }
                }
                logger.info("total anime without songs: " + emptyAnime.size());

                jukebox.addUser(user);
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
        if (jukebox.containsUser(username)) {
            jukebox.removeUser(username.toLowerCase());
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
                if (!jukebox.getAnimeBank().containsKey(node.getNode().getId())) {
                    jukebox.getAnimeBank().put(node.getNode().getId(), new IwaAnime(node.getNode()));
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
                                        .replace(Constants.animethemes.VIDEO_TAGS, (video.getTags() == null || video.getTags().isBlank()) ? "" : ("-" + video.getTags()));
                                IwaTheme theme = new IwaTheme(video.getId(), animeTheme.getSong().getTitle(), artists, video.getAudio().getLink(), videoUrl, malId);
                                if (jukebox.getAnimeBank().containsKey(malId)) {
                                    jukebox.getThemeBank().putIfAbsent(theme.getId(), theme);
                                    jukebox.getAnimeBank().get(malId).addThemes(theme.getId());
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
