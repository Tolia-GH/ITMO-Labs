package se.ifmo.soap;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import se.ifmo.service.StarshipService;

@WebService(endpointInterface = "se.ifmo.soap.StarshipWebService", targetNamespace = "http://soap.ifmo.se/", serviceName = "StarshipService")
public class StarshipWebServiceImpl implements StarshipWebService {

    @Inject
    private StarshipService starshipService;

    @Override
    public void unloadSpaceMarineById(long starshipId, long spaceMarineId) {
        try {
            starshipService.unloadSpaceMarineById(starshipId, spaceMarineId);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

    @Override
    public void unloadAllSpaceMarines(long starshipId) {
        try {
            starshipService.unloadAllSpaceMarine(starshipId);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }
}
