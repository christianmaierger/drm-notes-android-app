package com.example.drmapp.ui.addEntry_Time_Morning;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drmapp.R;
import com.example.drmapp.ui.addEntry.AddEntryViewModel;

public class TimeMorningFragment extends Fragment {

    private TimeMorningViewModel timemorningViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        timemorningViewModel =
                new ViewModelProvider(this).get(TimeMorningViewModel.class);
        View root = inflater.inflate(R.layout.fragment_time__morning, container, false);
        final TextView textView = root.findViewById(R.id.timemorningmessage);
        timemorningViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}