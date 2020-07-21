/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Booking;
import model.BookingReport;

/**
 *
 * @author ViruSs0209
 */
public class BookingDAO extends BaseDAO {

    public boolean delete(String x) {
        x = x.replaceAll("[\\[\\]\"]", "");
        String[] bookingIDs = x.split(",");

        String petDeleteSQL = "DELETE FROM [dbo].[Booking]\n"
                + "      WHERE ID IN ( ";

        String petOwnerDeleteSQL = "DELETE FROM [dbo].[Pet_Booking]\n"
                + "      WHERE BookingID IN ( ";

        for (int i = 0; i < bookingIDs.length; i++) {
            if (i == bookingIDs.length - 1) {
                petDeleteSQL += "'" + bookingIDs[i] + "')";
                petOwnerDeleteSQL += "'" + bookingIDs[i] + "')";
            } else {
                petDeleteSQL += "'" + bookingIDs[i] + "', ";
                petOwnerDeleteSQL += "'" + bookingIDs[i] + "', ";
            }
        }

        try {
            PreparedStatement statement1 = this.connection.prepareCall(petDeleteSQL);
            PreparedStatement statement2 = this.connection.prepareCall(petOwnerDeleteSQL);

            statement2.executeUpdate();
            statement1.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

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

    public Booking getBookingById(String id) {
        String sql = "SELECT Booking.ID, Users.UserID, ( Users.Fname + ' ' + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes, \n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee, \n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID \n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID WHERE Booking.ID = '" + id + "'";

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

    public ArrayList<BookingReport> getBookingInSixMonths() {
        ArrayList<BookingReport> bookingReports = new ArrayList<>();

        String sql = "SELECT DATEPART(month FROM Booking.CreatedAt) AS Month,DATEPART(year FROM Booking.CreatedAt) as Year, "
                + "count(*) As Bookings, SUM(Fee) As Fee FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID\n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID WHERE "
                + "Booking.CreatedAt >= Dateadd(Month, Datediff(Month, 0, DATEADD(m, -6,current_timestamp)), 0) AND Booking.Status != 'cancelled' \n"
                + "GROUP BY DATEPART(month FROM Booking.CreatedAt), DATEPART(year FROM Booking.CreatedAt)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingReport report = new BookingReport(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)), Double.parseDouble(rs.getString(4)));

                bookingReports.add(report);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookingReports;
    }

    public ArrayList<BookingReport> getBookingsByWeeks() {
        ArrayList<BookingReport> bookingReports = new ArrayList<>();

        String sql = "SELECT DATEPART(Week FROM Booking.CreatedAt) AS Week, DATEPART(Month FROM Booking.CreatedAt) As Month, DATEPART(year FROM Booking.CreatedAt) As Year,\n"
                + "count(*) As Bookings, SUM(Fee) As Fee FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID\n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID WHERE Booking.Status != 'cancelled' \n"
                + "GROUP BY DATEPART(Week FROM Booking.CreatedAt), DATEPART(Month FROM Booking.CreatedAt), DATEPART(year FROM Booking.CreatedAt)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingReport report = new BookingReport(rs.getString(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), Double.parseDouble(rs.getString(5)));

                bookingReports.add(report);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookingReports;
    }

    public ArrayList<BookingReport> getBookingsByDays() {
        ArrayList<BookingReport> bookingReports = new ArrayList<>();

        String sql = "SELECT DATEPART(Day FROM Booking.CreatedAt) AS DAY, \n"
                + "DATEPART(Month FROM Booking.CreatedAt) AS Month, \n"
                + "SUM(Fee) As Fee,\n"
                + "count(*) As Bookings\n"
                + "FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID\n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID\n"
                + "WHERE Booking.CreatedAt >= DATEADD(day, -30, getdate()) AND Booking.Status != 'cancelled'\n"
                + "GROUP BY DATEPART(Day FROM Booking.CreatedAt), DATEPART(Month FROM Booking.CreatedAt)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingReport report = new BookingReport(rs.getString(1), rs.getString(2), Double.parseDouble(rs.getString(3)));

                bookingReports.add(report);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookingReports;
    }

    public ArrayList<BookingReport> getBookingsPercentByStatus() {
        ArrayList<BookingReport> bookingReports = new ArrayList<>();

        String sql = "SELECT Status, COUNT(*) As bookings FROM Booking \n"
                + "GROUP BY Status";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingReport report = new BookingReport(rs.getString(1), Integer.parseInt(rs.getString(2)));

                bookingReports.add(report);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bookingReports;
    }

    public ArrayList<Booking> getRecentlyBooking() {
        String sql = "SELECT TOP 10 Booking.ID, Users.UserID, ( Users.Fname + Users.LName ) as Username, Users.Email, Pet.Name, Pet.ID as PetId, Booking.OwnerNotes, \n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee, \n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices FROM Pet_Booking JOIN Booking ON Booking.ID = Pet_Booking.BookingID\n"
                + "JOIN Users ON Users.Email = Pet_Booking.Owner JOIN Pet ON Pet_Booking.PetID = Pet.ID\n"
                + "GROUP BY Booking.ID , Users.UserID, Users.Fname, Users.LName , Users.Email, Pet.Name, Pet.ID, Booking.OwnerNotes, \n"
                + "Booking.ManagerNotes, Booking.CancelNotes, Booking.Arrival, Booking.Departure, Booking.Status, Booking.Fee, \n"
                + "Booking.CreatedAt, Booking.UpdatedAt, Booking.Receipt, Booking.ExtraServices\n"
                + "ORDER BY Booking.Departure DESC ";

        ArrayList<Booking> bookings = new ArrayList<>();

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
}
