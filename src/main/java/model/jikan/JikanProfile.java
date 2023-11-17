package model.jikan;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JikanProfile {

    @JsonAlias("mal_id")
    private long malId;
    private String username;
    private String url;
    private JikanImages images;
    private JikanStatistics statistics;

    public long getMalId() {
        return malId;
    }

    public void setMalId(long malId) {
        this.malId = malId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JikanImages getImages() {
        return images;
    }

    public void setImages(JikanImages images) {
        this.images = images;
    }

    public JikanStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(JikanStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String toString() {
        return "JikanProfileResponse{" +
                "malId=" + malId +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", images=" + images +
                ", statistics=" + statistics +
                '}';
    }
}
