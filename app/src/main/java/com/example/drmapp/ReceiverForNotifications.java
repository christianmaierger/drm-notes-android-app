package com.example.drmapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReceiverForNotifications extends BroadcastReceiver {


// debugger shows this is not called :(
    @Override
    public void onReceive(Context context, Intent intent) {

        int value = intent.getIntExtra("ButtonNumber", 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_input_add)
                .setContentTitle("Time to add Activities!")
                .setContentText("This Notification was triggered by Button " + value)
                // this is used for Android 7.1 and lower as there is no channel with own prio
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // when flag is set notification is automatically removed after tap
                .setAutoCancel(true);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
