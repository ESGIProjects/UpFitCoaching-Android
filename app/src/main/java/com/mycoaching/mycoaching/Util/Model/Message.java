package com.mycoaching.mycoaching.Util.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevin on 06/05/2018.
 */
public class Message {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("content")
    @Expose
    private String content;
}
