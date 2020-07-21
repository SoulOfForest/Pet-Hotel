/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import model.Log;
import model.User;
import service.ILogService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class LogDAO extends BaseDAO implements ILogService{
    public String generateId() {
        SecureRandom random = new SecureRandom();
        
        byte bytes[] = new byte[20];
        
        random.nextBytes(bytes);             
        
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
         
        return uuid.toString();        
    }
    
    @Override
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
    
    @Override
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
    
    @Override
    public ArrayList<Log> getByID(String userID) {
        ArrayList<Log> logs = new ArrayList<Log>();
        
        String sql = "SELECT * FROM Log WHERE EntityID = '" + userID + "'";
        
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
    
    @Override
    public ArrayList<Log> getListByPage(int start, int end, int itemsPerPage, ArrayList<Log> totalLogs) {
        ArrayList<Log> logsByPage = new ArrayList<>();
        
        if (end > totalLogs.size()) {
            end = totalLogs.size();
            start = end - itemsPerPage;
            
            if (start < 0) {
                start = 0;
            }
        }
        
        for(int i = start; i < end ; i++) {
            logsByPage.add(totalLogs.get(i));
        }
        
        return logsByPage;
    }
    
    public String appendQueryParam(String sql, String param, Object paramValue) {
        if (paramValue != null && !(paramValue instanceof Timestamp)) {
            if (sql.contains("WHERE")) {
                sql += " AND " + param + " LIKE '%" + paramValue.toString() + "%'";
            } else {
                sql += " WHERE " + param +  " LIKE '%" + paramValue.toString() + "%'";
            }
        } else {
            if (sql.contains("WHERE")) {
                sql += " AND " + param; 
            } else {
                sql += " WHERE " + param;
            }
        }
        
        return sql;
    }
    
    @Override
    public ArrayList<Log> search(String ID, String userEmail, String entity, String action, Timestamp createdAt, Timestamp createdTo) {
        ArrayList<Log> logs = new ArrayList<Log>();
        
        String sql = "SELECT * FROM Log";
        
        sql = appendQueryParam(sql, "Entity", entity);
        sql = appendQueryParam(sql, "EntityID", ID);
        sql = appendQueryParam(sql, "UserEmail", userEmail);
        sql = appendQueryParam(sql, "Action ", action);

        if (createdAt != null && createdTo != null) {
            sql = appendQueryParam(sql, "CreatedAt BETWEEN '" + createdAt.toString() + "' AND '" + createdTo.toString() + "'", createdAt);
        } else if (createdAt != null && createdTo == null) {
            sql = appendQueryParam(sql, "CreatedAt >= '" + createdAt.toString() + "'", createdAt);
        } else if (createdAt == null && createdTo != null) {
            sql = appendQueryParam(sql, "CreatedAt <= '" + createdTo.toString() + "'", createdTo);
        }
       
        System.out.println(sql);
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                Log log = new Log(rs.getString(1), rs.getString(2), rs.getString(6), rs.getString(4), rs.getString(7), rs.getString(3), rs.getTimestamp(5));

                logs.add(log);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return logs;
    }
    
}
