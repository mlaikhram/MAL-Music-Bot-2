package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import model.animethemes.AnimeThemesAudio;
import model.iwa.IwaUser;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class GuildSession {

    private final Map<String, IwaUser> users;
    private final LinkedHashSet<String> recentSongs;
    private AnimeThemesAudio currentSong;
    private String lastCommand;

    public final TrackScheduler scheduler;
    private final AudioSendHandler audioSendHandler;


    public GuildSession(Guild guild, AudioPlayerManager audioPlayerManager) {
        this.users = new TreeMap<>();
        this.recentSongs = new LinkedHashSet<>();

        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.scheduler = new TrackScheduler(audioPlayer);
        audioPlayer.addListener(this.scheduler);
        this.audioSendHandler = new AudioPlayerSendHandler(audioPlayer);

        guild.getAudioManager().setSendingHandler(this.audioSendHandler);
    }

    public void addUser(String username) {

    }
}
