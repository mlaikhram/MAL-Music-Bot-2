package model.animethemes;

public class ATImage {

    private long id;
    private String facet;
    private String link;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacet() {
        return facet;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "AnimeThemeImages{" +
                "id=" + id +
                ", facet='" + facet + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
