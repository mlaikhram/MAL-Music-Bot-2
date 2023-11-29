package balancer;


import audio.IwaFilter;
import model.iwa.IwaAnime;
import model.iwa.IwaTheme;
import model.iwa.IwaUser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class Balancer {

    protected IwaFilter filter;

    public Balancer() {

    }

    public abstract Balancers.type getType();
    public abstract long selectThemeId(Collection<IwaUser> users, Map<Long, IwaAnime> animeBank, Map<Long, IwaTheme> themeBank, Set<Long> themeBlackList) throws Exception;
    public abstract int getValidSongCount();

    public IwaFilter getFilter() {
        return this.filter;
    }

    public void setFilter(IwaFilter filter) {
        this.filter = filter;
    }
}
