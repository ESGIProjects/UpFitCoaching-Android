package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Event;
import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.Models.Post;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.Models.Thread;

import java.util.List;

/**
 * Created by kevin on 08/03/2018.
 */


public class ApiResults {

    String body;
    List<Message> listMessage;
    List<Thread> listThread;
    List<Post> listPost;
    List<Event> listEvent;
    int responseCode;
    Throwable exception;
    UserRetrofit ur;

    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    public List<Thread> getListThread() {
        return listThread;
    }

    public void setListThread(List<Thread> listThread) {
        this.listThread = listThread;
    }

    public List<Post> getListPost() {
        return listPost;
    }

    public void setListPost(List<Post> listPost) {
        this.listPost = listPost;
    }

    public List<Event> getListEvent() {
        return listEvent;
    }

    public void setListEvent(List<Event> listEvent) {
        this.listEvent = listEvent;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable e) {
        this.exception = e;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserRetrofit getUr() {
        return ur;
    }

    public void setUr(UserRetrofit ur) {
        this.ur = ur;
    }
}

