/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dimuthu
 */
public class Utils {

    static final String DB_URL = "jdbc:mysql://localhost:3306/pahana_edu";
    static final String USER = "root";
    static final String PASS = "";

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setMobile(rs.getString("mobile"));
                c.setUnit_consumed(rs.getInt("unit_consumed"));
                customers.add(c);
            }

        } catch (SQLException e) {
        }
        return customers;
        
    }
public Customer getCustomerById(int id) {
    Customer c = null;
    String query = "SELECT * FROM customers WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            c = new Customer();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setAddress(rs.getString("address"));
            c.setMobile(rs.getString("mobile"));
            c.setUnit_consumed(rs.getInt("unit_consumed"));
        }

    } catch (SQLException e) {
    }

    return c;
}
public boolean createCustomer(Customer c) {
    String query = "INSERT INTO customers (name, email, address, mobile, unit_consumed) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, c.getName());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getAddress());
        pstmt.setString(4, c.getMobile());
        pstmt.setInt(5, c.getUnit_consumed());

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    c.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }

    } catch (SQLException e) {
    }

    return false;
}
public boolean updateCustomer(Customer c) {
    String query = "UPDATE customers SET name = ?, email = ?, address = ?, mobile = ?, unit_consumed = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, c.getName());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getAddress());
        pstmt.setString(4, c.getMobile());
        pstmt.setInt(5, c.getUnit_consumed());
        pstmt.setInt(6, c.getId());

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
    }

    return false;
}
public boolean deleteCustomer(int id) {
    String query = "DELETE FROM customers WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
    }

    return false;
}
}