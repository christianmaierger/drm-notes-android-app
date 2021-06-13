package com.example.drmapp.ui.sam_emotional;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.activitiesPremade.ActivitiesPremadeViewModel;

public class SamEmotionalFragment extends Fragment implements View.OnClickListener{

    private SamEmotionalViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Emotion");

        mViewModel = new ViewModelProvider(this).get(SamEmotionalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sam_emotional, container, false);

        ImageButton button_1 = (ImageButton) root.findViewById(R.id.btnEmotional1);
        ImageButton button_2 = (ImageButton) root.findViewById(R.id.btnEmotional2);
        ImageButton button_3 = (ImageButton) root.findViewById(R.id.btnEmotional3);
        ImageButton button_4 = (ImageButton) root.findViewById(R.id.btnEmotional4);
        ImageButton button_5 = (ImageButton) root.findViewById(R.id.btnEmotional5);

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
                navController.navigate(R.id.action_samEmotionalFragment_to_samArousalFragment);
                break;
        }
    }
}
