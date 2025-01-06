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
import com.jakubwawak.clip.entity.Clip;

/**
 * DatabaseClip class for the Clip application
 */
public class DatabaseClip {

    private Database database;

    /**
     * Constructor for the DatabaseClip class
     */
    public DatabaseClip() {
        this.database = ClipApplication.database;
    }

    /**
     * Function for calculating the clip url
     * 
     * @return the clip url
     */
    public String calculateClipUrl() {
        String clipUrl;
        do {
            clipUrl = generateRandomString(20);
        } while (isClipUrlExists(clipUrl));
        return clipUrl;
    }

    /**
     * Function for generating a random string
     * 
     * @param length - the length of the string
     * @return the random string
     */
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    /**
     * Function for checking if the clip url exists
     * 
     * @param clipUrl - the url of the clip
     * @return true if the clip url exists, false otherwise
     */
    private boolean isClipUrlExists(String clipUrl) {
        String sql = "SELECT COUNT(*) FROM clip WHERE clip_url = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, clipUrl);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            database.log("Failed to check if clip URL exists (" + clipUrl + ")", "DB-CLIP");
        }
        return false;
    }

    /**
     * Function for creating a clip
     * 
     * @param clip - the clip to create
     * @return 1 if the clip was created successfully, 0 if it failed
     */
    public int createClip(Clip clip) {
        String sql = "INSERT INTO clip (clip_url, clip_title, clip_raw, clip_extension, clip_created_at, clip_updated_at, clip_private, clip_deleted, clip_password, clip_password_salt, clip_editor_password, clip_word_count, clip_reactions_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, clip.getClipUrl());
            pstmt.setString(2, clip.getClipTitle());
            pstmt.setString(3, clip.getClipRaw());
            pstmt.setString(4, clip.getClipExtension());
            pstmt.setTimestamp(5, clip.getClipCreatedAt());
            pstmt.setTimestamp(6, clip.getClipUpdatedAt());
            pstmt.setBoolean(7, clip.isClipPrivate());
            pstmt.setBoolean(8, clip.isClipDeleted());
            pstmt.setString(9, clip.getClipPassword());
            pstmt.setString(10, clip.getClipPasswordSalt());
            pstmt.setString(11, clip.getClipEditorPassword());
            pstmt.setInt(12, clip.getClipWordCount());
            pstmt.setInt(13, clip.getClipReactionsCount());
            pstmt.execute();
            database.log("Clip created (" + clip.getClipUrl() + ") for user " + clip.getUserId(), "DB-CLIP");
            return 1;
        } catch (SQLException e) {
            database.log("Failed to create clip (" + clip.getClipUrl() + ") - " + e.getMessage(), "DB-CLIP");
            return 0;
        }
    }

    /**
     * Function for updating a clip
     * 
     * @param clip - the clip to update
     * @return 1 if the clip was updated successfully, 0 if it failed
     */
    public int updateClip(Clip clip) {
        String sql = "UPDATE clip SET clip_title = ?, clip_raw = ?, clip_extension = ?, clip_updated_at = ?, clip_private = ?, clip_deleted = ?, clip_password = ?, clip_password_salt = ?, clip_editor_password = ?, clip_word_count = ?, clip_reactions_count = ? WHERE clip_url = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, clip.getClipTitle());
            pstmt.setString(2, clip.getClipRaw());
            pstmt.setString(3, clip.getClipExtension());
            pstmt.setTimestamp(4, clip.getClipUpdatedAt());
            pstmt.setBoolean(5, clip.isClipPrivate());
            pstmt.setBoolean(6, clip.isClipDeleted());
            pstmt.setString(7, clip.getClipPassword());
            pstmt.setString(8, clip.getClipPasswordSalt());
            pstmt.setString(9, clip.getClipEditorPassword());
            pstmt.setInt(10, clip.getClipWordCount());
            pstmt.setInt(11, clip.getClipReactionsCount());
            pstmt.setString(12, clip.getClipUrl());
            pstmt.execute();
            database.log("Clip updated (" + clip.getClipUrl() + ")", "DB-CLIP");
            return 1;
        } catch (SQLException e) {
            database.log("Failed to update clip (" + clip.getClipUrl() + ") - " + e.getMessage(), "DB-CLIP");
            return 0;
        }
    }

    /**
     * Function for getting a clip by its url
     * 
     * @param clipUrl - the url of the clip
     * @return the clip
     */
    public Clip getClipByUrl(String clipUrl) {
        String sql = "SELECT * FROM clip WHERE clip_url = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, clipUrl);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Clip(resultSet);
            }
        } catch (SQLException e) {
            database.log("Failed to get clip by url (" + clipUrl + ")", "DB-CLIP");
        }
        return null;
    }
}
