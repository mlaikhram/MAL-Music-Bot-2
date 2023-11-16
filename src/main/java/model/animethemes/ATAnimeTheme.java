package model.animethemes;

import java.util.List;

public class ATAnimeTheme {

    private long id;
    private String slug;
    private ATSong song;
    private List<ATAnimeThemeEntry> animethemeentries;

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

    public ATSong getSong() {
        return song;
    }

    public void setSong(ATSong song) {
        this.song = song;
    }

    public List<ATAnimeThemeEntry> getAnimethemeentries() {
        return animethemeentries;
    }

    public void setAnimethemeentries(List<ATAnimeThemeEntry> animethemeentries) {
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
