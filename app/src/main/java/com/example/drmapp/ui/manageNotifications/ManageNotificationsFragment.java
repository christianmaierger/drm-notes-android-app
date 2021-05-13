package com.example.drmapp.ui.manageNotifications;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ManageNotificationsFragment extends Fragment  {


    private ManageNotificationsViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);
        final TextView textView = root.findViewById(R.id.textForAddTimeButton);
        final TextView timeTextView1 = root.findViewById(R.id.time1);

        //set flag to change functionality of button, its "logo" and description, initially false, as button is not pressed and timepicker is hidden
        mViewModel.setAddTimeButtonpressed(false);

        // Change listener, immer wenn sich buttonText im Model ändert, wird er auch hier geändert durch "Controler", schönes MVC Pattern
        mViewModel.getButtonText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // this binding  crashes the app, also in line 58 a manuel setting of the text for this text view did the same
       /* mViewModel.getTimeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                timeTextView1.setText(s);
            }
        });
*/

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
                   if(mViewModel.isAddTimeButtonpressed()==false) {

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

                     //set flag to change functionality of button, its "logo" and description
                       mViewModel.setAddTimeButtonpressed(true);


                   } else if(mViewModel.isAddTimeButtonpressed()==true) {
                       timePickerGroup.setVisibility(View.GONE);
                       // also change layout of Text and Button, so user understand he can cancle timepicking
                       // get the drawable we want to insert, in this case a X for cancle
                       Drawable drawable = getResources().getDrawable(R.drawable.ic_input_add);
                       //set flag to change functionality
                       button.setImageDrawable(drawable);
                       // todo no hardcoding
                       mViewModel.getButtonText().setValue("Press To Add Notification Time");
                       //set flag to change functionality of button, its "logo" and description
                       mViewModel.setAddTimeButtonpressed(false);

                   }
               }
           });


           // here listener is set to Submit button below time picker and gets the selected time to store it in ViewModel
        Button btnGet = (Button) root.findViewById(R.id.getTimeButton);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour;
                int minute;
                TimePicker picker = getView().findViewById(R.id.simpleTimePicker);
                // above API 23 the methods to get the time have changed, both ways are implemented here
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                mViewModel.getTimeText().setValue("Selected Date: "+ hour +":"+ minute);
                buildNotification();
            }
        });

        return root;
    }


    public void buildNotification () {

        // Create an explicit intent for an Activity in your app
        // try with hardcoded link to MainActivity
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getActivity(), 0, intent, 0);

        //for test channel id is just 1
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), "1")
            .setSmallIcon(R.drawable.ic_input_add)
            .setContentTitle("Test")
            .setContentText("Test Content")
            // this is used for Android 7.1 and lower as there is no channel with own prio
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
             // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            // when flag is set notification is automatically removed after tap
            .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getActivity());

// notificationId is a unique int for each notification that you must define
        //first param notification id for test just 1, needs to be saved to delete notification later on
        notificationManager.notify(1, builder.build());
}




}