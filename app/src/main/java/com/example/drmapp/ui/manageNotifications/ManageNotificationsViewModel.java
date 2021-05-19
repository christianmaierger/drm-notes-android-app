package com.example.drmapp.ui.manageNotifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ManageNotificationsViewModel extends ViewModel {

    private boolean addTimeButtonpressed;
    private List<MutableLiveData<String>> timeList;
    private MutableLiveData<String> timeText;
    private MutableLiveData<String> buttonText;
    private MutableLiveData<Time> time;
    private List<MutableLiveData<String>> timeStringList;

    public ManageNotificationsViewModel() {
        timeText = new MutableLiveData<>();
        timeText.setValue("No Time for Notifications selected so far");
        buttonText = new MutableLiveData<>();
        time = new MutableLiveData<>();
        timeStringList= new LinkedList<MutableLiveData<String>>();
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

    public List<MutableLiveData<String>> getTimeStringList() {
        return timeStringList;
    }

    public void setAddTimeButtonpressed(boolean addTimeButtonpressed) {
        this.addTimeButtonpressed = addTimeButtonpressed;
    }
}