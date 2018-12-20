package com.example.tje.food.Model;

public class RestaurantListView {

    //안드로이드에서 불러올 음식점 리스트 컬럼들
    private int restaurant_id;
    private String restaurant_mainimage;
    private String restaurant_name;
    private String restaurant_description;
    private String menu_type;
    private int read_count;
    private double sum_score;
    private int allcount;


    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_mainimage() {
        return restaurant_mainimage;
    }

    public void setRestaurant_mainimage(String restaurant_mainimage) {
        this.restaurant_mainimage = restaurant_mainimage;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_description() {
        return restaurant_description;
    }

    public void setRestaurant_description(String restaurant_description) {
        this.restaurant_description = restaurant_description;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public double getSum_score() {
        return sum_score;
    }

    public void setSum_score(double sum_score) {
        this.sum_score = sum_score;
    }

    public int getAllcount() {
        return allcount;
    }

    public void setAllcount(int allcount) {
        this.allcount = allcount;
    }
}
