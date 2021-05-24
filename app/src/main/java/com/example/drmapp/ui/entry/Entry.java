package com.example.drmapp.ui.entry;


import java.util.ArrayList;

/**
 * Die Klasse Entry soll die Werte speichern, die der User beim Ausfuellen des Fragebogens eingibt.
 * Eine Instanz der Klasse stellt dabei einen Eintrag dar.
 * In einer Arrayliste werden alle Entry Objekte gespeichert.
 * Mittels .get(position) kann auf die einzelnen Objekte der ArrayListe zugegriffen werden.
 * */


public class Entry {

    private String date;
    private String time;
    private String activity;
    private String feeling;
    private String mood;
    private String thoughts;
    private String comments;
    private boolean isExpaned; // notwendig fuer ausklappbare RecyclerView

    public Entry(String date, String time, String activity, String feeling, String mood, String thoughts, String comments) {
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.feeling = feeling;
        this.mood = mood;
        this.thoughts = thoughts;
        this.comments = comments;
        isExpaned=false;
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
    public static ArrayList<Entry> createEntryList() {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry("05/05/21", "09:00", "Eating/Drinking", "Happy", ":)", "Food is yummy", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat."));
        entries.add(new Entry("05/05/21", "13:00", "Working", "Happy", ":)", "Make much money", "no comment"));
        entries.add(new Entry("05/05/21", "18:00", "CareWork", "Happy", ":)", "Love coffee", "no comment"));
        entries.add(new Entry("05/05/21", "01:00", "Chores", "Happy", ":)", "Love coffee", "no comment"));
        entries.add(new Entry("05/05/21", "01:00", "Leisure", "Happy", ":)", "Love coffee", "no comment"));
        entries.add(new Entry("05/05/21", "02:00", "Other", "Happy", ":)", "Love coffee", "no comment"));

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




