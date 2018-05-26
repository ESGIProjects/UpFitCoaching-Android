package com.mycoaching.mycoaching.Models;

/**
 * Created by kevin on 20/05/2018.
 */
public class Contact {

    private String name, lastMessage, id;

    public Contact(String name, String lastMessage, String id){
        this.name = name;
        this.lastMessage = lastMessage;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
