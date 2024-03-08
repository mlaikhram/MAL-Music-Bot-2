package balancer;

import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Constants;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// TODO: TEST THIS ONE
public class OverlapBalancer extends Balancer {

    private static final Logger logger = LoggerFactory.getLogger(OverlapBalancer.class);

    private int validSongCount;

    public OverlapBalancer() {
        super();
    }

    @Override
    public Balancers.type getType() {
        return Balancers.type.OVERLAP;
    }

    @Override
    public long selectThemeId(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank, Set<Long> themeBlackList) throws Exception {
        if (!filter.isValid()) {
            filter.createFilter(users, animeBank, themeBank);
        }


        List<Long> themeList = new ArrayList(overlap(users.stream().map(user -> Stream.concat(
                    user.getMalIds(Constants.myanimelist.status.completed.toString()).stream(),
                    user.getMalIds(Constants.myanimelist.status.watching.toString()).stream()
                ).filter(id -> filter.isValidAnime(id))
                .map(id -> animeBank.get(id).getThemeSet().stream()
                        .filter(themeId -> filter.isValidTheme(themeId)).collect(Collectors.toSet()))
                .flatMap(Set::stream)
                .toList())
                .toList()));

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

    private static <T> Set<T> overlap(Collection<? extends Collection<T>> collections) {
        if (collections.isEmpty()) return Collections.emptySet();

        return collections.stream().flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
