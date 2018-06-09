package com.mycoaching.mycoaching.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.realm.Realm;

import static android.content.ContentValues.TAG;

/**
 * Created by kevin on 28/05/2018.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    Realm r;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }

}
