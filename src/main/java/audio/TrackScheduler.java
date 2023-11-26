package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    private final GuildSession session;

    public TrackScheduler(AudioPlayer player, GuildSession session) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();

        this.session = session;
    }

    public void queue(AudioTrack track) {
        player.playTrack(track);
//        if (!this.player.startTrack(track, true)) {
//            this.queue.offer(track);
//        }
    }

//    public void nextTrack() {
//        this.player.startTrack(this.queue.poll(), false);
//    }

    public boolean isPlayingTrack() {
        return  this.player.getPlayingTrack() != null;
    }

    public void stopTrack() {
        this.player.stopTrack();
        this.queue.clear();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        session.songEnded(endReason);
    }
}
