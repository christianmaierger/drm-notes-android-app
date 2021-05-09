package com.example.drmapp.ui.addEntry_Time_Afternoon;

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

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.activitiespremade.ActivitiesPremade;

public class TimeAfternoonFragment extends Fragment implements View.OnClickListener {

    private TimeAfternoonViewModel mViewModel;

    public static TimeAfternoonFragment newInstance() {
        return new TimeAfternoonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Time Selection Afternoon");

        mViewModel =
                new ViewModelProvider(this).get(TimeAfternoonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_time_afternoon, container, false);

        Button button_1 = (Button) root.findViewById(R.id.afternoon_option_1);
        Button button_2 = (Button) root.findViewById(R.id.afternoon_option_2);
        Button button_3 = (Button) root.findViewById(R.id.afternoon_option_3);
        Button button_4 = (Button) root.findViewById(R.id.afternoon_option_4);
        Button button_5 = (Button) root.findViewById(R.id.afternoon_option_5);


        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

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