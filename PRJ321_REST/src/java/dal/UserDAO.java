/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import JBCrypt.BCrypt;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import model.UserReport;

/**
 *
 * @author ViruSs0209
 */
public class UserDAO extends BaseDAO {

    public String generateUserId() {
        SecureRandom random = new SecureRandom();

        byte bytes[] = new byte[20];

        random.nextBytes(bytes);

        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        return uuid.toString();
    }

    public String hashPassword(String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));

        return hash;
    }

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

    public void register(User user) {
        String hashedPassword = hashPassword(user.getPassword());
        String userId = generateUserId();

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

            firstStatement.setString(1, userId);
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

            secondStatement.setString(1, userId);
            secondStatement.setString(2, user.getRole());

            firstStatement.executeUpdate();
            secondStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getByID(String id) {
        String sql = "SELECT Users.UserID, UserName, Status, Role, Email, Password, "
                + "Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar F"
                + "ROM Users JOIN User_Roles ON User_Roles.UserID = Users.UserID WHERE Users.UserID = '" + id + "'";

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

    public ArrayList<User> searchByEmailAndRole(String userEmail, String userRole) {
        ArrayList<User> users = new ArrayList<>();

        if (userRole.equalsIgnoreCase("all")) {
            userRole = "";
        }

        String sql = "SELECT Users.UserID, UserName, Status, Role, Email, Password, "
                + "Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar F"
                + "ROM Users JOIN User_Roles ON User_Roles.UserID = Users.UserID WHERE Email LIKE '%" + userEmail + "%' AND Role LIKE '%" + userRole + "%'";

        System.out.println(sql);
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

    public ArrayList<User> getUsersByEmails(String x) {
        ArrayList<User> users = new ArrayList<>();
        x = x.replaceAll("[\\[\\]\"]", "");
        String[] emails = x.split(",");

        String sql = "SELECT Users.UserID, UserName, Status, Role, Email, Password, "
                + "Fname, LName, Age, Gender, Country, CreatedAt, UpdatedAt, Avatar F"
                + "ROM Users JOIN User_Roles ON User_Roles.UserID = Users.UserID WHERE Email IN ( ";

        for (int i = 0; i < emails.length; i++) {
            if (i == emails.length - 1) {
                sql += "'" + emails[i] + "')";
            } else {
                sql += "'" + emails[i] + "', ";
            }
        }

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

    public boolean disable(String x) {
        x = x.replaceAll("[\\[\\]\"]", "");
        String[] emails = x.split(",");

        String sql = "UPDATE [dbo].[Users] SET [Status] = 'disabled'" + " WHERE Email IN ( ";

        for (int i = 0; i < emails.length; i++) {
            if (i == emails.length - 1) {
                sql += "'" + emails[i] + "')";
            } else {
                sql += "'" + emails[i] + "', ";
            }
        }

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean enable(String x) {
        x = x.replaceAll("[\\[\\]\"]", "");
        String[] emails = x.split(",");

        String sql = "UPDATE [dbo].[Users] SET [Status] = 'enabled'" + " WHERE Email IN ( ";

        for (int i = 0; i < emails.length; i++) {
            if (i == emails.length - 1) {
                sql += "'" + emails[i] + "')";
            } else {
                sql += "'" + emails[i] + "', ";
            }
        }

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean delete(String x) {
        UserDAO db = new UserDAO();

        x = x.replaceAll("[\\[\\]\"]", "");
        String[] emails = x.split(",");

        String deleteUserSql = "DELETE FROM [dbo].[Users]" + " WHERE Email IN ( ";
        String deleteUserRoleSql = "DELETE FROM User_Roles\n"
                + "WHERE UserID IN (\n"
                + "	SELECT UserID FROM Users\n"
                + "	WHERE Email IN ( ";

        String deleteBookingSQL = "DELETE FROM Booking WHERE ID NOT IN (\n"
                + "	select BookingID from Pet_Booking\n"
                + ")";

        String deletePetSQL = "DELETE from Pet\n"
                + "WHERE Pet.ID NOT IN (\n"
                + "	select Pet_Owner.PetID from Pet_Owner\n"
                + ")";

        String deletePetOwnerSQL = "DELETE FROM Pet_Owner\n"
                + "WHERE Pet_Owner.Owner IN ( ";
        String deletePetBookingSQL = "DELETE FROM Pet_Booking\n"
                + "WHERE Pet_Booking.Owner IN ( ";

        String deleteUserPhoto = "DELETE FROM Photos\n"
                + "WHERE Type = 'User' AND ID NOT IN (\n"
                + "	SELECT Avatar FROM Users JOIN Photos ON Users.Avatar = Photos.ID\n"
                + ")";

        String deleteBookingReceipt = "DELETE FROM Photos\n"
                + "WHERE Type = 'Receipt' AND ID NOT IN (\n"
                + "	SELECT Receipt FROM Booking JOIN Photos ON Booking.Receipt = Photos.ID\n"
                + ")";

        String deletePetPhoto = "DELETE FROM Photos\n"
                + "WHERE Type = 'Pet' AND ID NOT IN (\n"
                + "	SELECT Photo FROM Pet JOIN Photos ON Pet.Photo = Photos.ID\n"
                + ")";

        String deleteBookingFeedback = "DELETE FROM [dbo].[Feedback]\n"
                + "      WHERE [User] IN ( ";
            
        for (int i = 0; i < emails.length; i++) {
            if (i == emails.length - 1) {
                deleteUserSql += "'" + emails[i] + "')";
                deletePetBookingSQL += "'" + emails[i] + "')";
                deletePetOwnerSQL += "'" + emails[i] + "')";
                deleteUserRoleSql += "'" + emails[i] + "'))";
                deleteBookingFeedback += "'" + emails[i] + "')";

            } else {
                deletePetBookingSQL += "'" + emails[i] + "', ";
                deletePetOwnerSQL += "'" + emails[i] + "', ";
                deleteUserSql += "'" + emails[i] + "', ";
                deleteUserRoleSql += "'" + emails[i] + "', ";
                deleteBookingFeedback += "'" + emails[i] + "', ";
            }
        }
        
        System.out.println(deleteBookingFeedback);

        try {
            PreparedStatement statement2 = this.connection.prepareCall(deleteUserRoleSql);
            PreparedStatement statement3 = this.connection.prepareCall(deletePetOwnerSQL);
            PreparedStatement statement4 = this.connection.prepareCall(deletePetBookingSQL);
            PreparedStatement statement5 = this.connection.prepareCall(deletePetSQL);
            PreparedStatement statement6 = this.connection.prepareCall(deleteBookingSQL);
            PreparedStatement statement1 = this.connection.prepareCall(deleteUserSql);
            PreparedStatement statement7 = this.connection.prepareCall(deleteUserPhoto);
            PreparedStatement statement8 = this.connection.prepareCall(deletePetPhoto);
            PreparedStatement statement9 = this.connection.prepareCall(deleteBookingReceipt);
            PreparedStatement statement10 = this.connection.prepareCall(deleteBookingFeedback);

            
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();
            statement5.executeUpdate();
            statement6.executeUpdate();
            statement10.executeUpdate();
            statement1.executeUpdate();
            statement7.executeUpdate();
            statement8.executeUpdate();
            statement9.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public ArrayList<UserReport> getUserInLastSixMonths() {
        ArrayList<UserReport> reports = new ArrayList<>();

        String sql = "SELECT DATEPART(month FROM Users.CreatedAt) AS Month,DATEPART(year FROM Users.CreatedAt) as Year, \n"
                + "count(*) As Users FROM Users WHERE Users.CreatedAt >= Dateadd(Month, Datediff(Month, 0, DATEADD(m, -6,current_timestamp)), 0)\n"
                + "GROUP BY DATEPART(month FROM Users.CreatedAt), DATEPART(year FROM Users.CreatedAt)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                UserReport report = new UserReport(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)));

                reports.add(report);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reports;
    }

}
