/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Pet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Log;
import model.PageVisit;
import model.Pet;
import model.User;
import service.ILogService;
import service.IPageVisitService;
import service.IPetService;
import service.IPhotoService;
import service.IUserService;

/**
 *
 * @author ViruSs0209
 */
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)
public class PetsEdit extends HttpServlet {
    @Inject
    IUserService userDB;
    
    @Inject
    IPhotoService photoDB;
    
    @Inject
    ILogService logDB;
    
    @Inject
    IPetService db;
    
    @Inject
    IPageVisitService pageVisitDB;
    
    public void insertPageView (String uri) {
        Calendar calendar = Calendar.getInstance();
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        
        PageVisit page = new PageVisit(month, year, uri, 1);
        pageVisitDB.update(page);
    }

    public String generateID() {
        SecureRandom random = new SecureRandom();
        
        byte bytes[] = new byte[20];
        
        random.nextBytes(bytes);             
        
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
         
        return uuid.toString();        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        insertPageView(request.getRequestURI());
        
        String id = request.getParameter("id");
        
        request.setAttribute("pet", db.getByID(id));
        request.setAttribute("currentMenu", "pets");
        request.getRequestDispatcher("/view/pet/petEdit.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String breed = request.getParameter("breed");
        String size = request.getParameter("size");   
        String type = request.getParameter("type");   
        
        User currentUser = (User) request.getSession().getAttribute("user");
        
        Pet updatedPet = db.getByID(id);

        // Create Log User For What They Updated
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.addProperty("UserID", currentUser.getUserID());
        jsonObject.addProperty("UpdatedPet", updatedPet.getID());
        
        if (!updatedPet.getName().equals(name)) {
            jsonObject.addProperty("Name", name);
        }
        
        if (!updatedPet.getBreed().equals(breed)) {
            jsonObject.addProperty("Breed", breed);
        }
        
        if (!updatedPet.getSize().equals(size)) {
            jsonObject.addProperty("Size", size);
        }
        
        if (!updatedPet.getType().equals(type)) {
            jsonObject.addProperty("Type", type);
        } 
        
        if (!updatedPet.getOwner().equals(owner)) {
            jsonObject.addProperty("Owner", owner);
        } 
        
        
         // Set What Updated For User
        updatedPet.setName(name);
        updatedPet.setOwner(owner);
        updatedPet.setSize(size);
        updatedPet.setBreed(breed);
        updatedPet.setType(type);
     
        try {
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");                
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            updatedPet.setUpdatedAt(currentTime);
            
            Part filePart = request.getPart("avatar"); // Retrieves <input type="file" name="file">     
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

            InputStream fileContent = null;

            String imageID = null;
            // Check If User Haven't Update Any Images
            if (!fileName.equals("")) {
                
                if (updatedPet.getAvatar() != null) {
                    photoDB.deletePhotoById(updatedPet.getAvatar());
                }
                
                imageID = generateID();
                
                fileContent = filePart.getInputStream();
                
                updatedPet.setAvatar(imageID);                         
                jsonObject.addProperty("avatar", imageID);
                
                photoDB.insert(fileContent, "Pet", imageID, currentTime);
            }             
            
            
            db.updatePet(updatedPet);
            // Create Log For Update Action
            Log log = new Log();
            
            Gson jsonConverter = new Gson();            
            
            log.setUserEmail(currentUser.getEmail());
            log.setEntity("Pet");
            log.setEntityID(id);
            log.setAction("Updated");                
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);
            
            logDB.insert(log);         
            
            request.getSession().setAttribute("updatePet", "Update Pet Successfully !");       
            
        } catch (ParseException ex) {
            request.getSession().setAttribute("fail", "Update Pet Unsuccessfully !");                                 
        }
        
        response.sendRedirect("/pets");  
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
