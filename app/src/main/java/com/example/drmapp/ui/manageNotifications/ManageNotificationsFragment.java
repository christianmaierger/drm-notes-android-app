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
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.R;
import com.example.drmapp.ReceiverForNotifications;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class ManageNotificationsFragment extends Fragment implements View.OnClickListener  {
    private ManageNotificationsViewModel mViewModel;
    private View root;
    private Button timeTextButton1;
    private Button timeTextButton2;
    private Button timeTextButton3;
    // weil oft buttons die die Zeit anzeigen/changen oder solche, die eine Time deleten,
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

    // Zweite TimePicker Group um eine einfache Möglichkeit zu haben einen TimePicker für das neu
    // Erstellen von times zu haben und einen für das Changen, wegen unterschiedlicher Funktionalität
    private Group timePickerGroup;
    private Group timePickerGroup2;

    private FloatingActionButton addNotificationTimeButton;
    private TimePicker picker;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);

        // Text neben dem Button der den TimePicker erscheinen lässt
        final TextView textViewBesidesAddNotificationButton = root.findViewById(R.id.textForAddTimeButton);


        // Folgend die Buttons die die ausgewählten Zeiten für Notifications zeigen
        // und bei click den TimePicker öffnen um die jeweilige time des buttons zu changen
        timeTextButton1 = root.findViewById(R.id.time1);
        timeTextButton2 = root.findViewById(R.id.time2);
        timeTextButton3 = root.findViewById(R.id.time3);

        //Da die Funktionalitäten für diese buttons jeweils ähnlich sind, wird der "KlassenHandler",
        // wird von der Klasse implementiert,
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

        addNotificationTimeButton = root.findViewById(R.id.addTimePicker);
        // flag setzen um festzuhalten, ob der addNotificationTimeButton gedrückt wurde
        // da sich dann sein Logo ändert und der TimePicker erscheinen muss
        mViewModel.setAddTimeButtonpressed(false);

        // Die beiden Groups für die TimePicker die erscheinen, wenn man times added oder wenn man times changed
        timePickerGroup = root.findViewById(R.id.timePickerGroup);
        timePickerGroup2 = root.findViewById(R.id.timePickerGroup2);
        // Beide time picker in 24 h Modus setzen, was leider nicht im XML direkt geht
        picker = root.findViewById(R.id.simpleTimePicker);
        picker.setIs24HourView(true);
        TimePicker picker2 = root.findViewById(R.id.simpleTimePicker2);
        picker2.setIs24HourView(true);

        // Hier werden der TextView neben dem add button für times und
        // alle drei TextViews für Notifications an ihre Daten/Texte im Model gebunden
        bindTextViewAndTextFromModel(textViewBesidesAddNotificationButton, mViewModel.getButtonText());
        bindTextViewAndTextFromModel(timeTextButton1, mViewModel.getTimeText1());
        bindTextViewAndTextFromModel(timeTextButton2, mViewModel.getTimeText2());
        bindTextViewAndTextFromModel(timeTextButton3, mViewModel.getTimeText3());

        setFunctionalityForAddNotifiactionTimeButton();

        // für den submit button der zu der timepicker group gehört, die erscheint, wenn man
        // einen neuen Eintrag added per addNotificationTime button
        setFunctionalityOfSubmitNotificationTimeButton();

        LinkedList<String> notificationTimes = retrieveNotificationTimesFromFile();

        restoreStateOfViewWithSavedNotificationTimes(notificationTimes);

        return root;
    }

    public View getRoot() {
        return root;
    }

    private int getVisibilityStateOfTimeButton1() {
        return visibilityStateOfTimeButton1;
    }

    private void setVisibilityStateOfTimeButton1(int visibilityStateOfTimeButton1) {
        this.visibilityStateOfTimeButton1 = visibilityStateOfTimeButton1;
    }

    private void restoreStateOfViewWithSavedNotificationTimes(LinkedList<String> notificationTimes) {

        mViewModel.getTimeText1().setValue(getString(R.string.noTimePickedText));
        mViewModel.setTimeAsString1(getString(R.string.noTimePickedText));

            if (notificationTimes.get(0) == null) {

                timeTextButton1.setVisibility(View.GONE);
                visibilityStateOfTimeButton1 = View.GONE;
                deleteButton1.setVisibility(View.GONE);
                visibilityStateOfDeleteTimeButton1 = View.GONE;
            }
        if(notificationTimes.size()>0) {
            if (notificationTimes.get(0) != null && notificationTimes.get(0) != "" && !notificationTimes.get(0).equals(getString(R.string.noTimePickedText))) {
                mViewModel.getTimeText1().setValue("Selected Date: " + notificationTimes.get(0));
                mViewModel.setTimeAsString1(notificationTimes.get(0));
                timeTextButton1.setVisibility(View.VISIBLE);
                visibilityStateOfTimeButton1 = View.VISIBLE;
                deleteButton1.setVisibility(View.VISIBLE);
                visibilityStateOfDeleteTimeButton1 = View.VISIBLE;
            } else if (notificationTimes.get(0) != null && notificationTimes.get(0).equals(getString(R.string.noTimePickedText))) {
                deleteButton1.setVisibility(View.GONE);
                visibilityStateOfDeleteTimeButton1 = View.GONE;
                mViewModel.getTimeText1().setValue(getString(R.string.noTimePickedText));
                mViewModel.setTimeAsString1(getString(R.string.noTimePickedText));
            }
        }
            if (notificationTimes.size()>1 && notificationTimes.get(1)!=null && !notificationTimes.get(1).equals("") && !notificationTimes.get(1).equals(getString(R.string.noTimePickedText))) {
                mViewModel.getTimeText2().setValue("Selected Date: " + notificationTimes.get(1));
                mViewModel.setTimeAsString2(notificationTimes.get(1));
                timeTextButton2.setVisibility(View.VISIBLE);
                visibilityStateOfTimeButton2= View.VISIBLE;
                deleteButton2.setVisibility(View.VISIBLE);
            } else if(notificationTimes.size()>1 && notificationTimes.get(1)!=null && notificationTimes.get(1).equals(getString(R.string.noTimePickedText))) {
            deleteButton2.setVisibility(View.GONE);
            visibilityStateOfDeleteTimeButton2=View.GONE;
            mViewModel.getTimeText2().setValue(getString(R.string.noTimePickedText));
            mViewModel.setTimeAsString2(getString(R.string.noTimePickedText));

        }
            if (notificationTimes.size()>2 && notificationTimes.get(2)!=null && !notificationTimes.get(2).equals("") && !notificationTimes.get(2).equals(getString(R.string.noTimePickedText))) {
                mViewModel.getTimeText3().setValue("Selected Date: " + notificationTimes.get(2));
                mViewModel.setTimeAsString3(notificationTimes.get(2));
                timeTextButton3.setVisibility(View.VISIBLE);
                visibilityStateOfTimeButton3= View.VISIBLE;
                deleteButton3.setVisibility(View.VISIBLE);
            } else if(notificationTimes.size()>2 && notificationTimes.get(2)!=null && notificationTimes.get(2).equals(getString(R.string.noTimePickedText))) {
            deleteButton3.setVisibility(View.GONE);
            visibilityStateOfDeleteTimeButton3=View.GONE;
            mViewModel.getTimeText3().setValue(getString(R.string.noTimePickedText));
            mViewModel.setTimeAsString3(getString(R.string.noTimePickedText));
        }

        }



    /**
     *
     * @return die gespeicherten NotificationTimes
     */
    private LinkedList<String> retrieveNotificationTimesFromFile() {
        LinkedList<String> notificationTimes = new LinkedList<>();

        try (FileInputStream fis = getContext().openFileInput("notificationTimes");) {
            if (fis!=null) {
                try (ObjectInputStream ois = new ObjectInputStream(fis);){

                    notificationTimes = (LinkedList<String>) ois.readObject();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notificationTimes;
    }

    /**
     *
     *
     * @param filename the name of the file to be stred/overwritten
     * @param fileContents the list with times to be stored
     */
    public void writeFileOnInternalStorage(String filename, List<String> fileContents) {

            try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);) {

                    out.writeObject(fileContents);
                    out.flush();
                    byte[] yourBytes = bos.toByteArray();
                    fos.write(yourBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Seit Android 3.0 wohl der beste und sicherste "Ort" um persistente Daten zu speichern
    // Hier werden die ausgewählten Zeichen in ein persistentes File geschrieben
    @Override
    public void onStop() {
        super.onStop();
        List<String> notificationTimes = new LinkedList<String>();
        if (mViewModel.getTimeAsString1()!=null && mViewModel.getTimeAsString1()!="") {
            notificationTimes.add(mViewModel.getTimeAsString1());
        } else {
            notificationTimes.add(null);
        }
        if (mViewModel.getTimeAsString2()!=null && mViewModel.getTimeAsString2()!="") {
            notificationTimes.add(mViewModel.getTimeAsString2());
        }  else {
        notificationTimes.add(null);
    }
        if (mViewModel.getTimeAsString3()!=null && mViewModel.getTimeAsString3()!="") {
            notificationTimes.add(mViewModel.getTimeAsString3());
        } else {
            notificationTimes.add(null);
        }
        writeFileOnInternalStorage("notificationTimes", notificationTimes);

    }



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

                Calendar time = buildTimeForNotification(hour, minute);

                // hier wird der TextWert für die MutableLive Data im Model gesetzt, welche dann
                // auf den TimeButtons als gewählte Uhrzeit angezeigt wird
                buttonPressed.setValue("Selected Date: " + hour + ":" + minute);

                switch(timeTextToChangeAndStore) {
                    case 1:
                        mViewModel.setTimeAsString1(hour+":"+minute);
                        deleteButton1.setVisibility(View.VISIBLE);
                        visibilityStateOfDeleteTimeButton1= deleteButton1.getVisibility();
                        buildAndSetChangeOrDeleteNotification(time, 1, false);
                        break;
                    case 2:
                        mViewModel.setTimeAsString2(hour+":"+minute);
                        deleteButton2.setVisibility(View.VISIBLE);
                        visibilityStateOfDeleteTimeButton2= deleteButton2.getVisibility();
                        buildAndSetChangeOrDeleteNotification(time, 2, false);
                        break;
                    case 3:
                        mViewModel.setTimeAsString1(hour+":"+minute);
                        deleteButton3.setVisibility(View.VISIBLE);
                        visibilityStateOfDeleteTimeButton3= deleteButton3.getVisibility();
                        buildAndSetChangeOrDeleteNotification(time, 3, false);
                        break;
                }


                returnStateOfViewToTimePickerGoneAndTimeSelectable();

            }
        });
    }

    /**
     * Die Methode setzt die Funktionalität für den submitButton der in der TimePickerGroup
     * ist, die erscheint wenn eine neue Zeit hinzugefügt werden soll, indem der addNotificationTimeButton
     * gepresst wird. Die Methode "gettet" die ausgewählte Zeit und leitet Sie weiter ins Model
     */
    private void setFunctionalityOfSubmitNotificationTimeButton() {

        setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
        visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
        visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
        visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
        visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
        visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();


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

                // vor der Abfrage den Zustand der Visibility wieder herstellen, wenn der SubmitButton
                // gedrückt wird ist ja gerade der TimePicker sichtbar und alle buttons für die Zeit
                // temporär GONE gesetzt
                timeTextButton1.setVisibility(visibilityStateOfTimeButton1);
                timeTextButton2.setVisibility(visibilityStateOfTimeButton2);
                timeTextButton3.setVisibility(visibilityStateOfTimeButton3);


                // Abfragen ob der TimePicker zum changen einer Time oder neu Anlegen aufgerufen wurde
                // und ob noch einer frei ist
               boolean allTextViewsTaken = timeTextButton1.getVisibility()==View.GONE && timeTextButton2.getVisibility()==View.GONE && timeTextButton3.getVisibility()==View.GONE;
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

                            buildAndSetChangeOrDeleteNotification(time, 1, false);
                   } else if  (timeTextButton2.getVisibility() == View.GONE || (time2IsLastVisibleTimeButton)  )  {
                       mViewModel.getTimeText2().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton2.setVisibility(View.VISIBLE);
                       visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                       deleteButton2.setVisibility(View.VISIBLE);
                       visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();

                       mViewModel.setTimeAsString2(hour+":"+minute);

                       buildAndSetChangeOrDeleteNotification(time, 2, false);
                   } else if ( timeTextButton3.getVisibility() == View.GONE  || (time3IsLastVisibleTimeButton) ) {
                       mViewModel.getTimeText3().setValue("Selected Date: " + hour + ":" + minute);
                       timeTextButton3.setVisibility(View.VISIBLE);
                      visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                       deleteButton3.setVisibility(View.VISIBLE);
                       visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();

                       mViewModel.setTimeAsString3(hour+":"+minute);

                       buildAndSetChangeOrDeleteNotification(time, 3, false);
                   }
                   returnStateOfViewToTimePickerGoneAndTimeSelectable();
               }
            }
        });
    }

    /**
     *
     *
     */
         private void setFunctionalityForAddNotifiactionTimeButton() {
        addNotificationTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Wenn das flag false ist, befindet sich View im Ausgangszustand, also TimePicker gone
                // und Group mit den TimeTexts und Buttons da, dass muss getauscht werden
                if(mViewModel.isAddTimeButtonpressed()==false) {


                    // erstmal temporär die eigentlichen visibility states aller button halten,
                    // damit man den Zustand wieder herstellen kann, wenn das time picken beendet ist
                    setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                    visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                    visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                    visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
                    visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
                    visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();

                    // solange der time picker sichtbar ist, alle buttons gone setzen, damit sie optisch
                    // nicht stören
                    timeTextButton1.setVisibility(View.GONE);
                    timeTextButton2.setVisibility(View.GONE);
                    timeTextButton3.setVisibility(View.GONE);
                    deleteButton1.setVisibility(View.GONE);
                    deleteButton2.setVisibility(View.GONE);
                    deleteButton3.setVisibility(View.GONE);


                    timePickerGroup.setVisibility(View.VISIBLE);
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_close_sign);
                    // statt einem + ein Kreuz zum Beenden auf den button
                    addNotificationTimeButton.setImageDrawable(drawable);
                    mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));
                    //set flag to change functionality of button, its "logo" and description
                    mViewModel.setAddTimeButtonpressed(true);

                } else if(mViewModel.isAddTimeButtonpressed()==true) {
                    // wenn der Button erneut betätigt wird, muss der TimePicker wieder verschwinden
                    // und der Ausgangszustand mit den TimeButtons wieder hergestellt werden
                    returnStateOfViewToTimePickerGoneAndTimeSelectable();
                }
            }
        });
    }

    private void bindTextViewAndTextFromModel(TextView textViewBesidesAddNotificationButton, MutableLiveData<String> buttonText) {
        // Change listener, immer wenn sich buttonText im Model ändert, wird er auch hier geändert durch Observer Pattern
        buttonText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewBesidesAddNotificationButton.setText(s);
            }
        });
    }


    /**
     * Der TimePicker wird wieder gone gesetzt und die TimeButtons werden wieder sichtbar,
     * wenn Sie Times enthalten, oder den Schriftzug, dass keine Time ausgewählt wurde. Der
     * Button zum Times Adden erhält als Logo wieder ein +. Diese Methode stellt den Zustand wieder her
     * egal ob TimePickerGroup erschien durch das Adden einer Zeit oder TimePickerGroup2 durch das
     * changen indem direkt ein TimeButton gedrückt wurde.
     */
    private void returnStateOfViewToTimePickerGoneAndTimeSelectable() {

        timePickerGroup.setVisibility(View.GONE);
        timePickerGroup2.setVisibility(View.GONE);

        // in den Funktionen die die Funktionalität des AddTimesButtons und der einzelnen TimeButtons
        // festlegen wurden alle Visibility states gehalten und werden jetzt wieder hergestellt:
        timeTextButton1.setVisibility(visibilityStateOfTimeButton1);
        timeTextButton2.setVisibility(visibilityStateOfTimeButton2);
        timeTextButton3.setVisibility(visibilityStateOfTimeButton3);
        deleteButton1.setVisibility(visibilityStateOfDeleteTimeButton1);
        deleteButton2.setVisibility(visibilityStateOfDeleteTimeButton2);
        deleteButton3.setVisibility(visibilityStateOfDeleteTimeButton3);

        // Es kommt wieder ein + auf den Button um dem USer zu verdeutlichen, er kann wieder
        // times adden durch drücken
        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_plus_sign);
        button.setImageDrawable(drawable);

        mViewModel.getButtonText().setValue(getString(R.string.pressToAddNotificationTime));
        // flag wieder umsetzen, da button wieder "ungedrückt" ist
        mViewModel.setAddTimeButtonpressed(false);
    }

    /**
     *
     * @param hour int value selected as hour on TimePicker
     * @param min int value selected as minute on TImePicker
     * @return a Calendar Object made from the params
     */
    private Calendar buildTimeForNotification(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }


    public void buildAndSetChangeOrDeleteNotification(@Nullable Calendar time, int timeButtonNumber, boolean delete) {

        // Create an explicit intent for an Activity in your app
        // try with hardcoded link to MainActivity
        Intent intent = new Intent(this.getActivity(), ReceiverForNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // versuche hier im Extra des Intents etwas an den reciever zu übergeben, scheint nicht wirklich zu gehen
        // der request code scheint identifier für die intents zu sein, übergebe ich mit an getBroadcast
        intent.putExtra("ButtonNumber", timeButtonNumber);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), timeButtonNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


            if(delete==false) {
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                System.out.println("Alarm gesetzt");
            } else {
                alarmMgr.cancel(pendingIntent);
                System.out.println("Alarm gelöscht");
            }

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
                   mViewModel.setTimeAsString1(getContext().getString(R.string.noTimePickedText));

                   buildAndSetChangeOrDeleteNotification(null,1, true );

               } else {
                   // wenn noch weitere buttons vorhanden sind, dann verschwindet 1 einfach
                    deleteButton1.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton1 = deleteButton1.getVisibility();
                    timeTextButton1.setVisibility(View.GONE);
                    setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                   mViewModel.getTimeText1().setValue(getContext().getString(R.string.noTimePickedText));
                   mViewModel.setTimeAsString1("");

                   buildAndSetChangeOrDeleteNotification(null,1, true );
                }

            } else if (view.getId() == R.id.deleteTime2) {
                // Abfragen ob time2 der einzig verbleibende Button ist, dann nicht gone setzen,
                // sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if( (deleteButton1.getVisibility()==View.GONE) && (deleteButton3.getVisibility()==View.GONE) ) {
                    deleteButton2.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
                    mViewModel.getTimeText2().setValue(getContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString2(getContext().getString(R.string.noTimePickedText));
                    buildAndSetChangeOrDeleteNotification(null,2, true );

                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 2 einfach
                } else {
                    deleteButton2.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton2 = deleteButton2.getVisibility();
                    timeTextButton2.setVisibility(View.GONE);
                    visibilityStateOfTimeButton2 = timeTextButton2.getVisibility();
                    mViewModel.getTimeText2().setValue(getContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString2("");
                    buildAndSetChangeOrDeleteNotification(null,2, true );
                }

            } else if (view.getId() == R.id.deleteTime3) {
                // Abfragen ob time3 der einzig verbleibende Button ist, dann nicht gone setzen,
                // sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if( (deleteButton1.getVisibility()==View.GONE) && (deleteButton2.getVisibility()==View.GONE) ) {
                    deleteButton3.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();
                    mViewModel.getTimeText3().setValue(getContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString3(getContext().getString(R.string.noTimePickedText));
                    buildAndSetChangeOrDeleteNotification(null,3, true );
                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 2 einfach
                } else {
                    deleteButton3.setVisibility(View.GONE);
                    visibilityStateOfDeleteTimeButton3 = deleteButton3.getVisibility();
                    timeTextButton3.setVisibility(View.GONE);
                    visibilityStateOfTimeButton3 = timeTextButton3.getVisibility();
                    mViewModel.getTimeText3().setValue(getContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString3("");
                    buildAndSetChangeOrDeleteNotification(null,3, true );
                }

            }
        }

    }

    private void setFunctionalityForTimeButtonsToChangeTime(View view) {
        if (view.getId() == R.id.time1) {
            // Button buttonPressed = getRoot().findViewById(R.id.time1);
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText1();
            setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());


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

        Drawable drawable = getResources().getDrawable(R.drawable.ic_close_sign);
        // change the src the so to speak graphical element on the button
        addNotificationTimeButton.setImageDrawable(drawable);

        mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));

        //Setzen des flag um die Funktionalität des button, its "logo" and description
        mViewModel.setAddTimeButtonpressed(true);
    }

}