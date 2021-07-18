package com.example.drmapp.ui.activitiesManual;
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
import com.example.drmapp.model.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ActivitiesManualFragment extends Fragment implements View.OnClickListener{

        private ActivitiesManualViewModel mViewModel;
        Entry entryUnderConstruction = new Entry();
        EditText inputEditText;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {

            //Setzen des Titels des Fragments
            ((MainActivity) getActivity()).setActionBarTitle("Activity Manual Selection");

            //Abrufen des aktuellen Eintrages
            entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

            mViewModel = new ViewModelProvider(this).get(ActivitiesManualViewModel.class);
            View root = inflater.inflate(R.layout.fragment_activities_manual, container, false);

            Button button_1 = (Button) root.findViewById(R.id.btnSubmitActivityM);

            button_1.setOnClickListener(this);

            inputEditText = root.findViewById(R.id.addActivityManual_editText);

            //Verbergen des Floating Action Buttons für das Speichern eines Eintrags
            FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
            fb.setVisibility(View.GONE);

            return root;
        }

    @Override
    public void onClick(View v) {

        entryUnderConstruction.setActivity(inputEditText.getText().toString());
        ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_activitiesManualFragment_to_emojiPremadeFragment);
    }
}


