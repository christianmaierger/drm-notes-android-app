package com.example.drmapp.model;


import android.content.Context;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.database.EntryDAO;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Die Klasse Entry soll die Werte speichern, die der User beim Ausfuellen des Fragebogens eingibt.
 * Eine Instanz der Klasse stellt dabei einen Eintrag dar.
 * In einer Arrayliste werden alle Entry Objekte gespeichert.
 * Mittels .get(position) kann auf die einzelnen Objekte der ArrayListe zugegriffen werden.
 * */

@Entity
public class Entry {

    // ein Feld wird automatisch zu einer Spalte in der Tabelle mit Namen der Klasse
    // da Room Zugang zu den Feldern braucht bei private immer Getter/Setter bereitstellen
    // column info setzt custom namen für die Felder
    // @ColumnInfo(name = "date")
    // @Ignore ignoriert Felder/Konstruktoren
    private static final AtomicInteger count = new AtomicInteger(0);
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private long date2;
    private String time;
    private int time_int; // Die Zeit wird zusätzlich in int gespeichert, um eine sinnvolle Sortierung nach der Zeit im RecyclerView zu ermöglichen
    private String activity;
    private int emoji;
    private int sam1;
    private int sam2;
    private int sam3;
    private String thoughts;

    private boolean isQuickEntry; // Speichervariable fuer Unterschiedung zwischen den ViewHoldern
    private boolean isExpaned; // Variable notwendig fuer ausklappbaren RecyclerView

    public Entry(boolean isQuickEntry, String date, String time, int time_int, String activity, int emoji, int sam1, int sam2, int sam3, String thoughts) {
        this.id= count.incrementAndGet();
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.emoji = emoji;
        this.sam1 = sam1;
        this.sam2 = sam2;
        this.sam3 = sam3;
        this.thoughts = thoughts;
        this.isQuickEntry = isQuickEntry;
        this.time_int = time_int;

        isExpaned=false;
    }

    @Ignore
    public Entry(boolean isQuickEntry, String date, String time, int time_int, String activity, String thoughts) {
        this.id= count.incrementAndGet();
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.thoughts = thoughts;
        this.isQuickEntry = isQuickEntry;
        this.time_int = time_int;
        isExpaned=false;

    }

    @Ignore
    public Entry() {
        this.id= count.incrementAndGet();
    }

    public boolean isQuickEntry(){ return isQuickEntry;}
    public void setQuickEntry(boolean quickEntry){ isQuickEntry = quickEntry;}

    public int getTime_int() {
        return time_int;
    }

    public void setTime_int(int time_int) {
        this.time_int = time_int;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpaned() {
        return isExpaned;
    }

    public void setExpaned(boolean expaned) {
        isExpaned = expaned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDate2() {
        return date2;
    }

    public void setDate2(long date2) {
        this.date2 = date2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public int getEmoji() {
        return emoji;
    }

    public void setEmoji(int emoji) {
        this.emoji = emoji;
    }

    public int getSam1() {
        return sam1;
    }

    public void setSam1(int sam1) {
        this.sam1 = sam1;
    }

    public int getSam2() {
        return sam2;
    }

    public void setSam2(int sam2) {
        this.sam2 = sam2;
    }
    public int getSam3() {
        return sam3;
    }

    public void setSam3(int sam3) {
        this.sam3 = sam3;
    }

    // method just to create some test data
    public static ArrayList<Entry> createEntryList(Context context) {
       ArrayList<Entry> entries = new ArrayList<Entry>();



      AppDatabase db = AppDatabase.getInstance(context);
       EntryDAO userDao = db.entryDao();

        // nicht so toll nur behelfsmasnahme
        ArrayList<Entry> ents = new ArrayList<>(entries);


        return ents;


    }


    @Override
    public String toString() {
        return "Entry{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", activity='" + activity + '\'' +
                ", emoji='" + emoji + '\'' +
                ", sam1=" + sam1 +
                ", sam2=" + sam2 +
                ", sam3=" + sam3 +
                ", thoughts='" + thoughts + '\'' +
                ", isExpaned=" + isExpaned +
                '}';
    }
}




