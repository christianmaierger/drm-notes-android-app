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
    /*private static final int LAYOUT_ONE=0;
    private static final int LAYOUT_TWO=1;*/
    private RecyclerView.ViewHolder holder;
    private int position;

    /*public int getItemViewType(int position){
        if(position==0)
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }*/


    public EntryRecViewAdapter(ArrayList<Entry> entries) {

        this.entries = entries;
    }


    /* RecyclerView calls this method whenever it needs to create a new ViewHolder.
                The method creates and initializes the ViewHolder and its associated View,
                but does not fill in the view's contents—the ViewHolder has not yet been bound
                to specific data.*/
   @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(parent.getContext())
         //       .inflate(R.layout.fragment_entry_list_item, parent, false);
       // return new ViewHolder(view);
       Entry ent = entries.get(position);

       View view = null;
       RecyclerView.ViewHolder viewHolder = null;
       if(!ent.isQuick())
       {
           view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.fragment_entry_list_item, parent, false);
           viewHolder = new ViewHolderOne(view);
       }
       else
       {
           view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.fragment_entry_list_item,parent,false);
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

        if(!ent.isQuick()) {


            // Get the data model based on position, so to speak index of the list with entries
            //Entry ent = entries.get(position);

            // Set item views based on your views and data model
            // therefore one has to follow the following pattern of definining views, setting them with the holder instance vars
            // then set the text/other value by getting the coressponding value from the entry in the list at the specific position

            /*
             * SwitchCase um die Bilder im RecyclerView zu aendern in Abhaenigkeit von der eingegebenen Actvity
             * */

            //ViewHolderOne viewHolderOne = (ViewHolderOne)holder;

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




            if (entries.get(position).isExpaned()) {
                ((ViewHolderOne) holder).expandedLayout.setVisibility(View.VISIBLE);
                ((ViewHolderOne) holder).downArrow.setVisibility(View.GONE);

            } else {
                ((ViewHolderOne) holder).expandedLayout.setVisibility(View.GONE);
                ((ViewHolderOne) holder).downArrow.setVisibility(View.VISIBLE);
            }
        }

        else {
            //ViewHolderOne vaultItemHolder = (ViewHolderOne) holder;
            //vaultItemHolder.name.setText(displayText);
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

    public class ViewHolderTwo extends RecyclerView.ViewHolder {

        public ViewHolderTwo(View itemView){
            super(itemView);
        }

    }

}

