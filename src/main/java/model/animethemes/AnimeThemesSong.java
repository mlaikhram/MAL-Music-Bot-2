package model.animethemes;

import java.util.List;

public class AnimeThemesSong {

    private long id;
    private String title;
    private List<AnimeThemesSongArtist> artists;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AnimeThemesSongArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<AnimeThemesSongArtist> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return "AnimeThemesSong{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artists=" + artists +
                '}';
    }
}
