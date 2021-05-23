package com.example.drmapp.ui.manageNotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
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
import com.example.drmapp.ReceiverForNotifications;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class ManageNotificationsFragment extends Fragment implements View.OnClickListener  {
    private ManageNotificationsViewModel mViewModel;
    private View root;
    private Button timeTextButton1;
    private Button timeTextButton2;
    private Button timeTextButton3;
    // weil oft temporär buttons die die Zeit anzeigen/changen oder solche, die eine time deleten
    // temporär gone gesetzt werden müssen, um dem TimePicker platz zu machen, halte ich
    // deren VisibilityStates
    private int visibilityStateOfTimeButton1;
    int visibilityStateOfTimeButton2;
    int visibilityStateOfTimeButton3;

    private FloatingActionButton deleteButton1;
    private FloatingActionButton deleteButton2;
    private FloatingActionButton deleteButton3;
    int visibilityStateOfDeleteTimeButton1;
    int visibilityStateOfDeleteTimeButton2;
    int visibilityStateOfDeleteTimeButton3;

    private Group timePickerGroup;
    // Zweite TimePicker Group um eine einfache Möglichkeit zu haben einen TimePicker für das neu
    // Erstellen von times zu haben und einen für das Changen, wegen unterschiedlicher FUnktionalität
    private Group timePickerGroup2;
    private FloatingActionButton addNotificationTimeButton;
    private TimePicker picker;
    private Bundle saveInstanceState;




    public View getRoot() {
        return root;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

      /*  if (savedInstanceState == null) {

        } else {


            visibilityStateOfTimeButton2= savedInstanceState.getInt("visibilityStateOfTimeButton2");

        }
*/


        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);

        // Text neben dem Button der den TimePicker erscheinen lässt
        final TextView textViewBesidesAddNotificationButton = root.findViewById(R.id.textForAddTimeButton);


        // Folgend die Buttons die die ausgewählten Zeiten für Notifications zeigen
        // und bei click den TimePicker öffnen um die jeweilige time des buttons zu changen
        timeTextButton1 = root.findViewById(R.id.time1);
        timeTextButton2 = root.findViewById(R.id.time2);
        timeTextButton3 = root.findViewById(R.id.time3);


        //Da die Funktionalitäten für diese buttons jweils ähnlich sind wird der "KlassenHandler"
        // eingesetzt um weniger Code zu kopieren und dies besser in Methode abhandeln zu können
        timeTextButton1.setOnClickListener(this);
        timeTextButton2.setOnClickListener(this);
        timeTextButton3.setOnClickListener(this);

        deleteButton1 = root.findViewById(R.id.deleteTime1);
        deleteButton2 = root.findViewById(R.id.deleteTime2);
        deleteButton3 = root.findViewById(R.id.deleteTime3);

        deleteButton1.setOnClickListener(this);
        deleteButton2.setOnClickListener(this);
        deleteButton3.setOnClickListener(this);

        // Die beiden Groups für den TimePicker der erscheint, wenn man times added und der
        // der erscheint, wenn man times changed
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

        // für den submit button der zu der timepicker group gehört, die erscheint, wenn man
        // einen neuen Eintrag added per addNotificationTime button
        setFunctionalityOfSubmitNotificationTimeButton();


        String contents;
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            contents = stringBuilder.toString();
        }

            System.out.println("Die Inhalte des Files sind " + contents);


        String[] splited = contents.split("\\s+");

        List<String> storedNotifificationTimes = new LinkedList<>(Arrays.asList(splited));

        //todo method that fills the buttonTexts with strings if they are there and restores gui to show
        // buttons with times

        return root;
    }




    public void writeFileOnInternalStorage(Context mcoContext, String filename, List<String> fileContents){
        String toWrite = "";
        for (String str : fileContents) {
            toWrite = toWrite + " " + str;
        }
        toWrite= toWrite.trim();

        try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(toWrite.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Seit Android 3.0 wohl der beste und sicherste "Ort" um persistente Daten zu speichern, mein kleines Zeug vielleicht erstmal per File Api
    @Override
    public void onStop() {
        super.onStop();
        List<String> notificationTimes = new LinkedList<String>();
        if (mViewModel.getTimeAsString1()!=null && mViewModel.getTimeAsString1()!="") {
            notificationTimes.add(mViewModel.getTimeAsString1());
        }
        if (mViewModel.getTimeAsString2()!=null && mViewModel.getTimeAsString1()!="") {
            notificationTimes.add(mViewModel.getTimeAsString2());
        }
        if (mViewModel.getTimeAsString3()!=null && mViewModel.getTimeAsString3()!="") {
            notificationTimes.add(mViewModel.getTimeAsString1());
        }


        writeFileOnInternalStorage(getContext(), "test", notificationTimes);

    }


    // is never called when Navigation is clicked or back button of Android is cklicked
    // Nachdem ich noch mehr gelesen habe, denke ich das ist der falsche Weg, für nicht persistente Sachen
    // gibt es eh das view model und hier auf Biegen und Brechen Dinge wie die Zeiten und damit Visibility der Buttons
    // die ja immer persistent sein müssen zu speichern für Unsinn, in onStop sollten wir mit speichern beginnen per File oder DB
   // @Override
    /*public void onSaveInstanceState(Bundle savedInstanceState) {

        //save the values of fragment if destroy on second to back
        if (visibilityStateOfTimeButton2==0)
            savedInstanceState.putInt("visibilityStateOfTimeButton2", visibilityStateOfDeleteTimeButton2);
        super.onSaveInstanceState(savedInstanceState);
    }*/

    /**
     * Die Methode setzt die Funktionalität für den submitButton der in der TimePickerGroup
     * ist, die erscheint wenn eine Zeit geändert werden soll, indem der jeweilige TimeButton
     * gepresst wird. Die Methode gettet die ausgewählte Zeit und leitet Sie weiter ins Model
     *
     * @param buttonPressed die zugehörigen Daten im Model zu dem TimeButton dessen Zeit geändert werden soll
     */
    private void setFunctionalityOfSubmitNotificationButton2(MutableLiveData<String> buttonPressed, int timeTextToChangeAndStore) {
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

                // hier wird der TextWert für die MutableLive Data im Model gesetzt, welche dann
                // auf den TimeButtons als gewählte Uhrzeit angezeigt wird
                buttonPressed.setValue("Selected Date: " + hour + ":" + minute);

                switch(timeTextToChangeAndStore) {
                    case 1:
                        mViewModel.setTimeAsString1(hour+":"+minute);
                        break;
                    case 2:
                        mViewModel.setTimeAsString2(hour+":"+minute);
                        break;
                    case 3:
                        mViewModel.setTimeAsString1(hour+":"+minute);
                        break;
                }


                returnStateOfViewToTimePickerGoneAndTimeSelectable();

            }
        });
    }


    private void setFunctionalityOfSubmitNotificationTimeButton() {

        setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
        visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
        visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
        visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
        visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
        visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();

        System.out.println(getVisibilityStateOfTimeButton1());
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

                // builden eines Kalenderobjekts das die Zeit hält, an welchem eine Notification
                // passieren soll. Wird dem AlarmManager übergeben, damit er dann die App "weckt" und
                // den BroadCastreciecer aufruft, damit der eine Notification feuert
                Calendar time = buildTimeForNotification(hour, minute);

               //int tmp Visibility1 = timeTextButton1.getVisibility();

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

                   // dadurch dass getestet wird, dass in den jeweils anderen beiden buttons nicht der String steht
                   // der angibt, dass Keine Time ausgewählt ist, kann man sicher sein, dass der button
                   // wieder visible gesetzt werden muss, sonst ändert sich der String in dem noch vorhanden Button

                   // da ja im letzten stehengebliebenen das Visible ist auch der Text drinnen steht, muss man das auschließen
                   // ein view muss also eine Zeit drinnen stehen haben und gone sein, damit er wirklich weg ist, denn
                   // es wird ja der Text eingesetzt beim deleten

                   // ersetzen will ich es wenn es nicht gone ist und keine time drinnen steht
                   boolean time1TextisNotTime;
                   if (mViewModel.getTimeText1().getValue()!=null) {
                       time1TextisNotTime = (mViewModel.getTimeText1().getValue().equals(getString(R.string.noTimePickedText)) || mViewModel.getTimeText1().getValue().equals(""));
                   } else {
                       time1TextisNotTime = true;
                   }
                   boolean time2TextisNotTime;
                   if (mViewModel.getTimeText2().getValue()!=null) {
                       time2TextisNotTime = ( mViewModel.getTimeText2().getValue().equals(getString(R.string.noTimePickedText)) || mViewModel.getTimeText2().getValue().equals("") );
                   } else {
                       time2TextisNotTime = true;
                   }
                   boolean time3TextisNotTime;
                   if (mViewModel.getTimeText3().getValue()!=null) {
                       time3TextisNotTime = ( mViewModel.getTimeText3().getValue().equals(getString(R.string.noTimePickedText)) || mViewModel.getTimeText3().getValue().equals("") );
                   } else {
                       time3TextisNotTime = true;
                   }

                   boolean time1Visible = timeTextButton1.getVisibility() == View.VISIBLE;
                   boolean time2Visible = timeTextButton2.getVisibility() == View.VISIBLE;
                   boolean time3Visible = timeTextButton3.getVisibility() == View.VISIBLE;
                   boolean time1IsLastVisibleTimeButton =time1Visible && time1TextisNotTime;
                   boolean time2IsLastVisibleTimeButton =time2Visible && time2TextisNotTime;
                   boolean time3IsLastVisibleTimeButton =time3Visible && time3TextisNotTime;


                   if (  timeTextButton1.getVisibility() == View.GONE || (time1IsLastVisibleTimeButton)  )
                        {
                       mViewModel.getTimeText1().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton1.setVisibility(View.VISIBLE);
                       setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                       deleteButton1.setVisibility(View.VISIBLE);
                       visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();

                            mViewModel.setTimeAsString1(hour+":"+minute);

                            buildAndSetNotification(time, 1);
                   } else if  (timeTextButton2.getVisibility() == View.GONE || (time2IsLastVisibleTimeButton)  )  {
                       mViewModel.getTimeText2().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton2.setVisibility(View.VISIBLE);
                       visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                       deleteButton2.setVisibility(View.VISIBLE);
                       visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();

                       mViewModel.setTimeAsString2(hour+":"+minute);

                       buildAndSetNotification(time, 2);
                   } else if ( timeTextButton3.getVisibility() == View.GONE  || (time3IsLastVisibleTimeButton) ) {
                       mViewModel.getTimeText3().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton3.setVisibility(View.VISIBLE);
                      visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                       deleteButton3.setVisibility(View.VISIBLE);
                       visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();

                       mViewModel.setTimeAsString3(hour+":"+minute);

                       buildAndSetNotification(time, 3);
                   }
                   returnStateOfViewToTimePickerGoneAndTimeSelectable();



               }


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


                    setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                    visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                    visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                    visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
                    visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
                    visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();




                    // setzt dass auch die inneren container Gone, allerdings haben wir deren State in tmp gehalten
                timeTextButton1.setVisibility(View.GONE);
                timeTextButton2.setVisibility(View.GONE);
                timeTextButton3.setVisibility(View.GONE);

                    deleteButton1.setVisibility(View.GONE);
                    deleteButton2.setVisibility(View.GONE);
                    deleteButton3.setVisibility(View.GONE);


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

        deleteButton1.setVisibility(visibilityStateOfDeleteTimeButton1);
        deleteButton2.setVisibility(visibilityStateOfDeleteTimeButton2);
        deleteButton3.setVisibility(visibilityStateOfDeleteTimeButton3);


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


    public void buildAndSetNotification(Calendar time, int timeButtonNumber) {

        // Create an explicit intent for an Activity in your app
        // try with hardcoded link to MainActivity
        Intent intent = new Intent(this.getContext(), ReceiverForNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // versuche hier im Extra des Intents etwas an den reciever zu übergeben, scheint nicht zu gehen
        intent.putExtra("ButtonNumber", timeButtonNumber);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);


        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

/*
        //for test channel id is just 1 das braucht man ab API 26 davor wird es ignored
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

        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        // setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        alarmMgr.setRepeating(AlarmManager.RTC, time.getTimeInMillis()/1000,
                AlarmManager.INTERVAL_DAY, alarmIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getActivity());

// notificationId is a unique int for each notification that you must define
        //first param notification id for test just 1, needs to be saved to delete notification later on
        // method seems to post imediatelly not regarding time of notification set with setWhen(long milis)
       // notificationManager.notify(1, builder.build());*/
}

    /**
     * For the view tapped by the user, in this case one of the three time buttons to change the time
     * the functionality is set so they bring up their timepicker and will display the time choosen
     * Or it is a deleteTime button then it will delete the corresponding time buttons time
     *
     * @param view ist hier immer der time oder deleteTime button der gedrückt wurde
     */
    @Override
    public void onClick(View view) {

        // Erst Abfragen ob die Funktionalität für die buttons zum Ändern der Zeit oder zum deleten
        // benötigt wird
        if( (view.getId() == R.id.time1) || (view.getId() == R.id.time2) || (view.getId() == R.id.time3) ) {
            setFunctionalityForTimeButtonsToChangeTime(view);
        } else if( (view.getId() == R.id.deleteTime1) || (view.getId() == R.id.deleteTime2) || (view.getId() == R.id.deleteTime3) ) {

            if(view.getId() == R.id.deleteTime1) {
               // Abfragen ob time1 der einzig verbleibende Button ist, dann nicht gone setzen,
                //sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
               if( (deleteButton2.getVisibility()==View.GONE) && (deleteButton3.getVisibility()==View.GONE) ) {
                   deleteButton1.setVisibility(View.GONE);
                   visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
                   mViewModel.getTimeText1().setValue(getContext().getString(R.string.noTimePickedText));

                   // wenn noch weitere buttons vorhanden sind, dann verschwindet 1 einfach
               } else {
                    deleteButton1.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
                    timeTextButton1.setVisibility(View.GONE);
                    setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                   mViewModel.getTimeText1().setValue(getContext().getString(R.string.noTimePickedText));
                }

            } else if (view.getId() == R.id.deleteTime2) {
                // Abfragen ob time2 der einzig verbleibende Button ist, dann nicht gone setzen,
                // sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if( (deleteButton1.getVisibility()==View.GONE) && (deleteButton3.getVisibility()==View.GONE) ) {
                    deleteButton2.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
                    mViewModel.getTimeText2().setValue(getContext().getString(R.string.noTimePickedText));
                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 2 einfach
                } else {
                    deleteButton2.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
                    timeTextButton2.setVisibility(View.GONE);
                    visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                    mViewModel.getTimeText2().setValue(getContext().getString(R.string.noTimePickedText));
                }

            } else if (view.getId() == R.id.deleteTime3) {
                // Abfragen ob time3 der einzig verbleibende Button ist, dann nicht gone setzen,
                // sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if( (deleteButton1.getVisibility()==View.GONE) && (deleteButton2.getVisibility()==View.GONE) ) {
                    deleteButton3.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();
                    mViewModel.getTimeText3().setValue(getContext().getString(R.string.noTimePickedText));


                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 2 einfach
                } else {
                    deleteButton3.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();
                    timeTextButton3.setVisibility(View.GONE);
                    visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                    mViewModel.getTimeText3().setValue(getContext().getString(R.string.noTimePickedText));
                }

            }


        }

    }

    private void setFunctionalityForTimeButtonsToChangeTime(View view) {
        if (view.getId() == R.id.time1) {
            // Button buttonPressed = getRoot().findViewById(R.id.time1);
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText1();
            setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
            deleteButton1.setVisibility(View.VISIBLE);
            visibilityStateOfDeleteTimeButton1= deleteButton1.getVisibility();

            // Funktionylität für den submit button der time picker grouß die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed, 1);

        } else if (view.getId() == R.id.time2) {
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText2();
            visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
            deleteButton2.setVisibility(View.VISIBLE);
            visibilityStateOfDeleteTimeButton2= deleteButton2.getVisibility();;

            // Funktionylität für den submit button der time picker group die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed, 2);

        } else if (view.getId() == R.id.time3) {
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText3();
            visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
            deleteButton3.setVisibility(View.VISIBLE);
            visibilityStateOfDeleteTimeButton3= deleteButton3.getVisibility();

            // Funktionylität für den submit button der time picker grouß die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed, 3);
        }

        // setzt dass auch die inneren container Gone, allerdings haben wir deren State in tmp gehalten
        timeTextButton1.setVisibility(View.GONE);
        timeTextButton2.setVisibility(View.GONE);
        timeTextButton3.setVisibility(View.GONE);

        deleteButton1.setVisibility(View.GONE);
        deleteButton2.setVisibility(View.GONE);
        deleteButton3.setVisibility(View.GONE);

        timePickerGroup2.setVisibility(View.VISIBLE);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_close_clear_cancel);
        // change the src the so to speak graphical element on the button
        addNotificationTimeButton.setImageDrawable(drawable);

        mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));

        //Setzen des flag um die Funktionalität des button, its "logo" and description
        mViewModel.setAddTimeButtonpressed(true);
    }

    private int getVisibilityStateOfTimeButton1() {
        return visibilityStateOfTimeButton1;
    }

    private void setVisibilityStateOfTimeButton1(int visibilityStateOfTimeButton1) {
        this.visibilityStateOfTimeButton1 = visibilityStateOfTimeButton1;
    }
}