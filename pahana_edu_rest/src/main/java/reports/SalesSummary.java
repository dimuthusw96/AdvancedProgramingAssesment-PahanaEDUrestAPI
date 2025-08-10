/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

/**
 *
 * @author Dimuthu
 */
public class SalesSummary {
    private int totalDays;
    private int totalBills;
    private double totalRevenue;
    private double avgBillValue;
    private double overallPercentageChange;

    public SalesSummary() {
    }

    public SalesSummary(int totalDays, int totalBills, double totalRevenue, double avgBillValue, double overallPercentageChange) {
        this.totalDays = totalDays;
        this.totalBills = totalBills;
        this.totalRevenue = totalRevenue;
        this.avgBillValue = avgBillValue;
        this.overallPercentageChange = overallPercentageChange;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
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

    public void setOverallPercentageChange(double overallPercentageChange) {
        this.overallPercentageChange = overallPercentageChange;
    }

    public int getTotalDays() {
        return totalDays;
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

    public double getOverallPercentageChange() {
        return overallPercentageChange;
    }
}
