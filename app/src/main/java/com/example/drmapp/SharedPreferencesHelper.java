package com.example.drmapp;

import android.content.Context;

public class SharedPreferencesHelper {

    private SharedPreferencesHelper() {
    }



    public static Long getNotification(Context context, Integer timeButtonNumber) {
        // Die timeButtonNumber fungiert hier als key zu dem jeweiligen Value einer Alarm Zeit im
        // SharedPreferencesFile
        return context.getSharedPreferences("PREF_NAME_NOTIFICATION", Context.MODE_PRIVATE)
                .getLong(timeButtonNumber.toString(), 0);
    }

    public static void saveNotification(Context context, Integer timeButtonNumber, Long time) {
        context.getSharedPreferences("PREF_NAME_NOTIFICATION", Context.MODE_PRIVATE)
                .edit().putLong(timeButtonNumber.toString(), time).apply();

    }



    public static String getNotificationTime1AsString(Context context, Integer timeButtonNumber) {
        // Die timeButtonNumber fungiert hier als key zu dem jeweiligen Value einer Alarm Zeit im
        // SharedPreferencesFile
        return context.getSharedPreferences("PREF_NAME_NOTIFICATIONASSTRING", Context.MODE_PRIVATE)
                .getString(timeButtonNumber.toString(), null);
    }

    public static void saveNotification1AsString(Context context, Integer timeButtonNumber, String time) {
        context.getSharedPreferences("PREF_NAME_NOTIFICATION", Context.MODE_PRIVATE)
                .edit().putString(timeButtonNumber.toString(), time).apply();

    }



}

