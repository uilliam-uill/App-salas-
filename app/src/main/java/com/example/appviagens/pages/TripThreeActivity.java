package com.example.appviagens.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appviagens.R;

public class TripThreeActivity extends AppCompatActivity {
    private SeekBar sk;
    private EditText km_initial;
    private CheckBox ckOil;
    private CheckBox ckSttepe;
    private CheckBox ckWater;
    private CheckBox ckDocuments;
    private CheckBox ckTire;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagethree);
        Trip trip = Trip.getInstance();
        sk = findViewById(R.id.seekBar3);
        sk.setMax(100);
        sk.setProgress(100);

        km_initial = findViewById(R.id.textKmInc);
        ckOil = findViewById(R.id.Oil);
        ckSttepe = findViewById(R.id.Sttepe);
        ckWater = findViewById(R.id.Water);
        ckDocuments = findViewById(R.id.Documents);
        ckTire = findViewById(R.id.Tires);

        button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip.setKm_inc(km_initial.getText().toString());
                if(ckOil.isChecked()){
                    trip.setOil(1);
                }else{
                    trip.setOil(0);
                }
                if(ckSttepe.isChecked()){
                    trip.setSttepe(1);
                }else{
                    trip.setSttepe(0);
                }
                if(ckWater.isChecked()){
                    trip.setWater(1);
                }else{
                    trip.setWater(0);
                }
                if(ckDocuments.isChecked()){
                    trip.setDocuments(1);
                }else{
                    trip.setDocuments(0);
                }
                if(ckTire.isChecked()){
                    trip.setTire(1);
                }else{
                    trip.setTire(0);
                }
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });
    }
}
