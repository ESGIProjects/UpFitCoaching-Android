package com.mycoaching.mycoaching.Models.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevin on 16/05/2018.
 */
public class Thread {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("forum")
    @Expose
    private Forum forum;

    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    @SerializedName("lastUser")
    @Expose
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
