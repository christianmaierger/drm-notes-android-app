package com.example.drmapp.ui.addEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.addEntry_Time_Afternoon.TimeAfternoonFragment;
import com.example.drmapp.ui.addEntry_Time_Evening.TimeEveningFragment;
import com.example.drmapp.ui.addEntry_Time_LateMorning.TimeLateMorningFragment;
import com.example.drmapp.ui.addEntry_Time_Morning.TimeMorningFragment;
import com.example.drmapp.ui.addEntry_Time_Night.TimeNightFragment;
import com.example.drmapp.ui.addEntry_Time_Noon.TimeNoonFragment;

public class AddEntryFragment extends Fragment implements View.OnClickListener {

    private AddEntryViewModel addEntryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Time Selection");

        addEntryViewModel =
                new ViewModelProvider(this).get(AddEntryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addentry, container, false);
        final TextView textView = root.findViewById(R.id.addentry);
        addEntryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        Button button_1 = (Button) root.findViewById(R.id.timedate_button_morning);
        Button button_2 = (Button) root.findViewById(R.id.timedate_button_latemorning);
        Button button_3 = (Button) root.findViewById(R.id.timedate_button_noon);
        Button button_4 = (Button) root.findViewById(R.id.timedate_button_afternoon);
        Button button_5 = (Button) root.findViewById(R.id.timedate_button_evening);
        Button button_6 = (Button) root.findViewById(R.id.timedate_button_night);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);

        return root;
    }
    @Override
    public void onClick(View v) {
        Fragment fragment = new Fragment();
        switch(v.getId()){
            case R.id.timedate_button_morning:
                fragment = new TimeMorningFragment();
                break;
            case R.id.timedate_button_latemorning:
                fragment = new TimeLateMorningFragment();
                break;
            case R.id.timedate_button_noon:
                fragment = new TimeNoonFragment();
                break;
            case R.id.timedate_button_afternoon:
                fragment = new TimeAfternoonFragment();
                break;
            case R.id.timedate_button_evening:
                fragment = new TimeEveningFragment();
                break;
            case R.id.timedate_button_night:
                fragment = new TimeNightFragment();
                break;
        }
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}