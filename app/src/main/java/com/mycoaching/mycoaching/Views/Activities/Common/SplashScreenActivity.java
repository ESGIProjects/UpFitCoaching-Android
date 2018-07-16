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
 * Created by tensa on 06/03/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {

    final int TIMER = 1500;
    Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        UserRealm ur = realm.where(UserRealm.class).findFirst();
        if(isNetworkAvailable(getApplicationContext())) {
            if (ur != null) {
                if(isTokenExpired(ur.getToken())){
                    refreshToken(ur.getToken(),getApplicationContext());
                }
                if (ur.getType() == 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreenActivity.this, CoachMainActivity.class);
                            performTransition(SplashScreenActivity.this, i, R.animator.slide_from_right, R.animator.slide_to_left);
                        }
                    }, TIMER);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreenActivity.this, UserMainActivity.class);
                            performTransition(SplashScreenActivity.this, i, R.animator.slide_from_right, R.animator.slide_to_left);
                        }
                    }, TIMER);
                }
            }
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
        else{
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            // we start the transition to LoginActivity when the time is up
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