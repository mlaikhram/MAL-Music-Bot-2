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

public class BalancedBalancer extends Balancer {

    private static final Logger logger = LoggerFactory.getLogger(BalancedBalancer.class);

    private int validSongCount;

    public BalancedBalancer() {
        super();
    }

    @Override
    public Balancers.type getType() {
        return Balancers.type.BALANCED;
    }

    @Override
    public long selectThemeId(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank, Set<Long> themeBlackList) throws Exception {
        if (!filter.isValid()) {
            filter.createFilter(users, animeBank, themeBank);
        }


        List<Long> masterThemeList = users.stream()
                .map(user -> user.getMalIdsByStatus(filter.getAllowedStatuses()))
                .flatMap(Set::stream)
                .distinct()
                .filter(id -> filter.isValidAnime(id))
                .flatMap(id -> animeBank.get(id).getThemeSet().entrySet().stream()
                        .filter(themeIdEntry -> users.stream()
                                .map(user -> user.getCurrentEpisodeNum(id))
                                .anyMatch(episodeNum -> themeIdEntry.getValue() <= episodeNum)))
                .map(Map.Entry::getKey)
                .filter(id -> filter.isValidTheme(id))
                .toList();

        List<IwaUser> userList = users.stream().filter(user -> filter.isValidUser(user.getMalId())).toList();

        if (userList.isEmpty()) {
            throw new Exception("Could not find any songs matching your filters! Try adjusting them or adding more users.");
        }
        else {
            Random r = new Random();
            IwaUser selectedUser = userList.get(r.nextInt(userList.size()));

            List<Long> themeList = selectedUser.getMalIdsByStatus(filter.getAllowedStatuses()).stream()
                    .filter(id -> filter.isValidAnime(id))
                    .flatMap(id -> animeBank.get(id).getThemeSet().entrySet().stream()
                            .filter(themeIdEntry -> themeIdEntry.getValue() <= selectedUser.getCurrentEpisodeNum(id)))
                    .map(Map.Entry::getKey)
                    .filter(id -> filter.isValidTheme(id))
                    .collect(Collectors.toList());

            this.validSongCount = masterThemeList.size();
            List<Long> blackListAppliedThemeList = themeList.stream().filter(id -> !themeBlackList.contains(id)).collect(Collectors.toList());

            if (blackListAppliedThemeList.isEmpty()) {
                logger.warn("All valid songs are blacklisted, clearing blacklist and returning a song.");
                themeBlackList.clear();
                blackListAppliedThemeList = themeList;
            }

            return blackListAppliedThemeList.get(r.nextInt(blackListAppliedThemeList.size()));
        }
    }

    @Override
    public int getValidSongCount() {
        return this.validSongCount;
    }
}
