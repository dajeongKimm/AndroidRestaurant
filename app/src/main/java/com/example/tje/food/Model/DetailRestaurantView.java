package com.example.tje.food.Model;

import java.util.Date;

public class DetailRestaurantView {

    private int restaurant_id;

    private String restaurant_name;
    private String restaurant_tel;
    private String address_city;
    private String address_gu;
    private String address_dong;
    private String address_detail;
    private String address_postcode;
    private String restaurant_businesstime;
    private String restaurant_breaktime;
    private Date restaurant_opendate;
    private String restaurant_description;
    private String restaurant_mainimage;
    private String menu_type;
    private boolean  discount_coupon;
    private boolean  discount_mobile;
    private boolean  discount_savemoney;
    private boolean  service_kidszon;
    private boolean  service_pet;
    private boolean  service_wipi;
    private boolean  service_reservation;
    private boolean  service_takeout;
    private boolean  service_parking;
    private boolean  service_toilet;
    private boolean  service_delivery;
    private int read_count;
    private double sum_score;
    private int allcount;

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_tel() {
        return restaurant_tel;
    }

    public void setRestaurant_tel(String restaurant_tel) {
        this.restaurant_tel = restaurant_tel;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_gu() {
        return address_gu;
    }

    public void setAddress_gu(String address_gu) {
        this.address_gu = address_gu;
    }

    public String getAddress_dong() {
        return address_dong;
    }

    public void setAddress_dong(String address_dong) {
        this.address_dong = address_dong;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public String getAddress_postcode() {
        return address_postcode;
    }

    public void setAddress_postcode(String address_postcode) {
        this.address_postcode = address_postcode;
    }

    public String getRestaurant_businesstime() {
        return restaurant_businesstime;
    }

    public void setRestaurant_businesstime(String restaurant_businesstime) {
        this.restaurant_businesstime = restaurant_businesstime;
    }

    public String getRestaurant_breaktime() {
        return restaurant_breaktime;
    }

    public void setRestaurant_breaktime(String restaurant_breaktime) {
        this.restaurant_breaktime = restaurant_breaktime;
    }

    public Date getRestaurant_opendate() {
        return restaurant_opendate;
    }

    public void setRestaurant_opendate(Date restaurant_opendate) {
        this.restaurant_opendate = restaurant_opendate;
    }

    public String getRestaurant_description() {
        return restaurant_description;
    }

    public void setRestaurant_description(String restaurant_description) {
        this.restaurant_description = restaurant_description;
    }

    public String getRestaurant_mainimage() {
        return restaurant_mainimage;
    }

    public void setRestaurant_mainimage(String restaurant_mainimage) {
        this.restaurant_mainimage = restaurant_mainimage;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public boolean isDiscount_coupon() {
        return discount_coupon;
    }

    public void setDiscount_coupon(boolean discount_coupon) {
        this.discount_coupon = discount_coupon;
    }

    public boolean isDiscount_mobile() {
        return discount_mobile;
    }

    public void setDiscount_mobile(boolean discount_mobile) {
        this.discount_mobile = discount_mobile;
    }

    public boolean isDiscount_savemoney() {
        return discount_savemoney;
    }

    public void setDiscount_savemoney(boolean discount_savemoney) {
        this.discount_savemoney = discount_savemoney;
    }

    public boolean isService_kidszon() {
        return service_kidszon;
    }

    public void setService_kidszon(boolean service_kidszon) {
        this.service_kidszon = service_kidszon;
    }

    public boolean isService_pet() {
        return service_pet;
    }

    public void setService_pet(boolean service_pet) {
        this.service_pet = service_pet;
    }

    public boolean isService_wipi() {
        return service_wipi;
    }

    public void setService_wipi(boolean service_wipi) {
        this.service_wipi = service_wipi;
    }

    public boolean isService_reservation() {
        return service_reservation;
    }

    public void setService_reservation(boolean service_reservation) {
        this.service_reservation = service_reservation;
    }

    public boolean isService_takeout() {
        return service_takeout;
    }

    public void setService_takeout(boolean service_takeout) {
        this.service_takeout = service_takeout;
    }

    public boolean isService_parking() {
        return service_parking;
    }

    public void setService_parking(boolean service_parking) {
        this.service_parking = service_parking;
    }

    public boolean isService_toilet() {
        return service_toilet;
    }

    public void setService_toilet(boolean service_toilet) {
        this.service_toilet = service_toilet;
    }

    public boolean isService_delivery() {
        return service_delivery;
    }

    public void setService_delivery(boolean service_delivery) {
        this.service_delivery = service_delivery;
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
