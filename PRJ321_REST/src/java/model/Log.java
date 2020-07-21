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
public class Log {
    private String id, entity, entityID, userEmail, content, action;
    private Timestamp createdAt;

    public Log(String entity, String entityID, String userEmail, String content, String action, Timestamp createdAt) {
        this.entity = entity;
        this.entityID = entityID;
        this.userEmail = userEmail;
        this.content = content;
        this.action = action;
        this.createdAt = createdAt;
    }

    public Log(String id, String entity, String entityID, String userEmail, String content, String action, Timestamp createdAt) {
        this.id = id;
        this.entity = entity;
        this.entityID = entityID;
        this.userEmail = userEmail;
        this.content = content;
        this.action = action;
        this.createdAt = createdAt;
    }
    
    public Log() {
        
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    
}