package model.animethemes;

import java.util.List;

public class ATAnime {

    private long id;
    private String name;
    private String slug;
    private List<ATAnimeTheme> animethemes;
    private List<ATImage> images;
    private List<ATResource> resources;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<ATAnimeTheme> getAnimethemes() {
        return animethemes;
    }

    public void setAnimethemes(List<ATAnimeTheme> animethemes) {
        this.animethemes = animethemes;
    }

    public List<ATImage> getImages() {
        return images;
    }

    public void setImages(List<ATImage> images) {
        this.images = images;
    }

    public List<ATResource> getResources() {
        return resources;
    }

    public void setResources(List<ATResource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "ATAnime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", animethemes=" + animethemes +
                ", images=" + images +
                ", resources=" + resources +
                '}';
    }
}
