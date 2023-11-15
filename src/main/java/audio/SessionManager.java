package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static SessionManager instance;

    private final Map<Long, GuildSession> sessions;
    private final AudioPlayerManager audioPlayerManager;

    public SessionManager() {
        this.sessions = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
    }

    public GuildSession getSession(Guild guild) {
        return this.sessions.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            return new GuildSession(guild, this.audioPlayerManager);
        });
    }

    /*public void loadAndPlay(Guild guild, MessageChannel channel, final AnimeThemeSong song, Consumer<String> onSongFailed) {
        final GuildSession session = this.getSession(guild);

        this.audioPlayerManager.loadItemOrdered(session, song.getUrl(), new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                session.scheduler.queue(track);
                session.setCurrentSong(song);
                logger.info("now playing: " + song.getUrl());
                channel.sendMessage("Try this one!").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                //
            }

            @Override
            public void noMatches() {
                logger.info("song doesn't exist anymore: " + song.getUrl());
                try {
                    DBUtils.deleteSong(song.getAnime().getEnglishTitle(), song.getName());
                    onSongFailed.accept(song.getName());
                }
                catch (Exception e) {
                    channel.sendMessage("Something's not right... " + e.getMessage()).queue();
                }
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
                if (exception.severity == FriendlyException.Severity.COMMON) {
                    logger.info("Song is private or copyright claimed: " + song.getUrl());
                    try {
                        DBUtils.deleteSong(song.getAnime().getEnglishTitle(), song.getName());
                        onSongFailed.accept(song.getName());
                    }
                    catch (Exception e) {
                        channel.sendMessage("Something's not right... " + e.getMessage()).queue();
                    }
                }
                else {
                    channel.sendMessage("Something's not right... " + exception.getMessage()).queue();
                }
            }
        });
    }*/


    public SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}
