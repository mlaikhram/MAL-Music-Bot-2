package model.jikan;

public class JikanImages {

    private JikanImage webp;

    public JikanImage getWebp() {
        return webp;
    }

    public void setWebp(JikanImage webp) {
        this.webp = webp;
    }

    @Override
    public String toString() {
        return "JikanImages{" +
                "webp=" + webp +
                '}';
    }
}
