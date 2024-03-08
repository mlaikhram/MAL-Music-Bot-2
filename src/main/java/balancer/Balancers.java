package balancer;

import net.dv8tion.jda.api.entities.emoji.Emoji;

public class Balancers {

    public enum type {
        UNIFORM("Each song has an equal chance of getting selected"),
        BALANCED("Each user has an equal chance to get a song"),
        INTERSECT("Songs on all user lists will be selected from equally"),
        OVERLAP("Songs on at least two user lists will be selected from equally"),
        WEIGHTED("Songs on more user lists will be more likely to be selected");

        private String description;

        type(String description) {
            this.description = description;
        }

        public String description() {
            return this.description;
        }
    }

    public static Balancer create(type type) {
        switch (type) {
            case BALANCED:
                return new BalancedBalancer();
            case INTERSECT:
                return new IntersectBalancer();
            case OVERLAP:
                return new OverlapBalancer();
            case WEIGHTED:
                return new WeightedBalancer();
            default:
                return new UniformBalancer();
        }
    }
}
