package com.mycoaching.mycoaching.Models.Retrofit;


/**
 * Created by kevin on 28/04/2018.
 * Version 1.0
 */

public class Event {

    private String id, name, type, status, start, end, created, updated;
    private UserRetrofit firstUser, secondUser, createdBy, updatedBy;

    public Event(String id, String name, String status, String start, String end, String created, String updated,
                 UserRetrofit firstUser, UserRetrofit secondUser, UserRetrofit createdBy, UserRetrofit updatedBy) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.end = end;
        this.created = created;
        this.updated = updated;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public UserRetrofit getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(UserRetrofit firstUser) {
        this.firstUser = firstUser;
    }

    public UserRetrofit getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(UserRetrofit secondUser) {
        this.secondUser = secondUser;
    }

    public UserRetrofit getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserRetrofit createdBy) {
        this.createdBy = createdBy;
    }

    public UserRetrofit getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserRetrofit updatedBy) {
        this.updatedBy = updatedBy;
    }
}
