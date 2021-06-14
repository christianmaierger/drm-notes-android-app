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

import com.example.drmapp.R;import com.example.drmapp.ui.entry.Entry;
import com.example.drmapp.ui.viewLast.ViewLastFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * Der Adapter beinhaltet die Logik des RecyclerViews und legt fest welche Elemente angezeigt werden.
 * Auf die Objekte in der Array List entries wird ueber .get(position) zugegriffen.
 *
 * */

public class EntryRecViewAdapter extends RecyclerView.Adapter<EntryRecViewAdapter.ViewHolder> {
    private List<Entry> entries = new LinkedList<>();


    public EntryRecViewAdapter(List<Entry> entries) {
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

        TextView textViewDate = holder.contentDate;
        textViewDate.setText(ent.getDate());
        TextView textViewTime = holder.contentTime;
        textViewTime.setText(ent.getTime());
        TextView textViewActivity = holder.contentActivity;
        textViewActivity.setText(ent.getActivity());
        TextView textViewThoughts = holder.contentThoughts;
        textViewThoughts.setText(ent.getThoughts());
        ImageView imageViewEmoji = holder.emoji;
        ImageView imageViewSam1 = holder.sam1;
        ImageView imageViewSam2 = holder.sam2;
        imageViewSam2.setImageResource(R.drawable.sam21);


        switch (ent.getEmoji()){
            case "happy":
                imageViewEmoji.setImageResource(R.drawable.smiling);
                break;
            case "sad":
                imageViewEmoji.setImageResource(R.drawable.crying);
                break;
            case "angry":
                imageViewEmoji.setImageResource(R.drawable.angry);
                break;
            case "anxious":
                imageViewEmoji.setImageResource(R.drawable.anxious);
                break;
            case "annoyed":
                imageViewEmoji.setImageResource(R.drawable.annoyed);
                break;
            case "surprised":
                imageViewEmoji.setImageResource(R.drawable.surprised);
                break;

        }


        switch (ent.getSam1()){
            case 1:
                imageViewSam1.setImageResource(R.drawable.sam21);
                break;
            case 2:
                imageViewSam1.setImageResource(R.drawable.sam22);
                break;
            case 3:
                imageViewSam1.setImageResource(R.drawable.sam23);
                break;
            case 4:
                imageViewSam1.setImageResource(R.drawable.sam24);
                break;
            case 5:
                imageViewSam1.setImageResource(R.drawable.sam25);
                break;

        }

        switch (ent.getSam2()){
            case 1:
                imageViewSam2.setImageResource(R.drawable.sam31);
                break;
            case 2:
                imageViewSam2.setImageResource(R.drawable.sam32);
                break;
            case 3:
                imageViewSam2.setImageResource(R.drawable.sam33);
                break;
            case 4:
                imageViewSam2.setImageResource(R.drawable.sam34);
                break;
            case 5:
                imageViewSam2.setImageResource(R.drawable.sam35);
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
        private TextView contentThoughts;
        private ImageView downArrow, upArrow, emoji, sam1, sam2;
        private ConstraintLayout expandedLayout;
        private CardView parent;



        public ViewHolder(View view) {
            super(view);

            parent = itemView.findViewById(R.id.parent);
            contentDate = itemView.findViewById(R.id.contentDate);
            contentTime = itemView.findViewById(R.id.contentTime);
            contentActivity = itemView.findViewById(R.id.ContentActivity);
            contentThoughts = itemView.findViewById(R.id.ContentThoughts);
            downArrow = itemView.findViewById(R.id.btnArrowDown);
            upArrow = itemView.findViewById(R.id.btnArrowUp);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            emoji = itemView.findViewById(R.id.imageViewEmoji);
            sam1 = itemView.findViewById(R.id.imageViewSAM1);
            sam2 = itemView.findViewById(R.id.imageViewSAM2);




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
