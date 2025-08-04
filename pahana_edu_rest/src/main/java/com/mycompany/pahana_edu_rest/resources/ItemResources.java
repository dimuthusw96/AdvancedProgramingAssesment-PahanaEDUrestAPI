/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahana_edu_rest.resources;

import Utils.Item;
import Utils.Utils;
import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Dimuthu
 */
@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResources {
     private final Utils utils = new Utils();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        return Response.ok(gson.toJson(utils.getItems())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") int id) {
        Item item = utils.getItemById(id);
        if (item == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"Item not found\"}")
                           .build();
        }
        return Response.ok(gson.toJson(item)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(String itemJson) {
        Item item = gson.fromJson(itemJson, Item.class);
        boolean created = utils.createItem(item);
        if (created) {
            return Response.status(Response.Status.CREATED)
                           .entity(gson.toJson(item))
                           .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("{\"error\":\"Item could not be created\"}")
                       .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(@PathParam("id") int id, String itemJson) {
        Item item = gson.fromJson(itemJson, Item.class);
        item.setId(id);
        boolean updated = utils.updateItem(item);
        if (updated) {
            return Response.ok(gson.toJson(item)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Item not found\"}")
                       .build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("id") int id) {
        boolean deleted = utils.deleteItem(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Item not found\"}")
                       .build();
    }
}
