package com.example.drmapp.ui.manageNotifications;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ManageNotificationsFragment extends Fragment  {
    boolean addTimeButtonpressed = false;

    private ManageNotificationsViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);
        final TextView textView = root.findViewById(R.id.textForAddTimeButton);
        final TextView timeTextView1 = root.findViewById(R.id.time1);
        // Change listener, immer wenn sich buttonText im Model ändert, wird er auch hier geändert durch "Controler", schönes MVC Pattern
        mViewModel.getButtonText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mViewModel.getTimeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                timeTextView1.setText(s);
            }
        });


        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);

       // final TextView time1 = root.findViewById(R.id.time1);
        // crashes App
        //time1.setText("Test");


           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Group timePickerGroup = getView().findViewById(R.id.timePickerGroup);
                   FloatingActionButton button =  getView().findViewById(R.id.addTimePicker);
                   //TextView addButtonText = getView().findViewById(R.id.textForAddTimeButton);

                   // dont know if this is best way to handle different funcs with one listener/handler
                   if(addTimeButtonpressed==false) {

                   timePickerGroup.setVisibility(View.VISIBLE);
                       //time picker in 24 h modus setzen, geht wohl nicht in xml
                       TimePicker picker = getView().findViewById(R.id.simpleTimePicker);
                       picker.setIs24HourView(true);

                   // also change layout of Text and Button, so user understand he can cancle timepicking
                     // get the drawable we want to insert, in this case a X for cancle
                     Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel);
                     // change the src the so to speak graphical element on the button
                     button.setImageDrawable(drawable);
                       // change text next zo button, todo not hard coded

                       mViewModel.getButtonText().setValue("Click to cancle Time Picking");

                     //set flag to change functionality
                     addTimeButtonpressed= true;


                   } else if(addTimeButtonpressed==true) {
                       timePickerGroup.setVisibility(View.GONE);
                       // also change layout of Text and Button, so user understand he can cancle timepicking
                       // get the drawable we want to insert, in this case a X for cancle
                       Drawable drawable = getResources().getDrawable(R.drawable.ic_input_add);
                       //set flag to change functionality
                       button.setImageDrawable(drawable);
                       // todo no hardcoding
                       mViewModel.getButtonText().setValue("Press To Add Notification Time");
                       addTimeButtonpressed= false;

                   }
               }
           });

        Button btnGet = (Button) root.findViewById(R.id.getTimeButton);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour;
                int minute;
                TimePicker picker = getView().findViewById(R.id.simpleTimePicker);
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }


                mViewModel.getTimeText().setValue("Selected Date: "+ hour +":"+ minute);
            }
        });

        return root;
    }



}