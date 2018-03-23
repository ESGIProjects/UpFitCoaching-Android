package com.mycoaching.mycoaching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Util.CommonMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tensa on 01/03/2018.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.signin) void signIn(){
        Toast.makeText(getApplicationContext(),"Test signin",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,ChatActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.signup) void signUp(){
        Toast.makeText(getApplicationContext(),"Test signup",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,MessagingActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.forgot) void forgot(){
        if(CommonMethods.checkFields(email.toString(),password.toString()) && CommonMethods.isAvailable(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(),"Bad",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }
}
