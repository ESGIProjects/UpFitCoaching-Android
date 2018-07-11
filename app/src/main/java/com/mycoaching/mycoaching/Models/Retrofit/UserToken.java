package com.mycoaching.mycoaching.Models.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevin on 11/07/2018.
 */
public class UserToken {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private UserRetrofit ur;

    public UserToken(String token, UserRetrofit ur) {
        this.token = token;
        this.ur = ur;
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
}
