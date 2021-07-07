package com.example.drmapp.ui.viewEntries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drmapp.R;
import com.example.drmapp.model.Entry;

import java.util.ArrayList;
import java.util.List;


/**
 * Der Adapter beinhaltet die Logik des RecyclerViews und legt fest welche Elemente angezeigt werden.
 * Es wird geprüft, ob es sich bei einem Eintrag um einen Quick Entry handelt.
 * Anschließend wird die Ansicht der Entries wird definiert, in Abhängigkeit davon, ob es ein Quick Entry ist oder ein "Standard" Entry ist.
 * Zudem wurde hier das Aus- und Einklappen der Einträge implementiert.
 */
public class EntryRecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Entry> entries = new ArrayList<>();
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;

    /*
     * Zwei View Types werden hier angelegt, eines für die Quick Entries und einen für die Standard Entries.
     * Die Unterschiedung basiert darauf, ob die Variable "isQuickEntry" beim Ausfüllen des Fragebogens auf true/false gesetzt wurde.
     * LAYOUT_ONE beinhaltet das Layout für die Standard Entries,
     * LAYOUT_TWO beinhaltet das Layout für die Quick Entries.
     * */
    @Override
    public int getItemViewType(int position) {
        Entry ent = entries.get(position);
        if (ent.isQuickEntry())
            return LAYOUT_TWO;
        else
            return LAYOUT_ONE;
    }

    /* "RecyclerView ruft diese Methode immer dann auf, wenn es einen neuen ViewHolder erstellen muss.
       Die Methode erzeugt und initialisiert den ViewHolder und die zugehörige View,
       füllt aber nicht den Inhalt der View aus - der ViewHolder ist noch nicht an bestimmte Daten gebunden.
       an bestimmte Daten gebunden."
       - https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=CjwKCAjwrPCGBhALEiwAUl9X03x2b6DUzt8ZO-JnmA3nva30ly4lWlosBfry-VtzE8xSWLZQxuIOBxoCOLYQAvD_BwE&gclsrc=aw.ds
       */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

       /*
       Logik fuer die zwei verschiedenen Layouts.
       Wobei LAYOUT_ONE fuer den regulaeren Eintrag steht und daher auch das layout von fragment_entry_list_item verwendet,
       wohingegen LAYOUT_TWO fuer den quickEntry steht und das layout fragment_entry_list_item_quick verwendet.
       */
        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_entry_list_item, parent, false);
            viewHolder = new ViewHolderOne(view, viewType);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_entry_list_item_quick, parent, false);
            viewHolder = new ViewHolderTwo(view, viewType);

        }
        return viewHolder;
    }

    /* "RecyclerView ruft diese Methode auf, um einen ViewHolder mit Daten zu verknuepfen.
    Die Methode holt die entsprechenden Daten und fuellt mit den Daten das Layout des View
    holder's Layout. Wenn die RecyclerView zum Beispiel eine Liste von Namen anzeigt,
    koennte die Methode den entsprechenden Namen in der Liste finden und das TextView-Widget des Ansichtshalters ausfüllen.
    Halter das TextView-Widget fuellen.
    Position ist die Position innerhalb des RecyclerView Adapters."
    - https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=CjwKCAjwrPCGBhALEiwAUl9X03x2b6DUzt8ZO-JnmA3nva30ly4lWlosBfry-VtzE8xSWLZQxuIOBxoCOLYQAvD_BwE&gclsrc=aw.ds

    Ent steht hier fuer einen einzelnen Eintrag, dessen Daten dann den jeweiligen Views zugeordnet werden.
    Zudem werden hier die Views definiert, die sich in Abhaenigkeit von den eingegebenen Daten aendern (Emojis, Self-Assessment Manikins, Thoughts)
    */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Entry ent = entries.get(position);

        if (holder.getItemViewType() == LAYOUT_ONE) {
            TextView textViewDate = ((ViewHolderOne) holder).contentDate;
            textViewDate.setText(ent.getDate());
            TextView textViewTime = ((ViewHolderOne) holder).contentTime;
            textViewTime.setText(ent.getTime());
            TextView textViewActivity = ((ViewHolderOne) holder).contentActivity;
            textViewActivity.setText(ent.getActivity());
            TextView textViewThoughts = ((ViewHolderOne) holder).contentThoughts;
            textViewThoughts.setText(ent.getThoughts());
            TextView textViewEmoji = ((ViewHolderOne) holder).emoji;
            // Die Emojis werden als int gespeichert, um die Ansicht als Emojis zu bekommen müssen sie jedoch vorher in Strings umgewandelt werden
            textViewEmoji.setText(new String(Character.toChars(ent.getEmoji())));
            ImageView imageViewSam1 = ((ViewHolderOne) holder).sam1;
            ImageView imageViewSam2 = ((ViewHolderOne) holder).sam2;
            ImageView imageViewSam3 = ((ViewHolderOne) holder).sam3;

            /* SELF ASSESSMENT MANIKINS
             * Die Speicherung erfolg in int und die jeweiligen Bilder werden über die Switch Cases wieder eingefügt
             * Sollte keine Angabe gemacht sein, dann wird ein leeres Bild angezeigt.
             *  */
            // Valence
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
                default: imageViewSam1.setImageResource(R.drawable.samdefault);
            }

            //Excitement
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
               default: imageViewSam2.setImageResource(R.drawable.samdefault);
            }

            //Dominance
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
               default: imageViewSam3.setImageResource(R.drawable.samdefault);
            }

            // Pfeil bei ausklappbaren Layout ein- und ausblenden
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
            TextView textViewEmoji = ((ViewHolderTwo) holder).emoji_q;
            // Die Emojis werden als int gespeichert, um die Ansicht als Emojis zu bekommen müssen sie jedoch vorher in Strings umgewandelt werden
            textViewEmoji.setText(new String(Character.toChars(ent.getEmoji())));

        }
    }

    //Ermittelt Größe des Datensatzes
    @Override
    public int getItemCount() {
        return entries.size();
    }

    // Haelt AdapterView aktuell
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    // Wird verwendet um ein Element auszuwählen, das gelöscht werden soll
    public Entry getItem(final int position) {
        return entries.get(position);
    }

    /*
     * Die ViewHolder dienen der Deklaration der Variablen und Verknuepfung mit den zugehoerigen Elementen in den XML Layout Dateien.
     * Auch hier wurden wieder zwei definiert, einen fuer den QuickEntry (ViewHolderTwo) und einen fuer den normalen Entry (ViewHolderOne)
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

            // On-Click-Listener fuer die Pfeile nach unten, damit die Einträge im RecView sich ein- und ausklappen lassen
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
        }}


    // Der ViewHolderTwo ist  nur ein "gekuerzter" ViewHolderOne
    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        private TextView contentDate_q;
        private TextView contentTime_q;
        private TextView contentActivity_q, emoji_q;
        private CardView parent_q;
        private int viewType;

        public int getViewType() {
            return viewType;
        }
        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public ViewHolderTwo(View itemView, int viewType) {
            super(itemView);

            parent_q = itemView.findViewById(R.id.parent_q);
            contentDate_q = itemView.findViewById(R.id.contentDate_q);
            contentTime_q = itemView.findViewById(R.id.contentTime_q);
            contentActivity_q = itemView.findViewById(R.id.ContentActivity_q);
            emoji_q = itemView.findViewById(R.id.imageViewEmoji_q);

            this.viewType = viewType;

            // Auch hier die OnClickListeners auf den Pfeilen, um ein Ein- und Ausklappen der Einträge zu ermöglichen

        }}}