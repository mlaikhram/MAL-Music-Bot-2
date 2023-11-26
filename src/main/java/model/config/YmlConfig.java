package model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Random;

public class YmlConfig {

    @JsonProperty
    private DiscordConfig discord;

    @JsonProperty
    private MALConfig mal;

    @JsonProperty
    private AnimeThemesConfig animethemes;

    @JsonProperty
    private JikanConfig jikan;

    @JsonProperty
    private ArrayList<String> voiceLines;


    public DiscordConfig getDiscord() {
        return discord;
    }

    public MALConfig getMal() {
        return mal;
    }

    public AnimeThemesConfig getAnimethemes() {
        return animethemes;
    }

    public JikanConfig getJikan() {
        return jikan;
    }

    public String getRandomVoiceLine() {
        if (voiceLines.isEmpty()) {
            return "";
        }
        return voiceLines.get(new Random().nextInt(voiceLines.size())) + " ";
    }

}
