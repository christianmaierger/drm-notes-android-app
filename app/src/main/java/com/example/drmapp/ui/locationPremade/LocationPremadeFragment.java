package com.example.drmapp.ui.locationPremade;

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
import com.example.drmapp.ui.locationManual.LocationManualFragment;
import com.example.drmapp.ui.overall.OverallFragment;
import com.example.drmapp.ui.personManual.PersonManualFragment;
import com.example.drmapp.ui.personPremade.PersonPremadeViewModel;

public class LocationPremadeFragment extends Fragment implements View.OnClickListener{

    private LocationPremadeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Location Selection");

        mViewModel = new ViewModelProvider(this).get(LocationPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location_premade, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnHome);
        Button button_2 = (Button) root.findViewById(R.id.btnWork);
        Button button_3 = (Button) root.findViewById(R.id.btnCommute);
        Button button_4 = (Button) root.findViewById(R.id.btnOutdoors);
        Button button_5 = (Button) root.findViewById(R.id.btnGym);
        Button button_6 = (Button) root.findViewById(R.id.btnSupermarket);
        Button button_7 = (Button) root.findViewById(R.id.btnLocationOther);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        Fragment fragment = new Fragment();
        switch(v.getId()){
            case R.id.btnLocationOther:
                fragment = new LocationManualFragment();
                break;
            default:
                fragment = new OverallFragment();
                break;
        }

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
