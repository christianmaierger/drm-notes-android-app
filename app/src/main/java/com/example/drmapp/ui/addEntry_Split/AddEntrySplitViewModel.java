package com.example.drmapp.ui.addEntry_Split;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddEntrySplitViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddEntrySplitViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is addEntry fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}