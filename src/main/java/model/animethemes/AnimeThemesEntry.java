package model.animethemes;

import java.util.List;

public class AnimeThemesEntry {

    private long id;
    private boolean spoiler;
    private List<AnimeThemesVideo> videos;

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

    public List<AnimeThemesVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<AnimeThemesVideo> videos) {
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
