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
    private boolean allTimesSelected;
    private MutableLiveData<String> buttonText;
    private MutableLiveData<String> timeText1;
    private MutableLiveData<String> timeText2;
    private MutableLiveData<String> timeText3;
    private String timeAsString1;
    private String timeAsString2;
    private String timeAsString3;

    // weil oft buttons die die Zeit anzeigen/changen oder solche, die eine Time deleten,
    // temporär gone gesetzt werden müssen, um dem TimePicker platz zu machen, halte ich
    // deren VisibilityStates
    private int visibilityStateOfTimeButton1;
    private int visibilityStateOfTimeButton2;
    private int visibilityStateOfTimeButton3;

    private int visibilityStateOfDeleteTimeButton1;
    private int visibilityStateOfDeleteTimeButton2;
    private int visibilityStateOfDeleteTimeButton3;

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

    public boolean isAllTimesSelected() {
        return allTimesSelected;
    }

    public void setAllTimesSelected(boolean allTimesSelected) {
        this.allTimesSelected = allTimesSelected;
    }

    public int getVisibilityStateOfTimeButton1() {
        return visibilityStateOfTimeButton1;
    }

    public void setVisibilityStateOfTimeButton1(int visibilityStateOfTimeButton1) {
        this.visibilityStateOfTimeButton1 = visibilityStateOfTimeButton1;
    }

    public int getVisibilityStateOfTimeButton2() {
        return visibilityStateOfTimeButton2;
    }

    public void setVisibilityStateOfTimeButton2(int visibilityStateOfTimeButton2) {
        this.visibilityStateOfTimeButton2 = visibilityStateOfTimeButton2;
    }

    public int getVisibilityStateOfTimeButton3() {
        return visibilityStateOfTimeButton3;
    }

    public void setVisibilityStateOfTimeButton3(int visibilityStateOfTimeButton3) {
        this.visibilityStateOfTimeButton3 = visibilityStateOfTimeButton3;
    }

    public int getVisibilityStateOfDeleteTimeButton1() {
        return visibilityStateOfDeleteTimeButton1;
    }

    public void setVisibilityStateOfDeleteTimeButton1(int visibilityStateOfDeleteTimeButton1) {
        this.visibilityStateOfDeleteTimeButton1 = visibilityStateOfDeleteTimeButton1;
    }

    public int getVisibilityStateOfDeleteTimeButton2() {
        return visibilityStateOfDeleteTimeButton2;
    }

    public void setVisibilityStateOfDeleteTimeButton2(int visibilityStateOfDeleteTimeButton2) {
        this.visibilityStateOfDeleteTimeButton2 = visibilityStateOfDeleteTimeButton2;
    }

    public int getVisibilityStateOfDeleteTimeButton3() {
        return visibilityStateOfDeleteTimeButton3;
    }

    public void setVisibilityStateOfDeleteTimeButton3(int visibilityStateOfDeleteTimeButton3) {
        this.visibilityStateOfDeleteTimeButton3 = visibilityStateOfDeleteTimeButton3;
    }

    public void setTime(MutableLiveData<Time> time) {
        this.time = time;
    }
}