package com.mycoaching.mycoaching.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;

/**
 * Created by kevin on 06/05/2018.
 */
public class Message {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("sender")
    @Expose
    private UserRetrofit sender;

    @SerializedName("receiver")
    @Expose
    private UserRetrofit receiver;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("content")
    @Expose
    private String content;

    public Message(Integer id, UserRetrofit sender, UserRetrofit receiver, String date, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRetrofit getSender() {
        return sender;
    }

    public void setSender(UserRetrofit sender) {
        this.sender = sender;
    }

    public UserRetrofit getReceiver() {
        return receiver;
    }

    public void setReceiver(UserRetrofit receiver) {
        this.receiver = receiver;
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
