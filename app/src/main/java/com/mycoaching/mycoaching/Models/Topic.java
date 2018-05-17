package com.mycoaching.mycoaching.Models;

/**
 * Created by kevin on 16/05/2018.
 */
public class Topic {

    private int icon;
    private String title;

    public Topic(int icon, String title){
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
