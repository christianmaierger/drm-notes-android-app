package com.example.drmapp.ui.noteQuickEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoteQuickEntryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NoteQuickEntryViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is addEntry fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}