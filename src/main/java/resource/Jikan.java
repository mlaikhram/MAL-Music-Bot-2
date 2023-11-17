package resource;

import model.jikan.JikanProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import util.ConfigHandler;

import java.util.Map;

public class Jikan {

    private static final Logger logger = LoggerFactory.getLogger(Jikan.class);

    private static final String PROFILE_ENDPOINT = "/users/{username}/full";

    public static ResponseEntity<JikanProfileResponse> getProfile(String username) {
        ResponseEntity<JikanProfileResponse> response = new RestTemplate().getForEntity(ConfigHandler.config().getJikan().getUrl() + PROFILE_ENDPOINT, JikanProfileResponse.class,
                Map.of(
                        "username", username
                )
        );

        logger.info(response.getBody().toString());

        return response;
    }
}

