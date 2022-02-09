package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    //Buttons
    Button conn;
    Button trac;
    Button fli;
    Button previousFlights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conn = findViewById(R.id.connect);
        trac = findViewById(R.id.track);
        fli = findViewById(R.id.flight);
        previousFlights = findViewById(R.id.PreviousFlights);
        conn.setOnClickListener(this);
        trac.setOnClickListener(this);
        fli.setOnClickListener(this);
        previousFlights.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.connect:
                //Did this for now because lazy
                startActivity(new Intent(MainActivity.this, FlightVisualizationActivity.class));
                break;
            case R.id.track:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.flight:
                startActivity(new Intent(MainActivity.this, InFlightActivity.class));
                break;

            case R.id.PreviousFlights:
                startActivity(new Intent(MainActivity.this, FlightDataSelector.class));
        }
    }
}
