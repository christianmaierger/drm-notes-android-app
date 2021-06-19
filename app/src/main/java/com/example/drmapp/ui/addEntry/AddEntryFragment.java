package com.example.drmapp.ui.addEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEntryFragment extends Fragment implements View.OnClickListener {

    private AddEntryViewModel addEntryViewModel;
    Entry entryUnderConstruction = new Entry();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Time Selection");

        Entry entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

        addEntryViewModel = new ViewModelProvider(this).get(AddEntryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addentry, container, false);

        Button button_1 = (Button) root.findViewById(R.id.timedate_button_morning);
        Button button_4 = (Button) root.findViewById(R.id.timedate_button_afternoon);
        Button button_5 = (Button) root.findViewById(R.id.timedate_button_evening);
        Button button_6 = (Button) root.findViewById(R.id.timedate_button_night);

        button_1.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);

        //Sichtbarmachen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.VISIBLE);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(!((MainActivity)getActivity()).getIsQuickEntry()) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_add_Entry_to_activitiesPremadeFragment);
        }else{
            switch (v.getId()){
                case R.id.timedate_button_morning:
                    entryUnderConstruction.setTime("Morning");
                    ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                    break;
            }
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_add_Entry_to_noteQuickEntryFragment);
        }
    }
    }
