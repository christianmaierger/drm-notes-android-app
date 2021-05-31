package com.example.drmapp.ui.addEntry_Split;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;

public class AddEntrySplitFragment extends Fragment implements View.OnClickListener {

    private AddEntrySplitViewModel addEntryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Entry");

        addEntryViewModel = new ViewModelProvider(this).get(AddEntrySplitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addentry_split, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnFullEntry);
        Button button_2 = (Button) root.findViewById(R.id.btnQuickEntry);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (v.getId()) {
            case R.id.btnQuickEntry:
                navController.navigate(R.id.action_addEntrySplitFragment_to_noteQuickEntryFragment);
                break;
            case R.id.btnFullEntry:
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
        }
    }
}