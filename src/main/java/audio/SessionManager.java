package audio;

import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static SessionManager instance;

    private final Map<Long, GuildSession> sessions;

    private SessionManager() {
        this.sessions = new HashMap<>();
    }

    public GuildSession getSession(Guild guild) {
        return this.sessions.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            return new GuildSession(guild);
        });
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}
