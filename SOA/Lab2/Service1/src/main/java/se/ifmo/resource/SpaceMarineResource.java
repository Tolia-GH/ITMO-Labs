package se.ifmo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.model.entity.SpaceMarine;
import se.ifmo.model.response.ErrorResponse;
import se.ifmo.model.response.SpaceMarineResponse;
import se.ifmo.service.SpaceMarineService;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

@Path("/space-marine")
public class SpaceMarineResource {
    @Inject
    private SpaceMarineService spaceMarineService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllSpaceMarine(
            @QueryParam("sort") @DefaultValue("id") String sort,
            @QueryParam("order") @DefaultValue("ASC") String order,
            @QueryParam("filter") List<String> filter,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        try {
            List<SpaceMarine> spaceMarines = spaceMarineService.getAllSpaceMarine(sort, order, filter, page, pageSize);
            SpaceMarineResponse spaceMarineResponse = new SpaceMarineResponse(spaceMarines);
            return Response.ok(spaceMarineResponse).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getSpaceMarineById(@PathParam("id") long id) {
        try {
            SpaceMarine spaceMarine = spaceMarineService.getSpaceMarineById(id);
            return Response.ok(spaceMarine).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
