package model.mal;

public class MALMainPicture {

    private String medium;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "MALMainPicture{" +
                "medium='" + medium + '\'' +
                '}';
    }
}
