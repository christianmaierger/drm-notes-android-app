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
import android.widget.EditText;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class EmojiManualFragment extends Fragment implements View.OnClickListener{

    private EmojiManualViewModel mViewModel;
    Entry entryUnderConstruction = new Entry();
    EditText inputEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Feelings Selection");
        entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

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

        inputEditText = root.findViewById(R.id.addEmojiManual_editText);
        return root;
    }

    public void onClick(View v) {


        // Dieses Vorgehen geht auch für abseitigere Emojis, aber z.B. nicht für Landesflaggen
        int result=0;
        IntStream unicodeStr = null;
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
           try {
           // Wir brauchen den Text wirklich als UnicodePoints, get Text liefert das Emoji in grafischer Form
           unicodeStr = inputEditText.getText().codePoints();

           // todo counten

            // Das in dem Stream nur ein Element ist, kann man immer auf das erste zugreifen
            OptionalInt first = unicodeStr.findFirst();
            // Hier wird (falls) vorhanden der Wert aus dem Optional geholt
                result = first.getAsInt();
            } catch (Exception e) {
               e.printStackTrace();
               // Hier wird bei Problemen standartmäsig ein leerer Wert gesetzt, also kein Emoji
               result=0;
            }
        }


        entryUnderConstruction.setEmoji(result);
        ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_emojiManualFragment_to_samEmotionalFragment);
    }
}