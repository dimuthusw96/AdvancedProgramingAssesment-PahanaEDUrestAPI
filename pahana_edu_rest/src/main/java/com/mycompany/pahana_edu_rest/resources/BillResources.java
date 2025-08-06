/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahana_edu_rest.resources;

import Utils.Customer;
import Utils.Bill;
import Utils.BillItem;
import Utils.Customer;
import Utils.Item;
import Utils.Utils;
import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @GET
@Path("/bills/{id}")
@Produces(MediaType.APPLICATION_JSON)
public Response getBillById(@PathParam("id") int id) {
    Bill bill = new Bill();
 String query="SELECT * FROM bills WHERE id = ?";
    try (Connection conn = Utils.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
       

        if (rs.next()) {
            bill.setId(rs.getInt("id"));
            bill.setBillDate(rs.getDate("bill_date"));
            bill.setTotalAmount(rs.getDouble("total_amount"));

            // ✅ Get customer ID
            int customerId = rs.getInt("customer_id");
            String custquery="SELECT * FROM customers WHERE id = ?";
            PreparedStatement custStmt = conn.prepareStatement(query);
// 🔄 Fetch full customer details
            
            custStmt.setInt(1, customerId);
            ResultSet custRs = custStmt.executeQuery();

            Customer customer = new Customer();
            if (custRs.next()) {
                customer.setId(custRs.getInt("id"));
                customer.setName(custRs.getString("name"));
                customer.setUnit_consumed(custRs.getInt("unit_consumed")); // if available
            }
            bill.setCustomer(customer);
        }

        // ✅ Get bill items
        PreparedStatement itemStmt = conn.prepareStatement("SELECT * FROM bill_items WHERE bill_id = ?");
        itemStmt.setInt(1, id);
        ResultSet itemRs = itemStmt.executeQuery();

        List<BillItem> billItems = new ArrayList<>();
        while (itemRs.next()) {
            BillItem bi = new BillItem();
            bi.setId(itemRs.getInt("id"));
            bi.setQuantity(itemRs.getInt("quantity"));
            bi.setPrice(itemRs.getDouble("price"));
            bi.setSubtotal(itemRs.getDouble("subtotal"));

            // ✅ Fetch item info (including name)
            int itemId = itemRs.getInt("item_id");
            PreparedStatement itemDetailStmt = conn.prepareStatement("SELECT * FROM items WHERE id = ?");
            itemDetailStmt.setInt(1, itemId);
            ResultSet itemDetailRs = itemDetailStmt.executeQuery();

            if (itemDetailRs.next()) {
                Item item = new Item();
                item.setId(itemDetailRs.getInt("id"));
                item.setName(itemDetailRs.getString("name"));
                item.setPrice(itemDetailRs.getDouble("price"));
                item.setQuantity(itemDetailRs.getInt("quantity"));

                bi.setItem(item);
            }

            billItems.add(bi);
        }

        bill.setBillItems(billItems);

    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    return Response.ok(bill).build();
}

}

