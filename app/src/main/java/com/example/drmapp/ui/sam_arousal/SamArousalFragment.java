package com.example.drmapp.ui.sam_arousal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.model.Entry;

public class SamArousalFragment extends Fragment implements View.OnClickListener{

    private SamArousalViewModel mViewModel;
    Entry entryUnderConstruction = new Entry();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Arousal");
        entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

        mViewModel = new ViewModelProvider(this).get(SamArousalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sam_arousal, container, false);

        ImageButton button_1 = (ImageButton) root.findViewById(R.id.btnArousal1);
        ImageButton button_2 = (ImageButton) root.findViewById(R.id.btnArousal2);
        ImageButton button_3 = (ImageButton) root.findViewById(R.id.btnArousal3);
        ImageButton button_4 = (ImageButton) root.findViewById(R.id.btnArousal4);
        ImageButton button_5 = (ImageButton) root.findViewById(R.id.btnArousal5);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        switch (v.getId()){
            case R.id.btnArousal1:
                entryUnderConstruction.setSam2(1);
                break;
            case R.id.btnArousal2:
                entryUnderConstruction.setSam2(2);
                break;
            case R.id.btnArousal3:
                entryUnderConstruction.setSam2(3);
                break;
            case R.id.btnArousal4:
                entryUnderConstruction.setSam2(4);
                break;
            case R.id.btnArousal5:
                entryUnderConstruction.setSam2(5);
                break;
        }
        ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
        navController.navigate(R.id.action_samArousalFragment_to_samDominanceFragment);
    }
}
