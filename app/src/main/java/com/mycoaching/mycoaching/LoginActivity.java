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
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @OnClick(R.id.signup) void signUp(){
        Toast.makeText(getApplicationContext(),"Test signup",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,MessagingActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @OnClick(R.id.forgot) void forgot(){
        Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }
}
