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
public class Booking {
    private String id, ownerNotes, cancelNotes, managerNotes, status, receipt, userID, userName, userEmail, petName, petId;
    private double fee;
    private Timestamp arrival, departure, createdAt, updatedAt;
    private boolean extraServices;

    public Booking(String id, String ownerNotes, String cancelNotes, String managerNotes, String status, String receipt, String userID, String userName, String userEmail, String petName, String petId, double fee, boolean extraServices, Timestamp arrival, Timestamp departure, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.ownerNotes = ownerNotes;
        this.cancelNotes = cancelNotes;
        this.managerNotes = managerNotes;
        this.status = status;
        this.receipt = receipt;
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.petName = petName;
        this.petId = petId;
        this.fee = fee;
        this.arrival = arrival;
        this.departure = departure;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.extraServices = extraServices;
    }

    
    public Booking(String id, String ownerNotes, String cancelNotes, String managerNotes, String status, String receipt, double fee, boolean extraServices, Timestamp arrival, Timestamp departure, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.ownerNotes = ownerNotes;
        this.cancelNotes = cancelNotes;
        this.managerNotes = managerNotes;
        this.status = status;
        this.receipt = receipt;
        this.fee = fee;
        this.arrival = arrival;
        this.departure = departure;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.extraServices = extraServices;
    }

    public Booking(String id, String ownerNotes, String cancelNotes, String managerNotes, String status, String receipt, double fee, boolean extraServices, Timestamp arrival, Timestamp departure, Timestamp createdAt) {
        this.id = id;
        this.ownerNotes = ownerNotes;
        this.cancelNotes = cancelNotes;
        this.managerNotes = managerNotes;
        this.status = status;
        this.receipt = receipt;
        this.fee = fee;
        this.arrival = arrival;
        this.departure = departure;
        this.createdAt = createdAt;
        this.extraServices = extraServices;
    }

    public Booking() {
    }
    

    
    public boolean isExtraServices() {
        return extraServices;
    }

    public void setExtraServices(boolean extraServices) {
        this.extraServices = extraServices;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerNotes() {
        return ownerNotes;
    }

    public void setOwnerNotes(String ownerNotes) {
        this.ownerNotes = ownerNotes;
    }

    public String getCancelNotes() {
        return cancelNotes;
    }

    public void setCancelNotes(String cancelNotes) {
        this.cancelNotes = cancelNotes;
    }

    public String getManagerNotes() {
        return managerNotes;
    }

    public void setManagerNotes(String managerNotes) {
        this.managerNotes = managerNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    public Timestamp getDeparture() {
        return departure;
    }

    public void setDeparture(Timestamp departure) {
        this.departure = departure;
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
