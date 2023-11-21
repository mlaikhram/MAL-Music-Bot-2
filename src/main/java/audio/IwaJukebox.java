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

    private Balancer balancer;
    // TODO: the filter will create a filteredUsers and filteredAnimeBank:
    // TODO: filteredUsers will make sure user's anime list excludes all invalid shows and shows without valid themes
    // TODO: filteredAnimeBank will make sure each anime excludes invalid themes and /shows without valid themes/?
    // TODO: balancer will run on this set (and the recentSongs set) and then produce an id
    // TODO: if the produced id is in the recentSongs list, iwa will complain about the balancer, and a song will not be played

    public IwaJukebox() {
        this.users = new TreeMap<>();
        this.animeBank = new HashMap<>();
        this.themeBank = new HashMap<>();
        this.recentSongs = new LinkedHashSet<>();
        this.balancer = new UniformBalancer();
    }

    public IwaTheme getTheme() throws Exception {
        // TODO: properly set filter
        balancer.setFilter(new IwaFilter());
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
    }

    public void removeUser(String username) {
        users.remove(username.toLowerCase());
    }

    public Map<Long, IwaAnime> getAnimeBank() {
        return animeBank;
    }

    public Map<Long, IwaTheme> getThemeBank() {
        return themeBank;
    }
}
