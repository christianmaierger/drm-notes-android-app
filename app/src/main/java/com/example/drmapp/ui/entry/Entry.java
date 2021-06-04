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
    private String emoji;
    private int sam1;
    private int sam2;
    private String thoughts;

    private boolean isExpaned; // notwendig fuer ausklappbare RecyclerView

    public Entry(String date, String time, String activity, String emoji, int sam1, int sam2, String thoughts) {
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.emoji = emoji;
        this.sam1 = sam1;
        this.sam2 = sam2;
        this.thoughts = thoughts;

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
    public static ArrayList<Entry> createEntryList() {
        ArrayList<Entry> entries = new ArrayList<Entry>();

               entries.add(new Entry("05/05/21", "07:00", "Eating/drinking", "happy", 1, 1, "Pancakes are good"));
                 entries.add(new Entry("05/05/21", "08:00", "Working/studying", "normal", 1, 1, "Laptop is loud"));
        entries.add(new Entry("05/05/21", "10:00", "Eating/drinking", "sad", 1, 1, "Coffee tasted horrible"));
        entries.add(new Entry("05/05/21", "13:00", "Hobby", "happy", 1, 1, "Took a walk"));
        entries.add(new Entry("05/05/21", "18:00", "Care work", "angry", 1, 1, "Dog did not like the bath"));
        entries.add(new Entry("05/05/21", "22:00", "Leisure Time", "happy", 1, 1, "Netflix"));

        //adapter.setEntries(entries);

        return entries;
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
                ", thoughts='" + thoughts + '\'' +
                ", isExpaned=" + isExpaned +
                '}';
    }
}




