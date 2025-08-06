/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Utils.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import Utils.Bill;
import Utils.BillItem;
import Utils.Customer;
import Utils.Utils;
import java.util.ArrayList;
import java.util.List;
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

            conn = Utils.getConnection();
            conn.setAutoCommit(false);

            // Insert bill
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

                // Insert bill item
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

            for (int result : billItemStmt.executeBatch()) {
                if (result == Statement.EXECUTE_FAILED) {
                    conn.rollback();
                    return false;
                }
            }

            for (int result : updateStockStmt.executeBatch()) {
                if (result == Statement.EXECUTE_FAILED) {
                    conn.rollback();
                    return false;
                }
            }

            updateCustomerStmt = conn.prepareStatement(updateCustomerUnitsSQL);
            updateCustomerStmt.setInt(1, totalUnitsSold);
            updateCustomerStmt.setInt(2, bill.getCustomer().getId());

            if (updateCustomerStmt.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            conn.commit();
            System.out.println("✅ Bill created successfully.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (billStmt != null) billStmt.close();
                if (billItemStmt != null) billItemStmt.close();
                if (updateStockStmt != null) updateStockStmt.close();
                if (updateCustomerStmt != null) updateCustomerStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Bill getBillById(int id) {
        Bill bill = null;
        String billQuery = "SELECT * FROM bills WHERE id = ?";
        String itemsQuery = "SELECT bi.*, i.name FROM bill_items bi JOIN items i ON bi.item_id = i.id WHERE bi.bill_id = ?";

        try (Connection conn = Utils.getConnection();
             PreparedStatement billStmt = conn.prepareStatement(billQuery);
             PreparedStatement itemStmt = conn.prepareStatement(itemsQuery)) {

            // Fetch main bill
            billStmt.setInt(1, id);
            ResultSet billRs = billStmt.executeQuery();

            if (billRs.next()) {
                bill = new Bill();
                bill.setId(billRs.getInt("id"));

                // Create a dummy customer object with just the ID (you can expand if needed)
                Customer c = new Customer();
                c.setId(billRs.getInt("customer_id"));
                bill.setCustomer(c);

                bill.setBillDate(billRs.getDate("bill_date"));
                bill.setTotalAmount(billRs.getDouble("total_amount"));

                // Fetch bill items
                itemStmt.setInt(1, id);
                ResultSet itemRs = itemStmt.executeQuery();

                List<BillItem> items = new ArrayList<>();
                while (itemRs.next()) {
                    BillItem bi = new BillItem();
                    bi.setId(itemRs.getInt("id"));
                    bi.setId(id);
                    bi.setQuantity(itemRs.getInt("quantity"));
                    bi.setPrice(itemRs.getDouble("price"));
                    bi.setSubtotal(itemRs.getDouble("subtotal"));

                    // Optional: Add item name or item object
                   	Item item = new Item();
                    item.setId(itemRs.getInt("item_id"));
                    item.setName(itemRs.getString("name"));
                    bi.setItem(item);

                    items.add(bi);
                }
                bill.setBillItems(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }
}
