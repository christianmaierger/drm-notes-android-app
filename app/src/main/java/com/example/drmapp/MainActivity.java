package com.example.drmapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.drmapp.NotificationBackEnd.AlarmWorker;
import com.example.drmapp.ui.entry.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean alarmsAllReset= false;
    private boolean isQuickEntry = false;
    private Entry entryUnderConstruction;

    public Entry getEntryUnderConstruction() { return entryUnderConstruction; }

    public void setEntryUnderConstruction(Entry entryUnderConstruction) { this.entryUnderConstruction = entryUnderConstruction; }


    public boolean isAlarmsAllReset() {
        return alarmsAllReset;
    }

    public void setAlarmsAllReset(boolean alarmsAllReset) {
        this.alarmsAllReset = alarmsAllReset;
    }

    public void setQuickEntryTrue(boolean val) {
        this.isQuickEntry = val;
    }

    public boolean getIsQuickEntry() {
        return this.isQuickEntry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Testen ob Alarme/Notifications schon erneut gesetzt wurden per Receiver, anonsten hier setzen als
        // Fallback beim AppStart
    if (!alarmsAllReset) {
        // Work Request erstellen, dass dann async bearbeitet werden kann vom WorkManager als eigener Thread
        WorkRequest alarmWorkRequest =
                new OneTimeWorkRequest.Builder(AlarmWorker.class)
                        .build();
        // Das WorkRequest wird zur Bearbeitung an den WorkManager übergeben
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(alarmWorkRequest);
        alarmsAllReset=true;
    }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // wichtig damit alle hier eingetragenen fragments/views genau die gleiche bar haben mit nav button
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.action_addEntrySplitFragment_to_nav_add_Entry, R.id.nav_view_Last, R.id.manageNotifications)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Dieser Channel wird bei höheren Apis zum senden ovn Notifications benötigt und soll
        // möglichst direkt beim Start der App angelegt werden
        createNotificationChannel();

        FloatingActionButton fwd = findViewById(R.id.fwd);
        FloatingActionButton home = findViewById(R.id.backHome);


        fwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_success);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_home);
            }
        });

    }

    // Erzeugen eines Notification Channel, der seit Android 8.0/Api 26 benötigt wird
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.menu_home);
            String description = getString(R.string.app_name);
            // High Importance um Senden der Notification sicher zu stellen
            int importance = NotificationManager.IMPORTANCE_HIGH;
            // Channel id könnte man arbiträr setzen, 1 da es nur einen gibt
            NotificationChannel channel = new NotificationChannel( "1", name, importance);
            channel.setDescription(description);
            // Der Channel wird beim System registriert und kann danach nicht mehr geändert werden
            // Er wird beim senden von NOtifications immer benötigt
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Erster param ist quasi das xml template mit den items und zweiter param das menu obj in das befüllt wird
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Ermöglicht Änderung des Header Titels bei Fragmentwechsel
    public void setActionBarTitle(String title) {
      try {
          getSupportActionBar().setTitle(title);
      } catch (NullPointerException exception) {
          exception.printStackTrace();
      }

    }



}