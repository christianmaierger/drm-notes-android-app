package com.example.drmapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class AlarmWorker extends Worker {

    public AlarmWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {


        long time1 =  SharedPreferencesHelper.getNotification(getApplicationContext(),1);
        if(time1 != 0)
        {
            settingAlarm(1,time1);
        }
        long time2 =  SharedPreferencesHelper.getNotification(getApplicationContext(),2);
        if(time2 != 0)
        {
            settingAlarm(2,time2);
        }
        long time3 =  SharedPreferencesHelper.getNotification(getApplicationContext(),3);
        if(time3 != 0)
        {
            settingAlarm(3,time3);
        }
        return Result.success();
    }

    private void settingAlarm(int timeButtonNumber, long time) {
        Intent intent = new Intent(getApplicationContext(), ReceiverForNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("ButtonNumber", timeButtonNumber);
        intent.putExtra("time", time);

        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), timeButtonNumber,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmMgr1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(time,
                    pendingIntent1);
            alarmMgr1.setAlarmClock(ac, pendingIntent1);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr1.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent1);
        } else {
            alarmMgr1.set(AlarmManager.RTC_WAKEUP, time, pendingIntent1);
        }

       // Denke eher die Alte Zeit speichern, denn die neue wird ja vom Receiver addiert und gespeichert
        // und wenn das Telefon zwischendurch ausgeht, muss es ja den alten Alarm wieder ausführen
       // time += TimeUnit.DAYS.toMillis(1);

        SharedPreferencesHelper.saveNotification(getApplicationContext(),timeButtonNumber, time);
    }
}
