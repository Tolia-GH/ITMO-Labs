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
            // URL format: http://haproxy:8080/Service1/v1/starship/{id}/unload/space-marine-id?spaceMarineId={smId}
            // But wait, the Service1 API for unload is actually defined where?
            // The user task said "Service1... split... Service2".
            // Service1 has SpaceMarineResource. Service2 has StarshipController.
            // Service1 is responsible for SpaceMarines. Service2 for Starships.
            // But usually unload implies updating Starship state (Service2) or SpaceMarine state (Service1)?
            // The lab usually requires Service2 to call Service1.
            // Let's assume Service1 has an endpoint for unloading?
            // I read Service1 source code earlier. StarshipResource seems to be in Service1?
            // Let me double check Service1 source code.
            
            // Wait, I saw StarshipResource.java in Service1 file list.
            // If StarshipResource is in Service1, then Service2 is just a proxy?
            // Or Service2 is the NEW Starship service?
            // The prompt says "Service2 ... running services ... install Consul ...".
            // And "Service1 ... split ... Web App + EJB".
            
            // If Service2 is the Starship service, it should have its own DB logic?
            // But Service2 StarshipService uses RestTemplate. So it calls someone else.
            // It calls jaxRSServiceBaseUrl.
            
            // Let's assume Service1 STILL has StarshipResource for now, or Service2 calls Service1's SpaceMarineResource?
            // But the URL in StarshipService is /api/v1/starship/...
            // If Service1 has StarshipResource, then the path is likely /v1/starship.
            
            String url = String.format("%s/v1/starship/%d/unload/space-marine-id?spaceMarineId=%d", jaxRSServiceBaseUrl, starshipId, spaceMarineId);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            logger.error("API call failed", e);
            return false;
        }
    }

    public boolean unloadAllSpaceMarines(long starshipId) {
        try {
            String url = String.format("%s/v1/starship/%d/unload-all", jaxRSServiceBaseUrl, starshipId);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            logger.error("API call failed", e);
            return false;
        }
    }
}

