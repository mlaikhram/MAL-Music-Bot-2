package audio;

import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;

import java.util.*;

public class IwaFilter {

    private Set<Long> invalidAnime;
    private Set<Long> invalidThemes;

    public IwaFilter() {
        this.invalidAnime = null;
        this.invalidThemes = null;
    }

    public void createFilter(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank) {
        this.invalidAnime = new HashSet<>();
        this.invalidThemes = new HashSet<>();
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
