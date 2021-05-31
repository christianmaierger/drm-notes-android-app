package com.example.drmapp.ui.addEntry_Time_Evening;

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

public class TimeEveningFragment extends Fragment implements View.OnClickListener {

    private TimeEveningViewModel mViewModel;

    public static TimeEveningFragment newInstance() {
        return new TimeEveningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Time Selection Evening");

        mViewModel =
                new ViewModelProvider(this).get(TimeEveningViewModel.class);
        View root = inflater.inflate(R.layout.fragment_time_evening, container, false);

        Button button_1 = (Button) root.findViewById(R.id.evening_option_1);
        Button button_2 = (Button) root.findViewById(R.id.evening_option_2);
        Button button_3 = (Button) root.findViewById(R.id.evening_option_3);
        Button button_4 = (Button) root.findViewById(R.id.evening_option_4);
        Button button_5 = (Button) root.findViewById(R.id.evening_option_5);


        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_timeEveningFragment2_to_activitiesPremadeFragment);
    }
}