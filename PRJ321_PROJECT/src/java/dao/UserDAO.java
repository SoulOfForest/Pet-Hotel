/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import util.BCrypt;
import java.io.InputStream;
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
import model.User;
import service.IUserService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class UserDAO extends BaseDAO implements IUserService{

    @Override
    public String hashPassword(String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return hash;
    }

    @Override
    public User getUser(String userName, String password) {
        String sql = "	SELECT Users.UserID, UserName, Status, Role, Email, Password, Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar FROM Users JOIN User_Roles ON User_Roles.UserID = Users.UserID"
                + " WHERE userName = '" + userName + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                boolean isValid = BCrypt.checkpw(password, rs.getString(6));
                System.out.println(isValid);
                if (isValid) {
                    String UserID = rs.getString(1);
                    String UserName = rs.getString(2);
                    String status = rs.getString(3);
                    String role = rs.getString(4);
                    String email = rs.getString(5);
                    String Password = rs.getString(6);
                    String firstName = rs.getString(7);
                    String lastName = rs.getString(8);
                    int age = rs.getInt(9);
                    boolean gender = rs.getBoolean(10);
                    String country = rs.getString(11);
                    Timestamp createdAt = rs.getTimestamp(12);
                    Timestamp updatedAt = rs.getTimestamp(13);
                    String avatar = rs.getString(14);

                    User user = new User(UserID, UserName, email, Password, firstName, lastName, country, role, status, gender, age, createdAt, updatedAt, avatar);

                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<User>();

        String sql = "SELECT Users.UserID, UserName, Status, Role, Email, Password, "
                + "Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar F"
                + "ROM Users JOIN User_Roles ON User_Roles.UserID = Users.UserID";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String UserID = rs.getString(1);
                String UserName = rs.getString(2);
                String status = rs.getString(3);
                String role = rs.getString(4);
                String email = rs.getString(5);
                String Password = rs.getString(6);
                String firstName = rs.getString(7);
                String lastName = rs.getString(8);
                int age = rs.getInt(9);
                boolean gender = rs.getBoolean(10);
                String country = rs.getString(11);
                Timestamp createdAt = rs.getTimestamp(12);
                Timestamp updatedAt = rs.getTimestamp(13);
                String avatar = rs.getString(14);

                User user = new User(UserID, UserName, email, Password, firstName, lastName, country, role, status, gender, age, createdAt, updatedAt, avatar);

                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }
    
    @Override
    public ArrayList<User> getListByPage(int start, int end, int itemsPerPage, ArrayList<User> totalUsers) {
        ArrayList<User> usersByPage = new ArrayList<>();

        if (end > totalUsers.size()) {
            end = totalUsers.size();
            start = end - itemsPerPage;
            
            if (start < 0) {
                start = 0;
            }
        }
        
        
        for(int i = start; i < end ; i++) {
            usersByPage.add(totalUsers.get(i));
        }

        return usersByPage;
    }
    
    public String appendQueryParam(String sql, String param, Object paramValue) {
        if (paramValue != null && !(paramValue instanceof Timestamp)) {
            if (sql.contains("WHERE")) {
                sql += " AND " + param + " LIKE '%" + paramValue.toString() + "%'";
            } else {
                sql += " WHERE " + param +  " LIKE '%" + paramValue.toString() + "%'";
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
    public ArrayList<User> search(String ID, String name, String email, String role, String status, Timestamp createdAt, Timestamp createdTo) {
        if (role != null && role.equals("all")) {
            role = "";
        } 
        
        ArrayList<User> users = new ArrayList<User>();
        
        String sql = "SELECT Users.UserID, UserName, Status, Role, Email, Password, "
                + "Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar FROM Users JOIN User_Roles ON Users.UserID = User_Roles.UserID ";
        
        sql = appendQueryParam(sql, "Users.UserID", ID);
        sql = appendQueryParam(sql, "( FName + LName )", name);
        sql = appendQueryParam(sql, "Email", email);
        sql = appendQueryParam(sql, "Role", role);
        sql = appendQueryParam(sql, "Status", status);
        
        if (createdAt != null && createdTo != null) {
            sql = appendQueryParam(sql, "CreatedAt BETWEEN '" + createdAt.toString() + "' AND '" + createdTo.toString() + "'", createdAt);
        } else if (createdAt != null && createdTo == null) {
            sql = appendQueryParam(sql, "CreatedAt >= '" + createdAt.toString() + "'", createdAt);
        } else if (createdAt == null && createdTo != null) {
            sql = appendQueryParam(sql, "CreatedAt <= '" + createdTo.toString() + "'", createdTo);
        }
       
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String UserID = rs.getString(1);
                String UserName = rs.getString(2);
                String Password = rs.getString(6);
                String firstName = rs.getString(7);
                String lastName = rs.getString(8);
                int age = rs.getInt(9);
                boolean gender = rs.getBoolean(10);
                String country = rs.getString(11);
                Timestamp updatedAt = rs.getTimestamp(13);
                String avatar = rs.getString(14);

                User user = new User(UserID, UserName, rs.getString(5), Password, firstName, lastName, country, rs.getString(4), rs.getString(3), gender, age, rs.getTimestamp(12), updatedAt, avatar);

                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }
    
    @Override
    public User getByID(String id) {
        String sql = "SELECT Users.UserID, UserName, Status, Role, Email, Password, "
                + "Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar F"
                + "ROM Users JOIN User_Roles ON User_Roles.UserID = Users.UserID WHERE Users.UserID = '" + id  + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String UserID = rs.getString(1);
                String UserName = rs.getString(2);
                String status = rs.getString(3);
                String role = rs.getString(4);
                String email = rs.getString(5);
                String Password = rs.getString(6);
                String firstName = rs.getString(7);
                String lastName = rs.getString(8);
                int age = rs.getInt(9);
                boolean gender = rs.getBoolean(10);
                String country = rs.getString(11);
                Timestamp createdAt = rs.getTimestamp(12);
                Timestamp updatedAt = rs.getTimestamp(13);
                String avatar = rs.getString(14);

                User user = new User(UserID, UserName, email, Password, firstName, lastName, country, role, status, gender, age, createdAt, updatedAt, avatar);
                
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE [dbo].[Users]\n"
                + "   SET [Fname] = ?\n"
                + "      ,[LName] = ?\n"
                + "      ,[Age] = ?\n"
                + "      ,[Gender] = ?\n"
                + "      ,[Country] = ?\n"
                + "      ,[UpdatedAt] = ?\n"
                + "      ,[Avatar] = ?\n"
                + "      ,[Status] = ?\n";

        try {
            sql += " WHERE UserID = '" + user.getUserID() + "'";

            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setBoolean(4, user.isGender());
            statement.setString(5, user.getCountry());
            statement.setTimestamp(6, user.getUpdatedAt());
            statement.setString(7, user.getAvatar());
            statement.setString(8, user.getStatus());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Error isValidRegistration(User user) {
        boolean isDuplicate = false;

        String sql = "SELECT UserName, Email FROM Users";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String userName = rs.getString(1);
                String email = rs.getString(2);

                if (user.getEmail().equals(email)) {
                    return new Error("email");
                } else if (user.getUserName().equals(userName)) {
                    return new Error("userName");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void register(User user) {
        String hashedPassword = hashPassword(user.getPassword());

        String sqlUserInsert = "INSERT INTO [dbo].[Users]\n"
                + "           ([UserID]\n"
                + "           ,[UserName]\n"
                + "           ,[Status]\n"
                + "           ,[Email]\n"
                + "           ,[Password]\n"
                + "           ,[Fname]\n"
                + "           ,[LName]\n"
                + "           ,[Age]\n"
                + "           ,[Gender]\n"
                + "           ,[Country]\n"
                + "           ,[CreatedAt])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        String sqlUserRoleInsert = "INSERT INTO [dbo].[User_Roles]\n"
                + "           ([UserID]\n"
                + "           ,[Role])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?)";

        try {
            PreparedStatement firstStatement = this.connection.prepareCall(sqlUserInsert);
            PreparedStatement secondStatement = this.connection.prepareCall(sqlUserRoleInsert);

            firstStatement.setString(1, user.getUserID());
            firstStatement.setString(2, user.getUserName());
            firstStatement.setString(3, user.getStatus());
            firstStatement.setString(4, user.getEmail());
            firstStatement.setString(5, hashedPassword);
            firstStatement.setString(6, user.getFirstName());
            firstStatement.setString(7, user.getLastName());
            firstStatement.setInt(8, user.getAge());
            firstStatement.setBoolean(9, user.isGender());
            firstStatement.setString(10, user.getCountry());
            firstStatement.setTimestamp(11, user.getCreatedAt());

            secondStatement.setString(1, user.getUserID());
            secondStatement.setString(2, user.getRole());

            firstStatement.executeUpdate();
            secondStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
