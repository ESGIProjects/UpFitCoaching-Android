package com.mycoaching.mycoaching.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ListChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ChatFragment;

/**
 * Created by kevin on 28/05/2018.
 * Version 1.0
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage rm) {

        // we define a default channel for android version >= 0
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

        // notification system is different since Android O, so we have to check the android version
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(getColor(R.color.colorPrimary));
            assert notificationManager != null;
            notificationBuilder.setChannelId(channelId);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // notifications are not displayed when ChatFragment or ListChatFragment are visible
        if((!ChatFragment.isActive && ListChatFragment.isActive == null) ||(!ChatFragment.isActive && !ListChatFragment.isActive)){
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
