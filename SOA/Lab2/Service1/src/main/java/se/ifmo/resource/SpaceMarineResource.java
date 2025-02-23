package se.ifmo.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.model.entity.NewSpaceMarine;
import se.ifmo.model.entity.SpaceMarine;
import se.ifmo.model.response.CountResponse;
import se.ifmo.model.response.SuccessResponse;
import se.ifmo.model.response.ErrorResponse;
import se.ifmo.model.response.SpaceMarineResponse;
import se.ifmo.service.SpaceMarineService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/by-heart-count")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteSpaceMarineByHeartCount(
            @QueryParam("heart-count") int heartCount
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/count/by-melee-weapon")
    @Produces(MediaType.APPLICATION_XML)
    public Response getSpaceMarineCountByMeleeWeapon(
            @QueryParam("melee-weapon") String weapon) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/by-name")
    @Produces(MediaType.APPLICATION_XML)
    public Response getSpaceMarineByName(
            @QueryParam("name") String name)  {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
