package model.mal;

import com.fasterxml.jackson.annotation.JsonAlias;
import util.Constants;

public class MALAnimeEntry {

    private long id;
    private String title;

    @JsonAlias("alternative_titles")
    private MALAltTitles altTitles;

    @JsonAlias("main_picture")
    private MALMainPicture picture;

    @JsonAlias("media_type")
    private Constants.myanimelist.type type;

    @JsonAlias("num_episodes")
    private int numEpisodes;

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

    public MALAltTitles getAltTitles() {
        return altTitles;
    }

    public void setAltTitles(MALAltTitles altTitles) {
        this.altTitles = altTitles;
    }

    public MALMainPicture getPicture() {
        return picture;
    }

    public void setPicture(MALMainPicture picture) {
        this.picture = picture;
    }

    public Constants.myanimelist.type getType() {
        return type;
    }

    public void setType(Constants.myanimelist.type type) {
        this.type = type;
    }

    public int getNumEpisodes() {
        return numEpisodes;
    }

    public void setNumEpisodes(int numEpisodes) {
        this.numEpisodes = numEpisodes;
    }

    @Override
    public String toString() {
        return "MALAnimeEntry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", altTitles=" + altTitles +
                ", picture=" + picture +
                ", type=" + type +
                ", numEpisodes=" + numEpisodes +
                '}';
    }
}
