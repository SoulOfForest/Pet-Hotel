package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Qualifier;
import model.Booking;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ViruSs0209
 */
public interface IBookingService {

    public void insert(Booking booking, String owner, String petId);

    public void delete(String id);

    public void update(Booking booking);

    public ArrayList<Booking> getListByPage(int start, int end, int itemsPerPage, ArrayList<Booking> totalBookings);

    public ArrayList<Booking> getAll();
    
    public ArrayList<Booking> getBookingByUserId(String id);
    
    public Booking getBookingByPetId(String id);
    
    public Booking getBookingById(String id);
    
    public boolean checkPetBooked(String petID);
    
    public ArrayList<Booking> search(String id, String owner, Timestamp periodAt, Timestamp periodTo, Timestamp createdAt, Timestamp createdTo, String feeFrom, String FeeTo, boolean extraFee, String petName, String status);
}
