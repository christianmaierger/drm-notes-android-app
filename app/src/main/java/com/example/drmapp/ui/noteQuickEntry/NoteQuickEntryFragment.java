package com.example.drmapp.ui.noteQuickEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;

public class NoteQuickEntryFragment extends Fragment implements View.OnClickListener {

    private NoteQuickEntryViewModel addEntryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Quick Entry");

        addEntryViewModel = new ViewModelProvider(this).get(NoteQuickEntryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quick_entry, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnSubmitQuickEntry);

        button_1.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (v.getId()) {
            case R.id.btnSubmitQuickEntry:
                navController.navigate(R.id.action_noteQuickEntryFragment_to_nav_home);
                break;
        }
    }
}