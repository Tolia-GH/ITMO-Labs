package se.ifmo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import se.ifmo.model.SuccessResponse;

import java.util.Map;

@Service
public class StarshipService {

    @Value("${jax-rs.service.baseurl}")
    private String jaxRSServiceBaseUrl;

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(StarshipService.class);

    @Autowired
    public StarshipService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean unloadSpaceMarineById(long starshipId, long spaceMarineId) {
        try {
            String url = String.format(
                    "%s/api/v1/starship/%d/unload/space-marine-id?space_marine_id={space_marine_id}",
                    jaxRSServiceBaseUrl,
                    starshipId
            );
            ResponseEntity<String> response = restTemplate.getForEntity(
                    url,
                    String.class,
                    Map.of("space_marine_id", spaceMarineId)
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            logger.error("API call failed", e);
            return false;
        }
    }

    public boolean unloadAllSpaceMarines(long starshipId) {
        try {
            String url = String.format("%s/api/v1/starship/%d/unload-all", jaxRSServiceBaseUrl, starshipId);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            logger.error("API call failed", e);
            return false;
        }
    }
}

