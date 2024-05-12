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

public class IntersectBalancer extends Balancer {

    private static final Logger logger = LoggerFactory.getLogger(IntersectBalancer.class);

    private int validSongCount;

    public IntersectBalancer() {
        super();
    }

    @Override
    public Balancers.type getType() {
        return Balancers.type.INTERSECT;
    }

    @Override
    public long selectThemeId(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank, Set<Long> themeBlackList) throws Exception {
        if (!filter.isValid()) {
            filter.createFilter(users, animeBank, themeBank);
        }


        List<Long> themeList = new ArrayList<>(intersect(users.stream()
                .map(user -> user.getMalIdsByStatus(filter.getAllowedStatuses()).stream()
                .filter(id -> filter.isValidAnime(id))
                .map(id -> animeBank.get(id).getThemeSet().entrySet().stream()
                        .filter(themeIdEntry -> themeIdEntry.getValue() <= user.getCurrentEpisodeNum(id))
                        .map(Map.Entry::getKey)
                        .filter(themeId -> filter.isValidTheme(themeId)).collect(Collectors.toSet()))
                .flatMap(Set::stream)
                .toList())
                .toList()));

        if (themeList.isEmpty()) {
            throw new Exception("Could not find any songs matching your filters! Try adjusting them or adding more users.");
        }
        else {
            this.validSongCount = themeList.size();
            List<Long> blackListAppliedThemeList = themeList.stream().filter(id -> !themeBlackList.contains(id)).collect(Collectors.toList());

            if (blackListAppliedThemeList.isEmpty()) {
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

    private static <T> Set<T> intersect(Collection<? extends Collection<T>> collections) {
        if(collections.isEmpty()) return Collections.emptySet();
        Collection<T> smallest
                = Collections.min(collections, Comparator.comparingInt(Collection::size));
        return smallest.stream().distinct()
                .filter(t -> collections.stream().allMatch(c -> c==smallest || c.contains(t)))
                .collect(Collectors.toSet());
    }
}
