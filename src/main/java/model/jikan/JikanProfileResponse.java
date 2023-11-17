package model.jikan;

import com.fasterxml.jackson.annotation.JsonAlias;

public class JikanProfileResponse {

    @JsonAlias("data")
    private JikanProfile profile;

    public JikanProfile getProfile() {
        return profile;
    }

    public void setProfile(JikanProfile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "JikanProfileResponse{" +
                "profile=" + profile +
                '}';
    }
}
