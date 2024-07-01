package com.example.appviagens.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appviagens.R;

public class TripTwoActivity extends AppCompatActivity {
    private SeekBar sk;
    private EditText origin;
    private EditText destiny;
    private EditText plate_car;
    private EditText observation;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagetwo);
        Trip trip = Trip.getInstance();
        sk = findViewById(R.id.seekBar2);
        sk.setMax(100);
        sk.setProgress(66);

        origin = findViewById(R.id.textOrigin);
        destiny = findViewById(R.id.textDestiny);
        plate_car = findViewById(R.id.textPlate);
        observation = findViewById(R.id.textObservation);

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip.setOrigin(origin.getText().toString());
                trip.setDestiny(destiny.getText().toString());
                trip.setPlate_car(plate_car.getText().toString());
                trip.setObservation(observation.getText().toString());
                Intent intent = new Intent(getApplicationContext(), TripThreeActivity.class);
                startActivity(intent);
            }
        });
    }
}
