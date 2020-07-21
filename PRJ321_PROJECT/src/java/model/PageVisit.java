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
public class PageVisit {
    private String month, year, page;
    private int visit, id;

    public PageVisit(String month, String year, String page, int visit, int id) {
        this.month = month;
        this.year = year;
        this.page = page;
        this.visit = visit;
        this.id = id;
    }
    
    public PageVisit(String month, String year, String page, int visit) {
        this.month = month;
        this.year = year;
        this.page = page;
        this.visit = visit;
    }

    public PageVisit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }
    
    
}
