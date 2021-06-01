package com.example.drmapp.ui.personPremade;

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
import com.example.drmapp.ui.locationPremade.LocationPremadeFragment;
import com.example.drmapp.ui.personManual.PersonManualFragment;

public class PersonPremadeFragment extends Fragment implements View.OnClickListener{

private PersonPremadeViewModel mViewModel;

@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
@Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Person Selection");

        mViewModel = new ViewModelProvider(this).get(PersonPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_people_premade, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnColleagues);
        Button button_2 = (Button) root.findViewById(R.id.btnFriends);
        Button button_3 = (Button) root.findViewById(R.id.btnPeopleOther);
        Button button_4 = (Button) root.findViewById(R.id.btnStrangers);
        Button button_5 = (Button) root.findViewById(R.id.btnFamily);
        Button button_6 = (Button) root.findViewById(R.id.btnNoOne);
        Button button_7 = (Button) root.findViewById(R.id.btnPartner);

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
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                switch(v.getId()){
                        case R.id.btnPeopleOther:
                                navController.navigate(R.id.action_personPremadeFragment_to_personManualFragment);
                                break;
                        default:
                                navController.navigate(R.id.action_personPremadeFragment_to_locationPremadeFragment);
                                break;
                }
        }
}
