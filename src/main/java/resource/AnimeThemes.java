package resource;

import model.animethemes.ATListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.ConfigHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimeThemes {

    private static final Logger logger = LoggerFactory.getLogger(MAL.class);

    private static final String LIST_ENDPOINT = "/anime?page[size]={pageLimit}&page[number]={pageNum}&filter[has]=resources&filter[site]=MyAnimeList&filter[external_id]={malIds}&include=animethemes.song,animethemes.song.artists,animethemes.animethemeentries.videos.audio,images,resources";

    public static ResponseEntity<ATListResponse> getList(List<Long> malIds, int pageNum) {
        ResponseEntity<ATListResponse> response = new RestTemplate().getForEntity(ConfigHandler.config().getAnimethemes().getUrl() + LIST_ENDPOINT, ATListResponse.class,
                Map.of(
                        "pageLimit", ConfigHandler.config().getAnimethemes().getPageLimit(),
                        "pageNum", pageNum,
                        "malIds", String.join(",", malIds.stream().map(Object::toString).collect(Collectors.toUnmodifiableList()))
                )
        );

        logger.info(response.getBody().toString());

        return response;
    }

    public static ResponseEntity<ATListResponse> getList(List<Long> malIds) {
        return getList(malIds, 1);
    }
}
