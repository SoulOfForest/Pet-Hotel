/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import model.PageVisit;
import service.IPageVisitService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class PageVisitDAO extends BaseDAO implements IPageVisitService {

    @Override
    public PageVisit isExist(PageVisit page) {
        String sql = "SELECT * FROM PageVisit WHERE page = '" + page.getPage() + "' AND month = '" + page.getMonth() + "' AND year = '" + page.getYear() + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return new PageVisit(rs.getString(2), rs.getString(3), rs.getString(1), Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PageVisitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void update(PageVisit page) {
        PageVisit existedPage = isExist(page);
        if (existedPage != null) {
            int updatedVisit =  existedPage.getVisit() + 1;
                    
            String sql = "UPDATE [dbo].[PageVisit]\n"
                    + "   SET [visit] = ?\n"
                    + " WHERE ID = " + existedPage.getId();
            
            try {
                PreparedStatement statement = this.connection.prepareCall(sql);
                
                statement.setInt(1, updatedVisit);
                
                statement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(PageVisitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            insert(page);
        }
    }

    @Override
    public void insert(PageVisit page) {
        String sql = "INSERT INTO [dbo].[PageVisit]\n"
                + "           ([page]\n"
                + "           ,[month]\n"
                + "           ,[year]\n"
                + "           ,[visit])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.setString(1, page.getPage());
            statement.setString(2, page.getMonth());
            statement.setString(3, page.getYear());
            statement.setInt(4, page.getVisit());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PageVisitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
