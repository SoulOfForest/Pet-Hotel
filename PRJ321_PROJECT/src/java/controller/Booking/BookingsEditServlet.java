/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Booking;

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
import model.Booking;
import model.Feedback;
import model.Log;
import model.PageVisit;
import model.User;
import service.IBookingService;
import service.IFeedbackService;
import service.ILogService;
import service.IPageVisitService;
import service.IPetService;
import service.IPhotoService;

/**
 *
 * @author ViruSs0209
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)

public class BookingsEditServlet extends HttpServlet {
    @Inject
    IBookingService bookingDB;
    
    @Inject
    IPetService petDB;
    
    @Inject
    ILogService logDB;
    
    @Inject
    IFeedbackService feedbackDB;
    
    @Inject
    IPhotoService photoDB;
    
    @Inject
    IPageVisitService pageVisitDB;
    
    public void insertPageView (String uri) {
        Calendar calendar = Calendar.getInstance();
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        
        PageVisit page = new PageVisit(month, year, uri, 1);
        pageVisitDB.update(page);
    }
    
    public String generateId() {
        SecureRandom random = new SecureRandom();

        byte bytes[] = new byte[20];

        random.nextBytes(bytes);

        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        return uuid.toString();
    }

    public Timestamp convertToTimestamp(String temp) {
        if (temp.contains(".")) {
            temp = temp.substring(0, temp.lastIndexOf("."));
        } else {
            temp = temp.replaceAll("/", "-");
            temp += ":00";
        }
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
        insertPageView(request.getRequestURI());
        
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");

        if (request.getSession().getAttribute("nameEmpty") != null) {
            request.setAttribute("nameEmpty", "Pet Can't be Empty !");
            request.getSession().setAttribute("nameEmpty", null);
        }

        if (request.getSession().getAttribute("periodEmpty") != null) {
            request.setAttribute("periodEmpty", "Period Range Can't be Empty !");
            request.getSession().setAttribute("periodEmpty", null);
        }

        if (request.getSession().getAttribute("periodFuture") != null) {
            request.setAttribute("periodFuture", "The Period Must Be In The Future !");
            request.getSession().setAttribute("periodFuture", null);
        }

        if (request.getSession().getAttribute("periodInvalid") != null) {
            request.setAttribute("periodInvalid", "Arrival Date Must After Depature Date !");
            request.getSession().setAttribute("periodInvalid", null);
        }

        System.out.println(petDB.getPetsByOwner(owner));

        request.setAttribute("pets", petDB.getPetsByOwner(owner));
        request.setAttribute("booking", bookingDB.getBookingById(id));
        request.setAttribute("currentMenu", "bookings");
        request.getRequestDispatcher("/view/booking/bookingEdit.jsp").forward(request, response);
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
        String userID = request.getParameter("userID");
        String pet = request.getParameter("pet");
        String notes = request.getParameter("notes");
        String employeeNotes = request.getParameter("employeeNotes");
        String cancellationNotes = request.getParameter("cancellationNotes");
        String fee = request.getParameter("fee");
        String totalFeeInText = request.getParameter("fee");
        double totalFee = Double.parseDouble(totalFeeInText.substring(1, totalFeeInText.length()));
        String status = request.getParameter("status");
        String sentFeedback = request.getParameter("feedback");
        boolean extraServices = false;

        if (request.getParameter("extra") != null) {
            extraServices = true;
        }

        Timestamp periodAt = convertToTimestamp(request.getParameter("createdAt"));
        Timestamp periodTo = convertToTimestamp(request.getParameter("createdTo"));

        User currentUser = (User) request.getSession().getAttribute("user");

        if (pet == null || pet.equals("")) {
            request.getSession().setAttribute("nameEmpty", "Pet Can't be Empty");
            response.sendRedirect("/bookings/edit?id=" + id + "&owner=" + owner);
            return;
        }
        if (periodAt == null || periodTo == null) {
            request.getSession().setAttribute("periodEmpty", "Period Range Can't be Empty");
            response.sendRedirect("/bookings/edit?id=" + id + "&owner=" + owner);
            return;
        }

        if ((periodAt.before(new Date()) || periodTo.before(new Date())) && currentUser.getRole().equals("pet owner")) {
            request.getSession().setAttribute("periodFuture", "The Period Must Be In The Future");
            response.sendRedirect("/bookings/edit?id=" + id + "&owner=" + owner);
            return;
        }

        if (periodAt.after(periodTo)) {
            request.getSession().setAttribute("periodInvalid", "Arrival Date Must After Depature Date !");
            response.sendRedirect("/bookings/edit?id=" + id + "&owner=" + owner);
            return;
        }

        Booking bookingById = bookingDB.getBookingById(id);

        // Create Log User For What They Updated
        JsonObject jsonObject = new JsonObject();

        if (currentUser.getRole().equals("pet owner")) {
            jsonObject.addProperty("UserID", currentUser.getUserID());
        } else if (currentUser.getRole().equals("employee")) {
            jsonObject.addProperty("EmployeeID", currentUser.getUserID());
        } else {
            jsonObject.addProperty("ManagerID", currentUser.getUserID());
        }

        jsonObject.addProperty("UpdatedBooking", bookingById.getId());

        if (!bookingById.getUserEmail().equals(owner)) {
            jsonObject.addProperty("Owner", owner);
        }

        if (!bookingById.getPetId().equals(pet)) {
            jsonObject.addProperty("Pet", pet);
        }

        if (!bookingById.getStatus().equals(status)) {
            jsonObject.addProperty("Status", status);
        }

        if (bookingById.isExtraServices() != extraServices) {
            jsonObject.addProperty("Extra Services", extraServices);
        }

        if (!bookingById.getOwnerNotes().equals(notes)) {
            jsonObject.addProperty("Owner Notes", notes);
        }

        if (employeeNotes != null && !bookingById.getManagerNotes().equals(employeeNotes)) {
            jsonObject.addProperty("Manager Notes", employeeNotes);
        }

        if (cancellationNotes != null && !bookingById.getCancelNotes().equals(cancellationNotes)) {
            jsonObject.addProperty("Cancellation Notes", cancellationNotes);
        }

        if (bookingById.getFee() != totalFee) {
            jsonObject.addProperty("Total Fee", totalFee);
        }

        if (!bookingById.getArrival().equals(periodTo)) {
            jsonObject.addProperty("Arrival", periodTo.toString());
        }

        if (!bookingById.getDeparture().equals(periodAt)) {
            jsonObject.addProperty("Departure", periodAt.toString());
        }

        // Set What Updated For User
        bookingById.setExtraServices(extraServices);
        bookingById.setUserEmail(owner);
        bookingById.setStatus(status);
        bookingById.setPetId(pet);
        bookingById.setOwnerNotes(notes.trim());

        if (employeeNotes != null) {
            bookingById.setManagerNotes(employeeNotes.trim());
        }

        if (cancellationNotes != null) {
            bookingById.setCancelNotes(cancellationNotes.trim());
        }

        bookingById.setArrival(periodTo);
        bookingById.setDeparture(periodAt);
        bookingById.setFee(totalFee);

        try {
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);

            Timestamp currentTime = new Timestamp(currentDate.getTime());

            bookingById.setUpdatedAt(currentTime);

            if (request.getPart("receipt") != null) {
                Part filePart = request.getPart("receipt"); // Retrieves <input type="file" name="file">     
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

                InputStream fileContent = null;

                String imageID = null;
                // Check If User Haven't Update Any Images
                if (!fileName.equals("")) {

                    if (bookingById.getReceipt() != null) {
                        photoDB.deletePhotoById(bookingById.getReceipt());
                    }

                    imageID = generateId();

                    fileContent = filePart.getInputStream();

                    bookingById.setReceipt(imageID);
                    jsonObject.addProperty("receipt", imageID);

                    photoDB.insert(fileContent, "Receipt", imageID, currentTime);
                }
            }

            bookingDB.update(bookingById);

            // Create Log For Update Action
            Log log = new Log();

            log.setUserEmail(currentUser.getEmail());
            log.setEntity("Booking");
            log.setEntityID(id);
            log.setAction("Updated");
            log.setContent(jsonObject.toString());
            log.setCreatedAt(currentTime);

            logDB.insert(log);

            if (sentFeedback != null) {
                Feedback feedback = new Feedback();
                Log feedbackLog = new Log();
                JsonObject jsonFeedback = new JsonObject();
                
                String feedbackId = generateId();
                          
                feedback.setId(feedbackId);
                feedback.setUser(owner);
                feedback.setBookingID(id);
                feedback.setContent(null);
                feedback.setCreatedAt(currentTime);
                feedback.setStatus("Not");
                
                jsonFeedback.addProperty("Received", owner);
                jsonFeedback.addProperty("BookingID", id);
                jsonFeedback.addProperty("Created At", currentTime.toString());
                jsonFeedback.addProperty("Content", "");
                jsonFeedback.addProperty("Status", "Not");
                
                feedbackLog.setUserEmail(currentUser.getEmail());
                feedbackLog.setEntity("Feedback");
                feedbackLog.setEntityID(id);
                feedbackLog.setAction("Created");
                feedbackLog.setContent(jsonFeedback.toString());
                feedbackLog.setCreatedAt(currentTime);
                
                feedbackDB.insert(feedback);
                logDB.insert(feedbackLog);
            }

            request.getSession().setAttribute("updateBooking", "Update Booking Successfully !");

        } catch (ParseException ex) {
            request.getSession().setAttribute("fail", "Update Booking Unsuccessfully !");
        }

        response.sendRedirect("/bookings");
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
