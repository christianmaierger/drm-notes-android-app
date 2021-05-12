package com.example.drmapp.ui.activitiesPremade;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;

public class ActivitiesPremadeFragment extends Fragment {

    private ActivitiesPremadeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity())
                .setActionBarTitle("Activity Selection");

        mViewModel =
                new ViewModelProvider(this).get(ActivitiesPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activities_premade, container, false);
        return root;
    }
}