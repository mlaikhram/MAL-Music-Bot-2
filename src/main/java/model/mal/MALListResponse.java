package model.mal;

import java.util.List;

public class MALListResponse {

    private List<MALNode> data;
    private Paging paging;

    public List<MALNode> getData() {
        return data;
    }

    public void setData(List<MALNode> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "AnimeListResponse{" +
                "data=" + data +
                ", paging=" + paging +
                '}';
    }
}
