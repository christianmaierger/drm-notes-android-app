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


public class ManageNotificationsFragment extends Fragment implements View.OnClickListener  {
    private ManageNotificationsViewModel mViewModel;
    private View root;
    private Button timeTextButton1;
    private Button timeTextButton2;
    private Button timeTextButton3;
    // for esaier handling especially when stting handlers, so not so much code has to be copied
    private List<Button> timeTextButtonList = new LinkedList<>();

    // weil das setzen eines containers auf gone auch die views darin auf gone setzt
    // halte ich hier tmp variablen, die mir helfen den ursprünglichen Zustand zu halten
    int visibilityStateOfTimeButton1;
    int visibilityStateOfTimeButton2;
    int visibilityStateOfTimeButton3;
    private Group timePickerGroup;
    private Group timePickerGroup2;
    private FloatingActionButton addNotificationTimeButton;
    private TimePicker picker;


    public View getRoot() {
        return root;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);

        // Text neben dem Button der den TimePicker erscheinen lässt
        final TextView textViewBesidesAddNotificationButton = root.findViewById(R.id.textForAddTimeButton);


        // Folgend die TextViews die die ausgewählten Zeiten für Notifications zeigen
        timeTextButton1 = root.findViewById(R.id.time1);
        timeTextButton2 = root.findViewById(R.id.time2);
        timeTextButton3 = root.findViewById(R.id.time3);

        timeTextButton1.setOnClickListener(this);
        timeTextButton2.setOnClickListener(this);
        timeTextButton3.setOnClickListener(this);

        timeTextButtonList.add(timeTextButton1);
        timeTextButtonList.add(timeTextButton2);
        timeTextButtonList.add(timeTextButton3);


        timePickerGroup = root.findViewById(R.id.timePickerGroup);
        timePickerGroup2 = root.findViewById(R.id.timePickerGroup2);
        addNotificationTimeButton = root.findViewById(R.id.addTimePicker);

        //time picker in 24 h modus setzen, was leider nicht im XML direkt geht
        picker = root.findViewById(R.id.simpleTimePicker);
        picker.setIs24HourView(true);
        TimePicker picker2 = root.findViewById(R.id.simpleTimePicker2);
        picker2.setIs24HourView(true);


        //set flag to change functionality of button, its "logo" and description, initially false, as button is not pressed and timepicker is hidden
        mViewModel.setAddTimeButtonpressed(false);

        // Hier werden der TextView neben dem add button für times und
        // alle drei TextViews für Notifications an ihre Daten/Texte im Model gebunden
        bindTextViewAndTextFromModel(textViewBesidesAddNotificationButton, mViewModel.getButtonText());
        bindTextViewAndTextFromModel(timeTextButton1, mViewModel.getTimeText1());
        bindTextViewAndTextFromModel(timeTextButton2, mViewModel.getTimeText2());
        bindTextViewAndTextFromModel(timeTextButton3, mViewModel.getTimeText3());


