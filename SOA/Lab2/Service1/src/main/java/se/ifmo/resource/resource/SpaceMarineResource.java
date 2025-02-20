package se.ifmo.resource.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.resource.model.SpaceMarine;
import se.ifmo.resource.service.SpaceMarineService;

import java.util.List;

@Path("/space_marine")
public class SpaceMarineResource {
    @Inject
    private SpaceMarineService spaceMarineService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllSpaceMarine(
            @QueryParam("sort") String sort,
            @QueryParam("order") String order,
            @QueryParam("filter") List<String> filter,
            @QueryParam("page") int page,
            @QueryParam("pageSize") int pageSize
    ) {
        List<SpaceMarine> spaceMarines = spaceMarineService.getAllSpaceMarine(sort, order, filter, page, pageSize);
        return Response.ok(spaceMarines).build();
    }
}
