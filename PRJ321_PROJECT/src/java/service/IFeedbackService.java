/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import model.Feedback;

/**
 *
 * @author ViruSs0209
 */
public interface IFeedbackService {
    public ArrayList<Feedback> getAll();
    
     public ArrayList<Feedback> getFeedbacksByUser(String userEmail);
     
     public ArrayList<Feedback> getNonFeedbacksByUser(String userEmail);
     
     public ArrayList<Feedback> search(String id, String owner, String bookingID, String status, Timestamp createdAt, Timestamp createdTo, Timestamp publishedAt, Timestamp publishedTo);
     
     public void insert(Feedback feedback);
     
     public ArrayList<Feedback> getListByPage(int start, int end, int itemsPerPage, ArrayList<Feedback> totalFeedbacks);
}
