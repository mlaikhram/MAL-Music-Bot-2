package model.jikan;

public class JikanAnimeStats {

    private int watching;
    private int completed;

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
        return "JKAnimeStats{" +
                "watching=" + watching +
                ", completed=" + completed +
                '}';
    }
}
