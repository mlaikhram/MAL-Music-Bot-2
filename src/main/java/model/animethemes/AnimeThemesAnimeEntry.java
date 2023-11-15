package model.animethemes;

import java.util.List;

public class AnimeThemesAnimeEntry {

    private long id;
    private String name;
    private List<AnimeThemes> animethemes;
    private List<AnimeThemesImages> images;
    private List<AnimeThemesResources> resources;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnimeThemes> getAnimethemes() {
        return animethemes;
    }

    public void setAnimethemes(List<AnimeThemes> animethemes) {
        this.animethemes = animethemes;
    }

    public List<AnimeThemesImages> getImages() {
        return images;
    }

    public void setImages(List<AnimeThemesImages> images) {
        this.images = images;
    }

    public List<AnimeThemesResources> getResources() {
        return resources;
    }

    public void setResources(List<AnimeThemesResources> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "AnimeThemesAnimeEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", animethemes=" + animethemes +
                ", images=" + images +
                ", resources=" + resources +
                '}';
    }
}
