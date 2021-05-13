package com.example.drmapp.ui.addEntry_Time_Morning;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.activitiesPremade.ActivitiesPremadeFragment;

public class TimeMorningFragment extends Fragment implements View.OnClickListener {

    private TimeMorningViewModel timemorningViewModel;

    public static TimeMorningFragment newInstance() {
        return new TimeMorningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Time Selection Morning");

        timemorningViewModel =
                new ViewModelProvider(this).get(TimeMorningViewModel.class);
        View root = inflater.inflate(R.layout.fragment_time_morning, container, false);
        final TextView textView = root.findViewById(R.id.timemorningmessage);
        timemorningViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

        Button button_1 = (Button) root.findViewById(R.id.morning_option_1);
        Button button_2 = (Button) root.findViewById(R.id.morning_option_2);
        Button button_3 = (Button) root.findViewById(R.id.morning_option_3);
        Button button_4 = (Button) root.findViewById(R.id.morning_option_4);
        Button button_5 = (Button) root.findViewById(R.id.morning_option_5);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new ActivitiesPremadeFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}