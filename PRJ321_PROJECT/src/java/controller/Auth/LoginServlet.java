/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Feedback;
import model.User;
import service.IFeedbackService;
import service.IUserService;

/**
 *
 * @author ViruSs0209
 */
public class LoginServlet extends HttpServlet {
    
    @Inject
    IUserService db;
    
    @Inject
    IFeedbackService fbDB;

    
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
        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password"); 
        
        User user = db.getUser(username, password);
        
        if (user != null) {
            
            if (user.getStatus().equals("disabled")) {
                request.setAttribute("disable", "This account is disabled");
                request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);                
                
                return;
            }            
            
            HttpSession session = request.getSession();
            
            session.setAttribute("user", user);
            
            Cookie userName = new Cookie("user", user.getUserID());
            userName.setMaxAge(30 * 60);
            
            response.addCookie(userName);
            
            request.getSession().setAttribute("login", "Login Successfully");
            
            if (user.getRole().equals("pet owner")) {
                ArrayList<Feedback> feedbacksByUser = fbDB.getNonFeedbacksByUser(user.getEmail());

                if (feedbacksByUser.size() > 0) {
                    request.getSession().setAttribute("feedbacks", feedbacksByUser);
                }
            }
            
            response.sendRedirect("/index");
            
        } else {
            request.setAttribute("error", "Username or password is not correct!");
            request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
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
