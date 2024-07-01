package com.example.appviagens.pages;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appviagens.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private GoogleMap mMap;

    private LocationManager locMan;

    private Location location;
    private MapView mapView;

    private TextView sp;
    private ArrayList<Double> latitudes;
    private ArrayList<Double> longitudes;
    private ArrayList<Float> speeds;
    private ArrayList<Float> directions;
    private Timer timer;
    private Button encerrar;
    private SQLiteDatabase bd;
    public static double latitude;
    public static double longitude;
    public static float speed;
    public static float direction;
    private EditText kmfim;

    private LocationManager locationManager;
    private Handler handler = new Handler();
    private Runnable locationUpdater;
    private long id = 0;
    Trip trip = Trip.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bd = openOrCreateDatabase("viagens", MODE_PRIVATE, null);
        kmfim = findViewById(R.id.kmfim);
        sp = findViewById(R.id.speed);
        latitudes = new ArrayList<>();
        longitudes = new ArrayList<>();
        speeds = new ArrayList<>();
        directions = new ArrayList<>();
        encerrar = findViewById(R.id.button4);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitudes.add(location.getLatitude());
            longitudes.add(location.getLongitude());
            speeds.add(location.getSpeed());
            directions.add(location.getBearing());
        }
        timer = new Timer();
        TimerTask locationTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupLocationUpdater();
                    }
                });
            }
        };
        timer.schedule(locationTask, 0, 60000);

        encerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kmfimd = kmfim.getText().toString();
                ContentValues valuesViagem = new ContentValues();
                valuesViagem.put("nome_motorista", trip.getName_driver());
                valuesViagem.put("date", trip.getDate_trip());
                valuesViagem.put("hora", trip.getHour_trip());
                valuesViagem.put("passageiros", trip.getPassagers());
                valuesViagem.put("origem", trip.getOrigin());
                valuesViagem.put("destino", trip.getDestiny());
                valuesViagem.put("observacao", trip.getObservation());

                id = bd.insert("viagem", null, valuesViagem);
                if (id != -1) {
                    ContentValues valuesCarro = new ContentValues();
                    valuesCarro.put("id_viagem", id);
                    valuesCarro.put("placa", trip.getPlate_car());
                    valuesCarro.put("km_inicial", trip.getKm_inc());
                    valuesCarro.put("km_final", kmfimd);
                    valuesCarro.put("estepe", trip.getSttepe());
                    valuesCarro.put("agua", trip.getWater());
                    valuesCarro.put("oleo", trip.getOil());
                    valuesCarro.put("documentos", trip.getDocuments());
                    valuesCarro.put("pneus", trip.getTire());
                    bd.insert("dados_carro", null, valuesCarro);
                }

                for (int i = 0; i < latitudes.size(); i++) {
                    ContentValues valuesLoc = new ContentValues();
                    valuesLoc.put("id_viagem", id);
                    valuesLoc.put("longitude", longitudes.get(i));
                    valuesLoc.put("latitude", latitudes.get(i));
                    valuesLoc.put("velocidade", speeds.get(i));
                    valuesLoc.put("direcao", directions.get(i));
                    bd.insert("localizacao", null, valuesLoc);
                }
                checkAndRequestSmsPermission();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
//                try {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("+5575982774608", null, "funcionou", null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent successfully!", Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "SMS sending failed. Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                    Log.e("SMS_SENDING_ERROR", "Error sending SMS: " + e.getMessage());
//                }
            }
        });
    }

    private void sendSMS(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent successfully!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS sending failed. Please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void checkAndRequestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // A permissão ainda não foi concedida, então solicita ao usuário
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        } else {
            // A permissão já foi concedida, você pode proceder com o envio de SMS
            sendSMS("+5575982774608", "Viagem para: " + trip.getDestiny() + " no dia: " + trip.getDate_trip()
             + " no horario: " + trip.getHour_trip() + " com os passageiros " + trip.getPassagers() + " saindo de " + trip.getOrigin() + " no carro " +
                    "de placa" + trip.getPlate_car() + " com algumas observações " + trip.getObservation());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida pelo usuário, pode proceder com o envio de SMS
                sendSMS("+5575982774608", "funcionou");
            } else {
                // Permissão negada pelo usuário
                Toast.makeText(this, "Permissão SEND_SMS negada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupLocationUpdater() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitudes.add(location.getLatitude());
            longitudes.add(location.getLongitude());
            speeds.add(location.getSpeed());
            directions.add(location.getBearing());
        }
    }
}