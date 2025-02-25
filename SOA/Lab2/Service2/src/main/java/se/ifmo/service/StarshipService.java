package se.ifmo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.ifmo.model.SuccessResponse;

@Service
public class StarshipService {

    @Value("${jax-rs.service.baseurl}")
    private String jaxRSServiceBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public SuccessResponse unloadSpaceMarineById(long starshipId, long spaceMarineId) {
        try {
            String url = "http://localhost:8080/v1/starship/{starship-id}/unload/space-marine-id?space-marine-id={space-marine-id}";
            // 调用第一个 Web Service（JAX-RS）
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, starshipId, spaceMarineId);

            // 根据 JAX-RS 服务的响应处理数据（此处假设成功返回200）
            if (response.getStatusCodeValue() == 200) {
                return new SuccessResponse(200, "Selected SpaceMarine unloaded from given spaceship");
            } else {
                return new SuccessResponse(400, "Invalid param value");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessResponse(500, "Internal server error");
        }
    }

    public SuccessResponse unloadAllSpaceMarines(long starshipId) {
        try {
            // 调用第一个 Web Service（JAX-RS）
            String url = "http://localhost:8080/v1/starship/" + starshipId + "/unload-all";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // 根据 JAX-RS 服务的响应处理数据（此处假设成功返回200）
            if (response.getStatusCodeValue() == 200) {
                return new SuccessResponse(200, "All spaceMarines are unloaded from given spaceship");
            } else {
                return new SuccessResponse(400, "Invalid param value");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessResponse(500, "Internal server error");
        }
    }
}
