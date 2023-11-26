package util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Constants {

    public static class commands {
        public static final String ADD_USER = "adduser";
        public static final String REMOVE_USER = "removeuser";
        public static final String LIST_USERS = "listusers";
        public static final String PLAY = "play";
        public static final String STOP = "stop";
        public static final String SETTINGS = "settings";
    }

    public static class componentids {
        public static final String ANIME_TYPES_DROPDOWN = "settings-anime-types";
    }

    public static class options {
        public static final String USERNAME = "username";
    }

    public static class myanimelist {
        public static final String USERNAME = "{username}";
        public static final String PROFILE_URL_TEMPLATE = "https://myanimelist.net/profile/" + USERNAME;
        public static final String MAL_ID = "{malId}";
        public static final String ANIME_URL_TEMPLATE = "https://myanimelist.net/anime/" + MAL_ID;

        public enum status {
            completed,
            watching,
            on_hold,
            dropped,
            plan_to_watch
        }

        public enum type {
            tv,
            movie,
            special,
            ova,
            ona,
            music,
            unknown
        }
    }

    public static class animethemes {
        public static final String ANIME_SLUG = "{animeSlug}";
        public static final String ANIMETHEME_SLUG = "{animethemeSlug}";
        public static final String VIDEO_TAGS = "{videoTags}";
        public static final String VIDEO_URL_TEMPLATE = "https://animethemes.moe/anime/" + ANIME_SLUG + "/" + ANIMETHEME_SLUG + VIDEO_TAGS;
    }

    public enum Balancers {
        UNIFORM,
        BALANCED,
        OVERLAP,
        INTERSECT,
        WEIGHTED
    }
}
