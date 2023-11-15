package model.animethemes;

public class AnimeThemesAudio {

    private long id;
    private String link;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AnimeThemesAudio{" +
                "id=" + id +
                ", link='" + link + '\'' +
                '}';
    }
}
