package com.example.drmapp.ui.emojiManual;

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

public class EmojiManualFragment extends Fragment implements View.OnClickListener{

    private EmojiManualViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Feelings Selection");

        mViewModel = new ViewModelProvider(this).get(EmojiManualViewModel.class);
        View root = inflater.inflate(R.layout.fragment_emoji_manual, container, false);

        /**
         * Um zu "personPremade" oder "ActivitiesManual" zu navigieren
         * -- Button instanziieren
         * -- setOnClickListener
         * -- OnClick setzen
         * -- Zusätzliche Klassen importieren
         * -- Sprünge neue setzen**/
        Button button_1 = (Button) root.findViewById(R.id.btnSubmitEmojiM);

        button_1.setOnClickListener(this);

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch(v.getId()){
            case R.id.btnSubmitEmojiM:
                navController.navigate(R.id.action_emojiManualFragment_to_overallFragment);
                break;
        }
    }
}