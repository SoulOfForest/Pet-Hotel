/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.InputStream;
import java.sql.Timestamp;
import model.Photo;

/**
 *
 * @author ViruSs0209
 */
public interface IPhotoService {
    public void deletePhotoById(String id);
    
    public Photo getPhotoByID(String id);
    
    public void insert(InputStream image, String type, String id, Timestamp currentTime);
}
