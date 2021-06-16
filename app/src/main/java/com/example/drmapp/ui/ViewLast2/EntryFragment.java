package com.example.drmapp.ui.ViewLast2;



import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.viewLast.EntryListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class EntryFragment extends Fragment {
    EntryListViewModel entryListViewModel;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        recyclerView=view.findViewById(R.id.entryRecyclerView);
        // for testing static method called to get some data, but it is a good question where to hold entries in the end

        EntryRecViewAdapter entryRecViewAdapter= new EntryRecViewAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(entryRecViewAdapter);


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