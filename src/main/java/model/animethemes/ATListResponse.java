package model.animethemes;

import java.util.List;

public class ATListResponse {

    private List<ATAnime> anime;
    private ATLinks links;

    public List<ATAnime> getAnime() {
        return anime;
    }

    public void setAnime(List<ATAnime> anime) {
        this.anime = anime;
    }

    public ATLinks getLinks() {
        return links;
    }

    public void setLinks(ATLinks links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "AnimeThemesResponse{" +
                "anime=" + anime +
                ", links=" + links +
                '}';
    }
}
