package com.example.drmapp.ui.activitiesManual;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.feelingsPremade.FeelingsPremadeFragment;

public class ActivitiesManualFragment extends Fragment implements View.OnClickListener{

        private ActivitiesManualViewModel mViewModel;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {

            ((MainActivity) getActivity()).setActionBarTitle("Activity Manual Selection");

            mViewModel = new ViewModelProvider(this).get(ActivitiesManualViewModel.class);
            View root = inflater.inflate(R.layout.fragment_activities_manual, container, false);

            Button button_1 = (Button) root.findViewById(R.id.btnSubmitActivityM);

            button_1.setOnClickListener(this);

            return root;
        }

    @Override
    public void onClick(View v) {
        Fragment fragment = new FeelingsPremadeFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

