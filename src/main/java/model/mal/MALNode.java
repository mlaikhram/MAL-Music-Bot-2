package model.mal;

public class MALNode {

    private AnimeEntry node;

    public AnimeEntry getNode() {
        return node;
    }

    public void setNode(AnimeEntry node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "MALNode{" +
                "node=" + node +
                '}';
    }
}
