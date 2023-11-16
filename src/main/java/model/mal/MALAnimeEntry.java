package model.mal;

import com.fasterxml.jackson.annotation.JsonAlias;

public class MALAnimeEntry {

    private long id;
    private String title;

    @JsonAlias("alternative_titles")
    private MALAltTitles altTitles;

    @JsonAlias("main_picture")
    private MALMainPicture picture;

    @JsonAlias("media_type")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AnimeEntry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", altTitles=" + altTitles +
                ", picture=" + picture +
                ", type='" + type + '\'' +
                '}';
    }
}
