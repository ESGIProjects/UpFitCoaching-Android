package com.mycoaching.mycoaching.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by kevin on 28/05/2018.
 * Version 1.0
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    /**
     * This method is called automatically when the firebase token is refreshed
     */

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
