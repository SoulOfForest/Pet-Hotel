/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Setting;
import model.User;
import service.IUserService;

/**
 *
 * @author ViruSs0209
 */
public class UsersServlet extends HttpServlet {
    
    @Inject
    IUserService db;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getSession().getAttribute("updatedSettings") != null) {
            request.setAttribute("updatedSettings", "Update Settings Successfully !");
            request.getSession().setAttribute("updatedSettings", null);
        }

        if (request.getSession().getAttribute("updatePerson") != null) {
            request.setAttribute("updatePerson", "Update Person Successfully !");
            request.getSession().setAttribute("updatePerson", null);
        }

        if (request.getSession().getAttribute("updatedSuccess") != null) {
            request.setAttribute("updatedSuccess", "Update Profile Successfully !");
            request.getSession().setAttribute("updatedSuccess", null);
        }

        if (request.getSession().getAttribute("disable") != null) {
            request.setAttribute("disable", "User(s) disabled successfully !");
            request.getSession().setAttribute("disable", null);
        }
        
        ArrayList<User> users = db.getAll();

        if (request.getParameter("action") != null && !request.getParameter("action").equals("")) {
            users = (ArrayList<User>) request.getSession().getAttribute("searchedUsers");
            request.setAttribute("action", "search");
        } else {
            if (request.getSession().getAttribute("searchedUsers") != null) {
                request.getSession().setAttribute("searchedUsers", null);
            }
        }

        int currentPage = -1;
        int itemsPerPage = 5;

        if (request.getParameter("itemsPerPage") != null) {
            itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
        }

        int start = 0, end = itemsPerPage;

        if (users.size() < itemsPerPage) {
            end = users.size();
        }
        if (request.getParameter("start") != null) {
            start = Integer.parseInt(request.getParameter("start"));
        }
        if (request.getParameter("end") != null) {
            end = Integer.parseInt(request.getParameter("end"));
        }
        currentPage = (start / itemsPerPage) + 1;

        int totalPage = users.size() / itemsPerPage;

        if ((totalPage * itemsPerPage) < users.size()) {
            totalPage += 1;
        }

        ArrayList<Integer> listPages = new ArrayList();

        for (int i = 1; i <= totalPage; i++) {

            listPages.add(i);
        }

        request.setAttribute("currentMenu", "users");
        request.setAttribute("currentPage", currentPage + "");
        request.setAttribute("itemsPerPage", itemsPerPage + "");
        request.setAttribute("pages", listPages);
        request.setAttribute("users", users);
        request.setAttribute("usersByPage", db.getListByPage(start, end, itemsPerPage, users));

        request.getRequestDispatcher("view/user/users.jsp").forward(request, response);
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
        String ID = request.getParameter("id");
        String email = request.getParameter("email");
        String status = request.getParameter("status");
        Timestamp createdAt = null;
        Timestamp createdTo = null;
        String name = request.getParameter("name");
        String role = request.getParameter("role");

        String requestURL = request.getRequestURL().toString();
        
        if (request.getQueryString() != null) {
            requestURL = request.getRequestURL().append('?').append(request.getQueryString()).toString();
        }
        
        if (role != null) {           
            if (request.getParameter("createdAt") != null && !request.getParameter("createdAt").equals("")) {
                createdAt = convertToTimestamp(request.getParameter("createdAt"));
            }

            if (request.getParameter("createdTo") != null && !request.getParameter("createdTo").equals("")) {
                createdTo = convertToTimestamp(request.getParameter("createdTo"));
            }

            ArrayList<User> users = db.search(ID, name, email, role, status, createdAt, createdTo);

            request.getSession().setAttribute("searchedUsers", users);
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
