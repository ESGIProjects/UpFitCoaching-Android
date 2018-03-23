package com.mycoaching.mycoaching;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tensa on 23/03/2018.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

}
