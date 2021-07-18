package com.example.drmapp.ui.emojiPremade;

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
import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.model.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmojiPremadeFragment extends Fragment implements View.OnClickListener{

    Entry entryUnderConstruction = new Entry();
    int unicode1, unicode2, unicode3, unicode4, unicode5, unicode6;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Setzen des Titels des Fragments
        ((MainActivity) getActivity()).setActionBarTitle("Feelings Selection");

        //Abrufen des aktuellen Eintrages
        entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

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
        Button button_3 = (Button) root.findViewById(R.id.btnEmoji3);
        Button button_4 = (Button) root.findViewById(R.id.btnEmoji4);
        Button button_5 = (Button) root.findViewById(R.id.btnEmoji5);
        Button button_6 = (Button) root.findViewById(R.id.btnEmoji6);
        Button button_7 = (Button) root.findViewById(R.id.btnEmojiOther);


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

        //Setzen der Emojis für alle Buttons
        unicode1 = 0x1F621;
        button_1.setText(new String(Character.toChars(unicode1)));
        unicode2 = 0x1F622;
        button_2.setText(new String(Character.toChars(unicode2)));
        unicode3 = 0x1F630;
        button_3.setText(new String(Character.toChars(unicode3)));
        unicode4 = 0x1F4AA;
        button_4.setText(new String(Character.toChars(unicode4)));
        unicode5 = 0x1f60A;
        button_5.setText(new String(Character.toChars(unicode5)));
        unicode6 = 0x1F613;
        button_6.setText(new String(Character.toChars(unicode6)));

        //Setzen der Emojis für "Other"-Button
        int other1 = 0x1F60A;
        int other2 = 0x1F631;
        int other3 = 0x1F605;
        int other4 = 0x1F60D;
        int other5 = 0x1F633;
        int other6 = 0x1F44D;
        int other7 = 0x1F648;
        int other8 = 0x1f928;
        int other9 = 0x1F634;
        int other10 = 0x2764;

        button_7.setText(new String(Character.toChars(other1))
                + new String(Character.toChars(other2))
                + new String(Character.toChars(other3))
                + new String(Character.toChars(other4))
                + new String(Character.toChars(other5)) + "\n"
                + new String(Character.toChars(other6))
                + new String(Character.toChars(other7))
                + new String(Character.toChars(other8))
                + new String(Character.toChars(other9))
                + new String(Character.toChars(other10)));

        return root;
    }

    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch(v.getId()){
            case R.id.btnEmojiOther:
                navController.navigate(R.id.action_emojiPremadeFragment_to_emojiManualFragment);
                break;
            default:
                switch (v.getId()){
                    case R.id.btnEmoji1:
                        entryUnderConstruction.setEmoji(unicode1);
                        break;
                    case R.id.btnEmoji2:
                        entryUnderConstruction.setEmoji(unicode2);
                        break;
                    case R.id.btnEmoji3:
                        entryUnderConstruction.setEmoji(unicode3);
                        break;
                    case R.id.btnEmoji4:
                        entryUnderConstruction.setEmoji(unicode4);
                        break;
                    case R.id.btnEmoji5:
                        entryUnderConstruction.setEmoji(unicode5);
                        break;
                    case R.id.btnEmoji6:
                        entryUnderConstruction.setEmoji(unicode6);
                        break;
                }

                if (!((MainActivity) getActivity()).getIsQuickEntry()) {
                    ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                    navController.navigate(R.id.action_emojiPremadeFragment_to_samEmotionalFragment);
                } else {
                    ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                    AppDatabase.getInstance(getActivity()).entryDao().insertEntry(entryUnderConstruction);
                    navController.navigate(R.id.action_emojiPremadeFragment_to_nav_home2);
                }


        }
    }
}