package com.example.drmapp.notificationBackend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.model.StoreSimpleDataHelper;

import java.util.concurrent.TimeUnit;

/**
 * Diese Klasse ist nötig um eine Kette von Alarmen zu erzeugen, da Android keine Methoden besitzt um zuverlässig
 * wiederkehrende Alarme einzustellen, die in jedem Fall das Gerät "aufwecken" würden. So ist es möglich,
 * das Gerät zu einer exakten ZEit zu wecken, diesen Receiver zu triggern und ihn wieder einen Alarm für in 24
 * h setzen zu lassen  und eine Notification an den User zu senden. Diese Kette wird solange durchgeführt,
 * bis der User die NotificationTime löscht/ändert.
 */
public class ReceiverForNotifications extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        // die ButtonNummebers dienen als requestCodes für die PendingIntents, so kann man immer die
        // zu einem Button gehörigen Alarme ändern/löschen
        int buttonNumber = intent.getIntExtra("ButtonNumber", 0);
        // Die eingestellte Zeit für eine Notifikation
        long time = intent.getLongExtra("time", 0);

        // Hier wird eine Notification erstellt, da das Device jetzt auf jeden Fall "aufgeweckt" sein muss
        // bzw. den Doze Mode verlassen haben muss und damit kann die Notification direkt an den
        // Nutzer gesendet werden.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_plus_sign)
                .setContentTitle("Time to add Activities!")
                .setContentText("Tap this Notification to get to the App")
                // this is used for Android 7.1 and lower as there is no channel with own prio
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //Vibration
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                //LED, if availible this should make led light
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


        // neuen Intent für den neuen Alarm anlegen und neue Zeit mitgeben, so dass der Receiver
        // die Zeit des aktuellen Alarms erhält und wieder darauf die gewünschte Zeit addieren kann
        // der request code dient als identifier für die intents, übergebe ich mit als Extra
        // die neue Zeit wird an den nächsten Receiver übergen, so dass es immer 24 h mehr sind in
        // einer "Alarm Chain" - durch andere Werte addieren zu time kann man es auch z.b. stündlich machen
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        time += TimeUnit.DAYS.toMillis(1);
        Intent nextIntent = new Intent(context, ReceiverForNotifications.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // der request code ist Identifier für die intents, übergebe ich mit an getBroadcast
        // auch die neue Zeit in 24 h wird übergeben, sodass der Reciever diese Infos mit empfängt
        nextIntent.putExtra("ButtonNumber", buttonNumber);
        nextIntent.putExtra("time", time);
        // wichtig ist das Flag, damit der PendingIntent des Alarms geupdatet wird und nicht immer neu erzeugt wird
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, buttonNumber,
                nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // der neue Alarm, der zu der gewünschten Notification Zeit in genau 24 h ausgelöst wird
        // eine Notification triggert und wieder einen Alarm, der wieder 24 h später stattfindet
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(time,
                    nextPendingIntent);
            alarmMgr.setAlarmClock(ac, nextPendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time, nextPendingIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, time, nextPendingIntent);
        }

        // hier die gesetzten Alarme persistent zwischenspeichern, damit man nach reboot diese wieder setzen kann
        StoreSimpleDataHelper.saveNotification(context,buttonNumber,time);

    }
}
