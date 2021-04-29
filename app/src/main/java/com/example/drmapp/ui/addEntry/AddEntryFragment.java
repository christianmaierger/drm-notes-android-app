package com.example.drmapp.ui.addEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.R;

public class AddEntryFragment extends Fragment {

    private AddEntryViewModel addEntryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        return root;
    }
}