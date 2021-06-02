package com.example.drmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ReceiverForNotifications extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        // die ButtonNummern dienen als requestCodes für die PendingIntents, so kann man immer die
        // zu einem Button gehörigen Alarme ändern/löschen
        int value = intent.getIntExtra("ButtonNumber", 0);
        long time = intent.getLongExtra("time", 0);

        // 86,400,000 milis entsprechen genau 24 h
            time = time +86400000;


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_plus_sign)
                .setContentTitle("Time to add Activities!")
                .setContentText("This Notification was triggered by Button " + value)
                // this is used for Android 7.1 and lower as there is no channel with own prio
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // when flag is set notification is automatically removed after tap
                .setAutoCancel(true);


        String tm = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
        System.out.println(tm);


        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        Intent intentNew = new Intent(context, ReceiverForNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // der request code dient als identifier für die intents, übergebe ich mit an getBroadcast
        intentNew.putExtra("ButtonNumber", value);
        // die neue Zeit wird an den nächsten Receiver übergen, so dass es immer 24 h mehr sind in
        // einer "Alarm Chain" - durch andere Wertke addieren zu time kann man es auch z.b. stündlich machen
        intentNew.putExtra("time", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, value, intentNew, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                System.out.println("Alarm gesetzt");
            }


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
