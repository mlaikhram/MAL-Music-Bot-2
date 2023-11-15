package model.mal;

public class Paging {

    private String next;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "next='" + next + '\'' +
                '}';
    }
}
