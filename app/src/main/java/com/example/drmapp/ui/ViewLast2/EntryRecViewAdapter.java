package com.example.drmapp.ui.ViewLast2;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drmapp.R;
import com.example.drmapp.ui.entry.Entry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


/**
 *
 * Der Adapter beinhaltet die Logik des RecyclerViews und legt fest welche Elemente angezeigt werden.
 * Auf die Objekte in der Array List entries wird ueber .get(position) zugegriffen.
 *
 * */



public class EntryRecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Entry> entries = new ArrayList<>();
    private static final int LAYOUT_ONE=0;
    private static final int LAYOUT_TWO=1;
    private RecyclerView.ViewHolder holder;
    private int position;


    public EntryRecViewAdapter(ArrayList<Entry> entries) {
        this.entries = entries;
    }


    /* RecyclerView calls this method whenever it needs to create a new ViewHolder.
                The method creates and initializes the ViewHolder and its associated View,
                but does not fill in the view's contents—the ViewHolder has not yet been bound
                to specific data.*/

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // Unterscheidung zwischen den zwei Layouts jenachdem ob die Variable isQuickEntry im Eintrag auf true/false gesetzt ist.


       Entry ent = entries.get(position);
       if(ent.isQuickEntry())
           viewType = LAYOUT_TWO;
       else
           viewType = LAYOUT_ONE;


       View view = null;
       RecyclerView.ViewHolder viewHolder = null;

       /*

       Logik fuer die zwei verschiedenen Layouts.
       Wobei LAYOUT_ONE fuer den regulaeren Eintrag steht und daher auch das layout von fragment_entry_list_item verwendet,
       wohingegen LAYOUT_TWO fuer den quickEntry steht und das layout fragment_entry_list_item_quick verwendet.

       */

       if(viewType==LAYOUT_ONE)
       {
           view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.fragment_entry_list_item, parent, false);
           viewHolder = new ViewHolderOne(view);
       }
       else
       {
           view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.fragment_entry_list_item_quick,parent,false);
           viewHolder = new ViewHolderTwo(view);

       }
       return viewHolder;

    }

    /*RecyclerView calls this method to associate a ViewHolder with data.
    The method fetches the appropriate data and uses the data to fill in the view
    holder's layout. For example, if the RecyclerView dislays a list of names,
    the method might find the appropriate name in the list and fill in the view
    holder's TextView widget.
    Position ist die Position innerhalb des RecyclerView Adapters.
    */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Entry ent = entries.get(position);

        /*
        * Hier wird ent steht hier fuer einen einzelnen Eintrag, dessen Daten dann den jeweiligen Views zugeordnet werden.
        * Zudem definiere ich hier die Logik der Views, die sich in Abhaenigkeit von den eingegebenen Daten aendern (Emojis, Self-Assessment Manikins)
        * */

        // Logik fuer normalen Entry
        if(holder.getItemViewType()==LAYOUT_ONE) {

             // SwitchCase um die Bilder im RecyclerView zu aendern in Abhaenigkeit von der eingegebenen Actvity
            TextView textViewDate = ((ViewHolderOne) holder).contentDate;
            textViewDate.setText(ent.getDate());
            TextView textViewTime = ((ViewHolderOne) holder).contentTime;
            textViewTime.setText(ent.getTime());
            TextView textViewActivity = ((ViewHolderOne) holder).contentActivity;
            textViewActivity.setText(ent.getActivity());
            TextView textViewThoughts = ((ViewHolderOne) holder).contentThoughts;
            textViewThoughts.setText(ent.getThoughts());
            TextView imageViewEmoji = ((ViewHolderOne) holder).emoji;
            ImageView imageViewSam1 = ((ViewHolderOne) holder).sam1;
            ImageView imageViewSam2 = ((ViewHolderOne) holder).sam2;
            ImageView imageViewSam3 = ((ViewHolderOne) holder).sam3;


             int unicode_angry = 0x1F621; //angry
             int unicode_sad = 0x1F622; //sad
             int unicode_anxious = 0x1F630; //anxious
             int unicode_proud = 0x1F4AA; //proud
             int unicode_happy= 0xFE0F; //happy
             int unicode_exhausted = 0x1F613; //exhausted



            switch (ent.getEmoji()) {
                case "happy":
                    imageViewEmoji.setText(new String(Character.toChars(unicode_happy)));
                    break;
                case "sad":
                    imageViewEmoji.setText(new String(Character.toChars(unicode_sad)));
                    break;
                case "angry":
                    imageViewEmoji.setText(new String(Character.toChars(unicode_angry)));
                    break;
                case "anxious":
                    imageViewEmoji.setText(new String(Character.toChars(unicode_anxious)));
                    break;
                case "exhausted":
                    imageViewEmoji.setText(new String(Character.toChars(unicode_exhausted)));;
                    break;
                case "proud":
                    imageViewEmoji.setText(new String(Character.toChars(unicode_proud)));
                    break;

            }

            //SAMS Valence
            switch (ent.getSam1()) {
                case 1:
                    imageViewSam1.setImageResource(R.drawable.sam1);
                    break;
                case 2:
                    imageViewSam1.setImageResource(R.drawable.sam2);
                    break;
                case 3:
                    imageViewSam1.setImageResource(R.drawable.sam3);
                    break;
                case 4:
                    imageViewSam1.setImageResource(R.drawable.sam4);
                    break;
                case 5:
                    imageViewSam1.setImageResource(R.drawable.sam5);
                    break;

            }

            //SAMS Excitement
            switch (ent.getSam2()) {
                case 1:
                    imageViewSam2.setImageResource(R.drawable.sam6);
                    break;
                case 2:
                    imageViewSam2.setImageResource(R.drawable.sam7);
                    break;
                case 3:
                    imageViewSam2.setImageResource(R.drawable.sam8);
                    break;
                case 4:
                    imageViewSam2.setImageResource(R.drawable.sam9);
                    break;
                case 5:
                    imageViewSam2.setImageResource(R.drawable.sam10);
                    break;

            }

            //SAMS Dominance
            switch (ent.getSam3()) {
                case 1:
                    imageViewSam3.setImageResource(R.drawable.sam13);
                    break;
                case 2:
                    imageViewSam3.setImageResource(R.drawable.sam11);
                    break;
                case 3:
                    imageViewSam3.setImageResource(R.drawable.sam12);
                    break;
                case 4:
                    imageViewSam3.setImageResource(R.drawable.sam15);
                    break;
                case 5:
                    imageViewSam3.setImageResource(R.drawable.sam16);
                    break;

            }


            // Das ist fuer den Pfeil, bei dem ausklappbaren Layout
            if (entries.get(position).isExpaned()) {
                ((ViewHolderOne) holder).expandedLayout.setVisibility(View.VISIBLE);
                ((ViewHolderOne) holder).downArrow.setVisibility(View.GONE);

            } else {
                ((ViewHolderOne) holder).expandedLayout.setVisibility(View.GONE);
                ((ViewHolderOne) holder).downArrow.setVisibility(View.VISIBLE);
            }
        }

        // Logik fuer QuickEntry
        else {

            TextView textViewDate = ((ViewHolderTwo) holder).contentDate_q;
            textViewDate.setText(ent.getDate());
            TextView textViewTime = ((ViewHolderTwo) holder).contentTime_q;
            textViewTime.setText(ent.getTime());
            TextView textViewActivity = ((ViewHolderTwo) holder).contentActivity_q;
            textViewActivity.setText(ent.getActivity());
            TextView textViewThoughts = ((ViewHolderTwo) holder).contentThoughts_q;
            textViewThoughts.setText(ent.getThoughts());

            if (entries.get(position).isExpaned()) {
                ((ViewHolderTwo) holder).expandedLayout_q.setVisibility(View.VISIBLE);
                ((ViewHolderTwo) holder).downArrow_q.setVisibility(View.GONE);

            } else {
                ((ViewHolderTwo) holder).expandedLayout_q.setVisibility(View.GONE);
                ((ViewHolderTwo) holder).downArrow_q.setVisibility(View.VISIBLE);
            }
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
    * Die ViewHolder dienen der Deklaration der Variablen und Verknuepfung mit den zugehoerigen Elementen in den XML Layout Dateien.
    * Auch hier habe ich wieder zwei definiert, einen fuer den QuickEntry (ViewHolderTwo) und einen fuer den normalen Entry (ViewHolderOne)
    * */

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        private TextView contentDate;
        private TextView contentTime;
        private TextView contentActivity;
        private TextView contentThoughts, emoji;
        private ImageView downArrow, upArrow, sam1, sam2, sam3;
        private ConstraintLayout expandedLayout;
        private CardView parent;



        public ViewHolderOne(View view) {
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
            sam3 = itemView.findViewById(R.id.imageViewSAM3);




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


    // Der ViewHolderTwo ist  nur ein "gekuerzter" ViewHolderOne
    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        private TextView contentDate_q;
        private TextView contentTime_q;
        private TextView contentActivity_q;
        private TextView contentThoughts_q;
        private ImageView downArrow_q, upArrow_q;
        private ConstraintLayout expandedLayout_q;
        private CardView parent_q;

        public ViewHolderTwo(View itemView){
            super(itemView);

            parent_q = itemView.findViewById(R.id.parent_q);
            contentDate_q = itemView.findViewById(R.id.contentDate_q);
            contentTime_q = itemView.findViewById(R.id.contentTime_q);
            contentActivity_q = itemView.findViewById(R.id.ContentActivity_q);
            contentThoughts_q = itemView.findViewById(R.id.ContentThoughts_q);
            downArrow_q = itemView.findViewById(R.id.btnArrowDown_q);
            upArrow_q = itemView.findViewById(R.id.btnArrowUp_q);
            expandedLayout_q = itemView.findViewById(R.id.expandedLayout_q);





            downArrow_q.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry entry = entries.get(getAdapterPosition());
                    entry.setExpaned(!entry.isExpaned());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow_q.setOnClickListener(new View.OnClickListener() {
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

