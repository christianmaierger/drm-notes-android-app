package com.example.drmapp;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.drmapp.ui.EntryFragment;
import com.example.drmapp.ui.EntryRecViewAdapter;
import com.example.drmapp.ui.entry.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView entryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * entries soll alle Eintraege speichern. (Eventuell sollten wir hier ueberlegen, ob etwas wie eine Hashtabelle etc. sinnvoll waere)
         * Ein Entry beinhaltet die Werte fuer alle Antworten, die der Nutzer beim Ausfuellen eines Fragebogens eingibt.
         *
         * */

     entryRecyclerView = findViewById(R.id.entryRecyclerView);
       // aus AS Studio bsp mit EInsetzungen, kommt mir sinvoll vor, da wir ja irgendwie ein Fragment brauchen wo der RecyclerView drin gezeigt wird
       /*if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            EntryFragment entryRecyclerView = new EntryFragment();
            transaction.replace(R.id.nav_host_fragment, entryRecyclerView);
            transaction.commit();
       } */


        // TODO: Funktion schreiben, mit das Entry-Element erweitert wird jeweils um Date, Time, Activity, Feeling, etc.
        // TODO: In Activity OnClickListener -> entries (position itemCount, setActivity "Eating/Drinking"


       //entryRecyclerView.setAdapter(adapter);
        //Legt die Ansicht fest! (Gibt auch Grid etc.)
      // entryRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // rechts unten der weiter button ist dieser fab, floating action button
        FloatingActionButton fab = findViewById(R.id.fwd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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





}