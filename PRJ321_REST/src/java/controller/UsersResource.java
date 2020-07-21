/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dal.UserDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;
import model.UserReport;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ViruSs0209
 */


@Path("users")
public class UsersResource {
    @GET
    @Path("report")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getUserInLastSixMonths() {
        UserDAO db = new UserDAO();
        
        ArrayList<UserReport> users = db.getUserInLastSixMonths();
        
        return Response.status(200).entity(users).build();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response sayHello() {
        UserDAO db = new UserDAO();
        
        ArrayList<User> users = db.getAll();
        
        return Response.status(200).entity(users).build();
    }
    
    @POST
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response searchUsers(String x) {      
        try {
            JSONObject jsonObject = new JSONObject(x);
            
            UserDAO db = new UserDAO();
            
            ArrayList<User> matchedUsers = db.searchByEmailAndRole(jsonObject.getString("owner"), jsonObject.getString("role"));
                    
            return Response.status(200).entity(matchedUsers).build();
            
        } catch (JSONException ex) {
            return Response.status(400).build();
        } 
        
    }
        
    @PUT
    @Path("disable")
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response disableUsers(String x) {
        System.out.println(x);
        try {
            UserDAO db = new UserDAO();
            
            boolean isSucceed = db.disable(x);
            
            return Response.status(200).entity(db.getAll()).build();
        } catch (Exception ex) {
            return Response.status(400).build();
        } 
    }
    
    @PUT
    @Path("enable")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    
    public Response enableUsers(InputStream requestBody) {
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            UserDAO db = new UserDAO();
            
            boolean isSucceed = db.enable(out.toString());
            
            return Response.status(200).entity(db.getAll()).build();
        } catch (IOException ex) {
            Logger.getLogger(UsersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(400).build();
    }
    
    @DELETE
    @Path("delete")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    
    public Response deleteUsers(InputStream requestBody) {
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            UserDAO db = new UserDAO();
            
            boolean isSucceed = db.delete(out.toString());
            
            return Response.status(200).entity(db.getAll()).build();
        } catch (IOException ex) {
            Logger.getLogger(UsersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(400).build();
    }
    
}
