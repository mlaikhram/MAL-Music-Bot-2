package model.animethemes;

public class AnimeThemesVideo {

    private long id;
    private String link;
    private String tags;
    private AnimeThemesAudio audio;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public AnimeThemesAudio getAudio() {
        return audio;
    }

    public void setAudio(AnimeThemesAudio audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "AnimeThemesVideo{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", tags='" + tags + '\'' +
                ", audio=" + audio +
                '}';
    }
}
