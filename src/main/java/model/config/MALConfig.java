package model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MALConfig {

    @JsonProperty
    private String url;

    @JsonProperty
    private String clientId;

    @JsonProperty
    private int pageLimit;

    public String getUrl() {
        return url;
    }

    public String getClientId() {
        return clientId;
    }

    public int getPageLimit() {
        return pageLimit;
    }
}
