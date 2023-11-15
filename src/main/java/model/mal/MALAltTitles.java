package model.mal;

import java.util.List;

public class MALAltTitles {

    private List<String> synonyms;
    private String en;
    private String ja;

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    @Override
    public String toString() {
        return "MALAltTitles{" +
                "synonyms=" + synonyms +
                ", en='" + en + '\'' +
                ", ja='" + ja + '\'' +
                '}';
    }
}
