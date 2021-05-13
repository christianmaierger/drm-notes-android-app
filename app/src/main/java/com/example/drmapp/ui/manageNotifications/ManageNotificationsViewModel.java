package com.example.drmapp.ui.manageNotifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ManageNotificationsViewModel extends ViewModel {

    private boolean addTimeButtonpressed;
    private List<MutableLiveData<String>> timeList;
    private MutableLiveData<String> timeText;
    private MutableLiveData<String> buttonText;
    private MutableLiveData<Time> time;

    public ManageNotificationsViewModel() {
        timeText = new MutableLiveData<>();
        buttonText = new MutableLiveData<>();
        time = new MutableLiveData<>();
    }

    public boolean isAddTimeButtonpressed() {
        return addTimeButtonpressed;
    }

    public List<MutableLiveData<String>> getTimeList() {
        return timeList;
    }

    public MutableLiveData<Time> getTime() {
        return time;
    }

    public MutableLiveData<String> getTimeText() {
        return timeText;
    }

    public MutableLiveData<String> getButtonText() {
        return buttonText;
    }

    public void setAddTimeButtonpressed(boolean addTimeButtonpressed) {
        this.addTimeButtonpressed = addTimeButtonpressed;
    }
}