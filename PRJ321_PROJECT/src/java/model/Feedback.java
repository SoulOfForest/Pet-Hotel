/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author ViruSs0209
 */
public class Feedback {
    private String id, user, userID, bookingID, content, status;
    public Timestamp publishedAt, createdAt;
    private String avatar, fullName;
    private boolean gender;

    public Feedback(String id, String user, String bookingID, String content, String status, Timestamp publishedAt, Timestamp createdAt, String userID, String avatar, boolean gender, String fullName) {
        this.id = id;
        this.user = user;
        this.bookingID = bookingID;
        this.content = content;
        this.status = status;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.userID = userID;
        this.avatar = avatar;
        this.gender = gender;
        this.fullName = fullName;
    }
    
    public Feedback(String id, String user, String bookingID, String content, String status, Timestamp publishedAt, Timestamp createdAt, String userID) {
        this.id = id;
        this.user = user;
        this.bookingID = bookingID;
        this.content = content;
        this.status = status;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.userID = userID;

    }

    public Feedback() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
