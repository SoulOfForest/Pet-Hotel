/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Setting;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import model.Log;
import model.Setting;
import model.User;
import service.ILogService;
import service.ISettingService;

/**
 *
 * @author ViruSs0209
 */
public class SettingsServlet extends HttpServlet {
    @Inject
    ISettingService db;
    
    @Inject
    ILogService logDB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Setting> themes = new ArrayList();
  
        themes.add(new Setting("default", "Default", "rgb(24, 144, 255)"));
        themes.add(new Setting("cyan", "Cyan", "rgb(19, 194, 194)"));
        themes.add(new Setting("geekblue", "Geek Blue", "rgb(47, 84, 235)"));
        themes.add(new Setting("gold", "Gold", "rgb(250, 173, 20)"));
        themes.add(new Setting("lime", "Lime", "rgb(160, 217, 17)"));
        themes.add(new Setting("magenta","Magenta", "rgb(235, 47, 150)"));
        themes.add(new Setting("orange","Orange", "rgb(250, 140, 22)"));
        themes.add(new Setting("polargreen","Polar Green", "rgb(82, 196, 26)"));
        themes.add(new Setting("purple","Purple", "rgb(114, 46, 209)"));
        themes.add(new Setting("red", "Red", "rgb(245, 34, 45)"));
        
        Setting themeSession = (Setting) request.getSession().getAttribute("setting");
        Setting currentTheme = null;
        
        for (int i = 0; i < themes.size(); i++) {
            if (themes.get(i).getId().equals(themeSession.getTheme())) {
                currentTheme = themes.get(i);
                break;
            }
        }
        
        request.setAttribute("currentTheme", currentTheme.getColor());
        request.setAttribute("themes", themes);
        request.setAttribute("currentMenu", "settings");
        request.getRequestDispatcher("view/setting/settings.jsp").forward(request, response);
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
            DateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date now = new Date(System.currentTimeMillis());

            String formattedDate = dateFormatOutput.format(now);
            Date currentDate = dateFormatOutput.parse(formattedDate);

            Timestamp currentTime = new Timestamp(currentDate.getTime());

            String theme = request.getParameter("theme");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            double smallFee = Double.parseDouble(request.getParameter("fee"));
            double mediumFee = Double.parseDouble(request.getParameter("mediumFee"));
            double largeFee = Double.parseDouble(request.getParameter("largeFee"));
            double extraFee = Double.parseDouble(request.getParameter("extraFee"));

            Setting themeSession = (Setting) request.getSession().getAttribute("setting");
            User currentUser = (User) request.getSession().getAttribute("user");

            Log themeLog = new Log();
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("UserID", currentUser.getUserID());

            if (!themeSession.getTheme().equals(theme)) {
                jsonObject.addProperty("theme", theme);
            }

            if (themeSession.getSmallPetFee()!= smallFee) {
                jsonObject.addProperty("Small Pet Fee", smallFee);
            }
            
            if (themeSession.getFee()!= largeFee) {
                jsonObject.addProperty("Large Pet Fee", largeFee);
            }
            
            if (themeSession.getExtraFee()!= extraFee) {
                jsonObject.addProperty("Small Pet Fee", smallFee);
            }
            
            if (themeSession.getFee() != smallFee) {
                jsonObject.addProperty("Small Pet Fee", smallFee);
            }
            
            if (themeSession.getCapacity()!= capacity) {
                jsonObject.addProperty("capacity", capacity);
            }

            themeLog.setUserEmail(currentUser.getEmail());
            themeLog.setEntity("Settings");
            themeLog.setEntityID("Default");
            themeLog.setAction("Updated");
            themeLog.setContent(jsonObject.toString());
            themeLog.setCreatedAt(currentTime);

            themeSession.setTheme(theme);
            themeSession.setFee(largeFee);
            themeSession.setMediumPetFee(mediumFee);
            themeSession.setSmallPetFee(smallFee);
            themeSession.setExtraFee(extraFee);
            themeSession.setUpdatedAt(currentTime);
            themeSession.setCapacity(capacity);
            
            request.getSession().setAttribute("setting", theme);

            db.update(themeSession);
            logDB.insert(themeLog);

            request.getSession().setAttribute("updatedSettings", "Update Settings Successfully !");
            response.sendRedirect("/users");

        } catch (ParseException ex) {
            Logger.getLogger(SettingsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
