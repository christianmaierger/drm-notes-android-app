package com.example.drmapp.ui.manageNotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.R;
import com.example.drmapp.model.StoreSimpleDataHelper;
import com.example.drmapp.notificationBackend.ReceiverForNotifications;
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

    private FloatingActionButton deleteButton1;
    private FloatingActionButton deleteButton2;
    private FloatingActionButton deleteButton3;

    // Zweite TimePicker Group um eine einfache Möglichkeit zu haben einen TimePicker für das neu
    // Erstellen von times zu haben und einen für das Changen, wegen unterschiedlicher Funktionalität
    private Group timePickerGroup;
    private Group timePickerGroup2;
    // Daher auch zwei Submitbuttons und TimePicker, je einen pro Group
    private Button submitTime;
    private Button submitTime2;
    private TimePicker picker;
    private TimePicker picker2;

    private FloatingActionButton addNotificationTimeButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);

        //Entfernen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

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


        checkDisplaySizeAndSetTimePickerToSpinnerORClock();


        // Hier werden der TextView neben dem add button für times und
        // alle drei TextViews für Notifications an ihre Daten/Texte im Model gebunden
        bindTextViewAndTextFromModel(timeTextButton1, mViewModel.getTimeText1());
        bindTextViewAndTextFromModel(timeTextButton2, mViewModel.getTimeText2());
        bindTextViewAndTextFromModel(timeTextButton3, mViewModel.getTimeText3());


        // hier werden die Visibility States der einzelnen Buttons das erste mal zwischen gespeichert
        mViewModel.setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
        mViewModel.setVisibilityStateOfTimeButton2(timeTextButton2.getVisibility());
        mViewModel.setVisibilityStateOfTimeButton3(timeTextButton3.getVisibility());
        mViewModel.setVisibilityStateOfDeleteTimeButton1(deleteButton1.getVisibility());
        mViewModel.setVisibilityStateOfDeleteTimeButton2(deleteButton2.getVisibility());
        mViewModel.setVisibilityStateOfDeleteTimeButton3(deleteButton3.getVisibility());

        setFunctionalityForAddNotifiactionTimeButton();

        // für den submit button der zu der timepicker group gehört, die erscheint, wenn man
        // einen neuen Eintrag added per addNotificationTime button
        setFunctionalityOfSubmitNotificationTimeButton();

        // in die liste werden die persistent gespeicherten NotificationTimes geladen
        LinkedList<String> notificationTimes = StoreSimpleDataHelper.retrieveNotificationTimesFromFile(getActivity());
        // die NotificationTimes werden in das ViewModel geladen und die passenden
        // Visibility states für TimeButtons und DeleteTime Buttons hergestellt
        restoreStateOfViewWithSavedNotificationTimes(notificationTimes);

        return root;
    }

    /**
     * Diese Methode fragt die Breite des Displays ab und erzeugt bei Displays mit weniger als
     * 770 breite den TimePicker als Spinner, da dieser angenehmer für den User zu bedienen ist
     * als eine Uhr, bei der die Elemente dann extrem klein geraten.
     *
     */
    private void checkDisplaySizeAndSetTimePickerToSpinnerORClock() {
        int width;

        // Da je nach HandyModel es zu problemen beim Erkennen des Displays und dessen Breite kommen
        // kann werden hier verschiedene Wege bereitgestellt, die letzte Möglichkeit mit
        // getDefaultDisplay() ist eigentlich depricated funktiioniert aber sehr gut als FallBack
      try {
          WindowMetrics display = requireActivity().getWindowManager().getCurrentWindowMetrics();
          width = display.getBounds().width();
      } catch (Exception | NoSuchMethodError e) {
          try {
              Display display = getActivity().getDisplay();
              Point size = new Point();
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                  display.getRealSize(size);
              } else {
                  display.getSize(size);
              }
              width = size.x;
          } catch (NoSuchMethodError err) {
              Display display = getActivity().getWindowManager().getDefaultDisplay();
              Point size = new Point();
              display.getSize(size);
              width = size.x;
          }
      }

        // Wenn der Bildschirm unter eine gewisse Breite hat, dann soll ein Spinner, statt eine Uhr
        // als TimePicker angezeigt werden, da die Zahlen zu klein werden um komfortabel ausgewählt zu werden
        if(width<770) {
            // Die beiden Groups für die TimePicker die erscheinen, wenn man times added oder wenn man times changed
            // in diesem Fall is der Picker ein Spinner
            timePickerGroup = root.findViewById(R.id.timePickerGroupSpinner);
            timePickerGroup2 = root.findViewById(R.id.timePickerGroup2Spinner);
            // Beide time picker in 24 h Modus setzen, was leider nicht im XML direkt geht
            picker = root.findViewById(R.id.simpleTimePickerSpinner);
            picker.setIs24HourView(true);
            picker2 = root.findViewById(R.id.simpleTimePicker2Spinner);
            picker2.setIs24HourView(true);
            submitTime = root.findViewById(R.id.getTimeSpinner);
            submitTime2 = root.findViewById(R.id.getTime2Spinner);
        } else {
            // Die beiden Groups für die TimePicker die erscheinen, wenn man times added oder wenn man times changed
            // in diesem Fall ist der TimePicker eine Uhr
            timePickerGroup = root.findViewById(R.id.timePickerGroup);
            timePickerGroup2 = root.findViewById(R.id.timePickerGroup2);
            picker = root.findViewById(R.id.simpleTimePicker);
            picker.setIs24HourView(true);
            picker2 = root.findViewById(R.id.simpleTimePicker2);
            picker2.setIs24HourView(true);
            submitTime = root.findViewById(R.id.getTime);
            submitTime2 = root.findViewById(R.id.getTime2);
        }
    }

    public View getRoot() {
        return root;
    }


    /**
     * Stellt die Sichtbarkeit von TimeButtons und zugehörigen DeleteButtons her, wenn für den Button
     * eine Zeit gespeichert wurde. TimeButton 1 muss dabei gesondert behandelt werden, da dieser
     * initial sichtbar ist mit Text, dass keine Zeit ausgewählt wurde.
     *
     * @param notificationTimes liste mit Notification Times die vom SmartPhone Speicher ausgelesen wurden
     */
    private void restoreStateOfViewWithSavedNotificationTimes(LinkedList<String> notificationTimes) {
           // Wenn mehr als ein Button eine Uhrzeit enthalten, dann soll ja kein Button anzeigen
        // dass noch keine Zeit ausgewählt wurde, das wird hier festgehalten
            int counterForButtonsFilledWithTimes = 0;
        for ( String timeInList : notificationTimes) {
            if(timeInList!=null) {
                if (!timeInList.equals("") || !timeInList.equals(R.string.noTimePickedText)) {
                    counterForButtonsFilledWithTimes++;
                }
            }
        }

       if (notificationTimes.size()==0) {
           // Initial, bevor Werte gespeichert wurden is Button 1 als einziger sichtbar mit folgendem Text
           // das noch keine Zeit ausgewählt wurde
           mViewModel.getTimeText1().setValue(getString(R.string.noTimePickedText));
           mViewModel.setTimeAsString1(getString(R.string.noTimePickedText));
           mViewModel.setVisibilityStateOfTimeButton1(View.VISIBLE);
           timeTextButton1.setVisibility(View.VISIBLE);
           deleteButton1.setVisibility(View.GONE);
           mViewModel.setVisibilityStateOfDeleteTimeButton1(View.GONE);

       }
           // initial sind keine Times gespeichert, wenn App zum ersten mal benutzt wird, dann kann
           // man auch keine Abfragen, da Liste leer ist, würde ja Null Pointer werfen
           if (notificationTimes.size() > 0) {
               // wenn hier null gepeichert ist, steht weder eine Zeit noch die Botschaft, dass keine Zeit gewählt wurde
               // in Button1, also muss er invisible sein, Sondervorgehen bei Button1, da dieser ja standartmäsig
               // mit der Botschaft keine Zeit gepickt, sichtbar ist
               if (notificationTimes.get(0) == null) {
                   timeTextButton1.setVisibility(View.GONE);
                   mViewModel.setVisibilityStateOfTimeButton1(View.GONE);
                   deleteButton1.setVisibility(View.GONE);
                   mViewModel.setVisibilityStateOfDeleteTimeButton1(View.GONE);
               }

               // wenn der Listenplatz 0 weder null ist, ein leerer String oder der NoNotification Text,
               // dann steht eine Zeit darin, die displayed werden muss
               if (notificationTimes.get(0) != null && !notificationTimes.get(0).equals("") && !notificationTimes.get(0).equals(getString(R.string.noTimePickedText))) {
                   mViewModel.getTimeText1().setValue("Selected Date: " + notificationTimes.get(0));
                   mViewModel.setTimeAsString1(notificationTimes.get(0));
                   timeTextButton1.setVisibility(View.VISIBLE);
                   mViewModel.setVisibilityStateOfTimeButton1(View.VISIBLE);
                   deleteButton1.setVisibility(View.VISIBLE);
                   mViewModel.setVisibilityStateOfDeleteTimeButton1(View.VISIBLE);
                   // Wenn darin der Text steht, dass keine Time gepickt wurde, dann muss nur der TimeButton
                   // ohne DeleteButton sichtbar werden, keine Zeit kann man ja nicht löschen
               } else if (notificationTimes.get(0) != null && notificationTimes.get(0).equals(getString(R.string.noTimePickedText)) && counterForButtonsFilledWithTimes<2) {
                   deleteButton1.setVisibility(View.GONE);
                   mViewModel.setVisibilityStateOfDeleteTimeButton1(View.GONE);
                   timeTextButton1.setVisibility(View.VISIBLE);
                   mViewModel.setVisibilityStateOfTimeButton1(View.VISIBLE);
                   mViewModel.getTimeText1().setValue(getString(R.string.noTimePickedText));
                   mViewModel.setTimeAsString1(getString(R.string.noTimePickedText));
               }
           }
           if (notificationTimes.size() > 1 && notificationTimes.get(1) != null && !notificationTimes.get(1).equals("") && !notificationTimes.get(1).equals(getString(R.string.noTimePickedText))) {
               mViewModel.getTimeText2().setValue("Selected Date: " + notificationTimes.get(1));
               mViewModel.setTimeAsString2(notificationTimes.get(1));
               timeTextButton2.setVisibility(View.VISIBLE);
               mViewModel.setVisibilityStateOfTimeButton2(View.VISIBLE);
               deleteButton2.setVisibility(View.VISIBLE);
               mViewModel.setVisibilityStateOfDeleteTimeButton2(View.VISIBLE);
           } else if (notificationTimes.size() > 1 && notificationTimes.get(1) != null && notificationTimes.get(1).equals(getString(R.string.noTimePickedText)) && counterForButtonsFilledWithTimes<2) {
               deleteButton2.setVisibility(View.GONE);
               mViewModel.setVisibilityStateOfDeleteTimeButton2(View.GONE);
               timeTextButton2.setVisibility(View.VISIBLE);
               mViewModel.setVisibilityStateOfTimeButton2(View.VISIBLE);
               mViewModel.getTimeText2().setValue(getString(R.string.noTimePickedText));
               mViewModel.setTimeAsString2(getString(R.string.noTimePickedText));

           }
           if (notificationTimes.size() > 2 && notificationTimes.get(2) != null && !notificationTimes.get(2).equals("") && !notificationTimes.get(2).equals(getString(R.string.noTimePickedText))) {
               mViewModel.getTimeText3().setValue("Selected Date: " + notificationTimes.get(2));
               mViewModel.setTimeAsString3(notificationTimes.get(2));
               timeTextButton3.setVisibility(View.VISIBLE);
               mViewModel.setVisibilityStateOfTimeButton3(View.VISIBLE);
               deleteButton3.setVisibility(View.VISIBLE);
               mViewModel.setVisibilityStateOfDeleteTimeButton3(View.VISIBLE);
           } else if (notificationTimes.size() > 2 && notificationTimes.get(2) != null && notificationTimes.get(2).equals(getString(R.string.noTimePickedText)) && counterForButtonsFilledWithTimes<2) {
               deleteButton3.setVisibility(View.GONE);
               mViewModel.setVisibilityStateOfDeleteTimeButton3(View.GONE);
               timeTextButton3.setVisibility(View.VISIBLE);
               mViewModel.setVisibilityStateOfTimeButton3(View.VISIBLE);
               mViewModel.getTimeText3().setValue(getString(R.string.noTimePickedText));
               mViewModel.setTimeAsString3(getString(R.string.noTimePickedText));
           }

    }


    // Seit Android 3.0 wohl der beste und sicherste "Ort" um einfache Daten persistent zu speichern
    // Hier werden die ausgewählten Zeiten als Liste in ein File geschrieben
    @Override
    public void onStop() {
        super.onStop();
        List<String> notificationTimes = new LinkedList<>();
        // Hier werden die gespeicherten Zeiten an die ListenPlätze geschrieben
        // zb Button1 an Platz 0, wenn keine Zeit gepeichert ist, wird an die Stelle null geschrieben
        if (mViewModel.getTimeAsString1()!=null && !mViewModel.getTimeAsString1().equals("")) {
            notificationTimes.add(mViewModel.getTimeAsString1());
        } else {
            notificationTimes.add(null);
        }
        if (mViewModel.getTimeAsString2()!=null && !mViewModel.getTimeAsString2().equals("")) {
            notificationTimes.add(mViewModel.getTimeAsString2());
        }  else {
            notificationTimes.add(null);
        }
        if (mViewModel.getTimeAsString3()!=null && !mViewModel.getTimeAsString3().equals("")) {
            notificationTimes.add(mViewModel.getTimeAsString3());
        } else {
            notificationTimes.add(null);
        }
        StoreSimpleDataHelper.writeFileOnInternalStorage("notificationTimes", notificationTimes, getActivity());

    }


    /**
     * Die Methode setzt die Funktionalität für den submitButton der in der TimePickerGroup
     * ist, die erscheint wenn eine Zeit geändert werden soll, indem der jeweilige TimeButton
     * gepresst wird. Die Methode gettet die ausgewählte Zeit und leitet Sie weiter ins Model
     *
     * @param buttonPressed die zugehörigen Daten im Model zu dem TimeButton dessen Zeit geändert werden soll
     */
    private void setFunctionalityOfSubmitNotificationButton2(MutableLiveData<String> buttonPressed, int timeTextToChangeAndStore) {

        submitTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour;
                int minute;
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

                String hourAndMinute = formatSingleDigitNumbers(String.valueOf(hour)
                        , String.valueOf(minute));

                // hier wird der TextWert für die MutableLive Data im Model gesetzt, welche dann
                // auf den TimeButtons als gewählte Uhrzeit angezeigt wird
                buttonPressed.setValue("Selected Date: " + hourAndMinute);

                // durch den switch wird der passende Button mit Text befüllt, dessen Delete Button
                // sichtbar und der passende Alarm durch übergabe der ButtonNumber zum identifizieren
                // überschrieben
                switch(timeTextToChangeAndStore) {
                    case 1:
                        mViewModel.setTimeAsString1(hourAndMinute);
                        deleteButton1.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfDeleteTimeButton1(deleteButton1.getVisibility());
                        buildAndSetChangeOrDeleteNotification(time, 1, false);
                        break;
                    case 2:
                        mViewModel.setTimeAsString2(hourAndMinute);
                        deleteButton2.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfDeleteTimeButton2(deleteButton2.getVisibility());
                        buildAndSetChangeOrDeleteNotification(time, 2, false);
                        break;
                    case 3:
                        mViewModel.setTimeAsString1(hourAndMinute);
                        deleteButton3.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfDeleteTimeButton3(deleteButton3.getVisibility());
                        buildAndSetChangeOrDeleteNotification(time, 3, false);
                        break;
                }
                // am Ende muss für den nutzer wieder der optische Zustand hergestellt werden,
                // bevor die Änderung eingetreten ist und alle Buttons temporär GONE gesetzt wurden
                returnStateOfViewToTimePickerGoneAndTimeSelectable();
            }
        });
    }

    /**
     *
     * Da Zahlen aus dem TimePicker keine fürhenden Nullen enthalten wird dies hier angefügt,
     * um dem Nutzer einen vertrauten Anblick zu bieten, so wird aus 5:5 -> 05:05
     *
     * @param hour Stunde des TimePickers in String Form
     * @param minute Minuten des TimePickers in String Form
     * @return einen neuen formatierten String mit Delimeter :
     */
    private String formatSingleDigitNumbers(String hour, String minute) {

            if(hour.length()==1) {
                hour="0"+hour;
            }
        if(minute.length()==1) {
            minute="0"+minute;
        }
        return hour+":"+minute;
    }

    /**
     * Die Methode setzt die Funktionalität für den submitButton der in der TimePickerGroup
     * ist, die erscheint wenn eine neue Zeit hinzugefügt werden soll, indem der addNotificationTimeButton
     * gepresst wird. Die Methode "gettet" die ausgewählte Zeit und leitet Sie weiter ins Model
     */
    private void  setFunctionalityOfSubmitNotificationTimeButton() {

        // here listener is set to Submit button below time picker and gets the selected time to store it in ViewModel

        submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour;
                int minute;
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
                // den BroadcastReceicer aufruft, damit der eine Notification feuert und einen neuen
                // Alarm für die gleiche Uhrzeit am nächsten Tag setzt
                Calendar time = buildTimeForNotification(hour, minute);

                String hourAndMinute = formatSingleDigitNumbers(String.valueOf(hour)
                        , String.valueOf(minute));

                // vor der Abfrage den Zustand der Visibility wieder herstellen, wenn der SubmitButton
                // gedrückt wird ist ja gerade der TimePicker sichtbar und alle buttons für die Zeit
                // temporär GONE gesetzt
                timeTextButton1.setVisibility(mViewModel.getVisibilityStateOfTimeButton1());
                timeTextButton2.setVisibility(mViewModel.getVisibilityStateOfTimeButton2());
                timeTextButton3.setVisibility(mViewModel.getVisibilityStateOfTimeButton3());



                if(mViewModel.isAddTimeButtonpressed()) {

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


                    if (  (timeTextButton1.getVisibility() == View.GONE || (time1IsLastVisibleTimeButton) ) && !time2IsLastVisibleTimeButton && !time3IsLastVisibleTimeButton)
                    {
                        mViewModel.getTimeText1().setValue("Selected Date: " + hourAndMinute);
                        timeTextButton1.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                        deleteButton1.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfDeleteTimeButton1(deleteButton1.getVisibility());

                        mViewModel.setTimeAsString1(hourAndMinute);

                        buildAndSetChangeOrDeleteNotification(time, 1, false);
                    } else if  ( (timeTextButton2.getVisibility() == View.GONE || (time2IsLastVisibleTimeButton)) && !time1IsLastVisibleTimeButton && !time3IsLastVisibleTimeButton  )  {
                        mViewModel.getTimeText2().setValue("Selected Date: " + hourAndMinute);
                        timeTextButton2.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfTimeButton2(timeTextButton2.getVisibility());
                        deleteButton2.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfDeleteTimeButton2(deleteButton2.getVisibility());

                        mViewModel.setTimeAsString2(hourAndMinute);

                        buildAndSetChangeOrDeleteNotification(time, 2, false);
                    } else if ( (timeTextButton3.getVisibility() == View.GONE  || (time3IsLastVisibleTimeButton) ) && !time1IsLastVisibleTimeButton && !time2IsLastVisibleTimeButton) {
                        mViewModel.getTimeText3().setValue("Selected Date: " + hourAndMinute);
                        timeTextButton3.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfTimeButton3(timeTextButton3.getVisibility());
                        deleteButton3.setVisibility(View.VISIBLE);
                        mViewModel.setVisibilityStateOfDeleteTimeButton3(deleteButton3.getVisibility());

                        mViewModel.setTimeAsString3(hourAndMinute);

                        buildAndSetChangeOrDeleteNotification(time, 3, false);
                    }
                    returnStateOfViewToTimePickerGoneAndTimeSelectable();
                }
            }
        });
    }

    private void setFunctionalityForTimeButtonsToChangeTime(View view) {
        if (view.getId() == R.id.time1) {
            // Button buttonPressed = getRoot().findViewById(R.id.time1);
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText1();
            mViewModel.setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());


            // Funktionylität für den submit button der time picker grouß die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed, 1);

        } else if (view.getId() == R.id.time2) {
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText2();
            mViewModel.setVisibilityStateOfTimeButton2(timeTextButton2.getVisibility());
            deleteButton2.setVisibility(View.VISIBLE);
            mViewModel.setVisibilityStateOfDeleteTimeButton2(deleteButton2.getVisibility());

            // Funktionylität für den submit button der time picker group die visible wird, wenn
            // man einen timeTextButton pressed um nur dessen Notification Time zu changen
            setFunctionalityOfSubmitNotificationButton2(buttonPressed, 2);

        } else if (view.getId() == R.id.time3) {
            MutableLiveData<String> buttonPressed = mViewModel.getTimeText3();
            mViewModel.setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
            deleteButton3.setVisibility(View.VISIBLE);
            mViewModel.setVisibilityStateOfDeleteTimeButton3(deleteButton3.getVisibility());

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

        //mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));

        //Setzen des flag um die Funktionalität des button, its "logo" and description
        mViewModel.setAddTimeButtonpressed(true);
    }

    /**
     *
     *
     */
    private void setFunctionalityForAddNotifiactionTimeButton() {
        addNotificationTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Abfragen ob noch ein TimeButton frei ist, ansonten kann keine weitere Zeit ausgewählt werden
                // also den Wert dem Feld des ViewModels zuweisen, das hält, ob noch Zeiten "frei" sind
                mViewModel.setAllTimesSelected(timeTextButton1.getVisibility()==View.VISIBLE && timeTextButton2.getVisibility()==View.VISIBLE && timeTextButton3.getVisibility()==View.VISIBLE);

                // wenn alle Buttons mit Zeiten für Notifications belegt sind, dann wird keine weitere Funktionalität
                // geboten, sondern nur ein Text displayed, dass keine Zeiten mehr gewählt werden können
                if (mViewModel.isAllTimesSelected()) {
                    Toast.makeText(getContext(), "Sry, more than 3 NotificationTimes can not be picked", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Wenn das flag false ist, befindet sich View im Ausgangszustand, also TimePicker gone
                // und Group mit den TimeTexts und Buttons da, dass muss getauscht werden
                if(!mViewModel.isAddTimeButtonpressed()) {


                    // erstmal temporär die eigentlichen visibility states aller button halten,
                    // damit man den Zustand wieder herstellen kann, wenn das time picken beendet ist
                    mViewModel.setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                    mViewModel.setVisibilityStateOfTimeButton2(timeTextButton2.getVisibility());
                    mViewModel.setVisibilityStateOfTimeButton3(timeTextButton3.getVisibility());
                    mViewModel.setVisibilityStateOfDeleteTimeButton1(deleteButton1.getVisibility());
                    mViewModel.setVisibilityStateOfDeleteTimeButton2(deleteButton2.getVisibility());
                    mViewModel.setVisibilityStateOfDeleteTimeButton3(deleteButton3.getVisibility());

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
                    //mViewModel.getButtonText().setValue(getString(R.string.cancleTimePicking));
                    //set flag to change functionality of button, its "logo" and description
                    mViewModel.setAddTimeButtonpressed(true);

                } else if(mViewModel.isAddTimeButtonpressed()) {
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
        timeTextButton1.setVisibility(mViewModel.getVisibilityStateOfTimeButton1());
        timeTextButton2.setVisibility(mViewModel.getVisibilityStateOfTimeButton2());
        timeTextButton3.setVisibility(mViewModel.getVisibilityStateOfTimeButton3());
        deleteButton1.setVisibility(mViewModel.getVisibilityStateOfDeleteTimeButton1());
        deleteButton2.setVisibility(mViewModel.getVisibilityStateOfDeleteTimeButton2());
        deleteButton3.setVisibility(mViewModel.getVisibilityStateOfDeleteTimeButton3());

        // Es kommt wieder ein + auf den Button um dem USer zu verdeutlichen, er kann wieder
        // times adden durch drücken
        FloatingActionButton button = root.findViewById(R.id.addTimePicker);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_plus_sign);
        button.setImageDrawable(drawable);

        //mViewModel.getButtonText().setValue(getString(R.string.pressToAddNotificationTime));
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

        Intent intent = new Intent(this.getActivity(), ReceiverForNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // der request code scheint identifier für die intents zu sein, übergebe ich mit an getBroadcast
        intent.putExtra("ButtonNumber", timeButtonNumber);
        long currentTime = System.currentTimeMillis();

        if(delete==false) {
            if(currentTime>time.getTimeInMillis()) {
                time.add(Calendar.DATE, 1);
            }
            // Bei neuem Alarm oder Änderung Zeit mitgeben, damit darauf aufbauen ein neuer Alarm in 24 h
            // gesetzt werden kann
            intent.putExtra("time", time.getTimeInMillis());
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), timeButtonNumber,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        if(delete==false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(time.getTimeInMillis(),
                        pendingIntent);
                alarmMgr.setAlarmClock(ac, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
            } else {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
            }
            StoreSimpleDataHelper.saveNotification(getActivity(),timeButtonNumber,time.getTimeInMillis());
        } else {
            alarmMgr.cancel(pendingIntent);
            // Gespeicherte Zeit wird daruch gelöscht, dass statt time als long 0L übergeben wird
            StoreSimpleDataHelper.saveNotification(getActivity(),timeButtonNumber,0L);
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
        if ((view.getId() == R.id.time1) || (view.getId() == R.id.time2) || (view.getId() == R.id.time3)) {
            setFunctionalityForTimeButtonsToChangeTime(view);
        } else if ((view.getId() == R.id.deleteTime1) || (view.getId() == R.id.deleteTime2) || (view.getId() == R.id.deleteTime3)) {

            // wenn Zeit 1 löschen gecklickt wurde, den Löschen Button entfernen
            if (view.getId() == R.id.deleteTime1) {
                deleteButton1.setVisibility(View.GONE);
                mViewModel.setVisibilityStateOfDeleteTimeButton1(deleteButton1.getVisibility());
                // Abfragen ob time1 der einzig verbleibende Button ist, dann nicht gone setzen,
                //sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if ((deleteButton2.getVisibility() == View.GONE) && (deleteButton3.getVisibility() == View.GONE)) {

                    mViewModel.getTimeText1().setValue(requireContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString1(getContext().getString(R.string.noTimePickedText));

                } else {
                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 1 einfach
                    timeTextButton1.setVisibility(View.GONE);
                    mViewModel.setVisibilityStateOfTimeButton1(timeTextButton1.getVisibility());
                    mViewModel.getTimeText1().setValue(requireContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString1("");

                }
                buildAndSetChangeOrDeleteNotification(null, 1, true);

            } else if (view.getId() == R.id.deleteTime2) {
                deleteButton2.setVisibility(View.GONE);
                mViewModel.setVisibilityStateOfDeleteTimeButton2(deleteButton2.getVisibility());
                // Abfragen ob time2 der einzig verbleibende Button ist, dann nicht gone setzen,
                // sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if ((deleteButton1.getVisibility() == View.GONE) && (deleteButton3.getVisibility() == View.GONE)) {
                    mViewModel.getTimeText2().setValue(requireContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString2(getContext().getString(R.string.noTimePickedText));

                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 2 einfach
                } else {
                    timeTextButton2.setVisibility(View.GONE);
                    mViewModel.setVisibilityStateOfTimeButton2(timeTextButton2.getVisibility());
                    mViewModel.getTimeText2().setValue(requireContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString2("");
                }
                buildAndSetChangeOrDeleteNotification(null, 2, true);

            } else if (view.getId() == R.id.deleteTime3) {
                deleteButton3.setVisibility(View.GONE);
                mViewModel.setVisibilityStateOfDeleteTimeButton3(deleteButton3.getVisibility());
                // Abfragen ob time3 der einzig verbleibende Button ist, dann nicht gone setzen,
                // sondern nur wieder Text tauschen dazu, dass keine Time ausgewählt ist
                if ((deleteButton1.getVisibility() == View.GONE) && (deleteButton2.getVisibility() == View.GONE)) {

                    mViewModel.getTimeText3().setValue(requireContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString3(requireContext().getString(R.string.noTimePickedText));
                    // wenn noch weitere buttons vorhanden sind, dann verschwindet 3 einfach
                } else {
                    timeTextButton3.setVisibility(View.GONE);
                    mViewModel.setVisibilityStateOfTimeButton3(timeTextButton3.getVisibility());
                    mViewModel.getTimeText3().setValue(requireContext().getString(R.string.noTimePickedText));
                    mViewModel.setTimeAsString3("");
                }
                buildAndSetChangeOrDeleteNotification(null, 3, true);

            }
        }

    }
}