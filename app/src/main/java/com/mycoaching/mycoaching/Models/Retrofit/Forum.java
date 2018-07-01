package com.mycoaching.mycoaching.Models.Retrofit;

/**
 * Created by kevin on 16/05/2018.
 */
public class Forum {

    private int id;
    private String title;

    public Forum(int id, String title) {
        this.id = id;
        this.title = title;
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

}
