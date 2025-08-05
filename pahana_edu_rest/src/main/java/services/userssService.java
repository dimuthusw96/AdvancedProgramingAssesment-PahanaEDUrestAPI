/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Utils.users;
import Utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dimuthu
 */
public class userssService {
    public List<users> getusers() {
        List<users> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = Utils.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users user = new users();
                user.setId(rs.getInt("id"));
                user.setUserNname(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
               
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public users getUserById(int id) {
    String query = "SELECT * FROM users WHERE id = ?";

    try (Connection conn = Utils.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            users user = new users();
            user.setId(rs.getInt("id"));
            user.setUserNname(rs.getString("userName")); // or "userName" if corrected
            user.setPassword(rs.getString("password"));
            return user;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    public boolean createUser(users user) {
        String query = "INSERT INTO users (userName, password) VALUES (?, ?)";

        try (Connection conn = Utils.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUserNname());
            pstmt.setString(2, user.getPassword());
            

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(users user) {
        String query = "UPDATE users SET userName = ?, password = ? WHERE id = ?";

        try (Connection conn = Utils.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUserNname());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getId());
           

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteuser(int id) {
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection conn = Utils.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean userValidate(users user) {
    String query = "SELECT * FROM users WHERE userName = ? AND password = ?";

    try (Connection conn = Utils.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, user.getUserNname());
        pstmt.setString(2, user.getPassword());

        ResultSet rs = pstmt.executeQuery();  // ✅ CORRECT
        return rs.next();  // ✅ if a record exists, user is valid

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
