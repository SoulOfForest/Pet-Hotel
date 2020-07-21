/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Feedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Feedback;
import model.PageVisit;
import model.User;
import service.IFeedbackService;
import service.IPageVisitService;

/**
 *
 * @author ViruSs0209
 */
public class FeedBackServlet extends HttpServlet {
    
    @Inject
    IFeedbackService db;
    
    @Inject
    IPageVisitService pageVisitDB;
    
    public void insertPageView (String uri) {
        Calendar calendar = Calendar.getInstance();
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        
        PageVisit page = new PageVisit(month, year, uri, 1);
        pageVisitDB.update(page);
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        insertPageView(request.getRequestURI());
        
        User currentUser = (User) request.getSession().getAttribute("user");
        
        ArrayList<Feedback> feedbacks = db.getAll();
        
        if (request.getSession().getAttribute("feedbacks") != null) {
            request.getSession().setAttribute("feedbacks", null);
        }
        
        if (currentUser.getRole().equals("pet owner")) {
            feedbacks = db.getFeedbacksByUser(currentUser.getEmail());
        }
        
        if (request.getParameter("action") != null) {
            System.out.println(request.getSession().getAttribute("searchedFeedbacks"));
            feedbacks = (ArrayList<Feedback>) request.getSession().getAttribute("searchedFeedbacks");
            request.setAttribute("action", "search");
        } else {
            if (request.getSession().getAttribute("searchedFeedbacks") != null) {
                request.getSession().setAttribute("searchedFeedbacks", null);
            }
        }

        int currentPage = -1;
        int itemsPerPage = 5;

        if (request.getParameter("itemsPerPage") != null) {
            itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
        }

        int start = 0, end = itemsPerPage;

        if (feedbacks.size() < itemsPerPage) {
            end = feedbacks.size();
        }
        if (request.getParameter("start") != null) {
            start = Integer.parseInt(request.getParameter("start"));
        }
        if (request.getParameter("end") != null) {
            end = Integer.parseInt(request.getParameter("end"));
        }
        currentPage = (start / itemsPerPage) + 1;

        int totalPage = feedbacks.size() / itemsPerPage;

        if ((totalPage * itemsPerPage) < feedbacks.size()) {
            totalPage += 1;
        }

        ArrayList<Integer> listPages = new ArrayList();

        for (int i = 1; i <= totalPage; i++) {

            listPages.add(i);
        }

        request.setAttribute("currentMenu", "feedbacks");
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("currentPage", currentPage + "");
        request.setAttribute("itemsPerPage", itemsPerPage + "");
        request.setAttribute("pages", listPages);
        request.setAttribute("feedbacksPerPage", db.getListByPage(start, end, itemsPerPage, feedbacks));
        request.getRequestDispatcher("/view/feedback/feedback.jsp").forward(request, response);
    }
    
    public Timestamp convertToTimestamp(String temp) {
        temp = temp.replaceAll("/", "-");
        temp += ":00";

        return Timestamp.valueOf(temp);
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
        String bookingID = request.getParameter("bookingID");
        String status = request.getParameter("status");
        
        Timestamp publishedAt = null;
        Timestamp publishedTo = null;
        Timestamp createdAt = null;
        Timestamp createdTo = null;
        
        if (status == null) {
            status = "";
        }
        
        if (request.getParameter("createdAt") != null && !request.getParameter("createdAt").equals("")) {
            createdAt = convertToTimestamp(request.getParameter("createdAt"));
        }
        
        if (request.getParameter("createdTo") != null && !request.getParameter("createdTo").equals("")) {
            createdTo = convertToTimestamp(request.getParameter("createdTo"));
        }
        
        if (request.getParameter("publishedAt") != null && !request.getParameter("publishedAt").equals("")) {
            publishedAt = convertToTimestamp(request.getParameter("publishedAt"));
        }
        
        if (request.getParameter("publishedTo") != null && !request.getParameter("publishedTo").equals("")) {
            publishedTo = convertToTimestamp(request.getParameter("publishedTo"));
        }

        String requestURL = request.getRequestURL().toString();
        
        if (request.getQueryString() != null) {
            requestURL = request.getRequestURL().append('?').append(request.getQueryString()).toString();
        }
        
        if (id != null) {
            User currentUser = (User) request.getSession().getAttribute("user");
            
            if (owner == null) {
                owner = currentUser.getEmail();
            }

            ArrayList<Feedback> feedbacks = db.search(id, owner, bookingID, status, createdAt, createdTo, publishedAt, publishedTo);
            request.getSession().setAttribute("searchedFeedbacks", feedbacks);
            
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
