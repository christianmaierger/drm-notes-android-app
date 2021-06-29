package com.example.drmapp.database.export;



import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.drmapp.database.AppDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;


public class ExportToDBWorker extends Worker {
    Context context;

    public ExportToDBWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }


    @NotNull
    @Override
    public Result doWork() {

        File exportDir = new File(context.getFilesDir(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, "exprotedDB" + ".csv");
        try {
            file.createNewFile();

            // Mit Hilfe eines externen CSVWriters und eines cursors wird der DB Query Zeilen und
            // Spaltenweise traversiert und dann Line für Line in ein CSV File geschrieben
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            Cursor cursor = AppDatabase.getInstance(context).query(("SELECT * FROM entry"), null);
            csvWriter.writeNext(cursor.getColumnNames());
            while (cursor.moveToNext()) {
               // Der Cursor durchläuft die entry Tabelle der DB Spalte für Spalte
                String arrayOfColumsOfOneLine[] = new String[cursor.getColumnCount()];
                // Die Forschleife durchläuft dann jede Spalte Zeile für Zeile
                for (int i = 0; i < cursor.getColumnCount() - 1; i++) {
                    arrayOfColumsOfOneLine[i] = cursor.getString(i);
                    System.out.println(arrayOfColumsOfOneLine[i]);
                    // die Spalten die int codes für Emojis enthalten werden hier ersetzt,
                    // wenn es vordefinierte sind
                   if(arrayOfColumsOfOneLine[i]!=null) {
                       switch (arrayOfColumsOfOneLine[i]) {
                           case "128545":
                               arrayOfColumsOfOneLine[i] = "Angry";
                               break;
                           case "128170":
                               arrayOfColumsOfOneLine[i] = "Competent";
                               break;
                           case "128546":
                               arrayOfColumsOfOneLine[i] = "Sad";
                               break;
                           case "128560":
                               arrayOfColumsOfOneLine[i] = "Terrified";
                               break;
                           case "128531":
                               arrayOfColumsOfOneLine[i] = "Unsure";
                               break;
                       }
                   }
                }
                    csvWriter.writeNext(arrayOfColumsOfOneLine);
            }
            csvWriter.close();
            cursor.close();

            // Weil dieser Worker auf einem Background Thread läuft, kann er nicht direkt auf die UI
            // zugreifen, dies wird hier sehr einfach gelöst, wenn auch wohl nicht am eleganstenten,
            //dadurch dass einfach ein Handler auf einem neuen Thread ein sehr kurzes Runnable ausführt,
            // dass einfach einen Text zeigt, ob der Exprot erfolg hatte oder nicht
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "DB Exported To CSV", Toast.LENGTH_SHORT).show();
                }
            }, 1000 );
        } catch (Exception sqlEx) {
            sqlEx.printStackTrace();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Sry, DB Export failed", Toast.LENGTH_SHORT).show();
                }
            }, 1000 );
            return Result.failure();
        }
        return Result.success();
    }

    }
