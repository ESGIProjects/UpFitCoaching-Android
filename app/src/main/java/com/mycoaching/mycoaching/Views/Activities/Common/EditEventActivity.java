package com.mycoaching.mycoaching.Views.Activities.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mycoaching.mycoaching.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 17/06/2018.
 */
public class EditEventActivity extends AppCompatActivity {

    @OnClick(R.id.location)
    void openMaps(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q= 14 allée de Normandie, Ermont");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Log.i("ID : ", getIntent().getStringExtra("eventID"));
        ButterKnife.bind(this);
    }
}
