package model.jikan;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JikanAnimeStats {

    private int watching;
    private int completed;
    @JsonAlias("on_hold")
    private int onHold;
    private int dropped;

    public int getWatching() {
        return watching;
    }

    public void setWatching(int watching) {
        this.watching = watching;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "JikanAnimeStats{" +
                "watching=" + watching +
                ", completed=" + completed +
                ", onHold=" + onHold +
                ", dropped=" + dropped +
                '}';
    }

    public int getOnHold() {
        return onHold;
    }

    public void setOnHold(int onHold) {
        this.onHold = onHold;
    }

    public int getDropped() {
        return dropped;
    }

    public void setDropped(int dropped) {
        this.dropped = dropped;
    }

}
