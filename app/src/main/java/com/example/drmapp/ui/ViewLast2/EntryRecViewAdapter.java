package com.example.drmapp.ui.ViewLast2;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.List;


/**
 *
 * Der Adapter beinhaltet die Logik des RecyclerViews und legt fest welche Elemente angezeigt werden.
 * Auf die Objekte in der Array List entries wird ueber .get(position) zugegriffen.
 *
 * */



public class EntryRecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Entry> entries = new ArrayList<>();
    private static final int LAYOUT_ONE=0;
    private static final int LAYOUT_TWO=1;
    private RecyclerView.ViewHolder holder;
   // private int position;

    @Override
    public int getItemViewType(int position) {
        Entry ent = entries.get(position);
        if(ent.isQuickEntry())
        return LAYOUT_TWO;
            else
        return LAYOUT_ONE;
    }

    public EntryRecViewAdapter() {

    }


    /* RecyclerView ruft diese Methode immer dann auf, wenn es einen neuen ViewHolder erstellen muss.
                Die Methode erzeugt und initialisiert den ViewHolder und die zugehörige View,
                füllt aber nicht den Inhalt der View aus - der ViewHolder ist noch nicht an bestimmte Daten gebunden.
                an bestimmte Daten gebunden. */

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {





        // Unterscheidung zwischen den zwei Layouts jenachdem ob die Variable isQuickEntry im Eintrag auf true/false gesetzt ist.
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
           viewHolder = new ViewHolderOne(view, viewType);
       }
       else
       {
           view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.fragment_entry_list_item_quick,parent,false);
           viewHolder = new ViewHolderTwo(view, viewType);

       }
       return viewHolder;

    }




    /* RecyclerView ruft diese Methode auf, um einen ViewHolder mit Daten zu verknuepfen.
    Die Methode holt die entsprechenden Daten und fuellt mit den Daten das Layout des View
    holder's Layout. Wenn die RecyclerView zum Beispiel eine Liste von Namen anzeigt,
    koennte die Methode den entsprechenden Namen in der Liste finden und das TextView-Widget des Ansichtshalters ausfüllen.
    Halter das TextView-Widget fuellen.
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
        // hier war das Problem, ViewHolder1 und 2 sind ja zwei unterschiedliche Objekte die von zwei unterschiedlichen Klassen erzeugt werden
        // die methode getItemViewType ist allerdings von deren Oberklasse ViewHolder und kann nicht überschrieben werden
        // da final, die hat dann nicht so funktioniert wie du es gebraucht hast, hat true ausgegeben
        // und der Compiler hat auch versucht ViewHolder2 Objekte in ViewHolderOne zu casten, was das Programm zerhauen hat
      //  if(holder instanceof //ViewHolderOne) {

        if(holder.getItemViewType() == LAYOUT_ONE){

             // SwitchCase um die Bilder im RecyclerView zu aendern in Abhaenigkeit von der eingegebenen Actvity
            TextView textViewDate =  ((ViewHolderOne) holder).contentDate;
            textViewDate.setText(ent.getDate());
            TextView textViewTime = ((ViewHolderOne) holder).contentTime;
            textViewTime.setText(ent.getTime());
            TextView textViewActivity = ((ViewHolderOne) holder).contentActivity;
            textViewActivity.setText(ent.getActivity());
            TextView textViewThoughts = ((ViewHolderOne) holder).contentThoughts;
            textViewThoughts.setText(ent.getThoughts());
            TextView textViewEmoji = ((ViewHolderOne) holder).emoji;
            textViewEmoji.setText(new String (Character.toChars(ent.getEmoji())));
            ImageView imageViewSam1 = ((ViewHolderOne) holder).sam1;
            ImageView imageViewSam2 = ((ViewHolderOne) holder).sam2;
            ImageView imageViewSam3 = ((ViewHolderOne) holder).sam3;


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
                    imageViewSam3.setImageResource(R.drawable.sam11);
                    break;
                case 2:
                    imageViewSam3.setImageResource(R.drawable.sam12);
                    break;
                case 3:
                    imageViewSam3.setImageResource(R.drawable.sam13);
                    break;
                case 4:
                    imageViewSam3.setImageResource(R.drawable.sam14);
                    break;
                case 5:
                    imageViewSam3.setImageResource(R.drawable.sam15);
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

    /*RecyclerView ruft diese Methode auf, um die Größe des Datensatzes zu ermitteln.
    In einer Adressbuch-App könnte dies zum Beispiel die Gesamtzahl der Adressen sein.
    RecyclerView verwendet dies, um festzustellen, wann es keine Elemente mehr gibt, die angezeigt werden können.*/
    @Override
    public int getItemCount() {

        return entries.size();
    }

    // Haelt AdapterView aktuell
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public Entry getItem(final int position){

        return entries.get(position);
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
        private int viewType;



        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public ViewHolderOne(View view, int viewType) {
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
            this.viewType = viewType;





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
        private int viewType;

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public ViewHolderTwo(View itemView, int viewType){
            super(itemView);

            parent_q = itemView.findViewById(R.id.parent_q);
            contentDate_q = itemView.findViewById(R.id.contentDate_q);
            contentTime_q = itemView.findViewById(R.id.contentTime_q);
            contentActivity_q = itemView.findViewById(R.id.ContentActivity_q);
            contentThoughts_q = itemView.findViewById(R.id.ContentThoughts_q);
            downArrow_q = itemView.findViewById(R.id.btnArrowDown_q);
            upArrow_q = itemView.findViewById(R.id.btnArrowUp_q);
            expandedLayout_q = itemView.findViewById(R.id.expandedLayout_q);
            this.viewType = viewType;



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

