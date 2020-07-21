/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Log;
import service.ILogService;

/**
 *
 * @author ViruSs0209
 */
public class AuditLogServlet extends HttpServlet {
    @Inject
    ILogService db;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        
        ArrayList<Log> logs = db.getAll();
        
        if (id != null) {
            logs = db.getByID(id);
            request.getSession().setAttribute("searchedLogs", logs);
        }       
        

        if (request.getParameter("action") != null && !request.getParameter("action").equals("")) {
            logs = (ArrayList<Log>) request.getSession().getAttribute("searchedLogs");
            request.setAttribute("action", "search");
        } else {
            if (request.getSession().getAttribute("searchedLogs") != null) {
                request.getSession().setAttribute("searchedLogs", null);
            }
        }
        int currentPage = -1;
        int itemsPerPage = 15;

        if (request.getParameter("itemsPerPage") != null) {
            itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
        }
        
        int start = 0, end = itemsPerPage;
            
            if (logs.size() < itemsPerPage) {
                end = logs.size();
            }
            if (request.getParameter("start") != null) {
                start = Integer.parseInt(request.getParameter("start"));
            }
            if (request.getParameter("end") != null) {
                end = Integer.parseInt(request.getParameter("end"));
            }
            currentPage = ( start / itemsPerPage ) + 1;
            
            int totalPage = logs.size() / itemsPerPage;
    
            if ((totalPage * itemsPerPage) < logs.size()) {
                totalPage += 1;
            }

            ArrayList<Integer> listPages = new ArrayList();
                      

            for (int i = 1; i <= totalPage; i++) {
                
                listPages.add(i);
            }

        
        request.setAttribute("currentMenu", "audit logs");
        request.setAttribute("itemsPerPage", itemsPerPage + "");
        request.setAttribute("logs", logs);
        request.setAttribute("currentPage", currentPage + "");
        request.setAttribute("pages", listPages);  
        request.setAttribute("logsByPage", db.getListByPage(start, end, itemsPerPage, logs));
        request.getRequestDispatcher("view/auditLog/audit.jsp").forward(request, response);
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
       
        String entityID = request.getParameter("entityID");
        String action = request.getParameter("Action");
        String entity = request.getParameter("entity");
        String userEmail = request.getParameter("email");
        
        Timestamp periodAt = null;
        Timestamp periodTo = null;
        
        if (entity == null) {
            entity = "";
        }
        
        if (action == null) {
            action = "";
        }
        
        String requestURL = request.getRequestURL().toString();
        
        if (request.getQueryString() != null) {
            requestURL = request.getRequestURL().append('?').append(request.getQueryString()).toString();
        }
        
        if (entityID != null) {           
            if (!request.getParameter("periodAt").equals("")) {
            periodAt = convertToTimestamp(request.getParameter("periodAt"));            
            }

            if (!request.getParameter("periodTo").equals("")) {
                periodTo = convertToTimestamp(request.getParameter("periodTo"));           
            }
        
            ArrayList<Log> logs = db.search(entityID, userEmail, entity, action, periodAt, periodTo);

            request.getSession().setAttribute("searchedLogs", logs);
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
