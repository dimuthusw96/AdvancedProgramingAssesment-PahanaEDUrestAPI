/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahana_edu_rest.resources;


import Utils.users;
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
import services.userssService;

/**
 *
 * @author Dimuthu
 */
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class userResources {
     private final services.userssService userservice=new userssService();
    private final Gson gson = new Gson();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getusers() {
        return Response.ok(gson.toJson(userservice.getusers())).build();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getusers(@PathParam("id") int id) {
        users user = userservice.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\":\"Item not found\"}")
                           .build();
        }
        return Response.ok(gson.toJson(user)).build();
    }
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String userJson) {
        users user = gson.fromJson(userJson, users.class);
        boolean created = userservice.createUser(user);
        if (created) {
            return Response.status(Response.Status.CREATED)
                           .entity(gson.toJson(user))
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
    public Response updateuser(@PathParam("id") int id, String userJson) {
        users user = gson.fromJson(userJson, users.class);
        user.setId(id);
        boolean updated = userservice.updateUser(user);
        if (updated) {
            return Response.ok(gson.toJson(user)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Item not found\"}")
                       .build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteuser(@PathParam("id") int id) {
        boolean deleted = userservice.deleteuser(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"error\":\"Item not found\"}")
                       .build();
    }
    
    @POST
@Path("/validate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response validateUser(String userJson) {
    users user = gson.fromJson(userJson, users.class);
    boolean validate = userservice.userValidate(user);
    if (validate) {
        return Response.status(Response.Status.CREATED)
                       .entity(gson.toJson(user))
                       .build();
    }
    return Response.status(Response.Status.UNAUTHORIZED) // More appropriate for failed login
                   .entity("{\"error\":\"Invalid credentials\"}")
                   .build();
}

 @POST
@Path("/alreadyuser")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response checkUser(String userJson) {
    users user = gson.fromJson(userJson, users.class);
   boolean username = userservice.validUserByname(user);
        if (username) {
        return Response.status(Response.Status.CREATED)
                       .entity(gson.toJson(user))
                       .build();
    }
    return Response.status(Response.Status.UNAUTHORIZED) // More appropriate for failed login
                   .entity("{\"error\":\"Invalid credentials\"}")
                   .build();
}

}
