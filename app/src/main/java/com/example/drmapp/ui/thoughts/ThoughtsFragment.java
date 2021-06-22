package com.example.drmapp.ui.thoughts;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.success.SuccessFragment;

public class ThoughtsFragment extends Fragment implements View.OnClickListener {

    private ThoughtsViewModel mViewModel;
    Entry entryUnderConstruction = new Entry();
    EditText inputEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Thoughts");
        entryUnderConstruction = ((MainActivity) getActivity()).getEntryUnderConstruction();

        mViewModel = new ViewModelProvider(this).get(ThoughtsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_thoughts, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnSubmitThoughts);

        button_1.setOnClickListener(this);

        inputEditText = root.findViewById(R.id.addThoughts_editText);

        return root;
    }

    public void onClick(View v) {

        ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);
        entryUnderConstruction.setThoughts(inputEditText.getText().toString());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_thoughtsFragment_to_successFragment);
    }
}