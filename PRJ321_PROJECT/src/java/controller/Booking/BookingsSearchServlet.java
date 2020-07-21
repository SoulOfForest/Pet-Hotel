/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Booking;
import model.User;
import service.IBookingService;

/**
 *
 * @author ViruSs0209
 */
public class BookingsSearchServlet extends HttpServlet {
    @Inject
    IBookingService db;

//    WeldContainer container = weld.initialize();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
        String owner = request.getParameter("owner");
        String petName = request.getParameter("petName");
        String status = request.getParameter("status");
        boolean extraServices = false;
        
        if (request.getParameter("extra") != null) {
            extraServices = true;
        }
        
        if (status == null) {
            status = "";
        }
        
        Timestamp periodAt = null;
        Timestamp periodTo = null;
        Timestamp createdAt = null;
        Timestamp createdTo = null;
        
        String feeFrom = request.getParameter("feeFrom");
        String feeTo = request.getParameter("feeTo");

        if (request.getParameter("createdAt") != null && !request.getParameter("createdAt").equals("")) {
            createdAt = convertToTimestamp(request.getParameter("createdAt"));
        }
        
        if (request.getParameter("createdTo") != null && !request.getParameter("createdTo").equals("")) {
            createdTo = convertToTimestamp(request.getParameter("createdTo"));
        }
        
        if (request.getParameter("periodAt") != null && !request.getParameter("periodAt").equals("")) {
            periodAt = convertToTimestamp(request.getParameter("periodAt"));
        }
        
        if (request.getParameter("periodTo") != null && !request.getParameter("periodTo").equals("")) {
            periodTo = convertToTimestamp(request.getParameter("periodTo"));
        }

        String requestURL = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().lastIndexOf("/"));

        if (request.getQueryString() != null) {
            requestURL += "?" + request.getQueryString().toString();
        }
        
        if (id != null) {
            User currentUser = (User) request.getSession().getAttribute("user");
            
            if (owner == null) {
                owner = currentUser.getEmail();
            }

            ArrayList<Booking> bookings = db.search(id, owner, periodAt, periodTo, createdAt, createdTo, feeFrom, feeTo, extraServices, petName, status);
            request.getSession().setAttribute("searchedBookings", bookings);
            
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

        System.out.println(requestURL);
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
