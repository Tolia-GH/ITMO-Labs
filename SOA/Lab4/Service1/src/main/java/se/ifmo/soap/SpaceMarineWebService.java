package se.ifmo.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import se.ifmo.dao.model.NewSpaceMarine;
import se.ifmo.dao.model.SpaceMarine;

import java.util.List;

@WebService(targetNamespace = "http://soap.ifmo.se/")
public interface SpaceMarineWebService {
    @WebMethod
    List<SpaceMarine> getAllSpaceMarine(
            @WebParam(name = "sort") List<String> sort,
            @WebParam(name = "order") String order,
            @WebParam(name = "filter") List<String> filter,
            @WebParam(name = "page") int page,
            @WebParam(name = "pageSize") int pageSize
    );

    @WebMethod
    SpaceMarine addSpaceMarine(@WebParam(name = "newSpaceMarine") NewSpaceMarine newSpaceMarine);

    @WebMethod
    SpaceMarine getSpaceMarineById(@WebParam(name = "id") long id);

    @WebMethod
    void updateSpaceMarineById(
            @WebParam(name = "id") long id, 
            @WebParam(name = "newSpaceMarine") NewSpaceMarine newSpaceMarine
    );

    @WebMethod
    void deleteSpaceMarineById(@WebParam(name = "id") long id);

    @WebMethod
    void deleteSpaceMarineByHeartCount(@WebParam(name = "heartCount") int heartCount);
}
