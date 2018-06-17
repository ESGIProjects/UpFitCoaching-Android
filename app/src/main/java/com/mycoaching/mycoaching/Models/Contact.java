package com.mycoaching.mycoaching.Models;

import io.realm.RealmObject;

/**
 * Created by kevin on 20/05/2018.
 */
public class Contact extends RealmObject{

    private String firstName, lastName, lastMessage, id;

    public Contact(String firstName, String lastName, String lastMessage, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastMessage = lastMessage;
        this.id = id;
    }

    public Contact(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
