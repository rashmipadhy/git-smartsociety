/*
package com.kfxlabs.smartsociety.api;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kfxlabs.smartsociety.Activity.DgAlertsActivity;
import com.kfxlabs.smartsociety.R;

import org.jetbrains.annotations.NotNull;

public class SoietyAppServices extends FirebaseMessagingService {

    String title;
    String body;
    String id;
    String CHANNEL_ID;

    public SoietyAppServices() {

    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            id = "Message";
            CharSequence name= "Message";
            String description = "Message";

            NotificationChannel notificationChannel;
            Uri sound = Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.eventually);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(id,name, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription(description);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();
                notificationChannel.setSound(sound,audioAttributes);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
                notificationManager.createNotificationChannel(notificationChannel);
            }


            title = remoteMessage.getNotification().getTitle();
            body=remoteMessage.getNotification().getBody();
            CHANNEL_ID="Message";
            Intent intent = new Intent(getBaseContext(), DgAlertsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),1410,intent,PendingIntent.FLAG_UPDATE_CURRENT);

//            Uri mysound = Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.notify);
            Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentText(body)
                    .setContentIntent(pendingIntent)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            if(notificationManager!=null){
                notificationManager.notify(1002,notification);
            }
//
////            sendNotifications(title,body);
        }

    }

    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
        Log.e("New_Token",s);
    }
}
*/
