/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import model.Pet;

/**
 *
 * @author ViruSs0209
 */
public interface IPetService {
    public void insert(String owner, Pet pet);
    
    public ArrayList<Pet> getListByPage(int start, int end, int itemsPerPage, ArrayList<Pet> totalPets);
    
    public boolean isBooking(String id);
    
    public ArrayList<Pet> getPetsByOwner(String owner);
    
    public Pet getByID(String id);
    
    public ArrayList<Pet> getAll();
    
    public void updatePet(Pet pet);
    
    public void delete(Pet pet);
    
    public ArrayList<Pet> search(String owner, String type, String size, String name, String breed);
}
