package com.example.drmapp.ui.emojiPremade;

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

public class EmojiPremadeFragment extends Fragment implements View.OnClickListener{

    private EmojiPremadeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Feelings Selection");

        mViewModel = new ViewModelProvider(this).get(EmojiPremadeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_emoji_premade, container, false);

        /**
         * Um zu "personPremade" oder "ActivitiesManual" zu navigieren
         * -- Button instanziieren
         * -- setOnClickListener
         * -- OnClick setzen
         * -- Zusätzliche Klassen importieren
         * -- Sprünge neue setzen**/
        Button button_1 = (Button) root.findViewById(R.id.btnEmoji1);
        Button button_2 = (Button) root.findViewById(R.id.btnEmoji2);
        Button button_3 = (Button) root.findViewById(R.id.btnEmojiOther);
        Button button_4 = (Button) root.findViewById(R.id.btnEmoji4);
        Button button_5 = (Button) root.findViewById(R.id.btnEmoji5);
        Button button_6 = (Button) root.findViewById(R.id.btnEmoji6);
        Button button_7 = (Button) root.findViewById(R.id.btnEmoji7);
        Button button_8 = (Button) root.findViewById(R.id.btnEmoji8);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);

        int unicode1 = 0x1F60A;
        button_1.setText(new String(Character.toChars(unicode1)));
        int unicode2 = 0x1F603;
        button_2.setText(new String(Character.toChars(unicode2)));
        int unicode3 = 0x1F605;
        button_4.setText(new String(Character.toChars(unicode3)));
        int unicode4 = 0x1F60D;
        button_5.setText(new String(Character.toChars(unicode4)));
        int unicode5 = 0x1F621;
        button_6.setText(new String(Character.toChars(unicode5)));
        int unicode6 = 0x1F633;
        button_7.setText(new String(Character.toChars(unicode6)));
        int unicode7 = 0x1F625;
        button_8.setText(new String(Character.toChars(unicode7)));

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch(v.getId()){
            case R.id.btnEmojiOther:
                navController.navigate(R.id.action_emojiPremadeFragment_to_emojiManualFragment);
                break;
            default:
                navController.navigate(R.id.action_emojiPremadeFragment_to_overallFragment);
                break;
        }
    }
}