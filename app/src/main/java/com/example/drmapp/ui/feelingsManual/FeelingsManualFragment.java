package com.example.drmapp.ui.feelingsManual;

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
import com.example.drmapp.ui.feelingsPremade.FeelingsPremadeFragment;
import com.example.drmapp.ui.personPremade.PersonPremadeFragment;

public class FeelingsManualFragment extends Fragment implements View.OnClickListener{

private FeelingsManualViewModel mViewModel;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {

                ((MainActivity) getActivity()).setActionBarTitle("Feelings Selection Manual");

                mViewModel = new ViewModelProvider(this).get(FeelingsManualViewModel.class);
                View root = inflater.inflate(R.layout.fragment_feelings_manual, container, false);

                Button button_1 = (Button) root.findViewById(R.id.btnSubmitFeelingsM);

                button_1.setOnClickListener(this);

                return root;
        }

        @Override
        public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_feelingsManualFragment_to_personPremadeFragment);
        }



}
