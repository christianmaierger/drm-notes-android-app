package com.example.drmapp.notificationBackend;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.drmapp.model.StoreSimpleDataHelper;

public class AlarmWorker extends Worker {

    public AlarmWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    // Diese Methode wird vom WorkManager in einem BackgroundThread ausgeführt, sobald ein WorkRequest
    // vom Typ dieser Klasse an ihn übergeben wird, bzw. wird das request enqued und sobald möglich ausgeführt
    @Override
    public Result doWork() {

        // Mithilfe der DataHelper Klasse werden gespeicherte Notification Times ausgelesen und falls
        // nötig erneut gesetzt, dies stellt sicher, dass Notifications auch nach Reboots wieder
        // gesetzt werden
        long time1 =  StoreSimpleDataHelper.getNotification(getApplicationContext(),1);
        if(time1 != 0)
        {
            settingAlarm(1,time1);
        }
        long time2 =  StoreSimpleDataHelper.getNotification(getApplicationContext(),2);
        if(time2 != 0)
        {
            settingAlarm(2,time2);
        }
        long time3 =  StoreSimpleDataHelper.getNotification(getApplicationContext(),3);
        if(time3 != 0)
        {
            settingAlarm(3,time3);
        }
        return Result.success();
    }


    /**
     *
     * @param timeButtonNumber einer der drei TimeButtons, die zur besseren Zuordnung nummeriert sind
     * @param time eine eingestellte NotificationTime
     */
    private void settingAlarm(int timeButtonNumber, long time) {
        Intent intent = new Intent(getApplicationContext(), ReceiverForNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // die Intents erhalten die Zeit für die Notification und die Zeit, damit zur NotificationZeit,
        // wenn der ReceiverForNotifications getriggert wird, dieser die passende AlarmZeit um 24 h
        // inkrementieren und den Alarm dann mit dieser neuen Zeit wieder einstellen kann
        intent.putExtra("ButtonNumber", timeButtonNumber);
        intent.putExtra("time", time);

        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), timeButtonNumber,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmMgr1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        // je nach AndroidVersion wird der Alarm passend gestellt
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(time,
                    pendingIntent1);
            alarmMgr1.setAlarmClock(ac, pendingIntent1);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr1.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent1);
        } else {
            alarmMgr1.set(AlarmManager.RTC_WAKEUP, time, pendingIntent1);
        }
        StoreSimpleDataHelper.saveNotification(getApplicationContext(),timeButtonNumber, time);
    }
}
