/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Database class for storing and retrieving data from the database
 */
public class Database {

    private String dbIp;
    private String dbUser;
    private String dbPassword;
    private Connection connection;

    public boolean connected;

    /**
     * Constructor for the Database class
     * 
     * @param dbIp       - the IP address of the database
     * @param dbUser     - the username of the database
     * @param dbPassword - the password of the database
     */
    public Database(String dbIp, String dbUser, String dbPassword) {
        this.dbIp = dbIp;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.connected = false;
    }

    /**
     * Connect to the database
     * 
     * @throws SQLException - if the connection fails
     */
    public void connect() throws SQLException {
        try {
            String url = "jdbc:mysql://" + dbIp + ":3306/clip_db"; // Assuming the database name is clip_db
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            connected = true;
            log("Connected to database", "DB-CONNECTION");
        } catch (SQLException e) {
            connected = false;
            log("Failed to connect to database", "DB-CONNECTION");
        }
    }

    /**
     * Function for getting the server name
     * 
     * @return the server name
     */
    public String getServerName() {
        String sql = "SELECT server_name FROM health";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("server_name");
            }
        } catch (SQLException e) {
            log("Failed to get server name", "DB-CONNECTION");
        }
        return "Unknown";
    }

    /**
     * Function for checking if account creation is enabled
     * 
     * @return true if account creation is enabled, false otherwise
     */
    public boolean isAccountCreationEnabled() {
        String sql = "SELECT account_creation_enabled FROM health";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("account_creation_enabled");
            }
        } catch (SQLException e) {
            log("Failed to get account creation enabled", "DB-CONNECTION");
        }
        return false;
    }

    /**
     * Function for getting the account creation limit
     * 
     * @return the account creation limit
     */
    public int getAccountCreationLimit() {
        String sql = "SELECT account_creation_limit FROM health";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("account_creation_limit");
            }
        } catch (SQLException e) {
            log("Failed to get account creation limit", "DB-CONNECTION");
        }
        return 0;
    }

    /**
     * Function for checking if the account creation limit is exceeded
     * 
     * @return true if the account creation limit is exceeded, false otherwise
     */
    public boolean isAccountAmountLimitExceeded() {
        String sql = "SELECT account_creation_limit FROM health";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("account_creation_limit") <= resultSet.getInt("account_creation_limit") + 1;
            }
        } catch (SQLException e) {
            log("Failed to get account amount limit exceeded", "DB-CONNECTION");
        }
        return false;
    }

    /**
     * Function for checking if private clips are enabled
     * 
     * @return true if private clips are enabled, false otherwise
     */
    public boolean isPrivateClipsEnabled() {
        String sql = "SELECT private_clips_enabled FROM health";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("private_clips_enabled");
            }
        } catch (SQLException e) {
            log("Failed to get private clips enabled", "DB-CONNECTION");
        }
        return false;
    }

    /**
     * Function for logging to database
     * 
     * @param message
     * @param category
     */
    public void log(String message, String category) {
        try {
            if (connected) {
                String sql = "INSERT INTO app_logs (log_type, log_error_flag, log_message, log_timestamp, log_user_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, category);
                    pstmt.setBoolean(2, false); // Assuming log_error_flag is false for regular logs
                    pstmt.setString(3, message);
                    pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                    pstmt.setInt(5, 0); // Assuming log_user_id is 0 for system logs, adjust as necessary
                    pstmt.execute();
                    System.out.println("LOG - - " + LocalDateTime.now() + " - " + category + " - " + message);
                } catch (SQLException e) {
                    System.out.println("Error inserting log into database (" + e.toString() + ")");
                }
            } else {
                System.out.println("LOG - - " + LocalDateTime.now() + " - " + category + " - " + message);
            }
        } catch (Exception ex) {
            System.out.println("Error logging to database (" + ex.toString() + ")");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}