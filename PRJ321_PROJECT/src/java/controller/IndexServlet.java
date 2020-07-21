/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
public class IndexServlet extends HttpServlet {
    
    @Inject
    IPageVisitService pageVisitDB;
    
    @Inject
    IFeedbackService feedbackDB;
    
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
        if (request.getSession().getAttribute("login") != null) {
            request.setAttribute("login", "Login Successfully !");
            request.getSession().setAttribute("login", null);
        }
        
        insertPageView(request.getRequestURI());
        
        request.setAttribute("feedbacks", feedbackDB.getAll());
        request.getRequestDispatcher("/view/common/index.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
