package com.example.drmapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.drmapp.ui.entry.Entry;


    /*Note: If your app runs in a single process, you should follow the singleton design pattern when instantiating
        an AppDatabase object. Each RoomDatabase instance is fairly expensive, and you rarely need access
        to multiple instances within a single process.

        If your app runs in multiple processes, include enableMultiInstanceInvalidation() in your database
        builder invocation. That way, when you have an instance of AppDatabase in each process,
        you can invalidate the shared database file in one process, and this invalidation automatically
        propagates to the instances of AppDatabase within other processes.*/

@Database(entities = {Entry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EntryDAO entryDao();

    private static AppDatabase instance;

    protected AppDatabase() {
    }

    // Ich benutze Lazy Initialisation Singleton Pattern um nur eine Instanz zu erzeugen, basic threadsafe
    public static AppDatabase getInstance(Context context) {
synchronized (AppDatabase.class) {
    if (instance == null) {
        instance = Room.databaseBuilder(context,
                AppDatabase.class, "drm-test1-database").build();
    }
}
        return instance;
    }

}
