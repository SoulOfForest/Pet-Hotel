/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Auth;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Log;
import model.User;
import service.ILogService;
import service.IUserService;

/**
 *
 * @author ViruSs0209
 */
public class RegisterServlet extends HttpServlet {
    
    @Inject
    IUserService db;
    
    @Inject
    ILogService logDB;
    
    public String generateId() {
        SecureRandom random = new SecureRandom();
        
        byte bytes[] = new byte[20];
        
        random.nextBytes(bytes);             
        
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
         
        return uuid.toString();        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            request.getRequestDispatcher("view/auth/register.jsp").forward(request, response);
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
        try {
            
            String userID = generateId();
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String userName = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int age = Integer.parseInt(request.getParameter("age"));
            boolean gender = false;
            if (request.getParameter("gender").equals("male")) {
                gender = true;
            }
            
            String country = request.getParameter("country");
            
            String status = "enabled";
            String role = "pet owner";
            
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");           
            Date now = new Date(System.currentTimeMillis());
            
            String formattedDate = dateFormatOutput.format(now);
     
            Date currentDate = dateFormatOutput.parse(formattedDate);
            
            Timestamp currentTime = new Timestamp(currentDate.getTime());
            
            
            User user = new User(userID, userName, email, password, firstName, lastName, country, role, status, gender, age, currentTime);

            Error error = db.isValidRegistration(user);

            if (error != null) {
                if (error.getMessage().equals("email")) {
                    request.setAttribute("emailError", "This Is Email Is Already Used By Other Users");
                } else if (error.getMessage().equals("userName")) {
                    request.setAttribute("userNameError", "This Is Username Is Already Used By Other Users");      
                }
                request.setAttribute("user", user);
                request.getRequestDispatcher("/view/auth/register.jsp").forward(request, response);
            } else {
                db.register(user);
                
                Log log = new Log();
                
                Gson jsonConverted = new Gson();
                
                log.setUserEmail(email);
                log.setEntity("User");
                log.setEntityID(userID);
                log.setAction("Created");                
                log.setContent(jsonConverted.toJson(user));
                log.setCreatedAt(currentTime);                          
               
                logDB.insert(log);
                
                request.setAttribute("register", "Register Successully !");
                request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
            }
//        
        } catch (ParseException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
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
