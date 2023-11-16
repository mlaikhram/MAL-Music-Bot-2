package model.iwa;

import model.mal.MALAnimeEntry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class IwaAnime {

    private long malId;
    private LinkedHashSet<String> names;
    private String pictureUrl;
    private Set<Long> themeSet;

    public IwaAnime(MALAnimeEntry animeEntry) {
        this.malId = animeEntry.getId();
        this.names = new LinkedHashSet<>();
        this.pictureUrl = animeEntry.getPicture().getMedium();
        this.themeSet = new HashSet<>();

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

    public Set<Long> getThemeSet() {
        return themeSet;
    }

    public void addThemes(Long ... ids) {
        themeSet.addAll(Arrays.asList(ids));
    }
}
