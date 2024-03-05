package balancer;

public class Balancers {

    public enum type {
        UNIFORM,
        BALANCED,
        INTERSECT,
        OVERLAP,
        WEIGHTED
    }

    public static Balancer create(type type) {
        switch (type) {
            case BALANCED:
                return new BalancedBalancer();
            case INTERSECT:
                return new IntersectBalancer();
            default:
                return new UniformBalancer();
        }
    }
}
