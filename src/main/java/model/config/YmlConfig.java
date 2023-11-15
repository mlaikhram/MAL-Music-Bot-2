package model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YmlConfig {

    @JsonProperty
    private DiscordConfig discord;

    @JsonProperty
    private MALConfig mal;

    @JsonProperty
    private AnimeThemesConfig animethemes;

    public DiscordConfig getDiscord() {
        return discord;
    }

    public MALConfig getMal() {
        return mal;
    }

    public AnimeThemesConfig getAnimethemes() {
        return animethemes;
    }
}
