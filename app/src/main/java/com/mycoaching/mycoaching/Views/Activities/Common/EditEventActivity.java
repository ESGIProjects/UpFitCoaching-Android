package com.mycoaching.mycoaching.Views.Activities.Common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mycoaching.mycoaching.R;

/**
 * Created by kevin on 17/06/2018.
 */
public class EditEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main);
        Log.i("ID : ", getIntent().getStringExtra("eventID"));
    }
}
