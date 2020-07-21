/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Log;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author ViruSs0209
 */
public class LogDAO extends BaseDAO {

    public String generateId() {
        SecureRandom random = new SecureRandom();
        
        byte bytes[] = new byte[20];
        
        random.nextBytes(bytes);        
        
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        
        return uuid.toString();        
    }
    
    public ArrayList<Log> getAll() {
        ArrayList<Log> logs = new ArrayList<Log>();
        
        String sql = "SELECT * FROM Log";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                String ID = rs.getString(1);
                String entity = rs.getString(2);
                String action = rs.getString(3);
                String userEmail = rs.getString(4);
                Timestamp createdAt = rs.getTimestamp(5);
                String entityID = rs.getString(6);
                String content = rs.getString(7);
                
                Log log = new Log(ID, entity, entityID, userEmail, content, action, createdAt);

                logs.add(log);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return logs;
    }
    
    public boolean insert(String userID, String disableUsers, String action) {
        
        try {
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            Gson jsonConverter = new Gson();
            JsonObject jsonObject = new JsonObject();
            JsonArray updatedUserIDs = new JsonArray();
            
            UserDAO db = new UserDAO();
            
            User user = db.getByID(userID);
            ArrayList<User> usersByEmail = db.getUsersByEmails(disableUsers);
         
            for (User u : usersByEmail) {
                updatedUserIDs.add(u.getUserID());
            }                       
            
            Log log = new Log();
            
            jsonObject.addProperty("userID", user.getUserID());
            if (action.equalsIgnoreCase("removed")) {
                jsonObject.add("removedUsers", updatedUserIDs);
            }
            else {
                jsonObject.add("updatedUser", updatedUserIDs);
            }
            jsonObject.addProperty("Status", action);
            
            log.setUserEmail(user.getEmail());
            log.setEntity("User");
            log.setEntityID(user.getUserID());
            
            if (action.equalsIgnoreCase("removed")) {
                log.setAction("Removed");
            } else {
                log.setAction("Updated");
            }
            
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);
            
            String sql = "INSERT INTO [dbo].[Log]\n"
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
                
                statement.setString(1, generateId());
                statement.setString(2, log.getEntity());
                statement.setString(3, log.getAction());
                statement.setString(4, log.getUserEmail());
                statement.setTimestamp(5, log.getCreatedAt());
                statement.setString(6, log.getEntityID());
                statement.setString(7, log.getContent());
                
                statement.executeUpdate();
                
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean insertPetLog(String userID, String x, String action) {   
        try {
            x = x.replaceAll("[\\[\\]\"]", "");
            String[] petIDs = x.split(",");

        
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            Gson jsonConverter = new Gson();
            JsonObject jsonObject = new JsonObject();
            JsonArray deletedPetIDs = new JsonArray();
            
            UserDAO db = new UserDAO();
            PetDAO petDB = new PetDAO();
            
            User user = db.getByID(userID);
            
            for (int i = 0; i < petIDs.length; i++) {
                deletedPetIDs.add(petIDs[i]);
            }                       
            
            Log log = new Log();
            
            jsonObject.addProperty("userID", user.getUserID());
            jsonObject.add("removedPets", deletedPetIDs);
            jsonObject.addProperty("Status", action);
            
            log.setUserEmail(user.getEmail());
            log.setEntity("Pet");
            log.setEntityID(user.getUserID());
            log.setAction("Removed");
            
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);
            
            String sql = "INSERT INTO [dbo].[Log]\n"
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
                
                statement.setString(1, generateId());
                statement.setString(2, log.getEntity());
                statement.setString(3, log.getAction());
                statement.setString(4, log.getUserEmail());
                statement.setTimestamp(5, log.getCreatedAt());
                statement.setString(6, log.getEntityID());
                statement.setString(7, log.getContent());
                
                statement.executeUpdate();
                
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean insertBookingLogs(String userID, String x, String action) {   
        try {
            x = x.replaceAll("[\\[\\]\"]", "");
            String[] bookingIDs = x.split(",");

        
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            Gson jsonConverter = new Gson();
            JsonObject jsonObject = new JsonObject();
            JsonArray deletedBookingIDs = new JsonArray();
            
            UserDAO db = new UserDAO();
            PetDAO petDB = new PetDAO();
            
            User user = db.getByID(userID);
            
            for (int i = 0; i < bookingIDs.length; i++) {
                deletedBookingIDs.add(bookingIDs[i]);
            }                       
            
            Log log = new Log();
            
            jsonObject.addProperty("userID", user.getUserID());
            jsonObject.add("removedBookings", deletedBookingIDs);
            jsonObject.addProperty("Status", action);
            
            log.setUserEmail(user.getEmail());
            log.setEntity("Booking");
            log.setEntityID(user.getUserID());
            log.setAction("Removed");
            
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);
            
            String sql = "INSERT INTO [dbo].[Log]\n"
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
                
                statement.setString(1, generateId());
                statement.setString(2, log.getEntity());
                statement.setString(3, log.getAction());
                statement.setString(4, log.getUserEmail());
                statement.setTimestamp(5, log.getCreatedAt());
                statement.setString(6, log.getEntityID());
                statement.setString(7, log.getContent());
                
                statement.executeUpdate();
                
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    
    public boolean insertFeedbackLog(String userID, JSONArray feedbacks, String action) {   
        try {
        
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            Gson jsonConverter = new Gson();
            JsonObject jsonObject = new JsonObject();
            JsonArray deletedFeedbackIds = new JsonArray();
            
            UserDAO db = new UserDAO();
            FeedbackDAO feedbackDB = new FeedbackDAO();
            
            User user = db.getByID(userID);
            
            for (int i = 0; i < feedbacks.length(); i++) {
                try {
                    deletedFeedbackIds.add(feedbacks.getString(i));
                } catch (JSONException ex) {
                    Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                       
            
            Log log = new Log();
            
            jsonObject.addProperty("userID", user.getUserID());
            jsonObject.add("removedFeedbacks", deletedFeedbackIds);
            jsonObject.addProperty("Status", action);
            
            log.setUserEmail(user.getEmail());
            log.setEntity("Feedback");
            log.setEntityID(user.getUserID());
            log.setAction("Removed");
            
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);
            
            String sql = "INSERT INTO [dbo].[Log]\n"
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
                
                statement.setString(1, generateId());
                statement.setString(2, log.getEntity());
                statement.setString(3, log.getAction());
                statement.setString(4, log.getUserEmail());
                statement.setTimestamp(5, log.getCreatedAt());
                statement.setString(6, log.getEntityID());
                statement.setString(7, log.getContent());
                
                statement.executeUpdate();
                
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void insert(Log log) {
        String sql = "INSERT INTO [dbo].[Log]\n" +
"           ([ID]\n" +
"           ,[Entity]\n" +
"           ,[Action]\n" +
"           ,[UserEmail]\n" +
"           ,[CreatedAt]\n" +
"           ,[EntityID]\n" +
"           ,[Content])\n" +
"     VALUES\n" +
"           (?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?\n" +
"           ,?)";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            statement.setString(1, generateId());
            statement.setString(2, log.getEntity());
            statement.setString(3, log.getAction());
            statement.setString(4, log.getUserEmail());
            statement.setTimestamp(5, log.getCreatedAt());
            statement.setString(6, log.getEntityID());
            statement.setString(7, log.getContent());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
