package model.iwa;

import model.mal.MALAnimeEntry;
import util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class IwaAnime {

    private long malId;
    private LinkedHashSet<String> names;
    private Constants.myanimelist.type type;
    private String pictureUrl;
    private Map<Long, Integer> themeSet;

    public IwaAnime(MALAnimeEntry animeEntry) {
        this.malId = animeEntry.getId();
        this.names = new LinkedHashSet<>();
        this.type = animeEntry.getType();
        this.pictureUrl = animeEntry.getPicture().getMedium();
        this.themeSet = new HashMap<>();

        this.names.add(animeEntry.getTitle());
        if (animeEntry.getAltTitles().getEn() != null) {
            this.names.add(animeEntry.getAltTitles().getEn());
        }
        if (animeEntry.getAltTitles().getJa() != null) {
            this.names.add(animeEntry.getAltTitles().getJa());
        }
        if (animeEntry.getAltTitles().getSynonyms() != null) {
            for (String altName : animeEntry.getAltTitles().getSynonyms()) {
                this.names.add(altName);
            }
        }
    }

    public long getMalId() {
        return malId;
    }

    public String getName() {
        return names.iterator().next();
    }

    public Constants.myanimelist.type getType() {
        return type;
    }

    public List<String> getAltNames() {
        return names.stream().skip(1).collect(Collectors.toList());
    }

    public String getUrl() {
        return Constants.myanimelist.ANIME_URL_TEMPLATE.replace(Constants.myanimelist.MAL_ID, malId + "");
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Map<Long, Integer> getThemeSet() {
        return themeSet;
    }

    public void addThemes(Map.Entry<Long, Integer> ... idFirstEpisodeEntry) {
        themeSet.putAll(Map.ofEntries(idFirstEpisodeEntry));
    }
}
