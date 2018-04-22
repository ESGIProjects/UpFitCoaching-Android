package com.mycoaching.mycoaching.Util;

public class Appointment {

    private String date, address;

    public Appointment(String date, String address){
        this.date = date;
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
