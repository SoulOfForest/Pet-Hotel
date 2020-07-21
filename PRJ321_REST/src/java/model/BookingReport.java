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
public class BookingReport {
    private String month;
    private String year;
    private String week;
    private String day;
    private String status;
    private int bookingsInMonth;
    private double fee;
    
    

    public BookingReport(String month, String year, int bookingsInMonth, double fee) {
        this.month = month;
        this.year = year;
        this.bookingsInMonth = bookingsInMonth;
        this.fee = fee;
    }
    
    public BookingReport(String week, String month, String year, int bookingsInMonth, double fee) {
        this.month = month;
        this.year = year;
        this.week = week;
        this.bookingsInMonth = bookingsInMonth;
        this.fee = fee;
    }
    
    public BookingReport(String day, String month, double fee) {
        this.day = day;
        this.month = month;
        this.fee = fee;
    }

    public BookingReport(String status, int bookings) {
        this.status = status;
        this.bookingsInMonth = bookings;
    }

    public BookingReport() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
    
    

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getBookingsInMonth() {
        return bookingsInMonth;
    }

    public void setBookingsInMonth(int bookingsInMonth) {
        this.bookingsInMonth = bookingsInMonth;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    
    
}
