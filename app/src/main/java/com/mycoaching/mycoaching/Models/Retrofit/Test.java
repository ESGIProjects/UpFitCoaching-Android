package com.mycoaching.mycoaching.Models.Retrofit;

/**
 * Created by kevin on 01/07/2018.
 */
public class Test {

    private String id, date, warmUp, startSpeed, increase, frequency, kneeFlexibility, shinFlexibility,
            hitFootFlexibility, closedFistGroundFlexibility, handFlatGroundFlexibility;
    private UserRetrofit user;

    public Test(String id, String date, String warmUp, String startSpeed, String increase, String frequency, String kneeFlexibility, String shinFlexibility, String hitFootFlexibility, String closedFistGroundFlexibility, String handFlatGroundFlexibility, UserRetrofit user) {
        this.id = id;
        this.date = date;
        this.warmUp = warmUp;
        this.startSpeed = startSpeed;
        this.increase = increase;
        this.frequency = frequency;
        this.kneeFlexibility = kneeFlexibility;
        this.shinFlexibility = shinFlexibility;
        this.hitFootFlexibility = hitFootFlexibility;
        this.closedFistGroundFlexibility = closedFistGroundFlexibility;
        this.handFlatGroundFlexibility = handFlatGroundFlexibility;
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

    public String getWarmUp() {
        return warmUp;
    }

    public void setWarmUp(String warmUp) {
        this.warmUp = warmUp;
    }

    public String getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(String startSpeed) {
        this.startSpeed = startSpeed;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getKneeFlexibility() {
        return kneeFlexibility;
    }

    public void setKneeFlexibility(String kneeFlexibility) {
        this.kneeFlexibility = kneeFlexibility;
    }

    public String getShinFlexibility() {
        return shinFlexibility;
    }

    public void setShinFlexibility(String shinFlexibility) {
        this.shinFlexibility = shinFlexibility;
    }

    public String getHitFootFlexibility() {
        return hitFootFlexibility;
    }

    public void setHitFootFlexibility(String hitFootFlexibility) {
        this.hitFootFlexibility = hitFootFlexibility;
    }

    public String getClosedFistGroundFlexibility() {
        return closedFistGroundFlexibility;
    }

    public void setClosedFistGroundFlexibility(String closedFistGroundFlexibility) {
        this.closedFistGroundFlexibility = closedFistGroundFlexibility;
    }

    public String getHandFlatGroundFlexibility() {
        return handFlatGroundFlexibility;
    }

    public void setHandFlatGroundFlexibility(String handFlatGroundFlexibility) {
        this.handFlatGroundFlexibility = handFlatGroundFlexibility;
    }

    public UserRetrofit getUser() {
        return user;
    }

    public void setUser(UserRetrofit user) {
        this.user = user;
    }
}
