package se.ifmo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.dao.response.ErrorResponse;
import se.ifmo.dao.response.SuccessResponse;
import se.ifmo.service.StarshipService;

import java.time.ZonedDateTime;

@Path("/starship")
public class StarshipResource {
    @Inject
    private StarshipService starshipService;

    @GET
    @Path("/{starship-id}/unload/space-marine-id")
    @Produces(MediaType.APPLICATION_XML)
    public Response unloadSpaceMarineById(
            @PathParam("starship-id") long starshipId,
            @QueryParam("space-marine-id") long spaceMarineId
    ) {
        try {
            starshipService.unloadSpaceMarineById(starshipId, spaceMarineId);
            SuccessResponse successResponse = new SuccessResponse(200, "Selected SpaceMarine unloaded from given spaceship", ZonedDateTime.now());
            return Response.ok(successResponse).build();
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
    @Path("/{starship-id}/unload-all")
    @Produces(MediaType.APPLICATION_XML)
    public Response unloadAllSpaceMarines(
            @PathParam("starship-id") long starshipId) {
        try {
            starshipService.unloadAllSpaceMarine(starshipId);
            SuccessResponse successResponse = new SuccessResponse(200, "Selected SpaceMarine unloaded from given spaceship", ZonedDateTime.now());
            return Response.ok(successResponse).build();
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
