package com.example.drmapp.ui.manageNotifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.drmapp.R;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ManageNotificationsViewModel extends ViewModel {

    private boolean addTimeButtonpressed;
    // dieser counter gibt immer an, welcher timeText durch Auswahl gefüllt werden soll,
    // wenn noch nicht alle Slots befüllt sind, ab dann muss ein ausgefüllter geändert werden
    private int timeTextViewToFillNext =1;
    private MutableLiveData<String> buttonText;
    private MutableLiveData<String> timeText1;
    private MutableLiveData<String> timeText2;
    private MutableLiveData<String> timeText3;
    private MutableLiveData<Time> time;


    public ManageNotificationsViewModel() {
        timeText1 = new MutableLiveData<>();
        buttonText = new MutableLiveData<>();
        timeText2 = new MutableLiveData<>();
        timeText3 = new MutableLiveData<>();
        time = new MutableLiveData<>();

    }

    public boolean isAddTimeButtonpressed() {
        return addTimeButtonpressed;
    }

    public int getTimeTextViewToFillNext() {
        return timeTextViewToFillNext;
    }

    public void setTimeTextViewToFillNext(int timeTextViewToFillNext) {
        this.timeTextViewToFillNext = timeTextViewToFillNext;
    }

    public MutableLiveData<String> getButtonText() {
        return buttonText;
    }

    public MutableLiveData<String> getTimeText1() {
        return timeText1;
    }

    public MutableLiveData<String> getTimeText2() {
        return timeText2;
    }

    public MutableLiveData<String> getTimeText3() {
        return timeText3;
    }

    public MutableLiveData<Time> getTime() {
        return time;
    }


    public void setAddTimeButtonpressed(boolean addTimeButtonpressed) {
        this.addTimeButtonpressed = addTimeButtonpressed;
    }
}