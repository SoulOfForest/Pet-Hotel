/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.User;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Log;
import model.User;
import service.ILogService;
import service.IPhotoService;
import service.IUserService;

/**
 *
 * @author ViruSs0209
 */
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50) 
public class ProfileServlet extends HttpServlet {
    @Inject
    IUserService db;
    
    @Inject
    IPhotoService photoDB;
    
    @Inject
    ILogService logDB;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("currentMenu", "users");
        request.getRequestDispatcher("view/common/profile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public String generateUserId() {
        SecureRandom random = new SecureRandom();
        
        byte bytes[] = new byte[20];
        
        random.nextBytes(bytes);             
        
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
         
        return uuid.toString();        
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String country = request.getParameter("country");
        int age = Integer.parseInt(request.getParameter("age"));    
        
        boolean gender = false;
        
        if (request.getParameter("gender").equals("Male")) {
            gender = true;
        }
        
        User currentUser = (User) request.getSession().getAttribute("user");
        // Create Log User For What They Updated
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.addProperty("UserID", currentUser.getUserID());
        
        if (!currentUser.getFirstName().equals(firstName)) {
            jsonObject.addProperty("firstName", firstName);
        }
        
        if (!currentUser.getLastName().equals(lastName)) {
            jsonObject.addProperty("lastName", lastName);
        }
        
        if (!currentUser.getCountry().equals(country)) {
            jsonObject.addProperty("country", country);
        }
        
        if (currentUser.getAge() != age) {
            jsonObject.addProperty("age", age);
        } 
        
        if (gender != currentUser.isGender()) {
            if (gender) {
                jsonObject.addProperty("gender", "Male");
            } else {
                jsonObject.addProperty("gender", "Feamle");
            }
        }

         // Set What Updated For User
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setCountry(country);
        currentUser.setAge(age);
        currentUser.setGender(gender);
     
        try {
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");                
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            currentUser.setUpdatedAt(currentTime);
            
            Part filePart = request.getPart("avatar"); // Retrieves <input type="file" name="file">     
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

            InputStream fileContent = null;
            
            String imageID = null;
            // Check If User Haven't Update Any Images
            if (!fileName.equals("")) {
                
                if (currentUser.getAvatar() != null) {
                    photoDB.deletePhotoById(currentUser.getAvatar());
                }
                
                imageID = generateUserId();
                
                fileContent = filePart.getInputStream();
                
                currentUser.setAvatar(imageID);                         
                jsonObject.addProperty("avatar", imageID);
                
                photoDB.insert(fileContent, "User", imageID, currentTime);
            }             
            
            db.updateUser(currentUser);
            // Create Log For Update Action
            Log log = new Log();
            
            Gson jsonConverter = new Gson();           
            
            log.setUserEmail(currentUser.getEmail());
            log.setEntity("User");
            log.setEntityID(currentUser.getUserID());
            log.setAction("Updated");                
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);
            
            logDB.insert(log);
            
            request.getSession().setAttribute("user", currentUser);   
            request.getSession().setAttribute("updatedSuccess", "Update Profile Successfully !");
            
            if (currentUser.getRole().equals("manager")) {
                response.sendRedirect("/users");
            } else {
                response.sendRedirect("/bookings");
            }
            
        } catch (ParseException ex) {
            request.getSession().setAttribute("updatedFail", "Update Profile Failed !");    
            if (currentUser.getRole().equals("manager")) {
                response.sendRedirect("/users");
            } else {
                response.sendRedirect("/bookings");
            }
        } 
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
