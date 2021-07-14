package com.example.drmapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.drmapp.model.Entry;


/**
 * Da das Erzeugen der Datenbank teuer ist (man hätte dann ja mehrere Instanzen im Speicher ), wir nur mit einem Prozess arbeiten und daher keinen
 * Weg der Erzeugung und Verifizierung verschiedener Instanzen und daher wird über das Singleton Pattern
 * nur eine Instanz zugänglich gemacht
 */


@Database(entities = {Entry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EntryDAO entryDao();

    private static AppDatabase instance;

    protected AppDatabase() {
    }

    // Ich benutze Lazy Initialisation Singleton Pattern um nur eine Instanz zu erzeugen, basic threadsafe
    public static AppDatabase getInstance(Context context) {
        // synchronized mit Klasse als Monitor stellt sicher, dass nur 1 Thread zu einem Zeitpunkt den
        // Code in der folgenden critical Section ausführt
synchronized (AppDatabase.class) {
    if (instance == null) {
        instance = Room.databaseBuilder(context,
                AppDatabase.class, "drm-test5-database").build();
    }
}
        return instance;
    }

}
