package com.example.drmapp.ui.activitiesPremade;

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
import com.example.drmapp.ui.activitiesManual.ActivitiesManualFragment;
import com.example.drmapp.ui.feelingsPremade.FeelingsPremadeFragment;
import com.example.drmapp.ui.personManual.PersonManualFragment;
import com.example.drmapp.ui.personPremade.PersonPremadeFragment;

public class ActivitiesPremadeFragment extends Fragment implements View.OnClickListener {

    private ActivitiesPremadeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Activity Selection");

        mViewModel = new ViewModelProvider(this).get(ActivitiesPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activities_premade, container, false);

        /**
         * Um zu "personPremade" oder "ActivitiesManual" zu navigieren
         * -- Button instanziieren
         * -- setOnClickListener
         * -- OnClick setzen
         * -- Zusätzliche Klassen importieren
         * -- Sprünge neue setzen**/
        Button button_1 = (Button) root.findViewById(R.id.activity_button_cooking);
        Button button_2 = (Button) root.findViewById(R.id.activity_button_eatingdrinking);
        Button button_3 = (Button) root.findViewById(R.id.activity_button_workingstudying);
        Button button_4 = (Button) root.findViewById(R.id.activity_button_carework);
        Button button_5 = (Button) root.findViewById(R.id.activity_button_chores);
        Button button_6 = (Button) root.findViewById(R.id.activity_button_hobby);
        Button button_7 = (Button) root.findViewById(R.id.activity_button_other);
        Button button_8 = (Button) root.findViewById(R.id.activity_button_leisuretime);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        Fragment fragment = new Fragment();
        switch(v.getId()){
            case R.id.activity_button_other:
                fragment = new ActivitiesManualFragment();
                break;
            default:
                fragment = new FeelingsPremadeFragment();
                break;
        }

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}