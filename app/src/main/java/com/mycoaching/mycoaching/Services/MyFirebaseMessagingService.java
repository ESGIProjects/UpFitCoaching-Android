package com.mycoaching.mycoaching.Services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.Common.LoginActivity;

/**
 * Created by kevin on 28/05/2018.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage rm) {

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentText(rm.getNotification().getBody())
                        .setContentTitle(rm.getNotification().getTitle())
                        .setAutoCancel(true)
                        .setColor(getResources().getColor(R.color.colorPrimary));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }
}
