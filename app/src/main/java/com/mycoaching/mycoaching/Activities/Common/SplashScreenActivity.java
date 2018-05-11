package com.mycoaching.mycoaching.Activities.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mycoaching.mycoaching.R;

import io.realm.Realm;

/**
 * Created by tensa on 06/03/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {

    final int TIMER = 1500;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();
        Realm.init(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        }, TIMER);
    }
}