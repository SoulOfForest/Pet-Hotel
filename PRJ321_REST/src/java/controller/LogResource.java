/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.LogDAO;
import dal.UserDAO;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Booking;
import model.User;

/**
 *
 * @author ViruSs0209
 */
@Path("logs")
public class LogResource {
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getAll() {
        LogDAO logDB = new LogDAO();
        
        return Response.status(200).entity(logDB.getAll()).build();
    }
    
    @POST
    @Path("disable/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response disableLog(@PathParam("id") String userID, String x) {
        UserDAO db = new UserDAO();
        LogDAO logDB = new LogDAO();
        
        boolean isSucceed = logDB.insert(userID, x, "Disabled");
        
        ArrayList<User> users = db.getAll();
        
        return Response.status(200).entity(users).build();
    }
    
    @POST
    @Path("enable/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response enableLog(@PathParam("id") String userID, String x) {
        UserDAO db = new UserDAO();
        LogDAO logDB = new LogDAO();
        
        boolean isSucceed = logDB.insert(userID, x, "Enabled");
        
        ArrayList<User> users = db.getAll();
        
        return Response.status(200).entity(users).build();
    }
    
    @POST
    @Path("remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response removeLog(@PathParam("id") String userID, String x) {
        UserDAO db = new UserDAO();
        LogDAO logDB = new LogDAO();
        
        boolean isSucceed = logDB.insert(userID, x, "Removed");
        
        ArrayList<User> users = db.getAll();
        
        return Response.status(200).entity(users).build();
    }
    
    @POST
    @Path("pets/remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response insertRemovePetLog(@PathParam("id") String userID, String x) {
        UserDAO db = new UserDAO();
        LogDAO logDB = new LogDAO();
        
        boolean isSucceed = logDB.insertPetLog(userID, x, "Removed");
        
        ArrayList<User> users = db.getAll();
        
        return Response.status(200).entity(users).build();
    }
    
    @POST
    @Path("bookings/remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response insertRemoveBookingLog(@PathParam("id") String userID, String x) {
        UserDAO db = new UserDAO();
        LogDAO logDB = new LogDAO();
        
        boolean isSucceed = logDB.insertBookingLogs(userID, x, "Removed");
        
        if (isSucceed) {
            ArrayList<User> users = db.getAll();
        
            return Response.status(200).entity(users).build();
        }
        
        return Response.status(400).build();
    }
}
