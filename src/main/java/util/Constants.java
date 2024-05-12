package util;

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
        public static final String ANIME_STATUSES_DROPDOWN = "settings-anime-statuses";
        public static final String ANIME_TYPES_DROPDOWN = "settings-anime-types";
        public static final String ANIME_BALANCER_DROPDOWN = "settings-anime-balancer";
        public static final String AUTOPLAY_SELECTOR = "settings-autoplay";
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
            completed("Completed"),
            watching("Watching"),
            on_hold("On-Hold"),
            dropped("Dropped"),
            plan_to_watch("Plan to Watch");

            private String label;

            status(String label) {
                this.label = label;
            }

            public String label() {
                return label;
            }
        }

        public enum type {
            tv("TV"),
            movie("Movie"),
            special("Special"),
            ova("OVA"),
            ona("ONA"),
            tv_special("TV Special"),
            music("Music"),
            pv("PV"),
            unknown("Unknown");

            private String label;

            type(String label) {
                this.label = label;
            }

            public String label() {
                return label;
            }
        }
    }

    public static class animethemes {
        public static final String ANIME_SLUG = "{animeSlug}";
        public static final String ANIMETHEME_SLUG = "{animethemeSlug}";
        public static final String VIDEO_TAGS = "{videoTags}";
        public static final String VIDEO_URL_TEMPLATE = "https://animethemes.moe/anime/" + ANIME_SLUG + "/" + ANIMETHEME_SLUG + VIDEO_TAGS;
    }

    public enum AutoplayOptions {
        DISABLED,
        ENABLED
    }
}
