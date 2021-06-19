package com.example.drmapp.ui.success;

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

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.database.EntryDAO;
import com.example.drmapp.ui.addEntry.AddEntryFragment;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SuccessFragment extends Fragment implements View.OnClickListener{

    private SuccessViewModel mViewModel;
    Entry entryUnderConstruction = new Entry();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Success");

        Entry entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

        mViewModel = new ViewModelProvider(this).get(SuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_success, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnSaveEntry);
        Button button_2 = (Button) root.findViewById(R.id.btnAddEntry);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);

        //Entfernen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        switch(v.getId()){
            case R.id.btnAddEntry:
                navController.navigate(R.id.action_successFragment_to_nav_add_Entry);

                break;
            case R.id.btnSaveEntry:
                AppDatabase.getInstance(getActivity()).entryDao().insertEntry(entryUnderConstruction);
                navController.navigate(R.id.action_successFragment_to_nav_home);
                break;
            default:
                break;
        }
    }
}
