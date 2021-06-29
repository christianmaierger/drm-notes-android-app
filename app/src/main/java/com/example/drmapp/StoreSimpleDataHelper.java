package com.example.drmapp;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class StoreSimpleDataHelper {

    private StoreSimpleDataHelper() {
    }


    /**
     *
     * @param context Der Application Context
     * @param timeButtonNumber zur besseren Zuordnung hat jeder TimeButton eine Nummer von 1-3
     * @return Die Zeit für eine Notification als Long
     */
    public static Long getNotification(Context context, Integer timeButtonNumber) {
        // Die timeButtonNumber fungiert hier als key zu dem jeweiligen Value einer Alarm Zeit im
        // SharedPreferencesFile, dieses eignet sich gut zur Speicherung nicht komplexer Objekte
        return context.getSharedPreferences("PREF_NAME_NOTIFICATION", Context.MODE_PRIVATE)
                .getLong(timeButtonNumber.toString(), 0);
    }

    public static void saveNotification(Context context, Integer timeButtonNumber, Long time) {
        context.getSharedPreferences("PREF_NAME_NOTIFICATION", Context.MODE_PRIVATE)
                .edit().putLong(timeButtonNumber.toString(), time).apply();

    }

    /**
     * Mit Hilfe von File-/ObjectInputStream wird die Liste mit NotificationTimes aus dem persistenten
     * Speicher des Smartphones wieder deserialisiert und in einer Liste gepeichert. Wenn keine
     * Liste gepeichert wurde, wird eine leere neue Liste returned.
     *
     * @return die gespeicherten NotificationTimes oder leere Liste
     */
    public static LinkedList<String> retrieveNotificationTimesFromFile(Context context) {
        LinkedList<String> notificationTimes = new LinkedList<>();


        File file = context.getFileStreamPath("notificationTimes");
        if(file.exists()) {
            try (FileInputStream fis = context.openFileInput("notificationTimes")) {
                if (fis != null) {
                    try (ObjectInputStream ois = new ObjectInputStream(fis)) {


                        notificationTimes = (LinkedList<String>) ois.readObject();

                    } catch (IOException | ClassNotFoundException | ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return notificationTimes;
    }

    /**
     *  Mit den Outputstreems wird die Liste zum ByteArry und serialisiert auf den Speicher im
     *  Smartphone geschrieben.
     *
     * @param filename the name of the file to be stored/overwritten
     * @param fileContents the list with times to be stored
     */
    public static void writeFileOnInternalStorage(String filename, List<String> fileContents, Context context) {

        try (FileOutputStream fos = context.getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
             ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {

            out.writeObject(fileContents);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            fos.write(yourBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

