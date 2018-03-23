package com.mycoaching.mycoaching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tensa on 01/03/2018.
 */

public class ChatActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
    }

}