        mViewModel.getTimeText1().setValue(getContext().getString(R.string.noTimePickedText));

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);

        setFunctionalityForAddNotifiactionTimeButton();

        System.out.println(" in the beginning 2 is" + timeTextButton2.getVisibility());

        // für den submit button der zu der timepicker group gehört, die erscheint, wenn man
        // einen neuen EIntrag added per addNotificationTime button
        setFunctionalityOfSubmitNotificationTimeButton();



        return root;
    }

    private void setFunctionalityOfSubmitNotificationButton2(MutableLiveData<String> buttonPressed) {
        Button submitTime2 = root.findViewById(R.id.getTime2);
        submitTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour;
                int minute;
                TimePicker picker2 = getView().findViewById(R.id.simpleTimePicker2);
                // above API 23 the methods to get the time have changed, both ways are implemented here
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker2.getHour();
                    minute = picker2.getMinute();
                }
                else{
                    hour = picker2.getCurrentHour();
                    minute = picker2.getCurrentMinute();
                }
                buttonPressed.setValue("Selected Date: " + hour + ":" + minute);

                returnStateOfViewToTimePickerGoneAndTimeSelectable();

            }
        });
    }


    private void setFunctionalityOfSubmitNotificationTimeButton() {

        visibilityStateOfTimeButton1 = timeTextButton1.getVisibility();
        visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
        visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();


        System.out.println(visibilityStateOfTimeButton1);
        System.out.println(visibilityStateOfTimeButton2);

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



                // vor der Abfrage den Zustand der Visibility wieder herstellen, der vom Ändern der
                // Visibility des "parent" contaniners geändert wird
                timeTextButton1.setVisibility(visibilityStateOfTimeButton1);
                timeTextButton2.setVisibility(visibilityStateOfTimeButton2);
                timeTextButton3.setVisibility(visibilityStateOfTimeButton3);

                // Abfragen ob der TimePicker zum changen einer Time oder neu Anlegen aufgerufen wurde
                // und ob noch einer frei ist
               boolean allTextViewsTaken = timeTextButton1.getVisibility()==View.GONE && timeTextButton2.getVisibility()==View.GONE && timeTextButton3.getVisibility()==View.GONE;
                System.out.println(" after setting vis to temp 2 is" + timeTextButton2.getVisibility());
               if(mViewModel.isAddTimeButtonpressed() && !allTextViewsTaken) {


                   if (timeTextButton1.getVisibility() == View.GONE || mViewModel.getTimeText1().getValue().equals(getContext().getString(R.string.noTimePickedText))) {
                       mViewModel.getTimeText1().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton1.setVisibility(View.VISIBLE);
                       visibilityStateOfTimeButton1 = timeTextButton1.getVisibility();
                   } else if (timeTextButton2.getVisibility() == View.GONE) {
                       mViewModel.getTimeText2().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton2.setVisibility(View.VISIBLE);
                       visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                   } else if (timeTextButton3.getVisibility() == View.GONE) {
                       mViewModel.getTimeText3().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton3.setVisibility(View.VISIBLE);
                      visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                   }
               }

                returnStateOfViewToTimePickerGoneAndTimeSelectable();

                Calendar time = buildTimeForNotification(hour, minute);
                buildAndSetNotification(time);
            }
        });
    }

    private void setFunctionalityForAddNotifiactionTimeButton() {
        addNotificationTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // Wenn das flag false ist, befindet sich View im Ausgangszustand, also TimePicker gone
                // und Group mit den TimeTexts und Buttons da, dass muss getauscht werden
                if(mViewModel.isAddTimeButtonpressed()==false) {


                    visibilityStateOfTimeButton1 = timeTextButton1.getVisibility();
                    visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                    visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();


                    // setzt dass auch die inneren container Gone, allerdings haben wir deren State in tmp gehalten
                timeTextButton1.setVisibility(View.GONE);
                timeTextButton2.setVisibility(View.GONE);
                timeTextButton3.setVisibility(View.GONE);


                timePickerGroup.setVisibility(View.VISIBLE);


                  Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel);
                  // change the src the so to speak graphical element on the button
                  addNotificationTimeButton.setImageDrawable(drawable);

                  mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));

                  //set flag to change functionality of button, its "logo" and description
                  mViewModel.setAddTimeButtonpressed(true);

                } else if(mViewModel.isAddTimeButtonpressed()==true) {
                    returnStateOfViewToTimePickerGoneAndTimeSelectable();
                }
            }
        });
    }

    private void bindTextViewAndTextFromModel(TextView textViewBesidesAddNotificationButton, MutableLiveData<String> buttonText) {
        // Change listener, immer wenn sich buttonText im Model ändert, wird er auch hier geändert durch "Controller", schönes MVC Pattern
        buttonText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewBesidesAddNotificationButton.setText(s);
            }
        });
    }


    // Der TimePicker wird wieder gone gesetzt und der Container mit gepickten Times wieder visible
    private void returnStateOfViewToTimePickerGoneAndTimeSelectable() {


        timePickerGroup.setVisibility(View.GONE);
        timePickerGroup2.setVisibility(View.GONE);
       // System.out.println(timeGroup1.getVisibility());



        timeTextButton1.setVisibility(visibilityStateOfTimeButton1);
        timeTextButton2.setVisibility(visibilityStateOfTimeButton2);
        timeTextButton3.setVisibility(visibilityStateOfTimeButton3);


        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);
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


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.time1) {
            // Button buttonPressed = getRoot().findViewById(R.id.time1);
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText1();
            visibilityStateOfTimeButton1 = timeTextButton1.getVisibility();

            // Funktionylität für den submit button der time picker grouß die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed);

        } else if (view.getId() == R.id.time2) {
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText2();
            visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();

            // Funktionylität für den submit button der time picker grouß die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed);

        } else if (view.getId() == R.id.time3) {
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText3();
            visibilityStateOfTimeButton1 = timeTextButton3.getVisibility();

            // Funktionylität für den submit button der time picker grouß die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed);

        }

        // setzt dass auch die inneren container Gone, allerdings haben wir deren State in tmp gehalten
        timeTextButton1.setVisibility(View.GONE);
        timeTextButton2.setVisibility(View.GONE);
        timeTextButton3.setVisibility(View.GONE);

        timePickerGroup2.setVisibility(View.VISIBLE);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel);
        // change the src the so to speak graphical element on the button
        addNotificationTimeButton.setImageDrawable(drawable);

        mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));

        //set flag to change functionality of button, its "logo" and description
        mViewModel.setAddTimeButtonpressed(true);


    }
}