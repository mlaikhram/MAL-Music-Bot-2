package model.animethemes;

import java.util.List;

public class AnimeThemesResponse {

    private List<AnimeThemesAnimeEntry> anime;
    private AnimeThemesLinks links;

    public List<AnimeThemesAnimeEntry> getAnime() {
        return anime;
    }

    public void setAnime(List<AnimeThemesAnimeEntry> anime) {
        this.anime = anime;
    }

    public AnimeThemesLinks getLinks() {
        return links;
    }

    public void setLinks(AnimeThemesLinks links) {
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
