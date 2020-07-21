/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Pet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pet;
import model.User;
import service.IPetService;

/**
 *
 * @author ViruSs0209
 */
public class PetsSearch extends HttpServlet {
    @Inject
    IPetService db;
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public Timestamp convertToTimestamp(String temp) {
        temp = temp.replaceAll("/", "-");
        temp += ":00";

        return Timestamp.valueOf(temp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String owner = request.getParameter("owner");
        String type = request.getParameter("type");
        String breed = request.getParameter("breed");
        String size = request.getParameter("size");
        String name = request.getParameter("petName");
        
        if (type == null) {
            type = "";
        }
        
        if (size == null) {
            size = "";
        }
        
        String requestURL = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().lastIndexOf("/"));

        if (request.getQueryString() != null) {
            requestURL += "?"  + request.getQueryString().toString();
        }
        
        if (name != null) {           
            
            User currentUser = (User) request.getSession().getAttribute("user");
            
            if (owner == null) {
                owner = currentUser.getEmail();
            }

            ArrayList<Pet> users = db.search(owner, type, size, name, breed);

            request.getSession().setAttribute("searchedPets", users);
        }
        
        if (request.getParameter("itemsPerPage") == null && !requestURL.contains("action")) {
            requestURL += "?action=search";
        }
        
        if (request.getParameter("itemsPerPage") != null) {
            if (!requestURL.contains("?")) {
                requestURL += "?itemsPerPage=" + request.getParameter("itemsPerPage");
            } else {
                requestURL += "&itemsPerPage=" + request.getParameter("itemsPerPage");
            }
        }

        response.sendRedirect(requestURL);
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
