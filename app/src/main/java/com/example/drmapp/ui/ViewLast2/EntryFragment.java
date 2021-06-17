package com.example.drmapp.ui.ViewLast2;



import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.viewLast.EntryListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class EntryFragment extends Fragment {
    EntryListViewModel entryListViewModel;
    RecyclerView recyclerView;
    EntryRecViewAdapter entryRecViewAdapter= new EntryRecViewAdapter();
    private List<Entry> entries = new ArrayList<>();

    Entry deletedEntry = null; // Varibale in der das zu loeschende Element erst einmal aufbewahrt wird bis es endgueltig geloscht wird
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(MainActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.lightPurpel))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)

                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.lightGreen))
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
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

            final int position = viewHolder.getAdapterPosition();

            switch (direction){
                // SWIPE von rechts nach links
                case ItemTouchHelper.LEFT:
                    // TODO: Eintrag in DB finden, Eintrag in DB loeschen
                    entries.remove(position);
                    entryRecViewAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, deletedEntry.getActivity().toString() + ", Deleted!", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            entries.add(position, deletedEntry);
                            entryRecViewAdapter.notifyItemInserted(position);

                        }
                    }).show();


                    break;
                    // Swipe von links nach rechts
                case ItemTouchHelper.RIGHT:
                    // Moeglichkeit zu aendern?
                    break;
            }

        }
    };






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        recyclerView=view.findViewById(R.id.entryRecyclerView);
        // for testing static method called to get some data, but it is a good question where to hold entries in the end

        //EntryRecViewAdapter entryRecViewAdapter= new EntryRecViewAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(entryRecViewAdapter);

         // Verbindet den itemTouchHelper mit dem recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        //  entryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
        entryListViewModel = new EntryListViewModel(getActivity().getApplication());

      //  entryListViewModel.getEntryDao().insertEntry(new Entry(true, "05/05/21", "07:00", "Eating/drinking","Dinner is very nice"));
      //  entryListViewModel.getEntryDao().insertEntry(new Entry(false, "05/05/21", "08:00", "Working/studying", "normal", 2, 2, 2,*/ "Laptop is loud"));


        entryListViewModel.getEntryDao().insertAll(new Entry(false, "05/05/21", "07:00", "Eating/drinking", 0xFE0F, 1, 1,1, "Pancakes are good"), new Entry(false, "05/05/21", "08:00", "Working/studying", 0x1F630, 2, 2, 2, "Laptop is loud"));
        //Entry.createEntryList(getContext());

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

        return view;

    }
}