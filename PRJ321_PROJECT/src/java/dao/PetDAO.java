/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.lang.annotation.Retention;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import model.Pet;
import service.IPetService;

/**
 *
 * @author ViruSs0209
 */
@Default
@ManagedBean
public class PetDAO extends BaseDAO implements IPetService {
    
    @Override
    public void insert(String owner, Pet pet) {
        String sql = "INSERT INTO [dbo].[Pet]\n" +
"           ([ID]\n" +
"           ,[Photo]\n" +
"           ,[Name]\n" +
"           ,[Type]\n" +
"           ,[Breed]\n" +
"           ,[Size]\n" +
"           ,[CreatedAt]\n)" +
"     VALUES\n" +
"           (?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?" +
"           ,?)";
        
        String insertPetOwnerSQL = "INSERT INTO [dbo].[Pet_Owner]\n" +
"           ([PetID]\n" +
"           ,[Owner])\n" +
"     VALUES\n" +
"           (?\n" +
"           ,?)";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            PreparedStatement statement2 = this.connection.prepareCall(insertPetOwnerSQL);
            
            statement.setString(1, pet.getID());
            statement.setString(2, pet.getAvatar());
            statement.setString(3, pet.getName());
            statement.setString(4, pet.getType());
            statement.setString(5, pet.getBreed());
            statement.setString(6, pet.getSize());
            statement.setTimestamp(7, pet.getCreatedAt());
            
            statement2.setString(1, pet.getID());
            statement2.setString(2, owner);
            
            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public ArrayList<Pet> getListByPage(int start, int end, int itemsPerPage, ArrayList<Pet> totalPets) {
        ArrayList<Pet> petsByPage = new ArrayList<>();

        if (end > totalPets.size()) {
            end = totalPets.size();
            start = end - itemsPerPage;
            
            if (start < 0) {
                start = 0;
            }
        }
        
        
        for(int i = start; i < end ; i++) {
            petsByPage.add(totalPets.get(i));
        }

        return petsByPage;
    }
    
    public String appendQueryParam(String sql, String param, Object paramValue) {
        if (paramValue != null && !(paramValue instanceof Timestamp)) {
            if (sql.contains("WHERE")) {
                sql += " AND " + param + " LIKE '%" + paramValue.toString() + "%'";
            } else {
                sql += " WHERE " + param +  " LIKE '%" + paramValue.toString() + "%'";
            }
        } else {
            if (sql.contains("WHERE")) {
                sql += " AND " + param; 
            } else {
                sql += " WHERE " + param;
            }
        }
        
        return sql;
    }
    
    public ArrayList<Pet> search(String owner, String type, String size, String name, String breed) {
        
        ArrayList<Pet> pets = new ArrayList<Pet>();
        
        String sql = "SELECT ID, Photo, Name, Type, Breed, Size, CreatedAt, UpdatedAt, Owner FROM Pet JOIN Pet_Owner ON Pet.ID = Pet_Owner.PetID";
        
        sql = appendQueryParam(sql, "Owner", owner);
        sql = appendQueryParam(sql, "Type", type);
        sql = appendQueryParam(sql, "Size", size);
        sql = appendQueryParam(sql, "Name", name);
        sql = appendQueryParam(sql, "Breed", breed);
        
       
        System.out.println(sql);
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                String ID = rs.getString(1);
                String avatar = rs.getString(2);
                String petName = rs.getString(3);
                String petType = rs.getString(4);
                String petBreed = rs.getString(5);
                String petSize = rs.getString(6);
                Timestamp createdAt = rs.getTimestamp(7);
                Timestamp updatedAt = rs.getTimestamp(8);
                String petOwner = rs.getString(9);

                Pet pet = new Pet(ID, avatar, petName, petType, petBreed, petSize, petOwner, createdAt, updatedAt);

                pets.add(pet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pets;
    }
    
    @Override
    public boolean isBooking(String id) {
        String sql = "SELECT * FROM Pet JOIN Pet_Booking ON Pet_Booking.PetID = Pet.ID JOIN Booking ON Booking.ID = Pet_Booking.BookingID"
                + " WHERE NOT Status = 'completed' AND Pet.ID = '" + id + "'";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);
            
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    @Override
    public ArrayList<Pet> getPetsByOwner(String owner) {
        
        ArrayList<Pet> pets = new ArrayList<Pet>();
        
        String sql = "SELECT ID, Photo, Name, Type, Breed, Size, Pet.CreatedAt, Pet.UpdatedAt, Owner "
                + "FROM Pet JOIN Pet_Owner ON Pet.ID = Pet_Owner.PetID "
                + "JOIN Users ON Users.Email = Pet_Owner.Owner WHERE Users.UserID = '" + owner + "' OR Users.Email = '" + owner + "'";
 
        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                String ID = rs.getString(1);
                String avatar = rs.getString(2);
                String petName = rs.getString(3);
                String petType = rs.getString(4);
                String petBreed = rs.getString(5);
                String petSize = rs.getString(6);
                Timestamp createdAt = rs.getTimestamp(7);
                Timestamp updatedAt = rs.getTimestamp(8);
                String petOwner = rs.getString(9);

                Pet pet = new Pet(ID, avatar, petName, petType, petBreed, petSize, petOwner, createdAt, updatedAt);

                pets.add(pet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pets;
    }
    
    @Override
    public Pet getByID(String id) {
        String sql = "SELECT ID, Photo, Name, Type, Breed, Size, Pet.CreatedAt, Pet.UpdatedAt, Users.Email, Users.UserID "
                + "FROM Pet JOIN Pet_Owner ON Pet.ID = Pet_Owner.PetID JOIN Users ON Users.Email = Pet_Owner.Owner WHERE ID = '" + id + "'" ;

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String ID = rs.getString(1);
                String avatar = rs.getString(2);
                String petName = rs.getString(3);
                String petType = rs.getString(4);
                String petBreed = rs.getString(5);
                String petSize = rs.getString(6);
                Timestamp createdAt = rs.getTimestamp(7);
                Timestamp updatedAt = rs.getTimestamp(8);
                String petOwner = rs.getString(9);
                String petOwnerID = rs.getString(10);

                Pet pet = new Pet(ID, avatar, petName, petType, petBreed, petSize, petOwner, petOwnerID, createdAt, updatedAt);
                
                return pet;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    @Override
    public ArrayList<Pet> getAll() {
        ArrayList<Pet> pets = new ArrayList<Pet>();

        String sql = "SELECT ID, Photo, Name, Type, Breed, Size, CreatedAt, UpdatedAt, Owner FROM Pet JOIN Pet_Owner ON Pet.ID = Pet_Owner.PetID";

        try {
            PreparedStatement statement = this.connection.prepareCall(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String ID = rs.getString(1);
                String avatar = rs.getString(2);
                String name = rs.getString(3);
                String type = rs.getString(4);
                String breed = rs.getString(5);
                String size = rs.getString(6);
                Timestamp createdAt = rs.getTimestamp(7);
                Timestamp updatedAt = rs.getTimestamp(8);
                String owner = rs.getString(9);

                Pet pet = new Pet(ID, avatar, name, type, breed, size, owner, createdAt, updatedAt);

                pets.add(pet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pets;
    }
    
    @Override
    public void updatePet(Pet pet) {
        String updatePetSQL = "UPDATE [dbo].[Pet]\n" +
"   SET [Photo] = ?\n" +
"      ,[Name] = ?\n" +
"      ,[Type] = ?\n" +
"      ,[Breed] = ?\n" +
"      ,[Size] = ?\n" +
"      ,[UpdatedAt] = ?\n" +
" WHERE ID = '" + pet.getID() + "'";
        
        String updatePetOwnerSQL = "UPDATE [dbo].[Pet_Owner]\n" +
"   SET [Owner] = ?\n" +
" WHERE PetID = '" + pet.getID() + "'";
 
        try {
            PreparedStatement statement = this.connection.prepareCall(updatePetSQL);
            PreparedStatement statement2 = this.connection.prepareCall(updatePetOwnerSQL);

            statement.setString(1, pet.getAvatar());
            statement.setString(2, pet.getName());
            statement.setString(3, pet.getType());
            statement.setString(4, pet.getBreed());
            statement.setString(5, pet.getSize());
            statement.setTimestamp(6, pet.getUpdatedAt());
            
            statement2.setString(1, pet.getOwner());
            
            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void delete(Pet pet) {
        String petDeleteSQL = "DELETE FROM [dbo].[Pet]\n" +
"      WHERE ID = '" + pet.getID() + "'";
        
        String petOwnerDeleteSQL = "DELETE FROM [dbo].[Pet_Owner]\n" +
"      WHERE ID = '" + pet.getID() + "'";
        
        try {
            PreparedStatement statement = this.connection.prepareCall(petDeleteSQL);
            PreparedStatement statement2 = this.connection.prepareCall(petOwnerDeleteSQL);
            
            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
}
