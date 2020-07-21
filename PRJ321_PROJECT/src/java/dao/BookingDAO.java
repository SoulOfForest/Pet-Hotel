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
import javax.inject.Named;
import model.Booking;
import service.IBookingService;

/**
 *
 * @author ViruSs0209
 */
@Default
@Named("BookingDAO")
@ManagedBean
public class BookingDAO extends BaseDAO implements IBookingService{

    @Override
    public void insert(Booking booking, String owner, String petId) {
        String insertBookingSQL = "INSERT INTO [dbo].[Booking]\n"
                + "           ([ID]\n"
                + "           ,[OwnerNotes]\n"
                + "           ,[CancelNotes]\n"
                + "           ,[ManagerNotes]\n"
                + "           ,[Status]\n"
                + "           ,[Fee]\n"
                + "           ,[Arrival]\n"
                + "           ,[Departure]\n"
                + "           ,[CreatedAt]\n"
                + "           ,[Receipt]\n"
                + "           ,[ExtraServices])\n"
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

        String insertBookingOwnerSQL = "INSERT INTO [dbo].[Pet_Booking]\n"
                + "           ([PetID]\n"
                + "           ,[Owner]\n"
                + "           ,[BookingID])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        try {
            PreparedStatement statement2 = this.connection.prepareCall(insertBookingOwnerSQL);
            PreparedStatement statement = this.connection.prepareCall(insertBookingSQL);

            statement.setString(1, booking.getId());
            statement.setString(2, booking.getOwnerNotes());
            statement.setString(3, booking.getCancelNotes());
            statement.setString(4, booking.getManagerNotes());
            statement.setString(5, booking.getStatus());
            statement.setDouble(6, booking.getFee());
            statement.setTimestamp(7, booking.getArrival());
            statement.setTimestamp(8, booking.getDeparture());
            statement.setTimestamp(9, booking.getCreatedAt());
            statement.setString(10, booking.getReceipt());
            statement.setBoolean(11, booking.isExtraServices());

            statement2.setString(1, petId);
            statement2.setString(2, owner);
            statement2.setString(3, booking.getId());

            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        String deletePetBookingSQL = "DELETE FROM [dbo].[Pet_Booking]\n"
                + "      WHERE BookingID = '" + id + "'";

        String deleteBookingSQL = "DELETE FROM Booking WHERE ID = '" + id + "'";

        try {
            PreparedStatement statement_1 = this.connection.prepareCall(deletePetBookingSQL);
            PreparedStatement statement_2 = this.connection.prepareCall(deleteBookingSQL);

            statement_1.executeUpdate();
            statement_2.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Booking booking) {
        String updateBookingSQL = "UPDATE [dbo].[Booking]\n"
                + "   SET [OwnerNotes] = ?\n"
                + "      ,[CancelNotes] = ?\n"
                + "      ,[ManagerNotes] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[Fee] = ?\n"
                + "      ,[Arrival] = ?\n"
                + "      ,[Departure] = ?\n"
                + "      ,[UpdatedAt] = ?\n"
                + "      ,[Receipt] = ?\n"
                + "      ,[ExtraServices] = ?\n"
                + " WHERE Booking.ID = '" + booking.getId() + "'";

        String updatePetBookingSQL = "UPDATE [dbo].[Pet_Booking]\n"
                + "   SET [PetID] = ?\n"
                + "      ,[Owner] = ?\n"
                + " WHERE BookingID = '" + booking.getId() + "'";

        try {
            PreparedStatement statement_1 = this.connection.prepareCall(updateBookingSQL);
            PreparedStatement statement_2 = this.connection.prepareCall(updatePetBookingSQL);

            statement_1.setString(1, booking.getOwnerNotes());
            statement_1.setString(2, booking.getCancelNotes());
            statement_1.setString(3, booking.getManagerNotes());
            statement_1.setString(4, booking.getStatus());
            statement_1.setDouble(5, booking.getFee());
            statement_1.setTimestamp(6, booking.getArrival());
            statement_1.setTimestamp(7, booking.getDeparture());
            statement_1.setTimestamp(8, booking.getUpdatedAt());
            statement_1.setString(9, booking.getReceipt());
            statement_1.setBoolean(10, booking.isExtraServices());

            statement_2.setString(1, booking.getPetId());
            statement_2.setString(2, booking.getUserEmail());

            statement_1.executeUpdate();
            statement_2.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String appendQueryParam(String sql, String param, Object paramValue) {
        if (paramValue != null && !(paramValue instanceof Timestamp)) {
            if (sql.contains("WHERE")) {
                sql += " AND " + param + " LIKE '%" + paramValue.toString() + "%'";
            } else {
                sql += " WHERE " + param + " LIKE '%" + paramValue.toString() + "%'";
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
    public ArrayList<Booking> search(String id, String owner, Timestamp periodAt, Timestamp periodTo, Timestamp createdAt, Timestamp createdTo, String feeFrom, String FeeTo, boolean extraFee, String petName, String status) {
        ArrayList<Booking> bookings = new ArrayList<>();

        String sql = "SELECT Booking.ID, Users.UserID, ( Users.Fname + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes, \n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee, \n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID \n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID";

        sql = appendQueryParam(sql, "Booking.ID", id);
        sql = appendQueryParam(sql, "Email", owner);
        sql = appendQueryParam(sql, "Name", petName);
        sql = appendQueryParam(sql, "Booking.Status", status);

        if (extraFee) {
            sql = appendQueryParam(sql, "Booking.ExtraServices", 1);
        } else {
            sql = appendQueryParam(sql, "Booking.ExtraServices", 0);
        }

        if (createdAt != null && createdTo != null) {
            sql = appendQueryParam(sql, "Booking.CreatedAt BETWEEN '" + createdAt.toString() + "' AND '" + createdTo.toString() + "'", createdAt);
        } else if (createdAt != null && createdTo == null) {
            sql = appendQueryParam(sql, "Booking.CreatedAt >= '" + createdAt.toString() + "'", createdAt);
        } else if (createdAt == null && createdTo != null) {
            sql = appendQueryParam(sql, "Booking.CreatedAt <= '" + createdTo.toString() + "'", createdTo);
        }

        if (periodAt != null && periodTo != null) {
            sql = appendQueryParam(sql, "Departure >= '" + periodAt.toString() + "' AND Arrival <= '" + periodTo.toString() + "'", periodTo);
        } else if (periodAt != null && periodTo == null) {
            sql = appendQueryParam(sql, "Departure >= '" + periodAt.toString() + "'", periodAt);
        } else if (periodAt == null && periodTo != null) {
            sql = appendQueryParam(sql, "Arrival <= '" + periodTo.toString() + "'", periodTo);
        }

        System.out.println(feeFrom);
        System.out.println(FeeTo);

        if (!feeFrom.equals("") && !FeeTo.equals("")) {
            sql = appendQueryParam(sql, "Fee BETWEEN " + feeFrom + " AND " + FeeTo, null);
        }

        if (!feeFrom.equals("") && FeeTo.equals("")) {
            sql = appendQueryParam(sql, "Fee >= " + feeFrom, null);
        }

        if (feeFrom.equals("") && !FeeTo.equals("")) {
            sql = appendQueryParam(sql, "Fee <= " + FeeTo, null);
        }

        System.out.println(sql);

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookingID = rs.getString(1);
                String userID = rs.getString(2);
                String Username = rs.getString(3);
                String Email = rs.getString(4);
                String Name = rs.getString(5);
                String petID = rs.getString(6);
                String OwnerNotes = rs.getString(7);
                String ManagerNotes = rs.getString(8);
                String CancelNotes = rs.getString(9);
                Timestamp Arrival = rs.getTimestamp(10);
                Timestamp Departure = rs.getTimestamp(11);
                Timestamp updatedAt = rs.getTimestamp(15);
                Double fee = rs.getDouble(13);
                String receipt = rs.getString(16);
                boolean extraServices = rs.getBoolean(17);

                Booking booking = new Booking(bookingID, OwnerNotes, CancelNotes, ManagerNotes, rs.getString(12), receipt, userID, Username, Email, Name, petID, fee, extraServices, Arrival, Departure, rs.getTimestamp(14), updatedAt);

                bookings.add(booking);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookings;
    }

    @Override
    public ArrayList<Booking> getListByPage(int start, int end, int itemsPerPage, ArrayList<Booking> totalBookings) {
        ArrayList<Booking> bookingsByPage = new ArrayList<>();

        if (end > totalBookings.size()) {
            end = totalBookings.size();
            start = end - itemsPerPage;

            if (start < 0) {
                start = 0;
            }
        }

        for (int i = start; i < end; i++) {
            bookingsByPage.add(totalBookings.get(i));
        }

        return bookingsByPage;
    }

    @Override
    public ArrayList<Booking> getAll() {
        ArrayList<Booking> bookings = new ArrayList<>();

        String sql = "SELECT Booking.ID, Users.UserID, ( Users.Fname + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes, \n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee, \n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID \n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookingID = rs.getString(1);
                String userID = rs.getString(2);
                String Username = rs.getString(3);
                String Email = rs.getString(4);
                String Name = rs.getString(5);
                String petID = rs.getString(6);
                String OwnerNotes = rs.getString(7);
                String ManagerNotes = rs.getString(8);
                String CancelNotes = rs.getString(9);
                Timestamp Arrival = rs.getTimestamp(10);
                Timestamp Departure = rs.getTimestamp(11);
                Timestamp createdAt = rs.getTimestamp(14);
                Timestamp updatedAt = rs.getTimestamp(15);
                String status = rs.getString(12);
                Double fee = rs.getDouble(13);
                String receipt = rs.getString(16);
                boolean extraServices = rs.getBoolean(17);

                Booking booking = new Booking(bookingID, OwnerNotes, CancelNotes, ManagerNotes, status, receipt, userID, Username, Email, Name, petID, fee, extraServices, Arrival, Departure, createdAt, updatedAt);

                bookings.add(booking);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookings;
    }

    @Override
    public ArrayList<Booking> getBookingByUserId(String id) {
        ArrayList<Booking> bookings = new ArrayList<>();

        String sql = "SELECT Booking.ID, Users.UserID, ( Users.Fname + ' ' + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes,\n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee,\n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID \n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID WHERE Users.UserID = '" + id + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookingID = rs.getString(1);
                String userID = rs.getString(2);
                String Username = rs.getString(3);
                String Email = rs.getString(4);
                String Name = rs.getString(5);
                String petID = rs.getString(6);
                String OwnerNotes = rs.getString(7);
                String ManagerNotes = rs.getString(8);
                String CancelNotes = rs.getString(9);
                Timestamp Arrival = rs.getTimestamp(10);
                Timestamp Departure = rs.getTimestamp(11);
                Timestamp createdAt = rs.getTimestamp(14);
                Timestamp updatedAt = rs.getTimestamp(15);
                String status = rs.getString(12);
                Double fee = rs.getDouble(13);
                String receipt = rs.getString(16);
                boolean extraServices = rs.getBoolean(17);

                Booking booking = new Booking(bookingID, OwnerNotes, CancelNotes, ManagerNotes, status, receipt, userID, Username, Email, Name, petID, fee, extraServices, Arrival, Departure, createdAt, updatedAt);

                bookings.add(booking);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookings;
    }

    @Override
    public Booking getBookingByPetId(String id) {

        String sql = "SELECT Booking.ID, Users.UserID, ( Users.Fname + ' ' + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes,\n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee,\n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID \n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID WHERE Pet.ID = '" + id + "' AND Booking.Status IN ('progress', 'booked')";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookingID = rs.getString(1);
                String userID = rs.getString(2);
                String Username = rs.getString(3);
                String Email = rs.getString(4);
                String Name = rs.getString(5);
                String petID = rs.getString(6);
                String OwnerNotes = rs.getString(7);
                String ManagerNotes = rs.getString(8);
                String CancelNotes = rs.getString(9);
                Timestamp Arrival = rs.getTimestamp(10);
                Timestamp Departure = rs.getTimestamp(11);
                Timestamp createdAt = rs.getTimestamp(14);
                Timestamp updatedAt = rs.getTimestamp(15);
                String status = rs.getString(12);
                Double fee = rs.getDouble(13);
                String receipt = rs.getString(16);
                boolean extraServices = rs.getBoolean(17);

                Booking booking = new Booking(bookingID, OwnerNotes, CancelNotes, ManagerNotes, status, receipt, userID, Username, Email, Name, petID, fee, extraServices, Arrival, Departure, createdAt, updatedAt);

                return booking;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Booking getBookingById(String id) {
        String sql = "SELECT Booking.ID, Users.UserID, ( Users.Fname + ' ' + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes, \n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee, \n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID \n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID WHERE Booking.ID = '" + id + "'";

        System.out.println(sql);
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String bookingID = rs.getString(1);
                String userID = rs.getString(2);
                String Username = rs.getString(3);
                String Email = rs.getString(4);
                String Name = rs.getString(5);
                String petID = rs.getString(6);
                String OwnerNotes = rs.getString(7);
                String ManagerNotes = rs.getString(8);
                String CancelNotes = rs.getString(9);
                Timestamp Arrival = rs.getTimestamp(10);
                Timestamp Departure = rs.getTimestamp(11);
                Timestamp createdAt = rs.getTimestamp(14);
                Timestamp updatedAt = rs.getTimestamp(15);
                String status = rs.getString(12);
                Double fee = rs.getDouble(13);
                String receipt = rs.getString(16);
                boolean extraServices = rs.getBoolean(17);

                Booking booking = new Booking(bookingID, OwnerNotes, CancelNotes, ManagerNotes, status, receipt, userID, Username, Email, Name, petID, fee, extraServices, Arrival, Departure, createdAt, updatedAt);

                return booking;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean checkPetBooked(String petID) {
        String sql = "SELECT * FROM Pet JOIN Pet_Booking ON Pet_Booking.PetID = Pet.ID JOIN Booking ON Booking.ID = Pet_Booking.BookingID WHERE Status NOT IN  ('completed', 'cancelled') "
                + "AND Pet.ID ='" + petID + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
