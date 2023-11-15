package model.animethemes;

import java.util.List;

public class AnimeThemes {

    private long id;
    private String slug;
    private AnimeThemesSong song;
    private List<AnimeThemesEntry> animethemeentries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public AnimeThemesSong getSong() {
        return song;
    }

    public void setSong(AnimeThemesSong song) {
        this.song = song;
    }

    public List<AnimeThemesEntry> getAnimethemeentries() {
        return animethemeentries;
    }

    public void setAnimethemeentries(List<AnimeThemesEntry> animethemeentries) {
        this.animethemeentries = animethemeentries;
    }

    @Override
    public String toString() {
        return "AnimeThemes{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", song=" + song +
                ", animethemeentries=" + animethemeentries +
                '}';
    }
}
