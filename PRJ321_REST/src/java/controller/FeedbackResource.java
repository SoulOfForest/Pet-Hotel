/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.FeedbackDAO;
import dal.LogDAO;
import dal.UserDAO;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Feedback;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ViruSs0209
 */
@Path("/feedbacks")
public class FeedbackResource {
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFeedbacks() {
        FeedbackDAO db = new FeedbackDAO();
        
        try {
            ArrayList<Feedback> feedbacks = db.getAll();

            return Response.status(200).entity(feedbacks).build();
        } catch (Exception err) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFeedbacks(@PathParam("id") String userID) {
        FeedbackDAO db = new FeedbackDAO();
        
        try {
            ArrayList<Feedback> feedbacks = db.getFeedbackByUserID(userID);

            return Response.status(200).entity(feedbacks).build();
        } catch (Exception err) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFeedbackById(@PathParam("id") String feedbackID) {
        try {
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);

            Timestamp currentTime = new Timestamp(currentDate.getTime());

            FeedbackDAO db = new FeedbackDAO();

            try {
                Feedback feedbackByID = db.getFeedbackByID(feedbackID);

                return Response.status(200).entity(feedbackByID).build();
            } catch (Exception err) {
                return Response.status(400).build();
            }
        } catch (ParseException ex) {
            return Response.status(400).build();
        }
    }

    @POST
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String updateFeedback(@PathParam("id") String feedbackID, String x) {
        try {
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            JSONObject jsonObject = new JSONObject(x);

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);

            Timestamp currentTime = new Timestamp(currentDate.getTime());

            FeedbackDAO db = new FeedbackDAO();
            UserDAO userDB = new UserDAO();

            try {
                User userById = userDB.getByID(jsonObject.getString("user"));

                db.update(jsonObject.getString("content"), userById, feedbackID, currentTime);

                return "Success";
            } catch (Exception err) {
                return err.getMessage();
            }

        } catch (ParseException ex) {
            return ex.getMessage();
        } catch (JSONException ex) {
            return ex.getMessage();
        }
    }

    @DELETE
    @Path("delete")
    @Produces({MediaType.APPLICATION_JSON})

    public Response deleteFeedbacks(String x) {
        FeedbackDAO db = new FeedbackDAO();
        UserDAO userDB = new UserDAO();
        LogDAO logDB = new LogDAO();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(x);

            boolean isSucceed = db.delete(jsonObject.getJSONArray("feedbacks"));
            boolean insertLog = logDB.insertFeedbackLog(jsonObject.getString("user"), jsonObject.getJSONArray("feedbacks"), "Removed");

            if (isSucceed) {
                return Response.status(200).entity(userDB.getAll()).build();
            }

        } catch (JSONException ex) {
            Logger.getLogger(FeedbackResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(400).build();
    }
}
