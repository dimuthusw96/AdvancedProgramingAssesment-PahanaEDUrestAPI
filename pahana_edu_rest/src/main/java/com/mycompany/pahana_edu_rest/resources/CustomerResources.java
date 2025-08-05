/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahana_edu_rest.resources;
import Utils.Customer;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.CustomerService;
import Utils.Utils;
/**
 *
 * @author Dimuthu
 */

@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResources {
    
    @GET
    public Response getCustomers() {
        CustomerService customerservice = new CustomerService();
        return Response.ok(new Gson().toJson(customerservice.getCustomers())).build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") int id) {
        CustomerService customerservice = new CustomerService();
        Customer customer = customerservice.getCustomerById(id);
        if (customer != null) {
            return Response.ok(new Gson().toJson(customer)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }
    }

    @POST
    public Response createCustomer(String customerJson) {
         CustomerService customerservice = new CustomerService();
        Customer customer = new Gson().fromJson(customerJson, Customer.class);
        boolean created = customerservice.createCustomer(customer);
        if (created) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Gson().toJson(customer))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Customer could not be created\"}")
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateCustomer(@PathParam("id") int id, String customerJson) {
         CustomerService customerservice = new CustomerService();
        Customer customer = new Gson().fromJson(customerJson, Customer.class);
        customer.setId(id);
        boolean updated = customerservice.updateCustomer(customer);
        if (updated) {
            return Response.ok(new Gson().toJson(customer)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        CustomerService customerservice = new CustomerService();
        boolean deleted = customerservice.deleteCustomer(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }
    }
    @POST
@Path("/bulk")
public Response createCustomersBulk(String customersJson) {
    CustomerService service = new CustomerService();
    Customer[] customers = new Gson().fromJson(customersJson, Customer[].class);

    boolean success = true;
    for (Customer c : customers) {
        success &= service.createCustomer(c);
    }

    if (success) {
        return Response.status(Response.Status.CREATED).entity("{\"message\":\"All customers created.\"}").build();
    } else {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Failed to create one or more customers.\"}").build();
    }
}
}
