package com.mycoaching.mycoaching.Activities.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mycoaching.mycoaching.Activities.LoginActivity;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tensa on 17/03/2018.
 */

public class UserRegisterActivity extends AppCompatActivity {

    @BindView(R.id.type_label)
    TextView type_label;

    @BindView(R.id.user_type)
    Spinner user_type;

    @BindView(R.id.mail)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.birthDate)
    EditText birthDate;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_left,R.animator.slide_to_right);
    }

}
