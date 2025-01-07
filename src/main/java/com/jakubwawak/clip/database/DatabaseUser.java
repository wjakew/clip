/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.entity.User;

/**
 * DatabaseUser class for storing and retrieving user data from the database
 */
public class DatabaseUser {

    public Database database;

    /**
     * Constructor for the DatabaseUser class
     */
    public DatabaseUser() {
        this.database = ClipApplication.database;
    }

    /**
     * Function for creating a user
     * 
     * @param user - the user to create
     * @return 1 if the user was created successfully, -1 otherwise
     */
    public int createUser(User user) {
        String sql = "INSERT INTO users (username, email, password, password_salt, created_at, active, last_login, blog_url, blog_name, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPassword_salt());
            pstmt.setTimestamp(5, user.getCreated_at());
            pstmt.setBoolean(6, user.isActive());
            pstmt.setTimestamp(7, user.getLast_login());
            pstmt.setString(8, user.getBlog_url());
            pstmt.setString(9, user.getBlog_name());
            pstmt.setBoolean(10, user.isAdmin());
            pstmt.execute();
            return 1;
        } catch (SQLException e) {
            database.log("Failed to create user " + user.getUsername(), "USER-CREATION-FAILED");
            return -1;
        }
    }

    /**
     * Function for checking if a user exists
     * 
     * @param email - the email of the user to check
     * @return true if the user exists, false otherwise
     */
    public boolean isUserExists(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            database.log("Failed to check if user exists (" + e.getMessage() + ")", "USER-EXISTS-CHECK-FAILED");
            return false;
        }
    }

}
