package balancer;

import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Constants;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniformBalancer extends Balancer {

    private static final Logger logger = LoggerFactory.getLogger(UniformBalancer.class);

    private int validSongCount;

    public UniformBalancer() {
        super();
    }

    @Override
    public long selectThemeId(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank, Set<Long> themeBlackList) throws Exception {
        if (!filter.isValid()) {
            filter.createFilter(users, animeBank, themeBank);
        }


        List<Long> themeList = Stream.concat(
                users.stream().map(user -> user.getMalIds(Constants.myanimelist.status.completed.toString())).flatMap(Set::stream),
                users.stream().map(user -> user.getMalIds(Constants.myanimelist.status.completed.toString())).flatMap(Set::stream)
        )
                .filter(id -> filter.isValidAnime(id))
                .flatMap(id -> animeBank.get(id).getThemeSet().stream())
                .filter(id -> filter.isValidTheme(id))
                .collect(Collectors.toList());


        if (themeList.size() == 0) {
            throw new Exception("Could not find any songs matching your filters! Try adjusting them or adding more users.");
        }
        else {
            this.validSongCount = themeList.size();
            List<Long> blackListAppliedThemeList = themeList.stream().filter(id -> !themeBlackList.contains(id)).collect(Collectors.toList());

            if (blackListAppliedThemeList.size() == 0) {
                logger.warn("All valid songs are blacklisted, clearing blacklist and returning a song.");
                themeBlackList.clear();
                blackListAppliedThemeList = themeList;
            }

            Random r = new Random();
            return blackListAppliedThemeList.get(r.nextInt(blackListAppliedThemeList.size()));
        }
    }

    @Override
    public int getValidSongCount() {
        return this.validSongCount;
    }
}
