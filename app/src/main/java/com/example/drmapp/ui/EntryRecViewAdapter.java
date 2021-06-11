package com.example.drmapp.ui;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.drmapp.R;
import com.example.drmapp.ui.addEntry_Time_Morning.TimeMorningFragment;
import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.viewLast.ViewLastFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 *
 * Der Adapter beinhaltet die Logik des RecyclerViews und legt fest welche Elemente angezeigt werden.
 * Auf die Objekte in der Array List entries wird ueber .get(position) zugegriffen.
 *
 * */



public class EntryRecViewAdapter extends RecyclerView.Adapter<EntryRecViewAdapter.ViewHolder> {
    private ArrayList<Entry> entries = new ArrayList<>();


    public EntryRecViewAdapter(ArrayList<Entry> entries) {
        this.entries = entries;
    }


    /* RecyclerView calls this method whenever it needs to create a new ViewHolder.
                The method creates and initializes the ViewHolder and its associated View,
                but does not fill in the view's contents—the ViewHolder has not yet been bound
                to specific data.*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_entry_list_item, parent, false);
        return new ViewHolder(view);

    }

    /*RecyclerView calls this method to associate a ViewHolder with data.
    The method fetches the appropriate data and uses the data to fill in the view
    holder's layout. For example, if the RecyclerView dislays a list of names,
    the method might find the appropriate name in the list and fill in the view
    holder's TextView widget.
    Position ist die Position innerhalb des RecyclerView Adapters.
    */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        // Get the data model based on position, so to speak index of the list with entries
        Entry ent = entries.get(position);

        // Set item views based on your views and data model
        // therefore one has to follow the following pattern of definining views, setting them with the holder instance vars
        // then set the text/other value by getting the coressponding value from the entry in the list at the specific position

        /*
        * SwitchCase um die Bilder im RecyclerView zu aendern in Abhaenigkeit von der eingegebenen Actvity
        * */

        TextView textViewDa = holder.contentDate;
        textViewDa.setText(ent.getDate());
        TextView textViewCo = holder.contentComments;
        textViewCo.setText(ent.getComments());
        TextView textViewTi = holder.contentTime;
        textViewTi.setText(ent.getTime());
        TextView textViewAc = holder.contentActivity;
        textViewAc.setText(ent.getActivity());
        TextView textViewFe = holder.contentFeeling;
        textViewFe.setText(ent.getFeeling());
        TextView textViewMo = holder.contentMood;
        textViewMo.setText(ent.getMood());
        TextView textViewTh = holder.contentThoughts;
        textViewTh.setText(ent.getThoughts());
        ImageView imageView = holder.imageView;
        ImageView imageViewTime = holder.imageViewTime;





        switch(ent.getActivity()){
            case "Eating/Drinking":
                imageView.setImageResource(R.drawable.eating);
                break;

            case "Working":
                imageView.setImageResource(R.drawable.working);
                break;

            case "Cooking":
                imageView.setImageResource(R.drawable.cooking);
                break;
            case "Chores":
                imageView.setImageResource(R.drawable.chores);
                break;
            case "Hobby":
                imageView.setImageResource(R.drawable.hobby);
                break;
            case "CareWork":
                imageView.setImageResource(R.drawable.care);
                break;
            case "Leisure":
                imageView.setImageResource(R.drawable.leisure);
                break;
            default:
                imageView.setImageResource(R.drawable.other);
                break;

        }




        switch(ent.getTime()){
            case "06:00":
            case "07:00":
            case "08:00":
            case "09:00":
            case "10:00":
            case "11:00":
                imageViewTime.setImageResource(R.drawable.morning1);
                break;

            case "12:00":
            case "13:00":
            case "14:00":
            case "15:00":
            case "16:00":
            case "17:00":
                imageViewTime.setImageResource(R.drawable.day1);
                break;

            case "18:00":
            case "19:00":
            case "20:00":
            case "21:00":
            case "22:00":
            case "23:00":
                imageViewTime.setImageResource(R.drawable.evening1);
                break;

            case "00:00":
            case "01:00":
            case "02:00":
            case "03:00":
            case "04:00":
                imageViewTime.setImageResource(R.drawable.night1);
                break;

        }




        if(entries.get(position).isExpaned()){
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

        }

        else{
            holder.expandedLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }


    }

    /*RecyclerView calls this method to get the size of the data set.
    For example, in an address book app, this might be the total number of addresses.
    RecyclerView uses this to determine when there are no more items that can be displayed.*/
    @Override
    public int getItemCount() {

        return entries.size();
    }

    // Haelt AdapterView aktuell
    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }


    /*
    * Deklaration der Variablen und Verknuepfung mit den zugehoerigen Elementen in den XML Layout Dateien.
    *
    * */

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contentDate;
        private TextView contentTime;
        private TextView contentActivity;
        private TextView contentFeeling;
        private TextView contentMood;
        private TextView contentThoughts;
        private TextView contentComments;
        private ImageView imageView;
        private ImageView downArrow, upArrow, imageViewTime;
        private ConstraintLayout expandedLayout;
        private CardView parent;



        public ViewHolder(View view) {
            super(view);

            parent = itemView.findViewById(R.id.parent);
            contentDate = itemView.findViewById(R.id.contentDate);
            contentTime = itemView.findViewById(R.id.contentTime);


            contentActivity = itemView.findViewById(R.id.ContentActivity);
            contentFeeling = itemView.findViewById(R.id.ContentFeeling);
            contentMood = itemView.findViewById(R.id.ContentMood);
            contentThoughts = itemView.findViewById(R.id.ContentThoughts);
            contentComments = itemView.findViewById(R.id.ContentComments);
            imageView = itemView.findViewById(R.id.imageView);
            downArrow = itemView.findViewById(R.id.btnArrowDown);
            upArrow = itemView.findViewById(R.id.btnArrowUp);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            imageViewTime = itemView.findViewById(R.id.imageViewTime);


            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry entry = entries.get(getAdapterPosition());
                    entry.setExpaned(!entry.isExpaned());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry entry = entries.get(getAdapterPosition());
                    entry.setExpaned(!entry.isExpaned());
                    notifyItemChanged(getAdapterPosition());
                }
            });






        }


    }
}
