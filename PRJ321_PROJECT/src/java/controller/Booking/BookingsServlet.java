/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Booking;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Booking;
import model.PageVisit;
import model.Pet;
import model.Setting;
import model.User;
import service.IBookingService;
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


public class BookingsServlet extends HttpServlet {
    @Inject
    IBookingService db;
    
    @Inject
    IPhotoService photoDB;
    
    @Inject
    IPetService petDB;
    
    @Inject
    IPageVisitService pageVisitDB;
    

    final long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public String generateId() {
        SecureRandom random = new SecureRandom();

        byte bytes[] = new byte[20];

        random.nextBytes(bytes);

        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        return uuid.toString();
    }

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
        // Increase Page View For Tracking
        insertPageView(request.getRequestURI());
        
        User currentUser = (User) request.getSession().getAttribute("user");

        ArrayList<Booking> bookings = db.getAll();

        if (currentUser.getRole().equals("employee") || currentUser.getRole().equals("manager")) {
            bookings = db.getAll();
        } else {
            bookings = db.getBookingByUserId(currentUser.getUserID());
        }

        if (request.getSession().getAttribute("updatedSuccess") != null) {
            request.setAttribute("updatedSuccess", "Update Profile Successfully !");
            request.getSession().setAttribute("updatedSuccess", null);
        }

        if (request.getSession().getAttribute("createBooking") != null) {
            request.setAttribute("createBooking", "Create Booking Successfully !");
            request.getSession().setAttribute("createBooking", null);
        }

        if (request.getSession().getAttribute("updateBooking") != null) {
            request.setAttribute("updateBooking", "Update Booking Successfully !");
            request.getSession().setAttribute("updateBooking", null);
        }

        if (request.getSession().getAttribute("fail") != null) {
            request.setAttribute("fail", "Update Booking UnSuccessfully !");
            request.getSession().setAttribute("fail", null);
        }

        if (request.getSession().getAttribute("bookingDeleted") != null) {
            request.setAttribute("bookingDeleted", "Booking deleted successfully !");
            request.getSession().setAttribute("bookingDeleted", null);
        }

        if (request.getParameter("action") != null && !request.getParameter("action").equals("")) {
            bookings = (ArrayList<Booking>) request.getSession().getAttribute("searchedBookings");
            
            request.setAttribute("action", "search");
        } else {
            System.out.println("clear searched");
            if (request.getSession().getAttribute("searchedBookings") != null) {
                request.getSession().setAttribute("searchedBookings", null);
            } 
        }

        int currentPage = -1;
        int itemsPerPage = 5;

        if (request.getParameter("itemsPerPage") != null) {
            itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
        }

        int start = 0, end = itemsPerPage;

        if (bookings.size() < itemsPerPage) {
            end = bookings.size();
        }
        if (request.getParameter("start") != null) {
            start = Integer.parseInt(request.getParameter("start"));
        }
        if (request.getParameter("end") != null) {
            end = Integer.parseInt(request.getParameter("end"));
        }
        currentPage = (start / itemsPerPage) + 1;

        int totalPage = bookings.size() / itemsPerPage;

        if ((totalPage * itemsPerPage) < bookings.size()) {
            totalPage += 1;
        }

        ArrayList<Integer> listPages = new ArrayList();

        for (int i = 1; i <= totalPage; i++) {

            listPages.add(i);
        }

        request.setAttribute("currentMenu", "bookings");
        request.setAttribute("currentPage", currentPage + "");
        request.setAttribute("itemsPerPage", itemsPerPage + "");
        request.setAttribute("pages", listPages);
        request.setAttribute("bookings", bookings);
        request.setAttribute("bookingsPerPage", db.getListByPage(start, end, itemsPerPage, bookings));

