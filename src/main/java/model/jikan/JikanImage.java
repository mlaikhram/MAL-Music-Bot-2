package model.jikan;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JikanImage {

    @JsonAlias("image_url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JikanImage{" +
                "url='" + url + '\'' +
                '}';
    }
}
