/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jakubwawak.clip.ClipApplication;

/**
 * User class for storing and retrieving user data from the database
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String account_photo_url;
    private String background_color;
    private String primary_color;
    private String password;
    private String password_salt;
    private Timestamp created_at;
    private boolean active;
    private Timestamp last_login;
    private String blog_url;
    private String blog_name;
    private boolean admin;

    /**
     * Constructor for the User class
     * 
     * @param id                - the id of the user
     * @param username          - the username of the user
     * @param email             - the email of the user
     * @param account_photo_url - the account photo url of the user
     * @param background_color  - the background color of the user
     * @param primary_color     - the primary color of the user
     * @param password          - the password of the user
     * @param password_salt     - the password salt of the user
     * @param created_at        - the created at timestamp of the user
     * @param active            - the active flag of the user
     * @param last_login        - the last login timestamp of the user
     * @param blog_url          - the blog url of the user
     * @param blog_name         - the blog name of the user
     * @param admin             - the admin flag of the user
     */
    public User(int id, String username, String email, String account_photo_url, String background_color,
            String primary_color, String password, String password_salt, Timestamp created_at, boolean active,
            Timestamp last_login, String blog_url, String blog_name, boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.account_photo_url = account_photo_url;
        this.background_color = background_color;
        this.primary_color = primary_color;
        this.password = password;
        this.password_salt = password_salt;
        this.created_at = created_at;
        this.active = active;
        this.last_login = last_login;
        this.blog_url = blog_url;
        this.blog_name = blog_name;
        this.admin = admin;
    }

    /**
     * Constructor for the User class
     */
    public User() {
        this.id = 0;
        this.username = "";
        this.email = "";
        this.account_photo_url = "";
        this.background_color = "";
        this.primary_color = "";
        this.password = "";
        this.password_salt = "";
        this.created_at = null;
        this.active = true;
        this.last_login = null;
        this.blog_url = "";
        this.blog_name = "";
        this.admin = false;
    }

    /**
     * Constructor for the User class
     * 
     * @param resultSet - the result set of the user
     */
    public User(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("id");
            this.username = resultSet.getString("username");
            this.email = resultSet.getString("email");
            this.account_photo_url = resultSet.getString("account_photo_url");
            this.background_color = resultSet.getString("background_color");
            this.primary_color = resultSet.getString("primary_color");
            this.password = resultSet.getString("password");
            this.password_salt = resultSet.getString("password_salt");
            this.created_at = resultSet.getTimestamp("created_at");
            this.active = resultSet.getBoolean("active");
            this.last_login = resultSet.getTimestamp("last_login");
            this.blog_url = resultSet.getString("blog_url");
            this.blog_name = resultSet.getString("blog_name");
            this.admin = resultSet.getBoolean("admin");
        } catch (SQLException e) {
            ClipApplication.database.log("Error: " + e.getMessage(), "USER-RETRIVE-ERROR");
        }
    }

    /**
     * Getter for the user's ID
     * 
     * @return the user's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the user's ID
     * 
     * @param id - the new ID for the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the user's username
     * 
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the user's username
     * 
     * @param username - the new username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's email
     * 
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the user's email
     * 
     * @param email - the new email for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the user's account photo URL
     * 
     * @return the user's account photo URL
     */
    public String getAccount_photo_url() {
        return account_photo_url;
    }

    /**
     * Setter for the user's account photo URL
     * 
     * @param account_photo_url - the new account photo URL for the user
     */
    public void setAccount_photo_url(String account_photo_url) {
        this.account_photo_url = account_photo_url;
    }

    /**
     * Getter for the user's background color
     * 
     * @return the user's background color
     */
    public String getBackground_color() {
        return background_color;
    }

    /**
     * Setter for the user's background color
     * 
     * @param background_color - the new background color for the user
     */
    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    /**
     * Getter for the user's primary color
     * 
     * @return the user's primary color
     */
    public String getPrimary_color() {
        return primary_color;
    }

    /**
     * Setter for the user's primary color
     * 
     * @param primary_color - the new primary color for the user
     */
    public void setPrimary_color(String primary_color) {
        this.primary_color = primary_color;
    }

    /**
     * Getter for the user's password
     * 
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password
     * 
     * @param password - the new password for the user
     */
    public void setPassword(String password) {
        try {
            // Create a new instance of the MessageDigest class
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Add the password and salt to the MessageDigest instance
            md.update((password + password_salt).getBytes());
            // Generate the hashed password
            byte[] hashedPassword = md.digest();
            // Convert the hashed password to a string
            this.password = new String(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception (e.g., log it or rethrow it)
            ClipApplication.database.log("Error: " + e.getMessage(), "PASSWORD-HASHING-ERROR");
        }
    }

    /**
     * Checks if the provided plain text password matches the user's hashed password
     * 
     * @param plainTextPassword - the plain text password to check
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String plainTextPassword) {
        try {
            // Create a new instance of the MessageDigest class
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Add the plain text password and salt to the MessageDigest instance
            md.update((plainTextPassword + password_salt).getBytes());
            // Generate the hashed password
            byte[] hashedPassword = md.digest();
            // Convert the hashed password to a string
            String hashedPasswordString = new String(hashedPassword);
            // Compare the generated hashed password with the user's hashed password
            return hashedPasswordString.equals(password);
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception (e.g., log it or rethrow it)
            ClipApplication.database.log("Error: " + e.getMessage(), "PASSWORD-CHECK-ERROR");
            return false; // Return false in case of an exception to ensure security
        }
    }

    /**
     * Getter for the user's password salt
     * 
     * @return the user's password salt
     */
    public String getPassword_salt() {
        return password_salt;
    }

    /**
     * Setter for the user's password salt
     * 
     * @param password_salt - the new password salt for the user
     */
    public void setPassword_salt(String password_salt) {
        this.password_salt = password_salt;
    }

    /**
     * Getter for the user's creation timestamp
     * 
     * @return the user's creation timestamp
     */
    public Timestamp getCreated_at() {
        return created_at;
    }

    /**
     * Setter for the user's creation timestamp
     * 
     * @param created_at - the new creation timestamp for the user
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * Getter for the user's active status
     * 
     * @return the user's active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for the user's active status
     * 
     * @param active - the new active status for the user
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for the user's last login timestamp
     * 
     * @return the user's last login timestamp
     */
    public Timestamp getLast_login() {
        return last_login;
    }

    /**
     * Setter for the user's last login timestamp
     * 
     * @param last_login - the new last login timestamp for the user
     */
    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    /**
     * Getter for the user's blog URL
     * 
     * @return the user's blog URL
     */
    public String getBlog_url() {
        return blog_url;
    }

    /**
     * Setter for the user's blog URL
     * 
     * @param blog_url - the new blog URL for the user
     */
    public void setBlog_url(String blog_url) {
        this.blog_url = blog_url;
    }

    /**
     * Getter for the user's blog name
     * 
     * @return the user's blog name
     */
    public String getBlog_name() {
        return blog_name;
    }

    /**
     * Setter for the user's blog name
     * 
     * @param blog_name - the new blog name for the user
     */
    public void setBlog_name(String blog_name) {
        this.blog_name = blog_name;
    }

    /**
     * Getter for the user's admin status
     * 
     * @return the user's admin status
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Setter for the user's admin status
     * 
     * @param admin - the new admin status for the user
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
