package com.example.drmapp.ui.manageNotifications;

import android.graphics.drawable.Drawable;
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
        mViewModel =
                new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);
        final TextView textView = root.findViewById(R.id.manageNotifications);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);


           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Group timePickerGroup = getView().findViewById(R.id.timePickerGroup);
                   FloatingActionButton button =  getView().findViewById(R.id.addTimePicker);
                   TextView addButtonText = getView().findViewById(R.id.textForAddTimeButton);

                   // dont know if this is best way to handle different funcs with one listener/handler
                   if(addTimeButtonpressed==false) {
                   // TimePicker b = getView().findViewById(R.id.simpleTimePicker);
                   timePickerGroup.setVisibility(View.VISIBLE);
                   // also change layout of Text and Button, so user understand he can cancle timepicking
                     // get the drawable we want to insert, in this case a X for cancle
                     Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel);
                     // change the src the so to speak graphical element on the button
                     button.setImageDrawable(drawable);
                       // change text next zo button, todo not hard coded
                       addButtonText.setText("Click to cancle Time Picking");
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
                       addButtonText.setText("Press To Add Notification Time");
                       addTimeButtonpressed= false;

                   }
               }
           });



        return root;
    }

  public void showTimePicker(View view) {
      TimePicker b = getView().findViewById(R.id.simpleTimePicker);
     // b.setVisibility(View.VISIBLE);


  }




}