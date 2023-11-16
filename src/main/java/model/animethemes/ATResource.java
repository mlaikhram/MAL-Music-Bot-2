package model.animethemes;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ATResource {

    @JsonAlias("external_id")
    private long malId;
    private String link;

    public long getMalId() {
        return malId;
    }

    public void setMalId(long malId) {
        this.malId = malId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AnimeThemesResources{" +
                "malId=" + malId +
                ", link='" + link + '\'' +
                '}';
    }
}
