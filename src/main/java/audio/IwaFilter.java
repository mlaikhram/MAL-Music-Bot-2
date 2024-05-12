package audio;

import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;
import util.Constants;

import java.util.*;

public class IwaFilter {

    private Set<Constants.myanimelist.status> allowedStatuses;
    private Set<Constants.myanimelist.type> allowedTypes;

    private Set<Long> invalidUsers;
    private Set<Long> invalidAnime;
    private Set<Long> invalidThemes;

    public IwaFilter() {
        this.allowedStatuses = new HashSet<>();
        // TODO: update to allow players to set value
        this.allowedStatuses.addAll(Arrays.asList(
                Constants.myanimelist.status.completed,
                Constants.myanimelist.status.watching,
                Constants.myanimelist.status.on_hold,
                Constants.myanimelist.status.dropped
        ));
        this.allowedTypes = new HashSet<>();
        this.allowedTypes.addAll(Arrays.asList(Constants.myanimelist.type.values()));

        invalidate();
    }

    public Set<Constants.myanimelist.status> getAllowedStatuses() {
        return allowedStatuses;
    }

    public void setStatusFilter(Collection<Constants.myanimelist.status> allowedStatuses) {
        this.allowedStatuses.clear();
        this.allowedStatuses.addAll(allowedStatuses);
        invalidate();
    }

    public Set<Constants.myanimelist.type> getAllowedTypes() {
        return allowedTypes;
    }

    public void setTypeFilter(Collection<Constants.myanimelist.type> allowedTypes) {
        this.allowedTypes.clear();
        this.allowedTypes.addAll(allowedTypes);
        invalidate();
    }

    public void createFilter(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank) {
        this.invalidUsers = new HashSet<>();
        this.invalidAnime = new HashSet<>();
        this.invalidThemes = new HashSet<>();
        animeBank.values().forEach(anime -> {
            if (!allowedTypes.contains(anime.getType())) {
                invalidAnime.add(anime.getMalId());
                invalidThemes.addAll(anime.getThemeSet().keySet());
            }
        });
        users.forEach(user -> {
            if (user.getMalIdsByStatus(allowedStatuses).stream().noneMatch(this::isValidAnime)) {
                invalidUsers.add(user.getMalId());
            }
        });
    }

    public boolean isValidUser(long id) {
        return !invalidUsers.contains(id);
    }

    public boolean isValidAnime(long id) {
        return !invalidAnime.contains(id);
    }

    public boolean isValidTheme(long id) {
        return !invalidThemes.contains(id);
    }

    public boolean isValid() {
        return this.invalidUsers != null && this.invalidAnime != null && this.invalidThemes != null;
    }

    public void invalidate() {
        this.invalidUsers = null;
        this.invalidAnime = null;
        this.invalidThemes = null;
    }
}
