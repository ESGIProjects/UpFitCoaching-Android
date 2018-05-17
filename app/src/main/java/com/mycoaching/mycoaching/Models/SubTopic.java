package com.mycoaching.mycoaching.Models;

/**
 * Created by kevin on 16/05/2018.
 */
public class SubTopic {

    private String title, subtitle;

    public SubTopic(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
