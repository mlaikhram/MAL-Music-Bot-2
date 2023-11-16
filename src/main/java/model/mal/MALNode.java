package model.mal;

public class MALNode {

    private MALAnimeEntry node;

    public MALAnimeEntry getNode() {
        return node;
    }

    public void setNode(MALAnimeEntry node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "MALNode{" +
                "node=" + node +
                '}';
    }
}
