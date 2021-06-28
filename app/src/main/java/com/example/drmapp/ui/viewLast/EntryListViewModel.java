package com.example.drmapp.ui.viewLast;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.database.EntryDAO;
import com.example.drmapp.ui.entry.Entry;

import java.util.List;

public class EntryListViewModel extends ViewModel {
    // private String TAG = this.getClass().getSimpleName();
    private EntryDAO entryDao;
    private AppDatabase db;
    private LiveData<List<Entry>> entryListAsLiveData;

    public EntryListViewModel(Application application) {
        db = AppDatabase.getInstance(application.getApplicationContext());
        entryDao = db.entryDao();
        entryListAsLiveData = entryDao.getEntriesAsLiveData();
    }

    public LiveData<List<Entry>> getEntryListAsLiveData() {
        return entryListAsLiveData;
    }

    public void setEntryListAsLiveData(LiveData<List<Entry>> entryListAsLiveData) {
        this.entryListAsLiveData = entryListAsLiveData;
    }

    public EntryDAO getEntryDao() {
        return entryDao;
    }

    public void setEntryDao(EntryDAO entryDao) {
        this.entryDao = entryDao;
    }
}


