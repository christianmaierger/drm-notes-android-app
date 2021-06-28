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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drmapp.MainActivity;
import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class AddEntrySplitFragment extends Fragment implements View.OnClickListener {

    private AddEntrySplitViewModel addEntryViewModel;
    private MainActivity m = new MainActivity();
    Entry entryUnderConstruction= new Entry();;

    // Instant today = Instant.now();




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Calendar calendar_today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String today = sdf.format(calendar_today.getTime());


        Calendar calendar_yesterday = Calendar.getInstance();
        calendar_yesterday.add(Calendar.DATE, -1);
        String yesterday = sdf.format(calendar_yesterday.getTime());

        ((MainActivity) getActivity()).setActionBarTitle("Entry");
        //entryUnderConstruction = ((MainActivity)getActivity()).getEntryUnderConstruction();



        addEntryViewModel = new ViewModelProvider(this).get(AddEntrySplitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addentry_split, container, false);

        Button button_1 = (Button) root.findViewById(R.id.btnFullEntry);
        Button button_2 = (Button) root.findViewById(R.id.btnQuickEntry);
        Switch switchYesterday = (Switch) root.findViewById(R.id.switchYesterday);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);


        // Wenn der Switch nicht umgelegt ist, wird der OnCheckedChangeListener nicht aktiviert
        // Daher muss der Default Case (Today) davor definiert werden!
       // CharSequence text = today.toString().substring(5,10);
        entryUnderConstruction.setDate(today);
        ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);

        switchYesterday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                Context context = getContext();
                int duration = Toast.LENGTH_SHORT;
               // Instant yesterday = today.minus(1, ChronoUnit.DAYS);


               if(isChecked){
                CharSequence text = yesterday;
                    Toast toast = Toast.makeText(context, text, duration);
                    entryUnderConstruction.setDate(yesterday);
                    toast.show();}

                // fuer den Fall, dass der Nutzer den Switch umlegt und dann wieder zurueck legt muss hier
                // noch einmal der Today Case definiert werden

                else {
                    CharSequence text = today;
                    entryUnderConstruction.setDate(today);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();}

                ((MainActivity)getActivity()).setEntryUnderConstruction(entryUnderConstruction);

            }
        });

        //Sichtbarmachen des Floating Action Buttons für das Speichern eines Eintrags
        FloatingActionButton fb = (FloatingActionButton) getActivity().findViewById(R.id.fwd);
        fb.setVisibility(View.GONE);

        return root;
    }





    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        switch (v.getId()) {
            case R.id.btnQuickEntry:
                ((MainActivity)getActivity()).setQuickEntryTrue(true);
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
            case R.id.btnFullEntry:
                ((MainActivity)getActivity()).setQuickEntryTrue(false);
                navController.navigate(R.id.action_addEntrySplitFragment_to_nav_add_Entry);
                break;
        }
    }


}