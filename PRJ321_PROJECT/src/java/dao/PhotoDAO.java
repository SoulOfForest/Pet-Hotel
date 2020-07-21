/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import model.Photo;
import service.IPhotoService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class PhotoDAO extends BaseDAO implements IPhotoService{

    @Override
    public void deletePhotoById(String id) {
        String sql = "DELETE FROM Photos WHERE ID = '" + id + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Photo getPhotoByID(String id) {
        String sql = "SELECT * FROM Photos WHERE ID = '" + id + "'";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Photo photo = new Photo(rs.getString(1), rs.getString(4), rs.getTimestamp(3), rs.getBlob(2));

                return photo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void insert(InputStream image, String type, String id, Timestamp currentTime) {
        String sql = "INSERT INTO [dbo].[Photos]\n"
                + "           ([ID]\n"
                + "           ,[image]\n"
                + "           ,[CreatedAt]\n"
                + "           ,[Type])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.setString(1, id);
            statement.setBinaryStream(2, image, image.available());
            statement.setTimestamp(3, currentTime);
            statement.setString(4, type);
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
