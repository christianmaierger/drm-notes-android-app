package com.example.drmapp.ui.activitiesPremade;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivitiesPremadeFragment extends Fragment implements View.OnClickListener {

    private ActivitiesPremadeViewModel mViewModel;
    Entry entryUnderConstruction = new Entry();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Activity Selection");
        entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

        mViewModel = new ViewModelProvider(this).get(ActivitiesPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activities_premade, container, false);

        Button button_1 = (Button) root.findViewById(R.id.activity_button_cooking);
        Button button_2 = (Button) root.findViewById(R.id.activity_button_eatingdrinking);
        Button button_3 = (Button) root.findViewById(R.id.activity_button_workingstudying);
        Button button_4 = (Button) root.findViewById(R.id.activity_button_carework);
        Button button_5 = (Button) root.findViewById(R.id.activity_button_chores);
        Button button_6 = (Button) root.findViewById(R.id.activity_button_hobby);
        Button button_7 = (Button) root.findViewById(R.id.activity_button_leisuretime);
        Button button_8 = (Button) root.findViewById(R.id.activity_button_other);


        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);

        //Verbergen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        //MainActivity act = (MainActivity) getActivity();
         //  System.out.println(act.getEntryUnderConstruction());

        switch(v.getId()){
            case R.id.activity_button_other:
                navController.navigate(R.id.action_activitiesPremadeFragment_to_activitiesManualFragment);
                break;
            default:
                navController.navigate(R.id.action_activitiesPremadeFragment_to_emojiPremadeFragment);
                switch (v.getId()){
                    case R.id.activity_button_cooking:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textCookingButton));
                        break;
                    case R.id.activity_button_eatingdrinking:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textEatingDrinkingButton));
                        break;
                    case R.id.activity_button_workingstudying:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textWorkingStudyingButton));
                        break;
                    case R.id.activity_button_carework:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textCareWorkButton));
                        break;
                    case R.id.activity_button_chores:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textChoresButton));
                        break;
                    case R.id.activity_button_hobby:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textHobbyButton));
                        break;
                    case R.id.activity_button_leisuretime:
                        entryUnderConstruction.setActivity(getResources().getString(R.string.textLeisureTimeButton));
                        break;
                }
                ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                break;
        }
    }
}