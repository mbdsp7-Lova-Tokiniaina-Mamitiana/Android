package com.example.etu000603_android.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import com.example.etu000603_android.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class FirebaseService extends FirebaseMessagingService {
    public FirebaseService() {
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().isEmpty()){
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }else{
            showNotification(remoteMessage.getData());
        }
    }

    private void showNotification(Map<String,String> data){
        String title = data.get("title").toString();
        String body = data.get("body").toString();

        String NOTIFICATION_CHANNEL_ID = "com.example.connectionfirebase.services";

        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Code Sphere");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        assert notificationManager != null;
        notificationManager.notify(0,notificationBuilder.build());

    }

    private void showNotification(String title,String body){
        String NOTIFICATION_CHANNEL_ID = "com.example.connectionfirebase.services";
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Code Sphere");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);

            notificationManager.createNotificationChannel(notificationChannel);
        }*/
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.logo)

                .setContentTitle(title)
                .setContentText(body);

        notificationManager.notify(0,notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
      //  System.out.println("token:"+s);

    }
}
