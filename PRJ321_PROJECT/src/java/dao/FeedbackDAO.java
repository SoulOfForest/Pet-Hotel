/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import model.Feedback;
import service.IFeedbackService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class FeedbackDAO extends BaseDAO implements IFeedbackService{
    @Override
    public ArrayList<Feedback> getAll() {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        
        String sql = "SELECT Feedback.ID, [User], BookingID, Content, PublishedAt, Feedback.CreatedAt, Feedback.Status, "
                + "Users.UserID, Users.Avatar, Users.Gender, Users.Fname + ' ' + Users.LName AS 'FullName' "
                + "FROM Feedback JOIN Users ON Users.Email = Feedback.[User] LEFT JOIN Photos ON Photos.ID = Users.Avatar";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString(1);
                String user = rs.getString(2);
                String bookingID = rs.getString(3);
                String content = rs.getString(4);
                Timestamp publishedAt = rs.getTimestamp(5);
                Timestamp createdAt = rs.getTimestamp(6);
                String status = rs.getString(7);
                String userID = rs.getString(8);
                String avatar = rs.getString(9);
                boolean gender = rs.getBoolean(10);
                String fullName = rs.getString(11);
                
                feedbacks.add(new Feedback(id, user, bookingID, content, status, publishedAt, createdAt, userID, avatar, gender, fullName));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return feedbacks;
    }
    
    @Override
    public ArrayList<Feedback> getFeedbacksByUser(String userEmail) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        
        String sql = "SELECT Feedback.ID, Feedback.[User], Feedback.BookingID, Feedback.Content, Feedback.PublishedAt, "
                + "Feedback.CreatedAt, Feedback.Status, Users.UserID "
                + "FROM Feedback JOIN Users ON Feedback.[User] = Users.Email WHERE Feedback.[User] = '" + userEmail + "'";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString(1);
                String user = rs.getString(2);
                String bookingID = rs.getString(3);
                String content = rs.getString(4);
                Timestamp publishedAt = rs.getTimestamp(5);
                Timestamp createdAt = rs.getTimestamp(6);
                String status = rs.getString(7);
                String userID = rs.getString(8);
                
                
                feedbacks.add(new Feedback(id, user, bookingID, content, status, publishedAt, createdAt, userID));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return feedbacks;
    }
    
    @Override
    public ArrayList<Feedback> getNonFeedbacksByUser(String userEmail) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        
        String sql = "SELECT Feedback.ID, Feedback.[User], Feedback.BookingID, Feedback.Content, Feedback.PublishedAt, "
                + "Feedback.CreatedAt, Feedback.Status, Users.UserID "
                + "FROM Feedback JOIN Users ON Feedback.[User] = Users.Email WHERE Feedback.[User] = '" + userEmail + "' And Content IS NULL";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString(1);
                String user = rs.getString(2);
                String bookingID = rs.getString(3);
                String content = rs.getString(4);
                Timestamp publishedAt = rs.getTimestamp(5);
                Timestamp createdAt = rs.getTimestamp(6);
                String status = rs.getString(7);
                String userID = rs.getString(8);
                
                
                feedbacks.add(new Feedback(id, user, bookingID, content, status, publishedAt, createdAt, userID));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return feedbacks;
    }
    
    public String appendQueryParam(String sql, String param, Object paramValue) {
        if (paramValue != null && !(paramValue instanceof Timestamp)) {
            if (sql.contains("WHERE")) {
                sql += " AND " + param + " LIKE '%" + paramValue.toString() + "%'";
            } else {
                sql += " WHERE " + param + " LIKE '%" + paramValue.toString() + "%'";
            }
        } else if (paramValue != null){
            if (sql.contains("WHERE")) {
                sql += " AND " + param;
            } else {
                sql += " WHERE " + param;
            }
        }

        return sql;
    }
    
    @Override
    public ArrayList<Feedback> search(String id, String owner, String bookingID, String status, Timestamp createdAt, Timestamp createdTo, Timestamp publishedAt, Timestamp publishedTo) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        
        String sql = "SELECT Feedback.ID, Feedback.[User], Feedback.BookingID, Feedback.Content, Feedback.PublishedAt, "
                + "Feedback.CreatedAt, Feedback.Status, Users.UserID "
                + "FROM Feedback JOIN Users ON Feedback.[User] = Users.Email";
        
        sql = appendQueryParam(sql, "BookingID", bookingID);
        sql = appendQueryParam(sql, "Feedback.[User]", owner);
        sql = appendQueryParam(sql, "Feedback.ID", id);
        
        if (status == null) {
            status = "";
        }
        
        sql = appendQueryParam(sql, "Feedback.Status", status);
        
        if (createdAt != null && createdTo != null) {
            sql = appendQueryParam(sql, "Feedback.CreatedAt BETWEEN '" + createdAt.toString() + "' AND '" + createdTo.toString() + "'", createdAt);
        } else if (createdAt != null && createdTo == null) {
            sql = appendQueryParam(sql, "Feedback.CreatedAt >= '" + createdAt.toString() + "'", createdAt);
        } else if (createdAt == null && createdTo != null) {
            sql = appendQueryParam(sql, "Feedback.CreatedAt <= '" + createdTo.toString() + "'", createdTo);
        }
        
        if (publishedAt != null && publishedTo != null) {
            sql = appendQueryParam(sql, "Feedback.PublishedAt BETWEEN '" + publishedAt.toString() + "' AND '" + publishedTo.toString() + "'", publishedAt);
        } else if (publishedAt != null && publishedTo == null) {
            sql = appendQueryParam(sql, "Feedback.PublishedAt >= '" + publishedAt.toString() + "'", publishedAt);
        } else if (publishedAt == null && publishedTo != null) {
            sql = appendQueryParam(sql, "Feedback.PublishedAt <= '" + publishedTo.toString() + "'", publishedTo);
        }
        
        System.out.println(sql);
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                String fbid = rs.getString(1);
                String fbuser = rs.getString(2);
                String fbbookingID = rs.getString(3);
                String fbcontent = rs.getString(4);
                Timestamp fbpublishedAt = rs.getTimestamp(5);
                Timestamp fbcreatedAt = rs.getTimestamp(6);
                String fbstatus = rs.getString(7);
                String userID = rs.getString(8);
                
                feedbacks.add(new Feedback(fbid, fbuser, fbbookingID, fbcontent, fbstatus, fbpublishedAt, fbcreatedAt, userID));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return feedbacks;
    }
    
    @Override
    public void insert(Feedback feedback) {
        String sql = "INSERT INTO [dbo].[Feedback]\n" +
"           ([ID]\n" +
"           ,[User]\n" +
"           ,[BookingID]\n" +
"           ,[Content]\n" +
"           ,[CreatedAt]\n" +
"           ,[Status])\n" +
"     VALUES\n" +
"           (?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?)";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            statement.setString(1, feedback.getId());
            statement.setString(2, feedback.getUser());
            statement.setString(3, feedback.getBookingID());
            statement.setString(4, feedback.getContent());
            statement.setTimestamp(5, feedback.getCreatedAt());
            statement.setString(6, feedback.getStatus());
            
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public ArrayList<Feedback> getListByPage(int start, int end, int itemsPerPage, ArrayList<Feedback> totalFeedbacks) {
        ArrayList<Feedback> feedbacksPerPage = new ArrayList<>();

        if (end > totalFeedbacks.size()) {
            end = totalFeedbacks.size();
            start = end - itemsPerPage;

            if (start < 0) {
                start = 0;
            }
        }

        for (int i = start; i < end; i++) {
            feedbacksPerPage.add(totalFeedbacks.get(i));
        }

        return feedbacksPerPage;
    }
}
