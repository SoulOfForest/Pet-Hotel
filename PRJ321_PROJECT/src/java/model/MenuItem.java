/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ViruSs0209
 */
public class MenuItem {
    private String name;
    private String icon;
    private String href;

    public MenuItem(String name, String icon, String href) {
        this.name = name;
        this.icon = icon;
        this.href = href;
    }

    public MenuItem() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    
}
