package com.example.drmapp.ui.addEntry_Split;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEntrySplitFragment extends Fragment implements View.OnClickListener {

    private AddEntrySplitViewModel addEntryViewModel;
    private MainActivity m = new MainActivity();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Entry");

        addEntryViewModel = new ViewModelProvider(this).get(AddEntrySplitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addentry_split, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnFullEntry);
        Button button_2 = (Button) root.findViewById(R.id.btnQuickEntry);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);

        //Sichtbarmachen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (v.getId()) {
            case R.id.btnQuickEntry:
                m.setQuickEntryTrue(true);
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
            case R.id.btnFullEntry:
                m.setQuickEntryTrue(false);
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
        }
    }


}