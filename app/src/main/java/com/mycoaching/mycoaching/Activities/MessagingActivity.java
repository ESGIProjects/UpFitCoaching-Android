package com.mycoaching.mycoaching.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tensa on 06/03/2018.
 */

public class MessagingActivity extends AppCompatActivity{

    @BindView(R.id.picture)
    ImageView picture;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.last_message)
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        ButterKnife.bind(this);
    }
}