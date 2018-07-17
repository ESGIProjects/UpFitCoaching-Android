package com.mycoaching.mycoaching.Models.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevin on 11/07/2018.
 * Version 1.0
 */

public class UserToken {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private UserRetrofit ur;

    @SerializedName("coach")
    @Expose
    private UserRetrofit urc;

    @SerializedName("id")
    @Expose
    private String id;

    public UserToken(String token, UserRetrofit ur, UserRetrofit urc, String id) {
        this.token = token;
        this.ur = ur;
        this.urc = urc;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRetrofit getUr() {
        return ur;
    }

    public void setUr(UserRetrofit ur) {
        this.ur = ur;
    }

    public UserRetrofit getUrc() {
        return urc;
    }

    public void setUrc(UserRetrofit urc) {
        this.urc = urc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
