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
    private MutableLiveData<String> buttonText;
    private MutableLiveData<String> timeText1;
    private MutableLiveData<String> timeText2;
    private MutableLiveData<String> timeText3;
    private String timeAsString1;
    private String timeAsString2;
    private String timeAsString3;

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

    public String getTimeAsString1() {
        return timeAsString1;
    }

    public void setTimeAsString1(String timeAsString1) {
        this.timeAsString1 = timeAsString1;
    }

    public String getTimeAsString2() {
        return timeAsString2;
    }

    public void setTimeAsString2(String timeAsString2) {
        this.timeAsString2 = timeAsString2;
    }

    public String getTimeAsString3() {
        return timeAsString3;
    }

    public void setTimeAsString3(String timeAsString3) {
        this.timeAsString3 = timeAsString3;
    }

    public void setAddTimeButtonpressed(boolean addTimeButtonpressed) {
        this.addTimeButtonpressed = addTimeButtonpressed;
    }
}