package com.example.drmapp.ui;

/**
 * Um ehrlich zu sein weiss ich noch nicht so recht, was diese Klasse hier macht.
 *
 * */

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;

/**
 * A fragment representing a list of Items.
 */
public class EntryFragment extends Fragment {

/*    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    *//**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     *//*
    public EntryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EntryFragment newInstance(int columnCount) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if (getArguments() != null) {
           mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
       }
    }*/

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        recyclerView=view.findViewById(R.id.entryRecyclerView);
        // for testing static method called to get some data, but it is a good question where to hold entries in the end
        EntryRecViewAdapter entryRecViewAdapter=new EntryRecViewAdapter(Entry.createEntryList());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(entryRecViewAdapter);
        return view;




       /* // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new EntryRecViewAdapter(Entry.ITEMS));

        }*/

    }
}