package com.mycoaching.mycoaching.Models;

import java.util.Date;

/**
 * Created by kevin on 16/05/2018.
 */
public class Thread {

    private int id;
    private String title;
    private Forum forum;


    public Thread(int id, String title, Forum forum){
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
}
