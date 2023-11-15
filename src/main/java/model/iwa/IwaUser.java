package model.iwa;

import java.util.*;

public class IwaUser {

    private String name;
    private Map<String, Set<Long>> malIdMap;

    public IwaUser(String name) {
        this.name = name;
        this.malIdMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void AddMalIds(String status, Long ... malIds) {
        malIdMap.computeIfAbsent(status, (s) -> new HashSet<>()).addAll(Arrays.asList(malIds));
    }

    public Map<String, Set<Long>> getMalIds() {
        return malIdMap;
    }
}
