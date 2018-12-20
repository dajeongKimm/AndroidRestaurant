package com.example.tje.food.Model;

public class SimpleReviewScore {

    private int simple_review_score_id;
    private int restaurant_id;
    private String member_id;
    private int score_flavor;
    private int score_volume;
    private int score_service;
    private int total_score;

    public SimpleReviewScore() {}


    public int getSimple_review_score_id() {
        return simple_review_score_id;
    }


    public void setSimple_review_score_id(int simple_review_score_id) {
        this.simple_review_score_id = simple_review_score_id;
    }


    public int getRestaurant_id() {
        return restaurant_id;
    }


    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }


    public String getMember_id() {
        return member_id;
    }


    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }


    public int getScore_flavor() {
        return score_flavor;
    }


    public void setScore_flavor(int score_flavor) {
        this.score_flavor = score_flavor;
    }


    public int getScore_volume() {
        return score_volume;
    }


    public void setScore_volume(int score_volume) {
        this.score_volume = score_volume;
    }


    public int getScore_service() {
        return score_service;
    }


    public void setScore_service(int score_service) {
        this.score_service = score_service;
    }


    public int getTotal_score() {
        return total_score;
    }


    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

}
