package com.example.drmapp.ui.ViewLast2;


import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.drmapp.database.export.ExportToDBWorker;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.viewLast.EntryListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


/*
* Die Swipe-to-delete Funktionalität und die Möglichkeit alle Einträge des RecyclerViews zu löschen werden in dieser Klasse implementiert.
* */

public class EntryFragment extends Fragment {
    EntryListViewModel entryListViewModel;
    RecyclerView recyclerView;
    EntryRecViewAdapter entryRecViewAdapter= new EntryRecViewAdapter();
    private List<Entry> entries = new ArrayList<>();
    Entry deletedEntry = null; // Varibale in der das zu loeschende Element erst einmal aufbewahrt wird bis es endgueltig geloscht wird
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        // Die Swipe Gesten die innerhalb des RecyclerViews möglich sind, werden hier implementiert.
        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            // Hintergrundfarben und Icons fuer die Swipes
            new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        // Wird nicht verwendet, ist gedacht um die Items neu anzuordnen
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        // Hier wird die Funktionalitaet fuer die Swipes implementiert
        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

            // Die Position des Adapters finden und das Element von dort hier speichern
            final int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
            Entry entry = entryRecViewAdapter.getItem(bindingAdapterPosition);


            switch (direction){
                // SWIPE von rechts nach links
                case ItemTouchHelper.LEFT:

                    // Zwischenspeicher, falls Loeschen rueckgaengig gemacht werden soll
                    deletedEntry = entry;

                    // Eintrag aus der Datenbank loeschen
                    entryListViewModel.getEntryDao().deleteEntry(entry);

                    // Loeschen rueckgaengig machen
                    Snackbar.make(recyclerView, "Deleted!", Snackbar.LENGTH_LONG).
                            setBackgroundTint(getResources().getColor(R.color.lightPurpel))
                            .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // geloeschtes Element wieder in die DB einfuegen
                            entryListViewModel.getEntryDao().insertEntry(deletedEntry);
                        }
                    }).show();


                    break;

                    /* Eintrag aendern koennte hier implementiert werden
                    // Swipe von links nach rechts
                case ItemTouchHelper.RIGHT:

                    break;*/
            }

        }
    };






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        recyclerView=view.findViewById(R.id.entryRecyclerView);
        // for testing static method called to get some data, but it is a good question where to hold entries in the end


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(entryRecViewAdapter);

         // Verbindet den itemTouchHelper mit dem recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        entryListViewModel = new EntryListViewModel(getActivity().getApplication());



       // WOuld be also possible to show only entries of today or other date
        //entryListViewModel.setEntryListAsLiveData(entryListViewModel.getEntryDao().findByDate("05/05/21","07:00"));

        entryListViewModel.getEntryListAsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                entryRecViewAdapter.setEntries(entries);
            }
        });

        //Entfernen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

        FloatingActionButton delete =  view.findViewById(R.id.deleteEntriesButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
                Date today = new Date();
                Calendar calendar = Calendar.getInstance();
                String text = "";
                   calendar.setTime(today);
                    calendar.add(Calendar.DATE, -1);
                    Date yesterday = calendar.getTime();
                  //text = formatter.format(yesterday);
                long tmp = yesterday.getTime();


                // Loeschen älterer Einträge muss vom Nutzer  bestätigt werden
                Snackbar.make(recyclerView, "All Entries older than Today will be deleted!",
                        Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.lightPurpel))
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                entryListViewModel.getEntryDao().deleteEntriesOlderThanDate(tmp);
                            }
                        }).show();

            }
        });

        FloatingActionButton export =  view.findViewById(R.id.exportEntriesButton);

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkRequest exportWorkRequest =
                        new OneTimeWorkRequest.Builder(ExportToDBWorker.class)
                                .build();

                // Das WorkRequest wird zur Bearbeitung an den WorkManager übergeben
                WorkManager
                        .getInstance(getContext())
                        .enqueue(exportWorkRequest).getResult();

            }
        });

        return view;

    }
}