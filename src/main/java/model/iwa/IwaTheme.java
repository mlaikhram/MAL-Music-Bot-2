package model.iwa;

import util.IntSegmentList;

import java.util.List;

public class IwaTheme {

    private long id;
    private String title;
    private List<String> artists;
    private String audioUri;
    private String videoUrl;
    private long malId;
    private IntSegmentList episodeList;

    public IwaTheme(long id, String title, List<String> artists, String audioUri, String videoUrl, long malId, String episodesList) {
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.audioUri = audioUri;
        this.videoUrl = videoUrl;
        this.malId = malId;
        this.episodeList = new IntSegmentList(episodesList);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getArtists() {
        return artists;
    }

    public String getAudioUri() {
        return audioUri;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public long getMalId() {
        return malId;
    }

    public IntSegmentList getEpisodeList() {
        return episodeList;
    }

    @Override
    public String toString() {
        return "IwaTheme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artists=" + artists +
                ", audioUri='" + audioUri + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", malId=" + malId +
                ", episodeList=" + episodeList +
                '}';
    }
}
