package resource;

import model.animethemes.AnimeThemesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.ConfigHandler;

import java.util.List;
import java.util.Map;

public class AnimeThemes {

    private static final Logger logger = LoggerFactory.getLogger(MAL.class);

    private static final String LIST_ENDPOINT = "/anime?page[size]={pageLimit}&page[number]={pageNum}&filter[has]=resources&filter[site]=MyAnimeList&filter[external_id]={malIds}&include=animethemes.animethemeentries.videos.audio,images,resources";

    public static ResponseEntity<AnimeThemesResponse> getList(List<String> malIds, int pageNum) {

        ResponseEntity<AnimeThemesResponse> response = new RestTemplate().getForEntity(ConfigHandler.config().getAnimethemes().getUrl() + LIST_ENDPOINT, AnimeThemesResponse.class,
                Map.of(
                        "pageLimit", ConfigHandler.config().getAnimethemes().getPageLimit(),
                        "pageNum", pageNum,
                        "malIds", String.join(",", malIds)
                )
        );

        logger.info(response.toString());

        return response;
    }

    public static ResponseEntity<AnimeThemesResponse> getList(List<String> malIds) {
        return getList(malIds, 1);
    }
}
