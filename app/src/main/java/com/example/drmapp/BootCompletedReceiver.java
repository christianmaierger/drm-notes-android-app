package com.example.drmapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


public class BootCompletedReceiver extends BroadcastReceiver {

    // Im Manifest ist dieser Receiver registriert um beim Booten aufgerufen zu werden
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity activity = ((MainActivity) context.getApplicationContext());
        if (!activity.isAlarmsAllReset()) {
            // Work Request erstellen, dass dann async bearbeitet werden kann vom WorkManager als eigener Thread
            WorkRequest alarmWorkRequest =
                    new OneTimeWorkRequest.Builder(AlarmWorker.class)
                            .build();

            // Das WorkRequest wird zur Bearbeitung an den WorkManager übergeben
            WorkManager
                    .getInstance(context)
                    .enqueue(alarmWorkRequest);
            activity.setAlarmsAllReset(true);
        }
    }
}