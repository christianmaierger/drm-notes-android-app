package com.example.drmapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.drmapp.ui.entry.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView entryRecyclerView;
    private boolean alarmsAllReset= false;
    private boolean isQuickEntry = false;
    private Entry entryUnderConstruction = new Entry();


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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //todo testen ob ich auch so die Alarme wieder setzen kann, wenn die App restarted wird
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
                R.id.nav_home, R.id.nav_add_Entry, R.id.nav_view_Last, R.id.manageNotifications)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // channel needs to be created right at app start
        createNotificationChannel();


        // rechts unten der weiter button ist dieser fab, floating action button
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

    // create Notification Channel that is needed since Android 8.0 to send notifications
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // for test just random strigns from res
            CharSequence name = getString(R.string.menu_home);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // Channel id for test just manually 1
            NotificationChannel channel = new NotificationChannel( "1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; one can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // erster param ist quasi das xml template mit den items und zweiter param das menu obj in das befüllt wird
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // verstehe das binding der menu items noch nicht ganz zu den destinations, docu sagt
    // if your application contains multiple activities and some of them provide the same options menu,
    // consider creating an activity that implements nothing except the onCreateOptionsMenu() and onOptionsItemSelected() methods.

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Ermöglicht Änderung des Header Titels bei Fragmentwechsel
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }



}