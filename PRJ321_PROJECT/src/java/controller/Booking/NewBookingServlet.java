/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PageVisit;
import service.IPageVisitService;

/**
 *
 * @author ViruSs0209
 */
public class NewBookingServlet extends HttpServlet {
    
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
               
        request.setAttribute("currentMenu", "bookings");
        request.getRequestDispatcher("/view/booking/newBooking.jsp").forward(request, response);
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
