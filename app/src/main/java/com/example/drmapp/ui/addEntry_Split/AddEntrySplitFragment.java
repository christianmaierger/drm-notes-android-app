package com.example.drmapp.ui.addEntry_Split;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.model.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEntrySplitFragment extends Fragment implements View.OnClickListener {

    private AddEntrySplitViewModel addEntryViewModel;
    private MainActivity m = new MainActivity();
    Entry entryUnderConstruction;
    boolean switchChecked=false;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Entry");
        entryUnderConstruction = new Entry();
        ((MainActivity) getActivity()).setEntryUnderConstruction(entryUnderConstruction);

        addEntryViewModel = new ViewModelProvider(this).get(AddEntrySplitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addentry_split, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnFullEntry);
        Button button_2 = (Button) root.findViewById(R.id.btnQuickEntry);
        Switch switchYesterday = (Switch) root.findViewById(R.id.switchYesterday);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        switchYesterday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                switchChecked=true;
                Context context = getContext();
                int duration = Toast.LENGTH_SHORT;

                  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
                    Date today = new Date();
                    Calendar calendar = Calendar.getInstance();
                    String text = "";
                calendar.setTime(today);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                    if (isChecked) {
                        calendar.add(Calendar.DATE, -1);
                        Date yesterday = calendar.getTime();
                        text = formatter.format(yesterday);
                        entryUnderConstruction.setDateAsLong(yesterday.getTime());
                    } else {
                        Date tmp = calendar.getTime();
                        text = formatter.format(tmp);
                        entryUnderConstruction.setDateAsLong(tmp.getTime());
                    }
                entryUnderConstruction.setDateAsString(text);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);

            }
        });

        //Sichtbarmachen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fbFwd = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fbFwd.setVisibility(View.GONE);

        FloatingActionButton fbHome = (FloatingActionButton) getActivity().findViewById(R.id.backHome);
        fbHome.setVisibility(View.VISIBLE);


        return root;
    }





    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        // Wenn der Switch nicht einmal gedrückt wurde muss standartmäsig das heutige Datum eingefügt werden
        if(switchChecked==false) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
            // das aktuelle Datum
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date tmp = calendar.getTime();
            String text = formatter.format(today);
            entryUnderConstruction.setDateAsString(text);
            entryUnderConstruction.setDateAsLong(tmp.getTime());
        }

        switch (v.getId()) {
            case R.id.btnQuickEntry:
                ((MainActivity)getActivity()).setQuickEntryTrue(true);
                entryUnderConstruction.setQuickEntry(true);
                ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
            case R.id.btnFullEntry:
                ((MainActivity)getActivity()).setQuickEntryTrue(false);
                entryUnderConstruction.setQuickEntry(false);
                ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
        }
    }


}