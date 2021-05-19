package com.example.drmapp.ui.viewEntries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewLastViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewLastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is viewlast fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}