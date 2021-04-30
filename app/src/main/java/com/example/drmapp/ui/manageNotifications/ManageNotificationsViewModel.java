package com.example.drmapp.ui.manageNotifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageNotificationsViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private MutableLiveData<String> mText;

    public ManageNotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is manage notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}