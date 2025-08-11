/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Utils.Utils;
import reports.DailySalesReport;
import reports.SalesSummary;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import reports.ReorderLevels;

/**
 *
 * @author Dimuthu
 */
public class reportService {
    
    public List<DailySalesReport> getDailySalesReport(Date startDate, Date endDate) throws SQLException {
        List<DailySalesReport> reports = new ArrayList<>();
        
        String query = "SELECT DATE(b.bill_date) AS sale_date, COUNT(b.id) AS total_bills, " +
                      "SUM(b.total_amount) AS total_revenue, " +
                      "ROUND(SUM(b.total_amount) / COUNT(b.id), 2) AS avg_bill_value " +
                      "FROM bills b ";
        
        // Add date filtering if provided
        if (startDate != null && endDate != null) {
            query += "WHERE DATE(b.bill_date) BETWEEN ? AND ? ";
        } else if (startDate != null) {
            query += "WHERE DATE(b.bill_date) >= ? ";
        } else if (endDate != null) {
            query += "WHERE DATE(b.bill_date) <= ? ";
        }
        
        query += "GROUP BY DATE(b.bill_date) ORDER BY sale_date DESC";
        
        try (Connection conn = Utils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set parameters if dates are provided
            int paramIndex = 1;
            if (startDate != null && endDate != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                stmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            } else if (startDate != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            } else if (endDate != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DailySalesReport report = new DailySalesReport();
                report.setSaleDate(rs.getDate("sale_date"));
                report.setTotalBills(rs.getInt("total_bills"));
                report.setTotalRevenue(rs.getDouble("total_revenue"));
                report.setAvgBillValue(rs.getDouble("avg_bill_value"));
                reports.add(report);
            }
        }
        
        return reports;
    }
    
    public SalesSummary getSalesSummary(List<DailySalesReport> dailySales) {
        SalesSummary summary = new SalesSummary();
        
        if (dailySales == null || dailySales.isEmpty()) {
            return summary;
        }
        
        summary.setTotalDays(dailySales.size());
        
        int totalBills = 0;
        double totalRevenue = 0;
        
        for (DailySalesReport day : dailySales) {
            totalBills += day.getTotalBills();
            totalRevenue += day.getTotalRevenue();
        }
        
        summary.setTotalBills(totalBills);
        summary.setTotalRevenue(totalRevenue);
        summary.setAvgBillValue(totalRevenue / totalBills);
        
        // Calculate overall percentage change if we have multiple days
        if (dailySales.size() > 1) {
            double firstDayRevenue = dailySales.get(dailySales.size()-1).getTotalRevenue();
            double lastDayRevenue = dailySales.get(0).getTotalRevenue();
            
            if (firstDayRevenue > 0) {
                double change = ((lastDayRevenue - firstDayRevenue) / firstDayRevenue) * 100;
                summary.setOverallPercentageChange(Math.round(change * 100.0) / 100.0);
            }
        }
        
        return summary;
    }
    
    
     public List<ReorderLevels> getstockreorder() throws SQLException {
     List<ReorderLevels> reorders = new ArrayList<>();
     String query = "SELECT id, name, quantity, reorderlevel FROM items WHERE quantity <= reorderlevel ORDER BY quantity ASC;";
     try (Connection conn = Utils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
         ResultSet rs = stmt.executeQuery();
         
         while (rs.next()) {
             
             ReorderLevels ro=new ReorderLevels();
             ro.setId(rs.getInt("id"));
             ro.setName(rs.getString("name"));
             ro.setQuantity(rs.getInt("quantity"));
             ro.setReorderlevel(rs.getInt("reorderlevel"));
               
                
                reorders.add(ro);
            }
         return reorders;
     }
     }
}


