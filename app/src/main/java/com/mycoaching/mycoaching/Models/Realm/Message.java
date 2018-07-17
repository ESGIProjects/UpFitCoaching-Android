package com.mycoaching.mycoaching.Models.Realm;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;

import io.realm.RealmObject;

/**
 * Created by kevin on 06/05/2018.
 * Version 1.0
 */

/**
 * This model implements the Parcelable interface in order to be put in a Bundle object
 */

public class Message extends RealmObject implements Parcelable {

    private Integer id;

    private UserRetrofit sender, receiver;

    private String date, content;

    public Message(Integer id, UserRetrofit sender, UserRetrofit receiver, String date, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.content = content;
    }

    public Message(Parcel in) {
        this.id = in.readInt();
        this.sender = in.readParcelable(UserRetrofit.class.getClassLoader());
        this.receiver = in.readParcelable(UserRetrofit.class.getClassLoader());
        this.date = in.readString();
        this.content = in.readString();
    }

    public Message(){

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

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
