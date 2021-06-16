package com.example.drmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.TimeUnit;

public class ReceiverForNotifications extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        // die ButtonNummern dienen als requestCodes für die PendingIntents, so kann man immer die
        // zu einem Button gehörigen Alarme ändern/löschen
        int buttonNumber = intent.getIntExtra("ButtonNumber", 0);
        long time = intent.getLongExtra("time", 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_plus_sign)
                .setContentTitle("Time to add Activities!")
                .setContentText("This Notification was triggered by Button " + buttonNumber)
                // this is used for Android 7.1 and lower as there is no channel with own prio
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Vibration
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                //LED
                .setLights(Color.GREEN ,3000, 3000)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                // when flag is set notification is automatically removed after tap
                .setAutoCancel(true);

        // der PendingIntent für die Notification
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());


        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // neuen Intent für den neuen Alarm anlegen und neue Zeit mitgeben, so dass der Receiver
        // die Zeit des aktuellen Alarms erhält und wieder darauf die gewünschte Zeit addieren kann
        // der request code dient als identifier für die intents, übergebe ich mit als Extra
        // die neue Zeit wird an den nächsten Receiver übergen, so dass es immer 24 h mehr sind in
        // einer "Alarm Chain" - durch andere Werte addieren zu time kann man es auch z.b. stündlich machen
        time += TimeUnit.DAYS.toMillis(1);
        Intent nextIntent = new Intent(context, ReceiverForNotifications.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // der request code scheint identifier für die intents zu sein, übergebe ich mit an getBroadcast
        // auch die neue Zeit in 24 h wird übergeben
        nextIntent.putExtra("ButtonNumber", buttonNumber);
        nextIntent.putExtra("time", time);
        // wichtig ist das Flag, damit der PendingIntent des Alarms geupdatet wird und nicht immer neu erzeugt wird
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, buttonNumber,
                nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // todo verschiedene Methoden testen, zwar nicht empfohlen aber setAlarmClock scheint recht gut zu funktionieren
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(time,
                    nextPendingIntent);
            alarmMgr.setAlarmClock(ac, nextPendingIntent);
            // App kann durch die Benutzung von setExactAndAllow... ab Api 23 also Android 6.0 verwendet werden
          /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // der neue Alarm, der zu der geünschten Notification Zeit in genau 24 h ausgelöst wird
                // eine Notification triggert und wieder einen Alarm, der wieder 24 h später stattfindet
                alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }*/
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time, nextPendingIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, time, nextPendingIntent);
        }

        // hier die gesetzten Alarme zwischenspeichern, damit man nach reboot diese wieder setzen kann
        StoreSimpleDataHelper.saveNotification(context,buttonNumber,time);

    }
}
