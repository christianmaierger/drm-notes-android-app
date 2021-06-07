package com.example.drmapp.ui.personManual;

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
import com.example.drmapp.ui.overall.OverallFragment;


public class PersonManualFragment extends Fragment implements View.OnClickListener{

private PersonManualViewModel mViewModel;

@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
@Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Person Selection");

        mViewModel = new ViewModelProvider(this).get(PersonManualViewModel.class);
        View root = inflater.inflate(R.layout.fragment_people_manual, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnSubmitPeopleM);

        button_1.setOnClickListener(this);

        return root;
        }

        public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_personManualFragment_to_emojiPremadeFragment);

        }
}