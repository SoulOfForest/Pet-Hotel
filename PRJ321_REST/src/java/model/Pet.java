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
public class Pet {
    private String ID, avatar, name, type, breed, size, owner, ownerID;
    private Timestamp createdAt, updatedAt;

    public Pet(String ID, String avatar, String name, String type, String breed, String size, String owner, Timestamp createdAt, Timestamp updatedAt) {
        this.ID = ID;
        this.avatar = avatar;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.size = size;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Pet(String ID, String avatar, String name, String type, String breed, String size, String owner, String ownerID, Timestamp createdAt, Timestamp updatedAt) {
        this.ID = ID;
        this.avatar = avatar;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.size = size;
        this.owner = owner;
        this.ownerID = ownerID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    

    public Pet(String ID, String avatar, String name, String type, String breed, String size, String owner, Timestamp createdAt) {
        this.ID = ID;
        this.avatar = avatar;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.size = size;
        this.owner = owner;
        this.createdAt = createdAt;
    }

    

    public Pet() {
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
   

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
