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
 * Der Adapter stellt drei Methoden zur Auswahl, was die genau machen habe ich darueber geschrieben.
 * Ausserdem legt es fest, was in einem View der Recycler View enthalten ist, in dem es die Views aus "fragment_entry_list_item.xml" hier sucht und einbindet
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
        //Verbindet die Elemente aus dem Layout mit den Elementen aus der Entry Klasse!

        // Get the data model based on position, so to speak index of the list with entries
        Entry ent = entries.get(position);

        // Set item views based on your views and data model
        // therefore one has to follow the following pattern of definining views, setting them with the holder instance vars
        // then set the text/other value by getting the coressponding value from the entry in the list at the specific position
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




        switch(ent.getActivity().toString()){
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

        /*if(ent.isExpaned()){
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

        }else{
            holder.expandedLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }*/






      // holder.contentDate.setText(entries.get(position).getDate());
       //holder.contentTime.setText(entries.get(position).getTime());

      /* holder.downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ent.setExpaned(!ent.isExpaned());
                notifyItemChanged(position);

            }
        });

        holder.upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ent.setExpaned(!ent.isExpaned());
                notifyItemChanged(position);
            }
        });*/


    }

    /*RecyclerView calls this method to get the size of the data set.
    For example, in an address book app, this might be the total number of addresses.
    RecyclerView uses this to determine when there are no more items that can be displayed.*/
    @Override
    public int getItemCount() {

        return entries.size();
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
        // Haelt AdapterView aktuell!
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /* Generiert View Objekte.
        Hier einfuegen: Alle Views, die in einem Item der Liste enthalten sein sollen
        Erst deklarieren! */

        //TODO: Die restlichen Views aus "fragemnt_entry_list_item.xml" einfuegen


        private TextView contentDate;
        private TextView contentTime;
        private TextView contentActivity;
        private TextView contentFeeling;
        private TextView contentMood;
        private TextView contentThoughts;
        private TextView contentComments;
        private ImageView imageView;
        private ImageView downArrow, upArrow;
        private ConstraintLayout expandedLayout;

       // musste zu CardView geändert werden, verstehe es noch nicht ganz aber als RelativeLayout wirft es einen error
       private CardView parent;


        public ViewHolder(View view) {
            super(view);
            // Dann hier unten ueber find View finden! Das passt total so
            parent = itemView.findViewById(R.id.parent);
            contentDate = itemView.findViewById(R.id.contentDate);
            contentTime = itemView.findViewById(R.id.contentTime);


            contentActivity = itemView.findViewById(R.id.ContentActivity);
            contentFeeling = itemView.findViewById(R.id.ContentFeeling);
            contentMood = itemView.findViewById(R.id.ContentMood);
            contentThoughts = itemView.findViewById(R.id.ContentThoughts);
            contentComments = itemView.findViewById(R.id.ContentComments);
            imageView = itemView.findViewById(R.id.imageView);
            downArrow = imageView.findViewById(R.id.btnArrowDown);
            upArrow = imageView.findViewById(R.id.btnArrowUp);
            expandedLayout = imageView.findViewById(R.id.expandedLayout);

           /* downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry entry = entries.get(getAdapterPosition());
                    entry.setExpaned(!entry.isExpaned());
                    notifyItemChanged(getAdapterPosition());
                }
            });*/




        }


    }
}


/*

*/
/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 *//*

public class EntryRecViewAdapter extends RecyclerView.Adapter<EntryRecViewAdapter.ViewHolder> {
    //

    private final List<DummyItem> mValues;

    public EntryRecViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    */
/* RecyclerView calls this method whenever it needs to create a new ViewHolder.
    The method creates and initializes the ViewHolder and its associated View,
    but does not fill in the view's contents—the ViewHolder has not yet been bound
    to specific data.*//*

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history_list_item, parent, false);
        return new ViewHolder(view);

    }

    */
/*RecyclerView calls this method to associate a ViewHolder with data.
    The method fetches the appropriate data and uses the data to fill in the view
    holder's layout. For example, if the RecyclerView dislays a list of names,
    the method might find the appropriate name in the list and fill in the view
    holder's TextView widget.
    Position ist die Position innerhalb des RecyclerView Adapters.
    *//*

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    */
/*RecyclerView calls this method to get the size of the data set.
    For example, in an address book app, this might be the total number of addresses.
    RecyclerView uses this to determine when there are no more items that can be displayed.*//*

    @Override
    public int getItemCount() {
        return mValues.size();
        //return historyItems.size();
    }

    */
/*
     // Haelt AdapterView aktuell!
    public void setHistoryItems(ArryList<HistoryItem> historyItem){
        this.historyItem = historyItem;
        notifyDataSetChanged();
    } *//*


    public class ViewHolder extends RecyclerView.ViewHolder {
        */
/* Generiert View Objekte. Generiert Fragment_History_List.
        Hier einfuegen: Alle Views, die in einem Item der Liste enthalten sein sollen
        Erst deklarieren! *//*


        private TextView txtContent;
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            // Dann hier unten ueber find View finden!
            txtContent = itemView.findViewById(R.id.txtContent);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}*/
