package model.animethemes;

import java.util.List;

public class ATAnimeThemeEntry {

    private long id;
    private boolean spoiler;
    private List<ATVideo> videos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }

    public List<ATVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<ATVideo> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "AnimeThemesEntry{" +
                "id=" + id +
                ", spoiler=" + spoiler +
                ", videos=" + videos +
                '}';
    }
}
