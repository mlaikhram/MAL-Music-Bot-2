package model.jikan;

public class JikanStatistics {

    private JikanAnimeStats anime;

    public JikanAnimeStats getAnime() {
        return anime;
    }

    public void setAnime(JikanAnimeStats anime) {
        this.anime = anime;
    }

    @Override
    public String toString() {
        return "JikanStatistics{" +
                "anime=" + anime +
                '}';
    }
}
