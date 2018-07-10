package com.mycoaching.mycoaching.Models.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kevin on 08/07/2018.
 */
public class Prescription {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("user")
    @Expose
    private UserRetrofit user;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("exercises")
    @Expose
    private List<Exercise> listExercises;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRetrofit getUser() {
        return user;
    }

    public void setUser(UserRetrofit user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Exercise> getExercises() {
        return listExercises;
    }

    public void setExercises(List<Exercise> listExercises) {
        this.listExercises = listExercises;
    }

}

