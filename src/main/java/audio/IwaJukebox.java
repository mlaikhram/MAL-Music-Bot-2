package audio;

import balancer.Balancer;
import balancer.UniformBalancer;
import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;

import java.util.*;

public class IwaJukebox {

    private static final float RECENT_SONG_RATIO = 0.5f;

    private final Map<String, IwaUser> users;
    private final Map<Long, IwaAnime> animeBank;
    private final Map<Long, IwaTheme> themeBank;
    private final LinkedHashSet<Long> recentSongs;
    private IwaTheme currentSong;

    private IwaFilter filter;
    private Balancer balancer;


    public IwaJukebox() {
        this.users = new TreeMap<>();
        this.animeBank = new HashMap<>();
        this.themeBank = new HashMap<>();
        this.recentSongs = new LinkedHashSet<>();
        this.filter = new IwaFilter();
        this.balancer = new UniformBalancer();
    }

    public IwaTheme getTheme() throws Exception {
        balancer.setFilter(filter);
        long themeId = balancer.selectThemeId(users.values(), animeBank, themeBank, recentSongs);
        recentSongs.add(themeId);
        while ((float) recentSongs.size() / (float) balancer.getValidSongCount() > RECENT_SONG_RATIO) {
            recentSongs.remove(recentSongs.iterator().next());
        }
        currentSong = themeBank.get(themeId);
        return currentSong;
    }

    public IwaTheme getLastTheme() {
        return currentSong;
    }

    public Collection<IwaUser> getUsers() {
        return users.values();
    }

    public boolean containsUser(String username) {
        return users.containsKey(username.toLowerCase());
    }

    public void addUser(IwaUser user) {
        users.put(user.getUsername().toLowerCase(), user);
        filter.invalidate();
    }

    public void removeUser(String username) {
        users.remove(username.toLowerCase());
        filter.invalidate();
    }

    public Map<Long, IwaAnime> getAnimeBank() {
        return animeBank;
    }

    public Map<Long, IwaTheme> getThemeBank() {
        return themeBank;
    }

    public IwaFilter getFilter() {
        return filter;
    }

    public Balancer getBalancer() {
        return balancer;
    }

    public void setBalancer(Balancer balancer) {
        this.balancer = balancer;
    }
}
