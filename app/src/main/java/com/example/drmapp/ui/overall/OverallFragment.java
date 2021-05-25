package com.example.drmapp.ui.overall;

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
import android.widget.ImageButton;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.thoughts.ThoughtsFragment;

public class OverallFragment extends Fragment implements View.OnClickListener{

private OverallViewModel mViewModel;

@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
@Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Overall Selection");

        mViewModel = new ViewModelProvider(this).get(OverallViewModel.class);
        View root = inflater.inflate(R.layout.fragment_overall, container, false);

        ImageButton button_1 = (ImageButton) root.findViewById(R.id.btnOverall1);
        ImageButton button_2 = (ImageButton) root.findViewById(R.id.btnOverall2);
        ImageButton button_3 = (ImageButton) root.findViewById(R.id.btnOverall3);
        ImageButton button_4 = (ImageButton) root.findViewById(R.id.btnOverall4);
        ImageButton button_5 = (ImageButton) root.findViewById(R.id.btnOverall5);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

        return root;
        }

        public void onClick(View v) {
                Fragment fragment = new ThoughtsFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
        }
}