package se.ifmo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.model.entity.SpaceMarine;
import se.ifmo.model.response.ErrorResponse;
import se.ifmo.service.SpaceMarineService;

import java.time.ZonedDateTime;
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
        try {
            List<SpaceMarine> spaceMarines = spaceMarineService.getAllSpaceMarine(sort, order, filter, page, pageSize);
            return Response.ok(spaceMarines).build();
        } catch (IllegalAccessError e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        }
    }
}
