/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahana_edu_rest.resources;

import Utils.Bill;
import Utils.Utils;
import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import services.BillService;

/**
 *
 * @author Dimuthu
 */
@Path("bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResources {
    private final services.BillService billingservice =new BillService();
    private final Gson gson = new Gson();

    @POST
    public Response createBill(String billJson) {
        Bill bill = gson.fromJson(billJson, Bill.class);
        // Set bill date if not provided
        if (bill.getBillDate() == null) {
            bill.setBillDate(new Date());
        }
        boolean created = billingservice.createBill(bill);
        if (created) {
            return Response.status(Response.Status.CREATED)
                    .entity(gson.toJson(bill))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Bill could not be created\"}")
                    .build();
        }
    }
}

