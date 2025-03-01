package se.ifmo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.dao.model.NewSpaceMarine;
import se.ifmo.dao.model.SpaceMarine;
import se.ifmo.dao.response.CountResponse;
import se.ifmo.dao.response.SuccessResponse;
import se.ifmo.dao.response.ErrorResponse;
import se.ifmo.dao.response.SpaceMarineResponse;
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
            @QueryParam("sort") @DefaultValue("id") List<String> sort,
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
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addSpaceMarine(
            NewSpaceMarine newSpaceMarine) {
        try {
            SpaceMarine spaceMarine = spaceMarineService.addSpaceMarine(newSpaceMarine);
            return Response.ok(spaceMarine).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
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
    @Produces(MediaType.APPLICATION_XML)
    public Response getSpaceMarineById(
            @PathParam("id") long id) {
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
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateSpaceMarineById(
            @PathParam("id") long id,
            NewSpaceMarine newSpaceMarine) {
        try {
            spaceMarineService.updateSpaceMarine(id, newSpaceMarine);
            SuccessResponse successResponse = new SuccessResponse(200, "SpaceMarine Updated", ZonedDateTime.now());
            return Response.ok(successResponse).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteSpaceMarineById(
            @PathParam("id") long id) {
        try {
            spaceMarineService.deleteSpaceMarineByID(id);
            SuccessResponse successResponse = new SuccessResponse(200, "SpaceMarine deleted", ZonedDateTime.now());
            return Response.ok(successResponse).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/by-heart-count")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteSpaceMarineByHeartCount(
            @QueryParam("heartCount") int heartCount
    ) {
        try {
            spaceMarineService.deleteSpaceMarineByHeartCount(heartCount);
            SuccessResponse successResponse = new SuccessResponse(200, "SpaceMarine deleted", ZonedDateTime.now());
            return Response.ok(successResponse).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
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
    @Path("/count/by-melee-weapon")
    @Produces(MediaType.APPLICATION_XML)
    public Response getSpaceMarineCountByMeleeWeapon(
            @QueryParam("meleeWeapon") String weapon) {
        try {
            List<SpaceMarine> spaceMarineList = spaceMarineService.getSpaceMarineListByMeleeWeapon(weapon);
            CountResponse countResponse = new CountResponse(200, spaceMarineList.size(), ZonedDateTime.now());
            return Response.ok(countResponse).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
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
    @Path("/by-name")
    @Produces(MediaType.APPLICATION_XML)
    public Response getSpaceMarineByName(
            @QueryParam("prefix") String name)  {
        try {
            List<SpaceMarine> spaceMarineList = spaceMarineService.getSpaceMarineListByName(name);
            SpaceMarineResponse spaceMarineResponse = new SpaceMarineResponse(spaceMarineList);
            return Response.ok(spaceMarineResponse).build();
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Invalid param",
                    ZonedDateTime.now()
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse).build();
        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    e.getMessage(),
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
