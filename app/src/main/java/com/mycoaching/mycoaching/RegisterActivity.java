package com.mycoaching.mycoaching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tensa on 17/03/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
    }

}
