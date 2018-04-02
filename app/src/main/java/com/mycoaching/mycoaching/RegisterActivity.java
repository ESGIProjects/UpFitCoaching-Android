package com.mycoaching.mycoaching;

import android.content.Intent;
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

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_left,R.animator.slide_to_right);
    }

}
