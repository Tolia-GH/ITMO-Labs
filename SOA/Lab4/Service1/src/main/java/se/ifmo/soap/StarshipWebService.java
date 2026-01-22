package se.ifmo.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://soap.ifmo.se/")
public interface StarshipWebService {
    @WebMethod
    void unloadSpaceMarineById(
            @WebParam(name = "starship_id") long starshipId,
            @WebParam(name = "space_marine_id") long spaceMarineId
    );

    @WebMethod
    void unloadAllSpaceMarines(@WebParam(name = "starship_id") long starshipId);
}
