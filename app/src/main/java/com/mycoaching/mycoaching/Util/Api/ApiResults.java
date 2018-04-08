package com.mycoaching.mycoaching.Util.Api;

import java.util.List;

/**
 * Created by tensa on 08/03/2018.
 */


public class ApiResults {

    List<?> data;
    int responseCode;
    Throwable exception;
    String body;

    public List<?> getData(){
        return data;
    }

    public void setData(List<?> data){
        this.data = data;
    }

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
}

