/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PageVisit;

/**
 *
 * @author ViruSs0209
 */
public class PageVisitDAO extends BaseDAO{
    public ArrayList<PageVisit> getAll() {
        ArrayList<PageVisit> pageVisits = new ArrayList<>();
        
        String sql = "SELECT * FROM PageVisit";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                pageVisits.add(new PageVisit(rs.getString(2), rs.getString(3), rs.getString(1), Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PageVisitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pageVisits;
    }
}
