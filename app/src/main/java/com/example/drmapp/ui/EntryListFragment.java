package com.example.drmapp.ui;



import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drmapp.R;
import com.example.drmapp.ui.addEntry.AddEntryViewModel;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.viewLast.EntryListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class EntryListFragment extends Fragment {
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

        Entry.createEntryList(getContext());

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