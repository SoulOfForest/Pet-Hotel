/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ViruSs0209
 */
public class UserReport {
    private String year, month;
    private int numOfUsers;

    public UserReport(String month, String year, int numOfUsers) {
        this.year = year;
        this.month = month;
        this.numOfUsers = numOfUsers;
    }

    public UserReport() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNumOfUsers() {
        return numOfUsers;
    }

    public void setNumOfUsers(int numOfUsers) {
        this.numOfUsers = numOfUsers;
    }
    
    
}
