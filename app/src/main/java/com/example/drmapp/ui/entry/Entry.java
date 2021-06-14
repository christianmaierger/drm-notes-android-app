package com.example.drmapp.ui.entry;


import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.database.EntryDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Entry soll die Werte speichern, die der User beim Ausfuellen des Fragebogens eingibt.
 * Eine Instanz der Klasse stellt dabei einen Eintrag dar.
 * In einer Arrayliste werden alle Entry Objekte gespeichert.
 * Mittels .get(position) kann auf die einzelnen Objekte der ArrayListe zugegriffen werden.
 * */

@Entity
public class Entry {

        // id als Primärschlüssel
        @PrimaryKey
        private int ID;

    // ein Feld wird automatisch zu einer Spalte in der Tabelle mit Namen der Klasse
    // da Room Zugang zu den Feldern braucht bei private immer Getter/Setter bereitstellen
    // column info setzt custom namen für die Felder
   // @ColumnInfo(name = "date")
    private String date;
    private String time;
    private String activity;
    private String emoji;
    private int sam1;
    private int sam2;
    private String thoughts;

    // verhindert dass ein Feld eine Spalte wird, isExpanded muss man wahrscheinlich nicht dauerhaft speichern?
    @Ignore
    private boolean isExpanded; // notwendig fuer ausklappbare RecyclerView


    public Entry() {
    }

    public Entry(int ID, String date, String time, String activity, String emoji, int sam1, int sam2, String thoughts) {
        this.ID = ID;
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.emoji = emoji;
        this.sam1 = sam1;
        this.sam2 = sam2;
        this.thoughts = thoughts;
       // this.isExpanded = isExpanded;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isExpaned() {
        return isExpanded;
    }

    public void setExpaned(boolean expaned) {
        isExpanded = expaned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
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

    // method just to create some test data
    public static List<Entry> createEntryList(Context context) {
        //ArrayList<Entry> entries = new ArrayList<Entry>();


        AppDatabase db = AppDatabase.getInstance(context);
        EntryDAO userDao = db.entryDao();


       userDao.insertEntry(new Entry(1 , "05/05/21", "13:00", "Working", "Happy", 10, 20, "no comment"));


        List<Entry> entries = userDao.getAll();

       /* entries.add(new Entry("05/05/21", "09:00", "Eating/Drinking", "Happy", ":)", "Food is yummy", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat."));
        entries.add(new Entry("05/05/21", "13:00", "Working", "Happy", ":)", "Make much money", "no comment"));
        entries.add(new Entry("05/05/21", "18:00", "CareWork", "Happy", ":)", "Love coffee", "no comment"));
        entries.add(new Entry("05/05/21", "01:00", "Chores", "Happy", ":)", "Love coffee", "no comment"));
        entries.add(new Entry("05/05/21", "01:00", "Leisure", "Happy", ":)", "Love coffee", "no comment"));
        entries.add(new Entry("05/05/21", "02:00", "Other", "Happy", ":)", "Love coffee", "no comment"));*/

        //adapter.setEntries(entries);

        return entries;
    }


    @Override
    public String toString() {
        return "Entry{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", activity='" + activity + '\'' +
                ", thoughts='" + thoughts + '\'' +
                '}';
    }
}




