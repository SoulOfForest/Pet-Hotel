/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author ViruSs0209
 */
public class User {
    private String userID, userName, email, password, firstName, lastName, country, role, status, avatar;
    private boolean gender;
    private int age;
    private Timestamp createdAt, updatedAt;

    public User(String userID, String userName, String email, String password, String firstName, String lastName, String country, String role, String status, boolean gender, int age, Timestamp createdAt, Timestamp updatedAt, String avatar) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.role = role;
        this.status = status;
        this.gender = gender;
        this.age = age;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.avatar = avatar;
    }

    public User(String userID, String userName, String email, String password, String firstName, String lastName, String country, String role, String status, boolean gender, int age, Timestamp createdAt) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.role = role;
        this.status = status;
        this.gender = gender;
        this.age = age;
        this.createdAt = createdAt;
    }
    
    public User(String userName, String email, String password, String firstName, String lastName, String country, String role, String status, boolean gender, int age, Timestamp createdAt) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.role = role;
        this.status = status;
        this.gender = gender;
        this.age = age;
        this.createdAt = createdAt;
    }
    
    

    public User() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
}
