/*
    clip_db database
    @author Jakub Wawak
    @version 1.0
*/
CREATE DATABASE IF NOT EXISTS  clip_db;
USE clip_db;

-- health table to store persistent data about the database
CREATE TABLE IF NOT EXISTS health
(
    database_version VARCHAR(255) NOT NULL,
    server_name VARCHAR(255) NOT NULL,
    account_creation_enabled BOOLEAN NOT NULL,
    account_creation_limit INT NOT NULL,
    private_clips_enabled BOOLEAN NOT NULL
);

INSERT INTO health 
(database_version,
server_name,
account_creation_enabled,
account_creation_limit,
private_clips_enabled) 
VALUES ("100", "clips server", true, "10", true);


-- app_logs table to store logs about the application
CREATE TABLE IF NOT EXISTS app_logs(
    id INT AUTO_INCREMENT PRIMARY KEY,
    log_type VARCHAR(255) NOT NULL,
    log_error_flag BOOLEAN NOT NULL,
    log_message TEXT NOT NULL,
    log_timestamp TIMESTAMP NOT NULL,
    log_user_id INT NOT NULL 
);

-- users table to store user data
CREATE TABLE IF NOT EXISTS user
(
    id INT AUTO_INCREMENT PRIMARY KEY,

    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    account_photo_url VARCHAR(300),
    
    background_color VARCHAR(50),
    primary_color VARCHAR(50),

    password VARCHAR(500) NOT NULL,
    password_salt VARCHAR(500) NOT NULL,
    
    created_at TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL,
    last_login TIMESTAMP NOT NULL,

    blog_url VARCHAR(255) NOT NULL,
    blog_name VARCHAR(255) NOT NULL,

    admin BOOLEAN NOT NULL
) AUTO_INCREMENT = 1000000000;

-- user_session table to store user session data
CREATE TABLE IF NOT EXISTS user_session
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    session_token VARCHAR(500) NOT NULL,
    session_created_at TIMESTAMP NOT NULL,
    session_last_used_at TIMESTAMP NOT NULL,
    session_expiry_date TIMESTAMP NOT NULL,
    session_revoked BOOLEAN NOT NULL,

    CONSTRAINT fk_user_session_user FOREIGN KEY (user_id) REFERENCES user(id)
) AUTO_INCREMENT = 1;

-- user_notifications table to store user notifications
CREATE TABLE IF NOT EXISTS user_notifications
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    notification_type VARCHAR(255) NOT NULL,
    notification_message TEXT NOT NULL,
    notification_created_at TIMESTAMP NOT NULL,
    notification_read BOOLEAN NOT NULL,

    CONSTRAINT fk_user_notifications_user FOREIGN KEY (user_id) REFERENCES user(id)
) AUTO_INCREMENT = 1;

-- clips table to store clips
CREATE TABLE IF NOT EXISTS clip
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    
    clip_url VARCHAR(255) NOT NULL,
    clip_title VARCHAR(255) NOT NULL,
    
    clip_raw TEXT NOT NULL,
    clip_extension VARCHAR(255) NOT NULL,
    
    clip_created_at TIMESTAMP NOT NULL,
    clip_updated_at TIMESTAMP NOT NULL,
    
    clip_private BOOLEAN NOT NULL,
    clip_deleted BOOLEAN NOT NULL,

    clip_password VARCHAR(255) NOT NULL,
    clip_password_salt VARCHAR(255) NOT NULL,

    clip_editor_password VARCHAR(255) NOT NULL,

    clip_word_count INT NOT NULL,
    clip_reactions_count INT NOT NULL
    
) AUTO_INCREMENT = 1000000000;

-- user_blog_collection table to store user blog collection
CREATE TABLE IF NOT EXISTS user_blog_collection
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    clip_id INT NOT NULL,
    blog_content_comments TEXT NOT NULL,
    blog_visible BOOLEAN NOT NULL,
    blog_content_created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_user_blog_collection_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_user_blog_collection_clip FOREIGN KEY (clip_id) REFERENCES clip(id)
);


