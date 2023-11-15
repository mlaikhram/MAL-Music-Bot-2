package model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscordConfig {

    @JsonProperty
    private String token;

    public String getToken() {
        return token;
    }
}
