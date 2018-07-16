package com.mycoaching.mycoaching.Views.Activities.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.CoachActivity.CoachMainActivity;
import com.mycoaching.mycoaching.Views.Activities.UserActivity.UserMainActivity;

import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;

/**
 * Created by kevin on 06/03/2018.
 * Version 1.0
 */

public class SplashScreenActivity extends AppCompatActivity {

    protected final int TIMER = 1500;
    protected Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

        // realm is init here in order to perform realm transaction all accross the application
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        // we check is there is already a user registered on the device
        UserRealm ur = realm.where(UserRealm.class).findFirst();
        if(isNetworkAvailable(getApplicationContext())) {
            if (ur != null) {
                if(isTokenExpired(ur.getToken())){
                    refreshToken(ur.getToken(),getApplicationContext());
                }
                // if the user saved in local database is a coach, CoachMainActivity is launched
                if (ur.getType() == 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreenActivity.this, CoachMainActivity.class);
                            performTransition(SplashScreenActivity.this, i, R.animator.slide_from_right, R.animator.slide_to_left);
                        }
                    }, TIMER);
                }
                // else, UserMainActivity is launched
                else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreenActivity.this, UserMainActivity.class);
                            performTransition(SplashScreenActivity.this, i, R.animator.slide_from_right, R.animator.slide_to_left);
                        }
                    }, TIMER);
                }
            }
            // if there is no user registered, LogicActivity is launched
            else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                        finish();
                    }
                }, TIMER);
            }
        }
        // we start LoginActivity and alert the user that there is no internet
        else{
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }, TIMER);
        }
    }
}