package com.example.drmapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.drmapp.ui.entry.Entry;

import java.util.List;

@Dao
public interface EntryDAO {

        @Query("SELECT * FROM entry")
        List<Entry> getAll();

        @Query("SELECT * FROM entry WHERE id IN (:entryIds)")
        List<Entry> loadAllByIds(int[] entryIds);

        @Query("SELECT * FROM entry WHERE date LIKE :date AND " +
                "time LIKE :time LIMIT 1")
        Entry findByDate(String date, String time);

        // fügt einen Entry ein und wenn es den schon gibt wird ersetzt
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public void insertEntries(Entry... entry);

        @Insert
        void insertAll(Entry... entries);

        @Update
        public void updateEntries(Entry... entries);

        @Update
        public void updateEntry(Entry entry);

        @Delete
        void deleteEntry(Entry entry);

        @Delete
        void deleteEntries(Entry... entries);
}