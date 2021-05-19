package com.example.drmapp.ui.manageNotifications;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class ManageNotificationsFragment extends Fragment  {


    private ManageNotificationsViewModel mViewModel;
    private View root;
    // evtl das auch mit ins Model
    private List<TextView> textViewList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);
        final TextView textView = root.findViewById(R.id.textForAddTimeButton);
        final TextView timeTextView1 = root.findViewById(R.id.time1);
        textViewList = new LinkedList<>();
        textViewList.add(timeTextView1);

        //set flag to change functionality of button, its "logo" and description, initially false, as button is not pressed and timepicker is hidden
        mViewModel.setAddTimeButtonpressed(false);

        // Change listener, immer wenn sich buttonText im Model ändert, wird er auch hier geändert durch "Controler", schönes MVC Pattern
        mViewModel.getButtonText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // this binding binds the first existing Textview to the model
       mViewModel.getTimeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                timeTextView1.setText(s);
            }
        });


        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);


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
                       returnStateOfViewToTimePickerGoneAndTimeSelectable();

                   }
               }
           });


           // here listener is set to Submit button below time picker and gets the selected time to store it in ViewModel
        Button submitTime = (Button) root.findViewById(R.id.getTime);
        submitTime.setOnClickListener(new View.OnClickListener() {
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

                // Wenn nur eine Zeit ausgewählt ist, dann können wir direkt die existierende TextView time1 ändern
                if (mViewModel.getTimeStringList().size()<1) {


                    MutableLiveData<String> newEntry = new MutableLiveData<String>();
                    newEntry.setValue("Selected Date: " + hour + ":" + minute);
                    mViewModel.getTimeStringList().add(newEntry);
                    mViewModel.getTimeText().setValue("Selected Date: " + hour + ":" + minute);

                } else {

                    TextView newTextView = buildTextView();
                    textViewList.add(newTextView);

                    MutableLiveData<String> newEntry = new MutableLiveData<String>();
                    newEntry.setValue("Selected Date: " + hour + ":" + minute);
                    mViewModel.getTimeStringList().add(newEntry);

                    mViewModel.getTimeStringList().get(textViewList.indexOf(newTextView)).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            newTextView.setText(s);
                        }
                    });

                    addViewToLayout(newTextView);




                }

                returnStateOfViewToTimePickerGoneAndTimeSelectable();

                Calendar time = buildTimeForNotification(hour, minute);
                buildAndSetNotification(time);
            }
        });

        return root;
    }

    private void returnStateOfViewToTimePickerGoneAndTimeSelectable() {
        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);
        Group timePickerGroup = (Group) root.findViewById(R.id.timePickerGroup);

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

    private Calendar buildTimeForNotification(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }


    public void buildAndSetNotification(Calendar time) {

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
            // just test to see if this works bg service needed, does not work at all, I guess kind of AlarmManager needed
            // this method just adds a timestamp to notification
            .setWhen(time.getTimeInMillis())
            // when flag is set notification is automatically removed after tap
            .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getActivity());

// notificationId is a unique int for each notification that you must define
        //first param notification id for test just 1, needs to be saved to delete notification later on
        // method seems to post imediatelly not regarding time of notification set with setWhen(long milis)
        notificationManager.notify(1, builder.build());
}

        public TextView buildTextView() {


            TextView childView = new TextView(this.getContext());
            // set view id, else getId() returns -1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                childView.setId(View.generateViewId());
            }
            return childView;
        }


        public void addViewToLayout(View childView) {

            Group parentLayout =  root.findViewById(R.id.timeTextsGroup);
            ConstraintSet set = new ConstraintSet();

            parentLayout.addView(childView);

          //  set.clone(parentLayout);
            // connect start and end point of views, in this case top of child to top of parent.
            set.connect(childView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 60);
            // ... similarly add other constraints
          //  set.applyTo(parentLayout);

        }





}