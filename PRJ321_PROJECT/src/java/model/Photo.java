/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 *
 * @author ViruSs0209
 */
public class Photo {
    private String id;
    private Timestamp createdAt;
    private Blob  image;
    private String type;

    public Photo(String id, String type, Timestamp createdAt, Blob  image) {
        this.id = id;
        this.createdAt = createdAt;
        this.image = image;
        this.type = type;
    }

    public Photo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Blob  getImage() {
        return image;
    }

    public void setImage(Blob  image) {
        this.image = image;
    }
    
    
}
