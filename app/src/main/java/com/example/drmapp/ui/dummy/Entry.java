package com.example.drmapp.ui.dummy;


/**
 * Die Klasse Entry soll die Werte speichern, die der User beim Ausfuellen des Fragebogens eingibt.
 * Eine Instanz der Klasse stellt dabei einen Eintrag dar.
 * Datentypen sollten noch ueberdacht und ggf. verbessert werden.
 * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Entry {
 // TODO: Ueberlegen welche Datentypen sinnvoll sind fuer unsere Elemente!
    private String date;
    private String time;
    private String activity;
    private String feeling;
    private String mood;
    private String thoughts;
    private String comments;

    public Entry(String date, String time, String activity, String feeling, String mood, String thoughts, String comments) {
        this.date = date;
        this.time = time;
        this.activity = activity;
        this.feeling = feeling;
        this.mood = mood;
        this.thoughts = thoughts;
        this.comments = comments;
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







/*
    */
/**
     * An array of sample (dummy) items.
     *//*

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    */
/**
     * A map of sample (dummy) items, by ID.
     *//*

    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    */
/**
     * A dummy item representing a piece of content.
     *//*

    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}*/
