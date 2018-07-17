package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Realm.Message;
import com.mycoaching.mycoaching.Models.Retrofit.Appraisal;
import com.mycoaching.mycoaching.Models.Retrofit.Event;
import com.mycoaching.mycoaching.Models.Retrofit.Measurement;
import com.mycoaching.mycoaching.Models.Retrofit.Post;
import com.mycoaching.mycoaching.Models.Retrofit.Prescription;
import com.mycoaching.mycoaching.Models.Retrofit.Test;
import com.mycoaching.mycoaching.Models.Retrofit.Thread;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.Models.Retrofit.UserToken;

import java.util.List;

/**
 * Created by kevin on 08/03/2018.
 * Version 1.0
 */

public class ApiResults {

    private List<Message> listMessage;
    private List<Thread> listThread;
    private List<Post> listPost;
    private List<Event> listEvent;
    private List<Measurement> listMeasurement;
    private List<Prescription> listPrescription;
    private Appraisal lastAppraisal;
    private List<Test> listTest;
    private int responseCode;
    private String errorMessage;
    private UserRetrofit ur;
    private UserToken ut;
    private Throwable exception;

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

    public UserRetrofit getUr() {
        return ur;
    }

    public void setUr(UserRetrofit ur) {
        this.ur = ur;
    }

    public Appraisal getLastAppraisal() {
        return lastAppraisal;
    }

    public void setLastAppraisal(Appraisal lastAppraisal) {
        this.lastAppraisal = lastAppraisal;
    }

    public List<Test> getListTest() {
        return listTest;
    }

    public void setListTest(List<Test> listTest) {
        this.listTest = listTest;
    }

    public List<Measurement> getListMeasurement() {
        return listMeasurement;
    }

    public void setListMeasurement(List<Measurement> listMeasurement) {
        this.listMeasurement = listMeasurement;
    }

    public List<Prescription> getListPrescription() {
        return listPrescription;
    }

    public void setListPrescription(List<Prescription> listPrescription) {
        this.listPrescription = listPrescription;
    }

    public UserToken getUt() {
        return ut;
    }

    public void setUt(UserToken ut) {
        this.ut = ut;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

