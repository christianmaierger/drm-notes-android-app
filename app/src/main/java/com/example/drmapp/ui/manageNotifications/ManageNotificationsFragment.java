package com.example.drmapp.ui.manageNotifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ManageNotificationsFragment extends Fragment  {

    private ManageNotificationsViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ManageNotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage_notifications, container, false);
        final TextView textView = root.findViewById(R.id.manageNotifications);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addTimePicker);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Group timePickerGroup = getView().findViewById(R.id.timePickerGroup);
              // TimePicker b = getView().findViewById(R.id.simpleTimePicker);
                timePickerGroup.setVisibility(View.VISIBLE);
            }
        });


        return root;
    }

  public void showTimePicker(View view) {
      TimePicker b = getView().findViewById(R.id.simpleTimePicker);
     // b.setVisibility(View.VISIBLE);


  }




}