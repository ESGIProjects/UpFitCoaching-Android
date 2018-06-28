package com.mycoaching.mycoaching.Models.Retrofit;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by kevin on 28/06/2018.
 */
public class Appraisal {

    private String id, date, goal, sessionsByWeek, contraindication, sportAntecedents, helpNeeded,
        hasNutritionist, comments;
    private UserRetrofit user;

    public Appraisal(String id, String date, String goal, String sessionsByWeek, String contraindication,
                     String sportAntecedents, String helpNeeded, String hasNutritionist, String comments,
                     UserRetrofit user){
        this.id = id;
        this.date = date;
        this.goal = goal;
        this.sessionsByWeek = sessionsByWeek;
        this.contraindication = contraindication;
        this.sportAntecedents = sportAntecedents;
        this.helpNeeded = helpNeeded;
        this.hasNutritionist = hasNutritionist;
        this.comments = comments;
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getSessionsByWeek() {
        return sessionsByWeek;
    }

    public void setSessionsByWeek(String sessionsByWeek) {
        this.sessionsByWeek = sessionsByWeek;
    }

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    public String getSportAntecedents() {
        return sportAntecedents;
    }

    public void setSportAntecedents(String sportAntecedents) {
        this.sportAntecedents = sportAntecedents;
    }

    public String getHelpNeeded() {
        return helpNeeded;
    }

    public void setHelpNeeded(String helpNeeded) {
        this.helpNeeded = helpNeeded;
    }

    public String getHasNutritionist() {
        return hasNutritionist;
    }

    public void setHasNutritionist(String hasNutritionist) {
        this.hasNutritionist = hasNutritionist;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public UserRetrofit getUser() {
        return user;
    }

    public void setUser(UserRetrofit user) {
        this.user = user;
    }
}
