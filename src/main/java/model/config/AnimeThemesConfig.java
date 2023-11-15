package model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnimeThemesConfig {

    @JsonProperty
    private String url;

    @JsonProperty
    private int pageLimit;

    public String getUrl() {
        return url;
    }

    public int getPageLimit() {
        return pageLimit;
    }
}
