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
    private TextView timeTextView1;
    private TextView timeTextView2;
    private TextView timeTextView3;
    private Group timeGroup1;
    private Group timeGroup2;
    private Group timeGroup3;
    private Group timeContainer;

    // weil das setzen eines containers auf gone auch die views darin auf gone setzt
    // halte ich hier tmp variablen, die mir helfen den ursprünglichen Zustand zu halten
    int tmp1;
    int tmp2;
    int tmp3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);

        // Text neben dem Button der den TimePicker erscheinen lässt
        final TextView textViewBesidesAddNotificationButton = root.findViewById(R.id.textForAddTimeButton);

        // hier der Container für alles was mit ausgewählten Times für Notifications zu tun hat
        timeContainer = root.findViewById(R.id.timeTextsGroup);
        // Folgend die TextViews die die ausgewählten Zeiten für Notifications zeigen
        timeTextView1 = root.findViewById(R.id.time1);
        timeTextView2 = root.findViewById(R.id.time2);
        timeTextView3 = root.findViewById(R.id.time3);
        // Gruppen in denen die Textviews zusammen mit Buttons für Delete und Change sind
        timeGroup1 =  root.findViewById(R.id.timeText1Group);
        timeGroup2 =  root.findViewById(R.id.timeText2Group);
        timeGroup3 =  root.findViewById(R.id.timeText3Group);


        System.out.println("Bei con " + timeContainer.getVisibility());
        System.out.println("group2 at init" + timeGroup2.getVisibility());



        //set flag to change functionality of button, its "logo" and description, initially false, as button is not pressed and timepicker is hidden
        mViewModel.setAddTimeButtonpressed(false);

        // Hier werden der TextView neben dem add button für times und
        // alle drei TextViews für Notifications an ihre Daten/Texte im Model gebunden
        bindTextViewAndTextFromModel(textViewBesidesAddNotificationButton, mViewModel.getButtonText());
        bindTextViewAndTextFromModel(timeTextView1, mViewModel.getTimeText1());
        bindTextViewAndTextFromModel(timeTextView2, mViewModel.getTimeText2());
        bindTextViewAndTextFromModel(timeTextView3, mViewModel.getTimeText3());


        mViewModel.getTimeText1().setValue(getContext().getString(R.string.noTimePickedText));

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);

        setFunctionalityForAddNotifiactionTimeButton(button);


        System.out.println("2 at start before submitHandler is set is" + timeGroup2.getVisibility());

        tmp1 = timeGroup1.getVisibility();
        tmp2 = timeGroup2.getVisibility();
        tmp3 = timeGroup3.getVisibility();

        // sf suggested this, helped nothing
       // timeGroup2.clearAnimation();

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

                // Abfragen ob der TimePicker zum changen einer Time oder neu Anlegen aufgerufen wurde
                // und ob noch einer frei ist

                System.out.println("In submit handler befor setting vis to temp 2 is " +timeGroup2.getVisibility());


                // vor der Abfrage den Zustand der Visibility wieder herstellen, der vom Ändern der
                // Visibility des "parent" contaniners geändert wird
               timeGroup1.setVisibility(tmp1);
               timeGroup2.setVisibility(tmp2);
               timeGroup3.setVisibility(tmp3);

               boolean allTextViewsTaken = timeGroup1.getVisibility()==View.GONE && timeGroup2.getVisibility()==View.GONE && timeGroup3.getVisibility()==View.GONE;
                System.out.println(" after setting vis to temp 2 is" +timeGroup2.getVisibility());
               if(mViewModel.isAddTimeButtonpressed() && !allTextViewsTaken) {


                   if (timeGroup1.getVisibility() == View.GONE || mViewModel.getTimeText1().getValue().equals(getContext().getString(R.string.noTimePickedText))) {
                       mViewModel.getTimeText1().setValue("Selected Date: " + hour + ":" + minute);
                       timeGroup1.setVisibility(View.VISIBLE);
                       tmp1 = timeGroup1.getVisibility();
                   } else if (timeGroup2.getVisibility() == View.GONE) {
                       mViewModel.getTimeText2().setValue("Selected Date: " + hour + ":" + minute);
                       timeGroup2.setVisibility(View.VISIBLE);
                       tmp2 = timeGroup2.getVisibility();
                   } else if (timeGroup3.getVisibility() == View.GONE) {
                       mViewModel.getTimeText3().setValue("Selected Date: " + hour + ":" + minute);
                      timeGroup3.setVisibility(View.VISIBLE);
                       tmp3 = timeGroup3.getVisibility();
                   }


               }

                returnStateOfViewToTimePickerGoneAndTimeSelectable();

                Calendar time = buildTimeForNotification(hour, minute);
                buildAndSetNotification(time);
            }
        });

        return root;
    }

    private void setFunctionalityForAddNotifiactionTimeButton(FloatingActionButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Group timePickerGroup = getView().findViewById(R.id.timePickerGroup);
                FloatingActionButton button =  getView().findViewById(R.id.addTimePicker);

               // Wenn das flag false ist, befindet sich View im Ausgangszustand, also TimePicker gone
                // und Group mit den TimeTexts und Buttons da, dass muss getauscht werden
                if(mViewModel.isAddTimeButtonpressed()==false) {

                    System.out.println("bevor der Container gone gesetzt wird ist 2" + timeGroup2.getVisibility());
                    // mit tmp um den seiteneffekt herum?

                    timeGroup1.setVisibility(tmp1);
                    timeGroup2.setVisibility(tmp2);
                    timeGroup3.setVisibility(tmp3);


                   /* tmp1 = timeGroup1.getVisibility();
                    tmp2 = timeGroup2.getVisibility();
                    tmp3 = timeGroup3.getVisibility();*/

                    // setzt dass auch die inneren container Gone? muss so sein?
                timeContainer.setVisibility(View.GONE);


                    System.out.println("nachdem der Container gone gesetzt wird ist 2" + timeGroup2.getVisibility());

                timePickerGroup.setVisibility(View.VISIBLE);
                    System.out.println(timeGroup1.getVisibility());
                    //time picker in 24 h modus setzen, was leider nicht im XML direkt geht
                    TimePicker picker = getView().findViewById(R.id.simpleTimePicker);
                    picker.setIs24HourView(true);

                // also change layout of Text and Button, so user understand he can cancle timepicking
                  // get the drawable we want to insert, in this case a X for cancle
                  Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel);
                  // change the src the so to speak graphical element on the button
                  button.setImageDrawable(drawable);

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

        Group timePickerGroup = (Group) root.findViewById(R.id.timePickerGroup);
        timePickerGroup.setVisibility(View.GONE);
       // System.out.println(timeGroup1.getVisibility());

        tmp1 = timeGroup1.getVisibility();
        tmp2 = timeGroup2.getVisibility();
        tmp3 = timeGroup3.getVisibility();

        System.out.println(" 2 in reverse State bevor container vis" +timeGroup2.getVisibility());

        timeContainer.setVisibility(View.VISIBLE);

        System.out.println("2 after cont vis " +timeGroup2.getVisibility());

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










}