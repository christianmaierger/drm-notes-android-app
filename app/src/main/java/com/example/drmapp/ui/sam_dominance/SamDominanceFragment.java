package com.example.drmapp.ui.sam_dominance;

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

public class SamDominanceFragment extends Fragment implements View.OnClickListener{

    private SamDominanceViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Dominance");

        mViewModel = new ViewModelProvider(this).get(SamDominanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sam_dominance, container, false);

        ImageButton button_1 = (ImageButton) root.findViewById(R.id.btnDominance1);
        ImageButton button_2 = (ImageButton) root.findViewById(R.id.btnDominance2);
        ImageButton button_3 = (ImageButton) root.findViewById(R.id.btnDominance3);
        ImageButton button_4 = (ImageButton) root.findViewById(R.id.btnDominance4);
        ImageButton button_5 = (ImageButton) root.findViewById(R.id.btnDominance5);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch(v.getId()){
            default:
                navController.navigate(R.id.action_samDominanceFragment_to_thoughtsFragment);
                break;
        }
    }
}