package audio;

import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;
import util.Constants;

import java.util.*;

public class IwaFilter {

    private Set<Constants.myanimelist.type> allowedTypes;

    private Set<Long> invalidAnime;
    private Set<Long> invalidThemes;

    public IwaFilter() {
        this.allowedTypes = new HashSet<>();
        this.allowedTypes.addAll(Arrays.asList(Constants.myanimelist.type.values()));

        this.invalidAnime = null;
        this.invalidThemes = null;
    }

    public Set<Constants.myanimelist.type> getAllowedTypes() {
        return allowedTypes;
    }

    public void setFilter(Collection<Constants.myanimelist.type> allowedTypes) {
        this.allowedTypes.clear();
        this.allowedTypes.addAll(allowedTypes);
        invalidate();
    }

    public void createFilter(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank) {
        this.invalidAnime = new HashSet<>();
        this.invalidThemes = new HashSet<>();
        animeBank.values().forEach(anime -> {
            if (!allowedTypes.contains(anime.getType())) {
                invalidAnime.add(anime.getMalId());
                invalidThemes.addAll(anime.getThemeSet());
            }
        });
    }

    public boolean isValidAnime(long id) {
        return !invalidAnime.contains(id);
    }

    public boolean isValidTheme(long id) {
        return !invalidThemes.contains(id);
    }

    public boolean isValid() {
        return this.invalidAnime != null && this.invalidThemes != null;
    }

    public void invalidate() {
        this.invalidAnime = null;
        this.invalidThemes = null;
    }
}
