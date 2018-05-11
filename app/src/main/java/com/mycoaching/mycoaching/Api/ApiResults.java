package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;

import java.util.List;

/**
 * Created by kevin on 08/03/2018.
 */


public class ApiResults {

    String body;
    List<Message> listMessage;
    int responseCode;
    Throwable exception;
    UserRetrofit ur;

    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
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

    public UserRetrofit getUr() {
        return ur;
    }

    public void setUr(UserRetrofit ur){
        this.ur = ur;
    }
}

