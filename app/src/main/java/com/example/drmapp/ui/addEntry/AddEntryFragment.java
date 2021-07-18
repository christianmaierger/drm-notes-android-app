package com.example.drmapp.ui.addEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.model.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEntryFragment extends Fragment implements View.OnClickListener {

    Entry entryUnderConstruction;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Setzen des Titels des Fragments
        ((MainActivity) getActivity()).setActionBarTitle("Time Selection");

        //Abrufen des aktuellen Eintrages
        entryUnderConstruction = ((MainActivity) getActivity()).getEntryUnderConstruction();

        View root = inflater.inflate(R.layout.fragment_time, container, false);

        Button button_1 = (Button) root.findViewById(R.id.timedate_button_morning);
        Button button_2 = (Button) root.findViewById(R.id.timedate_button_afternoon);
        Button button_3 = (Button) root.findViewById(R.id.timedate_button_evening);
        Button button_4 = (Button) root.findViewById(R.id.timedate_button_night);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);

        //Sichtbarmachen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.timedate_button_morning:
                entryUnderConstruction.setTime("Morning");
                entryUnderConstruction.setTime_int(1);
                break;
            case R.id.timedate_button_afternoon:
                entryUnderConstruction.setTime("Afternoon");
                entryUnderConstruction.setTime_int(2);
                break;
            case R.id.timedate_button_evening:
                entryUnderConstruction.setTime(getResources().getString(R.string.textEveningButton));
                entryUnderConstruction.setTime_int(3);
                break;
            case R.id.timedate_button_night:
                entryUnderConstruction.setTime("Night");
                entryUnderConstruction.setTime_int(4);
                break;
        }

        if (!((MainActivity) getActivity()).getIsQuickEntry()) {
            ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_add_Entry_to_activitiesPremadeFragment);
        } else {
            ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_add_Entry_to_emojiPremadeFragment2);
        }
    }
}
