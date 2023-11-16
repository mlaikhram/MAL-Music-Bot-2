package model.animethemes;

import java.util.List;

public class ATSong {

    private long id;
    private String title;
    private List<ATArtist> artists;

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

    public List<ATArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<ATArtist> artists) {
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
