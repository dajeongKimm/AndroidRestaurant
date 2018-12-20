package com.example.tje.food.Model;

public class MenuList {

    private int menulist_id;
    private int restaurant_id;
    private String menu_title;
    private double menu_price;
    private int menu_calory;
    private String menu_imagepath;

    public int getMenulist_id() {
        return menulist_id;
    }
    public void setMenulist_id(int menulist_id) {
        this.menulist_id = menulist_id;
    }
    public int getRestaurant_id() {
        return restaurant_id;
    }
    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
    public String getMenu_title() {
        return menu_title;
    }
    public void setMenu_title(String menu_title) {
        this.menu_title = menu_title;
    }
    public double getMenu_price() {
        return menu_price;
    }
    public void setMenu_price(double menu_price) {
        this.menu_price = menu_price;
    }
    public int getMenu_calory() {
        return menu_calory;
    }
    public void setMenu_calory(int menu_calory) {
        this.menu_calory = menu_calory;
    }
    public String getMenu_imagepath() {
        return menu_imagepath;
    }
    public void setMenu_imagepath(String menu_imagepath) {
        this.menu_imagepath = menu_imagepath;
    }

}