        request.getRequestDispatcher("view/booking/bookings.jsp").forward(request, response);
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
        try {
            
            Setting setting = (Setting) request.getSession().getAttribute("setting");

            String owner = request.getParameter("owner");
            String pet = request.getParameter("pet");
            String status = request.getParameter("status");
            String ownerNotes = request.getParameter("notes");
            String employeeNotes = request.getParameter("employeeNotes");
            String cancelNotes = request.getParameter("cancellationNotes");
            boolean extraServices = false;
            
            if (request.getParameter("extra") != null) {
                extraServices = true;
            }

            Timestamp periodAt = null;
            Timestamp periodTo = null;

            if (request.getParameter("periodAt") != null && !request.getParameter("periodAt").equals("")) {
                periodAt = convertToTimestamp(request.getParameter("periodAt"));
            }

            if (request.getParameter("periodTo") != null && !request.getParameter("periodTo").equals("")) {
                periodTo = convertToTimestamp(request.getParameter("periodTo"));
            }
            
            if (db.getAll().size() + 1 > setting.getCapacity()) {
                request.setAttribute("fullCapacity", "Booking is Full ! See You Next Time :)");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/newBooking.jsp").forward(request, response);
                return;
            }

            if (owner != null && owner.equals("")) {
                request.setAttribute("ownerEmpty", "Owner Can't be Empty");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/booking/newBooking.jsp").forward(request, response);
                return;
            } else if (pet == null || pet.equals("")) {
                request.setAttribute("nameEmpty", "Pet Can't be Empty");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/booking/newBooking.jsp").forward(request, response);
                return;
            } else if (periodAt == null || periodTo == null) {
                request.setAttribute("periodEmpty", "Period Range Can't be Empty");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/booking/newBooking.jsp").forward(request, response);
                return;
            } else if (periodAt.before(new Date()) || periodTo.before(new Date())) {
                request.setAttribute("periodFuture", "The Period Must Be In The Future");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/booking/newBooking.jsp").forward(request, response);
                return;
            } else if (periodAt.after(periodTo)) {
                request.setAttribute("periodInvalid", "Arrival Date Must After Depature Date !");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/booking/newBooking.jsp").forward(request, response);
                return;
            } else if (db.checkPetBooked(pet)) {
                request.setAttribute("petInvalid", "Booking Failed ! Pet is Already Booked !");
                request.setAttribute("currentMenu", "bookings");
                request.getRequestDispatcher("view/booking/newBooking.jsp").forward(request, response);
                return;
            }
            
            Pet bookedPet = petDB.getByID(pet);

            Booking booking = new Booking();
            
            if (owner == null) {
                User currentUser = (User) request.getSession().getAttribute("user");
                owner = currentUser.getEmail();
            }

            if (status != null) {
                booking.setStatus(status);
            } else {
                booking.setStatus("booked");
            }

            if (employeeNotes != null && cancelNotes != null) {
                booking.setManagerNotes(employeeNotes.trim());
                booking.setCancelNotes(cancelNotes.trim());
            } else {
                booking.setManagerNotes("");
                booking.setCancelNotes("");
            }

            booking.setExtraServices(extraServices);
            booking.setOwnerNotes(ownerNotes.trim());
            booking.setArrival(periodTo);
            booking.setDeparture(periodAt);
            booking.setId(generateId());

            long periodTimeAt = periodAt.getTime() - periodAt.getTime() % MILLIS_PER_DAY;
            long periodTimeTo = periodTo.getTime() - periodTo.getTime() % MILLIS_PER_DAY;

            long days = TimeUnit.DAYS.convert(periodTimeTo - periodTimeAt, TimeUnit.MILLISECONDS);
            
            double totalFee = 0;
            
            switch(bookedPet.getSize()) {
                case "small": {
                    totalFee += (double) days * setting.getSmallPetFee();
                    break;
                }
                case "medium": {
                    totalFee += (double) days * setting.getMediumPetFee();
                    break;
                }
                case "large": {
                    totalFee += (double) days * setting.getFee();
                    break;
                }
            }
            
            if (extraServices) {
                totalFee += setting.getExtraFee();
            }

            booking.setFee(totalFee);

            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);

            Timestamp currentTime = new Timestamp(currentDate.getTime());

            booking.setCreatedAt(currentTime);

            if (request.getPart("receipt") != null) {
                Part filePart = request.getPart("receipt"); // Retrieves <input type="file" name="file">
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

                InputStream fileContent = null;
                String imageID = null;

                // Check If User Haven't Update Any Images
                if (!fileName.equals("")) {
                    imageID = generateId();

                    fileContent = filePart.getInputStream();

                    booking.setReceipt(imageID);

                    photoDB.insert(fileContent, "Receipt", imageID, currentTime);
                }
            }

            db.insert(booking, owner, pet);
//            
            request.getSession().setAttribute("createBooking", "Booking Successfully !");
        } catch (ParseException ex) {
            Logger.getLogger(BookingsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
