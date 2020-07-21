/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Pet;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.PageVisit;
import model.Pet;
import model.User;
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
public class PetsServlet extends HttpServlet {
    
    @Inject
    private IPetService petDB;
    
    @Inject
    private IPhotoService photoDB;
    
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        insertPageView(request.getRequestURI());
        
        User currentUser = (User) request.getSession().getAttribute("user");     

        ArrayList<Pet> pets = new ArrayList<Pet>();
        
        if (currentUser.getRole().equals("employee") || currentUser.getRole().equals("manager")) {
            pets = petDB.getAll();
        } else {
            pets = petDB.getPetsByOwner(currentUser.getUserID());
        }

        if (request.getSession().getAttribute("addSuccess") != null) {
            request.setAttribute("addSuccess", "Adding Pet Successfully !");
            request.getSession().setAttribute("addSuccess", null);
        }

        if (request.getSession().getAttribute("addFail") != null) {
            request.setAttribute("addFail", "Adding Pet Failed !");
            request.getSession().setAttribute("addFail", null);
        }

        if (request.getSession().getAttribute("updatePet") != null) {
            request.setAttribute("updatePet", "Update Pet Successfully !");
            request.getSession().setAttribute("updatePet", null);
        }
        
        if (request.getSession().getAttribute("disable") != null) {
            request.setAttribute("disable", "Pet(s) disabled successfully");
            request.getSession().setAttribute("disable", null);
        }
        
        if (request.getSession().getAttribute("deletedFail") != null) {
            request.setAttribute("deletedFail", "Pet(s) deleted UnSuccessfully");
            request.getSession().setAttribute("deletedFail", null);
        }
        
        if (request.getParameter("action") != null && !request.getParameter("action").equals("")) {
            pets = (ArrayList<Pet>) request.getSession().getAttribute("searchedPets");
            request.setAttribute("action", "search");
        } else {
            if (request.getSession().getAttribute("searchedPets") != null) {
                request.getSession().setAttribute("searchedPets", null);
            }
        }

        int currentPage = -1;
        int itemsPerPage = 5;

        if (request.getParameter("itemsPerPage") != null) {
            itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
        }

        int start = 0, end = itemsPerPage;

        if (pets.size() < itemsPerPage) {
            end = pets.size();
        }
        if (request.getParameter("start") != null) {
            start = Integer.parseInt(request.getParameter("start"));
        }
        if (request.getParameter("end") != null) {
            end = Integer.parseInt(request.getParameter("end"));
        }
        currentPage = (start / itemsPerPage) + 1;

        int totalPage = pets.size() / itemsPerPage;

        if ((totalPage * itemsPerPage) < pets.size()) {
            totalPage += 1;
        }

        ArrayList<Integer> listPages = new ArrayList();

        for (int i = 1; i <= totalPage; i++) {

            listPages.add(i);
        }

        request.setAttribute("pets", pets);
        request.setAttribute("currentMenu", "pets");
        request.setAttribute("currentPage", currentPage + "");
        request.setAttribute("itemsPerPage", itemsPerPage + "");
        request.setAttribute("pages", listPages);
        request.setAttribute("pets", pets);
        request.setAttribute("petsByPage", petDB.getListByPage(start, end, itemsPerPage, pets));

        request.getRequestDispatcher("view/pet/pets.jsp").forward(request, response);
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
        try {
            User currentUser = (User) request.getSession().getAttribute("user");
            
            String owner = request.getParameter("owner");
            String type = request.getParameter("type");
            String size = request.getParameter("size");
            String petName = request.getParameter("petName");
            String breed = request.getParameter("breed");

            if (owner != null && owner.equals("")) {
                request.setAttribute("ownerEmpty", "Owner Can't be Empty");
                request.getRequestDispatcher("view/pet/newPet.jsp").forward(request, response);
                return;
            } 
            if (petName.equals("")) {
                request.setAttribute("nameEmpty", "Name Can't be Empty");
                request.getRequestDispatcher("view/pet/newPet.jsp").forward(request, response);
                return;
            } 
            
            if (breed.equals("")) {
                request.setAttribute("breedEmpty", "Breed Can't be Empty");
                request.getRequestDispatcher("view/pet/newPet.jsp").forward(request, response);
                return;
            }

            Pet pet = new Pet();

            pet.setType(type);
            pet.setSize(size);
            pet.setName(petName);
            pet.setBreed(breed);
            pet.setID(generateId());
            
            if (owner == null) {           
                owner = currentUser.getEmail();
            }

            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);

            Timestamp currentTime = new Timestamp(currentDate.getTime());

            pet.setCreatedAt(currentTime);      

            Part filePart = request.getPart("avatar"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

            InputStream fileContent = null;
            String imageID = null;

            // Check If User Haven't Update Any Images
            if (!fileName.equals("")) {
                imageID = generateId();

                fileContent = filePart.getInputStream();

                pet.setAvatar(imageID);

                photoDB.insert(fileContent, "Pet", imageID, currentTime);
            }

            petDB.insert(owner, pet);

            request.getSession().setAttribute("addSuccess", "Adding Pet Successfully !");
        } catch (ParseException ex) {
            request.getSession().setAttribute("addSuccess", "Adding Pet Failed !");
        }

        response.sendRedirect("/pets");

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
