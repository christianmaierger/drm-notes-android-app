package com.example.drmapp.ui.emojiManual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.model.Entry;

import java.util.stream.IntStream;

public class EmojiManualFragment extends Fragment implements View.OnClickListener{

    Entry entryUnderConstruction = new Entry();
    EditText inputEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Setzen des Titels des Fragments
        ((MainActivity) getActivity()).setActionBarTitle("Feelings Selection");

        //Abrufen des aktuellen Eintrages
        entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();

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
               // Wir brauchen den Text wirklich als UnicodePoints, getText liefert das Emoji in grafischer Form
               unicodeStr = inputEditText.getText().codePoints();

             int[] intArr=  unicodeStr.toArray();
               // Hier wird  der Wert aus dem ersten Index des Array geholt
             result = intArr[0];
             if (intArr.length>1) {
                 // Falls das Emoji mehr als einen CodePoint hat, kann es mit unserer Methode
                 // nicht umgewandelt werden und es wird das rote Kreuz Error Emoji gesetzt
                 result = 10060;
             }
           } catch (Exception e) {
               e.printStackTrace();
               // Hier wird bei Problemen standartmäsig ein Wert gesetzt, rotes Kreuz
               result = 10060;
           }


           if (!((MainActivity) getActivity()).getIsQuickEntry()) {
               entryUnderConstruction.setEmoji(result);
               ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);

               NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
               navController.navigate(R.id.action_emojiManualFragment_to_samEmotionalFragment);
           } else {
               ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);
               AppDatabase.getInstance(getActivity()).entryDao().insertEntry(entryUnderConstruction);
               NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
               navController.navigate(R.id.action_emojiManualFragment_to_nav_home);
           }



       }
    }
}