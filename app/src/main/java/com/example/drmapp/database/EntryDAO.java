package com.example.drmapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.drmapp.ui.entry.Entry;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface EntryDAO {

        @Query("SELECT * FROM entry")
        List<Entry> getAll();

        @Query("SELECT * FROM entry")
        LiveData<List<Entry>> getEntriesAsLiveData();

        @Query("SELECT * FROM entry WHERE id IN (:entryIds)")
        List<Entry> loadAllByIds(int[] entryIds);

        @Query("SELECT * FROM entry WHERE date LIKE :date AND " +
                "time LIKE :time LIMIT 1")
        Entry findByDate(String date, String time);

        // fügt einen Entry ein und wenn es den schon gibt wird ersetzt
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public ListenableFuture<Long> insertEntry(Entry entry);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        public ListenableFuture<Long[]> insertAll(Entry... entries);

        @Update
        public ListenableFuture<Integer> updateEntries(Entry... entries);

        @Update
        public ListenableFuture<Integer> updateEntry(Entry entry);

        @Delete
        ListenableFuture<Integer>deleteEntry(Entry entry);

        @Delete
        ListenableFuture<Integer> deleteEntries(Entry... entries);


}