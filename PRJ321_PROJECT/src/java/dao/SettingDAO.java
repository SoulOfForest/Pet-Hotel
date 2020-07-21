/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import model.Setting;
import service.ISettingService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class SettingDAO extends BaseDAO implements ISettingService{

    public String generateId() {
        SecureRandom random = new SecureRandom();

        byte bytes[] = new byte[20];

        random.nextBytes(bytes);

        UUID uuid = UUID.nameUUIDFromBytes(bytes);

        return uuid.toString();
    }

    @Override
    public Setting getSettings() {
        String sql = "SELECT * FROM Settings";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String themeColor = rs.getString(2);
                Timestamp createdAt = rs.getTimestamp(3);
                Timestamp updatedAt = rs.getTimestamp(4);
                Double fee = rs.getDouble(5);
                int capacity = rs.getInt(6);
                Double smallPetFee = rs.getDouble(7);
                Double mediumPetFee = rs.getDouble(8);
                Double extraFee = rs.getDouble(9);

                Setting setting = new Setting(id, themeColor, fee, smallPetFee, mediumPetFee, extraFee, capacity, updatedAt, createdAt);

                return setting;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void update(Setting setting) {
        String sql = "UPDATE [dbo].[Settings]\n"
                + "      SET [Theme] = ?\n"
                + "      ,[UpdatedAt] = ?\n"
                + "      ,[Fee] = ?\n"
                + "      ,[Capacity] = ?\n"
                + "      ,[SmallPetFee] = ?\n"
                + "      ,[MediumPetFee] = ?\n"
                + "      ,[ExtraFee] = ?\n"
+ " WHERE ID = '" + setting.getId() + "'";
        System.out.println(setting.getId());
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.setString(1, setting.getTheme());
            statement.setTimestamp(2, setting.getUpdatedAt());
            statement.setDouble(3, setting.getFee());
            statement.setInt(4, setting.getCapacity());
            statement.setDouble(5, setting.getSmallPetFee());
            statement.setDouble(6, setting.getMediumPetFee());
            statement.setDouble(7, setting.getExtraFee());       

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insert(Setting theme) {
        String sql = "INSERT INTO [dbo].[Settings]\n"
                + "           ([ID]\n"
                + "           ,[Theme]\n"
                + "           ,[CreatedAt]\n"
                + "           ,[Fee])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            statement.setString(1, generateId());
            statement.setString(2, theme.getTheme());
            statement.setTimestamp(3, theme.getCreatedAt());
            statement.setDouble(4, theme.getFee());

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
