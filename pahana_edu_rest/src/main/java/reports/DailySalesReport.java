/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import java.sql.Date;

/**
 *
 * @author Dimuthu
 */
public class DailySalesReport {
    private Date saleDate;
    private int totalBills;
    private double totalRevenue;
    private double avgBillValue;
    private double percentageChange; // For trend calculation

    public DailySalesReport() {
    }

    public DailySalesReport(Date saleDate, int totalBills, double totalRevenue, double avgBillValue, double percentageChange) {
        this.saleDate = saleDate;
        this.totalBills = totalBills;
        this.totalRevenue = totalRevenue;
        this.avgBillValue = avgBillValue;
        this.percentageChange = percentageChange;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public int getTotalBills() {
        return totalBills;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getAvgBillValue() {
        return avgBillValue;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public void setTotalBills(int totalBills) {
        this.totalBills = totalBills;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setAvgBillValue(double avgBillValue) {
        this.avgBillValue = avgBillValue;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    
}



