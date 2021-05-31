package com.example.drmapp.ui.addEntry_Time_Night;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.activitiesPremade.ActivitiesPremadeFragment;

public class TimeNightFragment extends Fragment implements View.OnClickListener {

    private TimeNightViewModel mViewModel;

    public static TimeNightFragment newInstance() {
        return new TimeNightFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Time Selection Night");

        mViewModel =
                new ViewModelProvider(this).get(TimeNightViewModel.class);
        View root = inflater.inflate(R.layout.fragment_time_night, container, false);

        Button button_1 = (Button) root.findViewById(R.id.night_option_1);
        Button button_2 = (Button) root.findViewById(R.id.night_option_2);
        Button button_3 = (Button) root.findViewById(R.id.night_option_3);
        Button button_4 = (Button) root.findViewById(R.id.night_option_4);
        Button button_5 = (Button) root.findViewById(R.id.night_option_5);
        Button button_6 = (Button) root.findViewById(R.id.night_option_6);
        Button button_7 = (Button) root.findViewById(R.id.night_option_7);
        Button button_8 = (Button) root.findViewById(R.id.night_option_8);


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

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_timeNightFragment_to_activitiesPremadeFragment);
    }
}