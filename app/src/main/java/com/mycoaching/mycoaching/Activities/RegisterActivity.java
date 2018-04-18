package com.mycoaching.mycoaching.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mycoaching.mycoaching.R;

public class RegisterActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
    }

}
