package model.iwa;

import model.jikan.JikanProfile;

import java.util.*;

public class IwaUser {

    private long malId;
    private String username;
    private String profileUrl;
    private String profileImageUrl;
    private Map<String, Set<Long>> malIdMap;

    public IwaUser(JikanProfile userProfile) {
        this.malId = userProfile.getMalId();
        this.username = userProfile.getUsername();
        this.profileUrl = userProfile.getUrl();
        this.profileImageUrl = userProfile.getImages().getWebp().getUrl();
        this.malIdMap = new HashMap<>();
    }

    public long getMalId() {
        return malId;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void addMalIds(String status, Long ... malIds) {
        malIdMap.computeIfAbsent(status, (s) -> new HashSet<>()).addAll(Arrays.asList(malIds));
    }

    public Set<Long> getMalIds(String status) {
        return malIdMap.getOrDefault(status, Set.of());
    }
}
