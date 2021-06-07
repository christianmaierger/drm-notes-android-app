package com.example.drmapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.drmapp.ui.entry.Entry;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ENTRY_TABLE= "ENTRY_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_ACTIVITY = "ACTIVITY";
    public static final String COLUMN_FEELING = "FEELING";
    public static final String COLUMN_MOOD = "MOOD";
    public static final String COLUMN_THOUGHTS = "THOUGHTS";
    public static final String COLUMN_COMMENTS = "COMMENTS";

    //TODO: An neue Version mit Emojis und Mannekins anpassen

    public DatabaseHelper(@Nullable Context context) {
        super(context, "entry.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ENTRY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + " TEXT, " + COLUMN_TIME + " TEXT, " + COLUMN_ACTIVITY + " TEXT, "
                + COLUMN_FEELING + " TEXT, " + COLUMN_MOOD + " TEXT, " + COLUMN_THOUGHTS + " TEXT, " + COLUMN_COMMENTS + " TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //später umstellen auf void, return nur zum Testen

    public void addEntrytoDB(Entry entry){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, entry.getDate());
        cv.put(COLUMN_TIME, entry.getTime());
        cv.put(COLUMN_ACTIVITY, entry.getActivity());
        cv.put(COLUMN_FEELING, entry.getFeeling());
        cv.put(COLUMN_MOOD, entry.getMood());
        cv.put(COLUMN_THOUGHTS, entry.getThoughts());
        cv.put(COLUMN_COMMENTS, entry.getComments());

        //TODO bei nullColumnHack noch Minimum an nötigen Spalten angeben
        db.insert(ENTRY_TABLE, null, cv);
    }

    //TODO Methode zum setzen der Einträge in RecyclerView

}
