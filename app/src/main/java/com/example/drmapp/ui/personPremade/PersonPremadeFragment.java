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
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.personManual.PersonManualFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PersonPremadeFragment extends Fragment implements View.OnClickListener{

private PersonPremadeViewModel mViewModel;
Entry entryUnderConstruction = new Entry();

@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
@Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Person Selection");
        entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

        mViewModel = new ViewModelProvider(this).get(PersonPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_people_premade, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnNoOne);
        Button button_2 = (Button) root.findViewById(R.id.btnStrangers);
        Button button_3 = (Button) root.findViewById(R.id.btnColleagues);
        Button button_4 = (Button) root.findViewById(R.id.btnPartner);
        Button button_5 = (Button) root.findViewById(R.id.btnFriends);
        Button button_6 = (Button) root.findViewById(R.id.btnFamily);
        Button button_7 = (Button) root.findViewById(R.id.btnPeopleOther);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);

        //Anzeigen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.VISIBLE);

        return root;
        }

        public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                switch(v.getId()){
                        case R.id.btnPeopleOther:
                                navController.navigate(R.id.action_personPremadeFragment_to_personManualFragment);
                                break;
                        default:
                                navController.navigate(R.id.action_personPremadeFragment_to_emojiPremadeFragment);
                                //TODO: falls Personen noch gesammelt werden --> ausfüllen
                                /*
                                switch (v.getId()){
                                        case R.id.btnNoOne:
                                                entryUnderConstruction.setPerson(getResources().getString(R.string.textCookingButton));
                                                break;
                                        case R.id.btnStrangers:
                                                entryUnderConstruction.setActivity(getResources().getString(R.string.textEatingDrinkingButton));
                                                break;
                                        case R.id.btnColleagues:
                                                entryUnderConstruction.setActivity(getResources().getString(R.string.textWorkingStudyingButton));
                                                break;
                                        case R.id.btnPartner:
                                                entryUnderConstruction.setActivity(getResources().getString(R.string.textCareWorkButton));
                                                break;
                                        case R.id.btnFriends:
                                                entryUnderConstruction.setActivity(getResources().getString(R.string.textChoresButton));
                                                break;
                                        case R.id.btnFamily:
                                                entryUnderConstruction.setActivity(getResources().getString(R.string.textHobbyButton));
                                                break;
                                }
                                ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                                */
                                break;
                }
        }
}
