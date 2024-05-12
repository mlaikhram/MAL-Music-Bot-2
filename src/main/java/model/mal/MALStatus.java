package model.mal;

import com.fasterxml.jackson.annotation.JsonAlias;

public class MALStatus {

    @JsonAlias("num_episodes_watched")
    private int lastWatchedEpisode;

    @JsonAlias("is_rewatching")
    private boolean isRewatching;

    public int getLastWatchedEpisode() {
        return lastWatchedEpisode;
    }

    public void setLastWatchedEpisode(int lastWatchedEpisode) {
        this.lastWatchedEpisode = lastWatchedEpisode;
    }

    public boolean isRewatching() {
        return isRewatching;
    }

    public void setRewatching(boolean rewatching) {
        isRewatching = rewatching;
    }

    @Override
    public String toString() {
        return "MALStatus{" +
                "lastWatchedEpisode=" + lastWatchedEpisode +
                ", isRewatching=" + isRewatching +
                '}';
    }
}
