package com.example.appviagens.pages;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appviagens.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TripOneActivity extends AppCompatActivity {
    private EditText textName;
    private SeekBar sk;
    private int year;
    private EditText textDate;
    private EditText textHora;
    private EditText textPassagers;
    private int day;
    private int month;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageone);
        Trip trip = Trip.getInstance();
        sk = findViewById(R.id.seekBar1);
        sk.setMax(100);
        sk.setProgress(33);
        textName = findViewById(R.id.txtName);
        textPassagers = findViewById(R.id.textPassageiro);
        Calendar calendar = Calendar.getInstance();
        textDate = findViewById(R.id.textDate);
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);

                DatePickerDialog datePd = new DatePickerDialog(TripOneActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        textDate.setText(sdf.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePd.show();
            }
        });


        textHora = findViewById(R.id.textHora);
        textHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePd = new TimePickerDialog(TripOneActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textHora.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, 15, 0, true);
                timePd.show();
            }
        });

        button = findViewById(R.id.buttonProx);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip.setName_driver(textName.getText().toString());
                trip.setDate_trip(textDate.getText().toString());
                trip.setHour_trip(textHora.getText().toString());
                trip.setPassagers(textPassagers.getText().toString());
                Intent intent = new Intent(getApplicationContext(), TripTwoActivity.class);
                startActivity(intent);
            }
        });

    }
}
