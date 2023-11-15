package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import model.animethemes.*;
import model.iwa.IwaAnime;
import model.iwa.IwaUser;
import model.mal.AnimeListResponse;
import model.mal.MALNode;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.AnimeThemes;
import resource.MAL;
import util.ConfigHandler;

import java.util.*;

public class GuildSession {

    private static final Logger logger = LoggerFactory.getLogger(GuildSession.class);


    private final Map<String, IwaUser> users;
    private final Map<Long, IwaAnime> animeBank;
    private final LinkedHashSet<String> recentSongs;
    private AnimeThemesAudio currentSong;
    private String lastCommand;

    public final TrackScheduler scheduler;
    private final AudioSendHandler audioSendHandler;


    public GuildSession(Guild guild, AudioPlayerManager audioPlayerManager) {
        this.users = new TreeMap<>();
        this.animeBank = new HashMap<>();
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

                // TODO: start processing newAnime with AnimeThemes.getList + update message with final counts and processing new anime message (x/y)...

                users.put(user.getName(), user);
            }
        }
        catch (Exception e) {
            // TODO: return error message in chat + log
            logger.error(e.getMessage());
        }
    }

    // returns a list of malIds that are not yet populated in the animeBank with theme songs
    private List<Long> populateUser(IwaUser user, String status) {
        int offset = 0;
        List<Long> newAnime = new ArrayList<>();
        AnimeListResponse paginatedResponse;
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
        int page = 1;
        AnimeThemesResponse paginatedResponse;
        do {
            paginatedResponse = AnimeThemes.getList(newAnime.subList((page - 1) * ConfigHandler.config().getAnimethemes().getPageLimit(), page * ConfigHandler.config().getAnimethemes().getPageLimit()), page).getBody();
            for (AnimeThemesAnimeEntry anime : paginatedResponse.getAnime()) {
                for (model.animethemes.AnimeThemes animeTheme : anime.getAnimethemes()) {
                    for (AnimeThemesEntry animeThemesEntry : animeTheme.getAnimethemeentries()) {
                        for (AnimeThemesVideo animeThemesVideo : animeThemesEntry.getVideos()) {
                            // TODO: create theme and place in appropriate anime
                        }
                    }
                }
            }
            // TODO: update message with new count of processed anime entries (themes loaded)
            ++page;
        } while (paginatedResponse.getLinks().getNext() != null);
    }
}
