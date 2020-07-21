/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.BookingDAO;
import dal.PetDAO;
import dal.UserDAO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ViruSs0209
 */
@Path("bookings")
public class BookingResource {
    
    @GET
    @Path("recently")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getRecentlyBooking() {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getRecentlyBooking()).build();  
    }
    
    @GET
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getPercentByStatus() {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getBookingsPercentByStatus()).build();  
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getAll() {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getAll()).build();  
    }
    
    @GET
    @Path("/report")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getBookingReports() {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getBookingInSixMonths()).build();  
    }
    
    @GET
    @Path("/report/weeks")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getBookingReportsByWeeks() {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getBookingsByWeeks()).build();  
    }
    
    @GET
    @Path("/report/days")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getBookingReportsByDays() {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getBookingsByDays()).build();  
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getBookingByUserId(@PathParam("id") String userId) {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getBookingByUserId(userId)).build();  
    }
    
    @GET
    @Path("/search/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getBookingById(@PathParam("id") String bookingID) {
        BookingDAO db = new BookingDAO();
        
        return Response.status(200).entity(db.getBookingById(bookingID)).build();  
    }
    
    @DELETE
    @Path("delete")
    @Produces({MediaType.APPLICATION_JSON})

    public Response deleteBookings(String x) {
        BookingDAO db = new BookingDAO();
        UserDAO userDB = new UserDAO();

        boolean isSucceed = db.delete(x);

        if (isSucceed) {
            return Response.status(200).entity(userDB.getAll()).build();
        }

        return Response.status(400).build();
    }
}
