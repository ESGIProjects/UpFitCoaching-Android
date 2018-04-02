package com.mycoaching.mycoaching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.CoachActivity.CoachMainActivity;
import com.mycoaching.mycoaching.UserActivity.UserMainActivity;
import com.mycoaching.mycoaching.UserActivity.UserRegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tensa on 01/03/2018.
 */

public class LoginActivity extends AppCompatActivity {

    Intent i;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.coach)
    CheckBox coach;

    @OnClick(R.id.signin) void signIn(){
        if(coach.isChecked()){
            i = new Intent(this,CoachMainActivity.class);
        }
        else{
            i = new Intent(this,UserMainActivity.class);
        }
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @OnClick(R.id.signup) void signUp(){
        i = new Intent(this,MessagingActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @OnClick(R.id.forgot) void forgot(){
        i = new Intent(LoginActivity.this,UserRegisterActivity.class);
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
