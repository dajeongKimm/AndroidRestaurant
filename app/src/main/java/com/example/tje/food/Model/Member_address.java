package com.example.tje.food.Model;

import java.io.Serializable;

public class Member_address implements Serializable {

    private int address_id;

    private String address_detail;
    private String address_postcode;
    private String address_general;


    public  Member_address() { }



    public Member_address(int member_address_id, String address_postcode,
                          String address_detail, String address_general) {

        this.address_id = member_address_id;
        this.address_general = address_general;

        this.address_detail = address_detail;
        this.address_postcode = address_postcode;
    }
    public int getMember_address_id() {
        return address_id;
    }
    public void setMember_address_id(int member_address_id) {
        this.address_id = member_address_id;
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
    public String getAddress_general() {
        return address_general;
    }

    public void setAddress_general(String address_general) {
        this.address_general = address_general;
    }



}
