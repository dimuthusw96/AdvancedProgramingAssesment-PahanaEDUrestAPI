/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Utils.Bill;
import Utils.BillItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author Dimuthu
 */
public class BillService {
    public boolean createBill(Bill bill) {
        String insertBillSQL = "INSERT INTO bills (customer_id, bill_date, total_amount) VALUES (?, ?, ?)";
        String insertBillItemSQL = "INSERT INTO bill_items (bill_id, item_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";
        String updateItemStockSQL = "UPDATE items SET quantity = quantity - ? WHERE id = ?";
        String updateCustomerUnitsSQL = "UPDATE customers SET unit_consumed = unit_consumed + ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement billStmt = null;
        PreparedStatement billItemStmt = null;
        PreparedStatement updateStockStmt = null;
        PreparedStatement updateCustomerStmt = null;
        ResultSet generatedKeys = null;

        try {
            if (bill.getCustomer() == null || bill.getCustomer().getId() <= 0) {
                System.err.println("❌ Invalid customer data");
                return false;
            }

           conn = Utils.Utils.getConnection();
            conn.setAutoCommit(false);

            // Insert Bill
            billStmt = conn.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS);
            billStmt.setInt(1, bill.getCustomer().getId());
            billStmt.setTimestamp(2, new Timestamp(bill.getBillDate().getTime()));
            billStmt.setDouble(3, bill.getTotalAmount());

            if (billStmt.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            generatedKeys = billStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                conn.rollback();
                return false;
            }

            int billId = generatedKeys.getInt(1);
            bill.setId(billId);

            billItemStmt = conn.prepareStatement(insertBillItemSQL);
            updateStockStmt = conn.prepareStatement(updateItemStockSQL);
            int totalUnitsSold = 0;

            for (BillItem item : bill.getBillItems()) {
                if (item == null || item.getItem() == null || item.getItem().getId() <= 0) {
                    System.err.println("❌ Invalid bill item or item ID");
                    conn.rollback();
                    return false;
                }

                System.out.println("✅ Processing item ID: " + item.getItem().getId() + " Qty: " + item.getQuantity());

                // Insert into bill_items
                billItemStmt.setInt(1, billId);
                billItemStmt.setInt(2, item.getItem().getId());
                billItemStmt.setInt(3, item.getQuantity());
                billItemStmt.setDouble(4, item.getPrice());
                billItemStmt.setDouble(5, item.getSubtotal());
                billItemStmt.addBatch();

                // Update stock
                updateStockStmt.setInt(1, item.getQuantity());
                updateStockStmt.setInt(2, item.getItem().getId());
                updateStockStmt.addBatch();

                totalUnitsSold += item.getQuantity();
            }

            // Execute batch inserts and stock updates
            for (int result : billItemStmt.executeBatch()) {
                if (result == Statement.EXECUTE_FAILED) {
                    System.err.println("❌ Failed inserting bill item");
                    conn.rollback();
                    return false;
                }
            }
            for (int result : updateStockStmt.executeBatch()) {
                if (result == Statement.EXECUTE_FAILED) {
                    System.err.println("❌ Failed updating stock");
                    conn.rollback();
                    return false;
                }
            }

            // Update customer unit_consumed
            updateCustomerStmt = conn.prepareStatement(updateCustomerUnitsSQL);
            updateCustomerStmt.setInt(1, totalUnitsSold);
            updateCustomerStmt.setInt(2, bill.getCustomer().getId());

            int updateRes = updateCustomerStmt.executeUpdate();
            if (updateRes == 0) {
                System.err.println("❌ Failed updating customer units");
                conn.rollback();
                return false;
            }

            conn.commit();
            System.out.println("✅ Bill creation successful");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (billStmt != null) {
                    billStmt.close();
                }
                if (billItemStmt != null) {
                    billItemStmt.close();
                }
                if (updateStockStmt != null) {
                    updateStockStmt.close();
                }
                if (updateCustomerStmt != null) {
                    updateCustomerStmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
