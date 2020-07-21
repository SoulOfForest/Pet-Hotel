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
public class Setting {
    private String id, theme, color;
    private double largePetFee;
    private double smallPetFee;
    private double mediumPetFee;
    private double extraFee;
    private Timestamp updatedAt, createdAt;
    private int capacity;

    public Setting(String id, String theme, String color) {
        this.id = id;
        this.theme = theme;
        this.color = color;
    }

    public Setting(String id, String theme, double largePetFee, double smallPetFee, double mediumPetFee, double extraFee, int capacity, Timestamp updatedAt, Timestamp createdAt) {
        this.id = id;
        this.theme = theme;
        this.largePetFee = largePetFee;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.capacity = capacity;
        this.smallPetFee = smallPetFee;
        this.mediumPetFee = mediumPetFee;
        this.extraFee = extraFee;
    }

    public Setting(String id, String theme, double largePetFee, double smallPetFee, double mediumPetFee, double extraFee, int capacity, Timestamp createdAt) {
        this.id = id;
        this.theme = theme;
        this.largePetFee = largePetFee;
        this.createdAt = createdAt;
        this.smallPetFee = smallPetFee;
        this.mediumPetFee = mediumPetFee;
        this.extraFee = extraFee;
    }
    
//    public Setting(String theme, double fee, Timestamp createdAt) {
//        this.id = id;
//        this.theme = theme;
//        this.largePetFee = fee;
//        this.createdAt = createdAt;
//    }

    public Setting() {
    }

    public double getSmallPetFee() {
        return smallPetFee;
    }

    public void setSmallPetFee(double smallPetFee) {
        this.smallPetFee = smallPetFee;
    }

    public double getMediumPetFee() {
        return mediumPetFee;
    }

    public void setMediumPetFee(double mediumPetFee) {
        this.mediumPetFee = mediumPetFee;
    }

    public double getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(double extraFee) {
        this.extraFee = extraFee;
    }
    
    

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }   

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public double getFee() {
        return largePetFee;
    }

    public void setFee(double fee) {
        this.largePetFee = fee;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
