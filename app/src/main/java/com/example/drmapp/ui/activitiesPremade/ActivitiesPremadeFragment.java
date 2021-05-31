package com.example.drmapp.ui.activitiesPremade;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.drmapp.ui.activitiesManual.ActivitiesManualFragment;
import com.example.drmapp.ui.feelingsPremade.FeelingsPremadeFragment;
import com.example.drmapp.ui.personManual.PersonManualFragment;
import com.example.drmapp.ui.personPremade.PersonPremadeFragment;

public class ActivitiesPremadeFragment extends Fragment implements View.OnClickListener {

    private ActivitiesPremadeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Activity Selection");

        mViewModel = new ViewModelProvider(this).get(ActivitiesPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activities_premade, container, false);

        Button button_1 = (Button) root.findViewById(R.id.activity_button_cooking);
        Button button_2 = (Button) root.findViewById(R.id.activity_button_eatingdrinking);
        Button button_3 = (Button) root.findViewById(R.id.activity_button_workingstudying);
        Button button_4 = (Button) root.findViewById(R.id.activity_button_carework);
        Button button_5 = (Button) root.findViewById(R.id.activity_button_chores);
        Button button_6 = (Button) root.findViewById(R.id.activity_button_hobby);
        Button button_7 = (Button) root.findViewById(R.id.activity_button_other);
        Button button_8 = (Button) root.findViewById(R.id.activity_button_leisuretime);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch(v.getId()){
            case R.id.activity_button_other:
                navController.navigate(R.id.action_activitiesPremadeFragment_to_activitiesManualFragment);
                break;
            default:
                navController.navigate(R.id.action_activitiesPremadeFragment_to_feelingsPremadeFragment);
                break;
        }
    }
}