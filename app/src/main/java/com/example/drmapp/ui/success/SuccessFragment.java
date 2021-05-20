package com.example.drmapp.ui.success;

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
import com.example.drmapp.ui.addEntry.AddEntryFragment;

public class SuccessFragment extends Fragment implements View.OnClickListener{

    private SuccessViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Success");

        mViewModel = new ViewModelProvider(this).get(SuccessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_success, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnSaveEntry);
        Button button_2 = (Button) root.findViewById(R.id.btnAddEntry);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        Fragment fragment = new Fragment();
        switch(v.getId()){
            case R.id.btnAddEntry:
                fragment = new AddEntryFragment();
                break;
                //TODO: Wo soll der Nutzer hin, wenn Eintrag gespeichert und kein Neuer erzeugt werden so??
            case R.id.btnSaveEntry:
                fragment = new AddEntryFragment();
                break;
            default:
                break;
        }

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
