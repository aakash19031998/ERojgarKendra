package com.twocoms.rojgarkendra.dashboardscreen.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NavMenuModel {

    String iconName = "";
    int iconImg = -1; // menu icon resource id

    public String getIconName() {
        return iconName;
    }
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    public int getIconImg() {
        return iconImg;
    }
    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }

    public ArrayList<NavMenuModel> getAllSubMenu() {
        return allSubMenu;
    }

    public void setAllSubMenu(ArrayList<NavMenuModel> allSubMenu) {
        this.allSubMenu = allSubMenu;
    }

    ArrayList<NavMenuModel> allSubMenu = new ArrayList<>();

}
