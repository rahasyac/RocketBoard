package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FlightDataSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_data_selector);

        File directory = this.getFilesDir();
        String[] files = directory.list(new FilenameFilter(){
            @Override
            public boolean accept(File directory, String name){
                return name.startsWith("Flight:");
            }
        });


        RecyclerView rvFiles = (RecyclerView) findViewById(R.id.rvFiles);
        FilesAdapter adapter = new FilesAdapter(files);
        rvFiles.setAdapter(adapter);
        rvFiles.setLayoutManager(new LinearLayoutManager(this));

    }
}
