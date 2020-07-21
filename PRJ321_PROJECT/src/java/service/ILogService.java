/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import model.Log;

/**
 *
 * @author ViruSs0209
 */
public interface ILogService {
    public void insert(Log log);
    
    public ArrayList<Log> getAll();
    
    public ArrayList<Log> getByID(String userID);
    
    public ArrayList<Log> getListByPage(int start, int end, int itemsPerPage, ArrayList<Log> totalLogs);
    
    public ArrayList<Log> search(String ID, String userEmail, String entity, String action, Timestamp createdAt, Timestamp createdTo);
}
