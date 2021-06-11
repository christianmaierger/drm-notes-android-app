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
    @ColumnInfo(name = "date")
    private String date;
    private String time;
    private String activity;
    private String feeling;
    private String mood;
    private String thoughts;
    private String comments;
    // verhindert dass ein Feld eine Spalte wird, isExpanded muss man wahrscheinlich nicht dauerhaft speichern?
    @Ignore
    private boolean isExpanded; // notwendig fuer ausklappbare RecyclerView

    public Entry(String date, String time, String activity, String feeling, String mood, String thoughts, String comments) {
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.feeling = feeling;
        this.mood = mood;
        this.thoughts = thoughts;
        this.comments = comments;
        isExpanded=false;
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

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }



    // method just to create some test data
    public static List<Entry> createEntryList(Context context) {
        //ArrayList<Entry> entries = new ArrayList<Entry>();


        AppDatabase db = AppDatabase.getInstance(context);
        EntryDAO userDao = db.entryDao();


       userDao.insertEntries(new Entry("05/05/21", "13:00", "Working", "Happy", ":)", "Make much money", "no comment"));


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
                ", feeling='" + feeling + '\'' +
                ", mood='" + mood + '\'' +
                ", thoughts='" + thoughts + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}




