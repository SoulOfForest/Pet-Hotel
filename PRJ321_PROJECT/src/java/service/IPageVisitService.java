/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.PageVisit;

/**
 *
 * @author ViruSs0209
 */
public interface IPageVisitService {
    public PageVisit isExist(PageVisit page);
    
    public void update(PageVisit page);
    
    public void insert(PageVisit page);
}
