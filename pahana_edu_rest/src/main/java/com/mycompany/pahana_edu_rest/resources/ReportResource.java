/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pahana_edu_rest.resources;

import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import reports.DailySalesReport;
import reports.SalesSummary;
import reports.ReorderLevels;
/**
 *
 * @author Dimuthu
 */
@Path("/sales")
@Produces(MediaType.APPLICATION_JSON)
public class ReportResource {
    @Inject
    private services.reportService salesService;
    

    @GET
    @Path("/daily")
    public Response getDailySalesReport(
            @QueryParam("startDate") String startDateStr,
            @QueryParam("endDate") String endDateStr) {
        
        try {
            Date startDate = parseDate(startDateStr);
            Date endDate = parseDate(endDateStr);

            // Get data from service
            List<DailySalesReport> dailySales = salesService.getDailySalesReport(startDate, endDate);
            SalesSummary summary = salesService.getSalesSummary(dailySales);

            // Build response (Java 7 compatible way)
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("dailySales", dailySales);
            response.put("summary", summary);
            
            return Response.ok(response).build();

        } catch (ParseException e) {
            // Java 7 compatible error response
            Map<String, String> errorResponse = new HashMap<String, String>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Invalid date format. Use yyyy-MM-dd");
            errorResponse.put("details", e.getMessage());
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            
            // Java 7 compatible error response
            Map<String, String> errorResponse = new HashMap<String, String>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error processing your request");
            errorResponse.put("details", e.getMessage());
            errorResponse.put("errorType", e.getClass().getName());
            
            return Response.serverError()
                    .entity(errorResponse)
                    .build();
        }
    }

    @GET
    @Path("/daily/export")
    @Produces("text/csv")
    public Response exportDailySalesReport(
            @QueryParam("startDate") String startDateStr,
            @QueryParam("endDate") String endDateStr) {
        
        try {
            Date startDate = parseDate(startDateStr);
            Date endDate = parseDate(endDateStr);

            List<DailySalesReport> dailySales = salesService.getDailySalesReport(startDate, endDate);
            String csv = generateCSV(dailySales);
            
            return Response.ok(csv)
                    .header("Content-Disposition", "attachment; filename=daily_sales_report.csv")
                    .build();

        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Use yyyy-MM-dd")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error generating export")
                    .build();
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
    }

    private String generateCSV(List<DailySalesReport> dailySales) {
        StringBuilder csv = new StringBuilder();
        csv.append("Date,Total Bills,Total Revenue,Avg Bill Value,Trend\n");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (DailySalesReport day : dailySales) {
            csv.append(String.format("\"%s\",%d,%.2f,%.2f,%.2f%%\n",
                dateFormat.format(day.getSaleDate()),
                day.getTotalBills(),
                day.getTotalRevenue(),
                day.getAvgBillValue(),
                day.getPercentageChange()));
        }
        
        return csv.toString();
    }

    // Removed static interface methods and kept only what's needed
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
      private final Gson gson = new Gson();
    @GET
    @Path("/reorderlevels")
    public Response getStockReorderLevels() throws SQLException {
       return Response.ok(gson.toJson(salesService.getstockreorder())).build();
    }
    
    
    
}
