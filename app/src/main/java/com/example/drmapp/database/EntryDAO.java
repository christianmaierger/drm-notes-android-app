package com.example.drmapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.drmapp.model.Entry;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;


@Dao
public interface EntryDAO {


        @Query("SELECT * FROM entry ORDER BY dateAsLong DESC, time_int ")
        LiveData<List<Entry>> getEntriesAsLiveData();

        // fügt einen Entry ein und wenn es den schon gibt wird ersetzt
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        // ListenableFuture aus der GUVA Lib wird verwendet um einen einfachen Weg zu haben
        // asynchron auf die Datenbank zugreifen zu können
        ListenableFuture<Long> insertEntry(Entry entry);

        @Delete
        ListenableFuture<Integer>deleteEntry(Entry entry);

        @Query("DELETE FROM entry WHERE dateAsLong <= :date")
        ListenableFuture<Integer> deleteEntriesOlderThanDate(long date);

}