package com.example.drmapp.ui.noteQuickEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;

public class NoteQuickEntryFragment extends Fragment implements View.OnClickListener {

    private NoteQuickEntryViewModel addEntryViewModel;
    Entry entryUnderConstruction = new Entry();
    EditText inputEditText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Quick Entry");
        entryUnderConstruction = ((MainActivity) getActivity()).getEntryUnderConstruction();

        addEntryViewModel = new ViewModelProvider(this).get(NoteQuickEntryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quick_entry, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnSubmitQuickEntry);

        button_1.setOnClickListener(this);

        inputEditText = root.findViewById(R.id.addQuickEntry_editText);

        return root;
    }

    @Override
    public void onClick(View v) {

        //((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);
        //entryUnderConstruction.setThoughts(inputEditText.getText().toString());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_noteQuickEntryFragment_to_nav_home);

    }
}