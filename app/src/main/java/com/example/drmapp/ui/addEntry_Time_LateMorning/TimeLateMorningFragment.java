package com.example.drmapp.ui.addEntry_Time_LateMorning;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.activitiespremade.ActivitiesPremade;

public class TimeLateMorningFragment extends Fragment implements View.OnClickListener {

    private TimeLateMorningViewModel mViewModel;

    public static TimeLateMorningFragment newInstance() {
        return new TimeLateMorningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Time Selection Late Morning");

        mViewModel =
                new ViewModelProvider(this).get(TimeLateMorningViewModel.class);
        View root = inflater.inflate(R.layout.fragment_time_latemorning, container, false);

        Button button_1 = (Button) root.findViewById(R.id.latemorning_option_1);
        Button button_2 = (Button) root.findViewById(R.id.latemorning_option_2);
        Button button_3 = (Button) root.findViewById(R.id.latemorning_option_3);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new ActivitiesPremade();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}