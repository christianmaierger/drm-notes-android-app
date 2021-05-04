package com.example.drmapp.ui.addEntry_Time_Morning;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeMorningViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TimeMorningViewModel(){
        mText = new MutableLiveData<>();
        //mText.setValue("This is TimeMorning fragment");
    }

    public LiveData<String> getText(){return mText;}
}