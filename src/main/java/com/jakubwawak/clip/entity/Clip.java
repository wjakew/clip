/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.jakubwawak.clip.ClipApplication;
import com.jakubwawak.clip.database.DatabaseClip;

/**
 * Clip class for storing and retrieving clip data
 */
public class Clip {
    private int id;
    private int userId;
    private String clipUrl;
    private String clipTitle;
    private String clipRaw;
    private String clipExtension;
    private Timestamp clipCreatedAt;
    private Timestamp clipUpdatedAt;
    private boolean clipPrivate;
    private boolean clipDeleted;
    private String clipPassword;
    private String clipPasswordSalt;
    private String clipEditorPassword;
    private int clipWordCount;
    private int clipReactionsCount;

    /**
     * Constructor for the Clip class
     * @param id - the id of the clip
     * @param userId - the id of the user
     * @param clipUrl - the url of the clip
     * @param clipTitle - the title of the clip
     * @param clipRaw - the raw content of the clip
     * @param clipExtension - the extension of the clip
     * @param clipCreatedAt - the creation date of the clip
     * @param clipUpdatedAt - the last update date of the clip
     * @param clipPrivate - whether the clip is private
     * @param clipDeleted - whether the clip is deleted
     * @param clipPassword - the password of the clip
     * @param clipPasswordSalt - the salt of the password of the clip
     * @param clipEditorPassword - the password of the editor of the clip
     * @param clipWordCount - the word count of the clip
     * @param clipReactionsCount - the reactions count of the clip
     */
    public Clip(int id, int userId, String clipUrl, String clipTitle, String clipRaw, String clipExtension,
                Timestamp clipCreatedAt, Timestamp clipUpdatedAt, boolean clipPrivate, boolean clipDeleted,
                String clipPassword, String clipPasswordSalt, String clipEditorPassword,
                int clipWordCount, int clipReactionsCount) {
        this.id = id;
        this.userId = userId;
        this.clipUrl = clipUrl;
        this.clipTitle = clipTitle;
        this.clipRaw = clipRaw;
        this.clipExtension = clipExtension;
        this.clipCreatedAt = clipCreatedAt;
        this.clipUpdatedAt = clipUpdatedAt;
        this.clipPrivate = clipPrivate;
        this.clipDeleted = clipDeleted;
        this.clipPassword = clipPassword;
        this.clipPasswordSalt = clipPasswordSalt;
        this.clipEditorPassword = clipEditorPassword;
        this.clipWordCount = clipWordCount;
        this.clipReactionsCount = clipReactionsCount;
    }

    /**
     * Constructor for the Clip class
     * @param resultSet - the result set of the clip
     */
    public Clip(ResultSet resultSet){
        try{
            this.id = resultSet.getInt("id");
            this.userId = resultSet.getInt("user_id");
            this.clipUrl = resultSet.getString("clip_url");
            this.clipTitle = resultSet.getString("clip_title");
            this.clipRaw = resultSet.getString("clip_raw");
            this.clipExtension = resultSet.getString("clip_extension");
            this.clipCreatedAt = resultSet.getTimestamp("clip_created_at");
            this.clipUpdatedAt = resultSet.getTimestamp("clip_updated_at");
            this.clipPrivate = resultSet.getBoolean("clip_private");
            this.clipDeleted = resultSet.getBoolean("clip_deleted");
            this.clipPassword = resultSet.getString("clip_password");
            this.clipPasswordSalt = resultSet.getString("clip_password_salt");
            this.clipEditorPassword = resultSet.getString("clip_editor_password");
            this.clipWordCount = resultSet.getInt("clip_word_count");
            this.clipReactionsCount = resultSet.getInt("clip_reactions_count");
        }
        catch(SQLException e){
            ClipApplication.database.log("Error creating clip from result set (" + e.toString() + ")", "DB-CLIP");
        }
    }

    /**
     * Constructor for the Clip class
     */
    public Clip(){
        DatabaseClip databaseClip = new DatabaseClip();
        this.id = 0;
        this.userId = 0;
        this.clipUrl = databaseClip.calculateClipUrl();
        this.clipTitle = "";
        this.clipRaw = "";
        this.clipExtension = "";
        this.clipCreatedAt = new Timestamp(System.currentTimeMillis());
        this.clipUpdatedAt = new Timestamp(System.currentTimeMillis());
        this.clipPrivate = false;
        this.clipDeleted = false;
        this.clipPassword = "";
        this.clipPasswordSalt = "";
        this.clipEditorPassword = "";
        this.clipWordCount = 0;
        this.clipReactionsCount = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClipUrl() {
        return clipUrl;
    }

    public void setClipUrl(String clipUrl) {
        this.clipUrl = clipUrl;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getClipRaw() {
        return clipRaw;
    }

    public void setClipRaw(String clipRaw) {
        this.clipRaw = clipRaw;
    }

    public String getClipExtension() {
        return clipExtension;
    }

    public void setClipExtension(String clipExtension) {
        this.clipExtension = clipExtension;
    }

    public Timestamp getClipCreatedAt() {
        return clipCreatedAt;
    }

    public void setClipCreatedAt(Timestamp clipCreatedAt) {
        this.clipCreatedAt = clipCreatedAt;
    }

    public Timestamp getClipUpdatedAt() {
        return clipUpdatedAt;
    }

    public void setClipUpdatedAt(Timestamp clipUpdatedAt) {
        this.clipUpdatedAt = clipUpdatedAt;
    }

    public boolean isClipPrivate() {
        return clipPrivate;
    }

    public void setClipPrivate(boolean clipPrivate) {
        this.clipPrivate = clipPrivate;
    }

    public boolean isClipDeleted() {
        return clipDeleted;
    }

    public void setClipDeleted(boolean clipDeleted) {
        this.clipDeleted = clipDeleted;
    }

    public String getClipPassword() {
        return clipPassword;
    }

    public void setClipPassword(String clipPassword) {
        this.clipPassword = clipPassword;
    }

    public String getClipPasswordSalt() {
        return clipPasswordSalt;
    }

    public void setClipPasswordSalt(String clipPasswordSalt) {
        this.clipPasswordSalt = clipPasswordSalt;
    }

    public String getClipEditorPassword() {
        return clipEditorPassword;
    }

    public void setClipEditorPassword(String clipEditorPassword) {
        this.clipEditorPassword = clipEditorPassword;
    }

    public int getClipWordCount() {
        return clipWordCount;
    }

    public void setClipWordCount(int clipWordCount) {
        this.clipWordCount = clipWordCount;
    }

    public int getClipReactionsCount() {
        return clipReactionsCount;
    }

    public void setClipReactionsCount(int clipReactionsCount) {
        this.clipReactionsCount = clipReactionsCount;
    }
}
