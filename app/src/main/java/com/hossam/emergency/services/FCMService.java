package com.hossam.emergency.services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hossam.emergency.R;
import com.hossam.emergency.algorithem.UniqueIntgerID;
import com.hossam.emergency.ui.main.MainActivity;

import java.util.concurrent.ExecutionException;


public class FCMService extends FirebaseMessagingService {

    private NotificationManager notifManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getData().get("title");
        String name = remoteMessage.getData().get("body");
        String image = remoteMessage.getData().get("imgUrl");

        createNotification(title, name, image);

    }

    private Bitmap getProfilePhotoAsBitmap(String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(this).asBitmap().load(url).into(168, 168).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //
    public void createNotification(String title_message, String messageBody, String imgUrl) {

        final int NOTIFY_ID = UniqueIntgerID.generateUniqueId(); // ID of notification

        String id = "twar2_channel"; // default_channel_id
        String title = "twar2_channel_title"; // Default Channel

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);

            if (mChannel == null) {

                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setLightColor(Color.RED);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);

            }

            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))  // required
                    .setSmallIcon(R.mipmap.twar2_fire_logo) // required
                    .setContentText(messageBody)  // required
                    .setContentTitle(title_message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setLargeIcon(getProfilePhotoAsBitmap(imgUrl))
                    .setContentIntent(pendingIntent)
                    .setLights(Color.RED, 1000, 1000)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});


        } else {
            builder = new NotificationCompat.Builder(this);
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            builder.setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))                           // required
                    .setSmallIcon(R.mipmap.twar2_fire_logo) // required
                    .setContentText(messageBody)  // required
                    .setContentTitle(title_message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setLargeIcon(getProfilePhotoAsBitmap(imgUrl))
                    .setContentIntent(pendingIntent)
                    .setLights(Color.RED, 1000, 1000)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }


}

//
////    private void sendNotification(String title, String messageBody, String imgUrl) {
////
////        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////
////        Intent intent = new Intent(this, FCMService.class);
////
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* FRequest code */, intent,
////                PendingIntent.FLAG_ONE_SHOT);
////
////        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
////                .setSmallIcon(R.mipmap.twar2_fire_logo)
////                .setLargeIcon(getProfilePhotoAsBitmap(imgUrl))
////                .setContentTitle(title)
////                .setContentText(messageBody)
////                .setAutoCancel(true)
////                .setSound(defaultSoundUri).setVibrate(new long[]{1000})
////                .setLights(Color.RED, 1000, 2000)
////                .setContentIntent(pendingIntent);
////
////        Intent resultIntent = new Intent(this, MainActivity.class);
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////        stackBuilder.addParentStack(MainActivity.class);
////        stackBuilder.addNextIntent(resultIntent);
////
////        PendingIntent resultPendingIntent =
////                stackBuilder.getPendingIntent(
////                        0,
////                        PendingIntent.FLAG_UPDATE_CURRENT
////                );
////        notificationBuilder.setContentIntent(resultPendingIntent);
////        NotificationManager mNotificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        mNotificationManager.notify(UniqueIntgerID.generateUniqueId(), notificationBuilder.build());
////
////    }
//