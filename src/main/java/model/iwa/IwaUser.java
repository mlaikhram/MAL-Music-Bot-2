package model.iwa;

import model.jikan.JikanProfile;
import util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class IwaUser {

    private long malId;
    private String username;
    private String profileUrl;
    private String profileImageUrl;
    private Map<Constants.myanimelist.status, Map<Long, Integer>> malIdMap;

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

    public void addMalIds(Constants.myanimelist.status status, Map.Entry<Long, Integer>... malIdNumEpisodesEntry) {
        malIdMap.computeIfAbsent(status, (s) -> new HashMap<>()).putAll(Map.ofEntries(malIdNumEpisodesEntry));
    }

    public Set<Long> getMalIdsByStatus(Collection<Constants.myanimelist.status> statuses) {
        return statuses.stream().map(status -> malIdMap.containsKey(status) ? malIdMap.get(status).keySet() : new HashSet<Long>())
                .flatMap(keyset -> keyset.stream())
                .collect(Collectors.toSet());
    }

    public int getCurrentEpisodeNum(Long malId) {
        for (Map<Long, Integer> malEntry : malIdMap.values()) {
            if (malEntry.containsKey(malId)) {
                return malEntry.get(malId);
            }
        }
        return 0;
    }
}
