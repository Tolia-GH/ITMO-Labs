package se.ifmo.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://soap.ifmo.se/")
public interface StarshipWebService {
    @WebMethod
    void unloadSpaceMarineById(
            @WebParam(name = "starshipId") long starshipId,
            @WebParam(name = "spaceMarineId") long spaceMarineId
    );

    @WebMethod
    void unloadAllSpaceMarines(@WebParam(name = "starshipId") long starshipId);
}
