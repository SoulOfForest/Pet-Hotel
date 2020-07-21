/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Setting;

/**
 *
 * @author ViruSs0209
 */
public interface ISettingService {
    public Setting getSettings();
    
    public void update(Setting setting);
}
