package com.mycoaching.mycoaching.Models;

/**
 * Created by kevin on 16/05/2018.
 */
public class Session {

    String title,time,series,rep,status;
    int icon;

    public Session(String title, String time, String series, String rep, String status, int icon){
        this.title = title;
        this.time = time;
        this.series = series;
        this.rep = rep;
        this.status = status;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
