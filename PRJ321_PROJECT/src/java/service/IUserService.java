/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author ViruSs0209
 */
public interface IUserService {
    public String hashPassword(String password);
            
    public User getUser(String userName, String password);
    
    public ArrayList<User> getAll();
    
     public ArrayList<User> getListByPage(int start, int end, int itemsPerPage, ArrayList<User> totalUsers);
     
     public ArrayList<User> search(String ID, String name, String email, String role, String status, Timestamp createdAt, Timestamp createdTo);
     
     public User getByID(String id);
     
     public void updateUser(User user);
     
     public Error isValidRegistration(User user);
     
     public void register(User user);
}
