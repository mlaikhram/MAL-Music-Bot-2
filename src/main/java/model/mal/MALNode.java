package model.mal;

import com.fasterxml.jackson.annotation.JsonAlias;

public class MALNode {

    private MALAnimeEntry node;
    @JsonAlias("list_status")
    private MALStatus status;

    public MALAnimeEntry getNode() {
        return node;
    }

    public void setNode(MALAnimeEntry node) {
        this.node = node;
    }

    public MALStatus getStatus() {
        return status;
    }

    public void setStatus(MALStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MALNode{" +
                "node=" + node +
                ", status=" + status +
                '}';
    }
}
