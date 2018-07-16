package com.mycoaching.mycoaching.Models.Retrofit;

/**
 * Created by kevin on 03/07/2018.
 * Version 1.0
 */
public class Measurement {

    private String id, date, weight, height, hipCircumference, waistCircumference, thighCircumference,
            armCircumference;
    private UserRetrofit user;

    public Measurement(String id, String date, String weight, String height, String hipCircumference, String waistCircumference, String thighCircumference, String armCircumference, UserRetrofit user) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.hipCircumference = hipCircumference;
        this.waistCircumference = waistCircumference;
        this.thighCircumference = thighCircumference;
        this.armCircumference = armCircumference;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHipCircumference() {
        return hipCircumference;
    }

    public void setHipCircumference(String hipCircumference) {
        this.hipCircumference = hipCircumference;
    }

    public String getWaistCircumference() {
        return waistCircumference;
    }

    public void setWaistCircumference(String waistCircumference) {
        this.waistCircumference = waistCircumference;
    }

    public String getThighCircumference() {
        return thighCircumference;
    }

    public void setThighCircumference(String thighCircumference) {
        this.thighCircumference = thighCircumference;
    }

    public String getArmCircumference() {
        return armCircumference;
    }

    public void setArmCircumference(String armCircumference) {
        this.armCircumference = armCircumference;
    }

    public UserRetrofit getUser() {
        return user;
    }

    public void setUser(UserRetrofit user) {
        this.user = user;
    }
}
