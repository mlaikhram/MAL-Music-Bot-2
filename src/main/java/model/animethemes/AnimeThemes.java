package model.animethemes;

import java.util.List;

public class AnimeThemes {

    private long id;
    private List<AnimeThemesEntry> animethemeentries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                ", animethemeentries=" + animethemeentries +
                '}';
    }
}
