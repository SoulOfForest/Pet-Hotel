/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import com.google.gson.JsonObject;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;
import model.Log;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author ViruSs0209
 */
public class FeedbackDAO extends BaseDAO {

    public String generateId() {
        SecureRandom random = new SecureRandom();

        byte bytes[] = new byte[20];

        random.nextBytes(bytes);

        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        return uuid.toString();
    }
    
    public ArrayList<Feedback> getFeedbackByUserID(String userID) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        
        
        String sql = "SELECT Feedback.ID, Feedback.[User], Feedback.BookingID, Feedback.Content, Feedback.PublishedAt, Feedback.CreatedAt, Feedback.Status, "
                + "Users.UserID FROM Feedback JOIN Users ON Feedback.[User] = Users.Email WHERE Users.UserID = '" + userID + "'";
       
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
                
                feedbacks.add(new Feedback(id, user, bookingID, content, status, publishedAt, createdAt, rs.getString(8)));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return feedbacks;
    }
    
    public ArrayList<Feedback> getAll() {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        
        
        String sql = "SELECT Feedback.ID, Feedback.[User], Feedback.BookingID, Feedback.Content, Feedback.PublishedAt, Feedback.CreatedAt, Feedback.Status, "
                + "Users.UserID FROM Feedback JOIN Users ON Feedback.[User] = Users.Email";
       
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
                
                feedbacks.add(new Feedback(id, user, bookingID, content, status, publishedAt, createdAt, rs.getString(8)));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return feedbacks;
    }

    public void update(String content, User userById, String feedbackId, Timestamp currentTime) {
        String sql = "UPDATE [dbo].[Feedback]\n"
                + "   SET [Content] = ?\n"
                + "      ,[PublishedAt] = ?\n"
                + "      ,[Status] = ?\n"
                + " WHERE ID = '" + feedbackId + "'";

        String logSQL = "INSERT INTO [dbo].[Log]\n"
                + "           ([ID]\n"
                + "           ,[Entity]\n"
                + "           ,[Action]\n"
                + "           ,[UserEmail]\n"
                + "           ,[CreatedAt]\n"
                + "           ,[EntityID]\n"
                + "           ,[Content])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            PreparedStatement statement1 = this.connection.prepareCall(logSQL);

            LogDAO logDB = new LogDAO();
            Log log = new Log();

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("UserID", userById.getUserID());
            jsonObject.addProperty("Content", content);
            jsonObject.addProperty("Published At", currentTime.toString());
            jsonObject.addProperty("Status", "Done");

            log.setId(generateId());
            log.setUserEmail(userById.getEmail());
            log.setEntity("Feedback");
            log.setEntityID(feedbackId);
            log.setAction("Updated");
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);

            statement.setString(1, content);
            statement.setTimestamp(2, currentTime);
            statement.setString(3, "Done");

            statement1.setString(1, generateId());
            statement1.setString(2, log.getEntity());
            statement1.setString(3, log.getAction());
            statement1.setString(4, log.getUserEmail());
            statement1.setTimestamp(5, log.getCreatedAt());
            statement1.setString(6, log.getEntityID());
            statement1.setString(7, log.getContent());

            statement.executeUpdate();
            statement1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean delete(JSONArray feedbacks) {

        String deleteSQL = "DELETE FROM [dbo].[Feedback]\n"
                + "      WHERE ID IN ( ";

        for (int i = 0; i < feedbacks.length(); i++) {

            try {
                if (i == feedbacks.length() - 1) {
                    deleteSQL += "'" + feedbacks.getString(i) + "')";
                } else {
                    deleteSQL += "'" + feedbacks.getString(i) + "', ";
                }
            } catch (JSONException ex) {
                Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        try {
            PreparedStatement statement = this.connection.prepareCall(deleteSQL);

            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public Feedback getFeedbackByID(String id) {
        String sql = "SELECT Feedback.ID, Feedback.[User], Feedback.BookingID, Feedback.Content, Feedback.PublishedAt, "
                + "Feedback.CreatedAt, Feedback.Status, Users.UserID "
                + "FROM Feedback JOIN Users ON Feedback.[User] = Users.Email WHERE ID = '" + id + "'";

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

                return new Feedback(fbid, fbuser, fbbookingID, fbcontent, fbstatus, fbpublishedAt, fbcreatedAt, userID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
