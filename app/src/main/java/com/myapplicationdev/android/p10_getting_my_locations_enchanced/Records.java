package com.myapplicationdev.android.p10_getting_my_locations_enchanced;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Records extends AppCompatActivity {

    Button refresh, favourite;
    TextView num_of_record;
    ListView lv;
    ArrayList<String> records;
    ArrayAdapter<String> adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        refresh = findViewById(R.id.btnRefresh);
        favourite = findViewById(R.id.btnFav);
        num_of_record = findViewById(R.id.tvRecords);
        lv = findViewById(R.id.lvRecords);
        records = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        lv.setAdapter(adapter);
        context = this.getApplicationContext();

        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/P10", "location.txt");
            if (!file.exists()) {
                if (file.createNewFile())
                    Log.d("Create new file: ", "created new file");
                else
                    Log.d("Create new file: ", "failed to create new file");
            } else
                Log.d("Create new file: ", "file exist");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
            scanner.close();
            adapter.notifyDataSetChanged();
            num_of_record.setText("Number of record: " + records.size());
        } catch (Exception e) { e.printStackTrace(); }

        refresh.setOnClickListener(v -> {
            try {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/P10", "location.txt");
                if (!file.exists()) {
                    if (file.createNewFile())
                        Log.d("Create new file: ", "created new file");
                    else
                        Log.d("Create new file: ", "failed to create new file");
                } else
                    Log.d("Create new file: ", "file exist");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    records.add(scanner.nextLine());
                }
                scanner.close();
                adapter.notifyDataSetChanged();
                num_of_record.setText("Number of record: " + records.size());
            } catch (Exception e) { e.printStackTrace(); }
        });

        favourite.setOnClickListener(v -> {
            try {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/P10", "favourite.txt");
                if (!file.exists()) {
                    if (file.createNewFile())
                        Log.d("Create new file: ", "created new file");
                    else
                        Log.d("Create new file: ", "failed to create new file");
                } else
                    Log.d("Create new file: ", "file exist");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    records.add(scanner.nextLine());
                }
                scanner.close();
                adapter.notifyDataSetChanged();
                num_of_record.setText("Number of record: " + records.size());
            } catch (Exception e) { e.printStackTrace(); }
        });

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Add this location in your favorite list?");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/P10", "favourite.txt");
                            if (!file.exists()) {
                                if (file.createNewFile())
                                    Log.d("Create new favourite: ", "created new file");
                                else
                                    Log.d("Create new favourite: ", "failed to create new file");
                            } else
                                Log.d("Create new favourite: ", "file exist");
                            FileWriter writer = new FileWriter(file, true);
                            writer.write(records.get(position));
                            writer.flush();
                            writer.close();
                        } catch (Exception e) { e.printStackTrace(); }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
