package resource;

import model.mal.MALListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.ConfigHandler;

import java.util.Map;

public class MAL {

    private static final Logger logger = LoggerFactory.getLogger(MAL.class);

    // MAL API currently does not support fetching public user profiles for some reason -_-
    private static final String USER_ENDPOINT = "/users/{user}?fields=id,name,picture,anime_statistics(num_items_watching,num_items_completed)&nsfw=true";
    private static final String LIST_ENDPOINT = "/users/{user}/animelist?status={status}&limit={pageLimit}&fields=node(id,title,main_picture,alternative_titles,media_type,opening_themes,ending_themes),list_status(num_episodes_watched,is_rewatching)&nsfw=true&offset={offset}";

    public static ResponseEntity<MALListResponse> getList(String user, String status, int offset) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MAL-CLIENT-ID", ConfigHandler.config().getMal().getClientId());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<MALListResponse> response = restTemplate.exchange(ConfigHandler.config().getMal().getUrl() + LIST_ENDPOINT, HttpMethod.GET, httpEntity, MALListResponse.class,
            Map.of(
                "user", user,
                "status", status,
                "pageLimit", ConfigHandler.config().getMal().getPageLimit(),
                "offset", offset
            )
        );

        logger.info(response.getBody().toString());

        return response;
    }

    public static ResponseEntity<MALListResponse> getList(String user, String status) {
        return getList(user, status, 0);
    }
}
