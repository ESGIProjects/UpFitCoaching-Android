package com.mycoaching.mycoaching.Models.Retrofit;

/**
 * Created by kevin on 16/05/2018.
 * Version 1.0
 */

public class Thread {

    private int id;

    private String title, lastUpdated;

    private Forum forum;

    private UserRetrofit lastUser;

    public Thread(int id, String title, Forum forum) {
        this.id = id;
        this.title = title;
        this.forum = forum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public UserRetrofit getLastUser() {
        return lastUser;
    }

    public void setLastUser(UserRetrofit lastUser) {
        this.lastUser = lastUser;
    }
}
