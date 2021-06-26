package com.example.drmapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.drmapp.database.AppDatabase;
import com.example.drmapp.ui.entry.Entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class ExportToDBWorker extends Worker {
    Context context;

    public ExportToDBWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result doWork() {

        File exportDir = new File(context.getFilesDir(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, "exprotedDB" + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = AppDatabase.getInstance(context).query(("SELECT * FROM entry"), null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to export
                String arrStr[] = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() - 1; i++) {
                    arrStr[i] = curCSV.getString(i);
                    System.out.println(arrStr[i]);
                    if (arrStr[i].equals("128545")) {
                        arrStr[i] = "Angry";
                    } else if (arrStr[i].equals("128170")) {
                        arrStr[i] = "Competent";
                    } else if (arrStr[i].equals("128546")) {
                        arrStr[i] = "Sad";
                    } else if (arrStr[i].equals("128560")) {
                        arrStr[i] = "Terriied";
                    } else if (arrStr[i].equals("128531")) {
                        arrStr[i] = "Unsure";
                    }
                    System.out.println(arrStr[i]);
                }
                    csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
            System.out.print("DB was printed to CSV");
           // Toast.showToast(this, "Exported", Toast.LENGTH_SHORT);
        } catch (Exception sqlEx) {
         //("MainActivity", sqlEx.getMessage(), sqlEx);
        }
        return Result.success();
    }

    }
