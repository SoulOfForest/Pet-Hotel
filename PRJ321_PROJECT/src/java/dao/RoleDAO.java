/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import service.IRoleService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class RoleDAO extends BaseDAO implements IRoleService{
    public void update(String userID, String role) {
        String sql = "UPDATE [dbo].[User_Roles]\n" +
"   SET [Role] = ?\n" +
" WHERE UserID = '"  + userID + "'";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            statement.setString(1, role);
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
