package com.mycoaching.mycoaching.Util.Api;

import com.mycoaching.mycoaching.Util.Model.Retrofit.UserRetrofit;

import java.util.List;

/**
 * Created by tensa on 08/03/2018.
 */


public class ApiResults {

    String body;
    int responseCode;
    Throwable exception;
    UserRetrofit ur;

    public int getResponseCode(){
        return responseCode;
    }

    public void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }

    public Throwable getException(){
        return exception;
    }

    public void setException(Throwable e){
        this.exception = e;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public UserRetrofit getUr() {
        return ur;
    }

    public void setUr(UserRetrofit ur){
        this.ur = ur;
    }
}

