package com.mycoaching.mycoaching.Models.Retrofit;

/**
 * Created by kevin on 29/05/2018.
 * Version 1.0
 */
public class Post {

    private int id;
    private Thread thread;
    private UserRetrofit user;
    private String date, content;

    public Post(int id, Thread thread, UserRetrofit user, String date, String content) {
        this.id = id;
        this.thread = thread;
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
