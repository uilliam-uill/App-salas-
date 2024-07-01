package com.example.appviagens.pages;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appviagens.R;

import java.util.ArrayList;

public class TripDetailActivity extends AppCompatActivity {
    private ListView listView;
    private SQLiteDatabase bd;
    private ArrayList<String> tripDetailsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        listView = findViewById(R.id.tripDetailListView);
        tripDetailsList = new ArrayList<>();

        bd = openOrCreateDatabase("viagens", MODE_PRIVATE, null);
        int tripId = getIntent().getIntExtra("tripId", -1);
        loadTripDetails(tripId);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tripDetailsList);
        listView.setAdapter(adapter);
    }

    private void loadTripDetails(int tripId) {
        Cursor cursor = bd.rawQuery("SELECT * FROM viagem WHERE id_viagem = " + tripId, null);
        if (cursor.moveToFirst()) {
            tripDetailsList.add("Nome do Motorista: " + cursor.getString(1));
            tripDetailsList.add("Data: " + cursor.getString(2));
            tripDetailsList.add("Hora: " + cursor.getString(3));
            tripDetailsList.add("Passageiros: " + cursor.getString(4));
            tripDetailsList.add("Origem: " + cursor.getString(5));
            tripDetailsList.add("Destino: " + cursor.getString(6));
            tripDetailsList.add("Observação: " + cursor.getString(7));
        }
        cursor.close();

        cursor = bd.rawQuery("SELECT * FROM dados_carro WHERE id_viagem = " + tripId, null);
        if (cursor.moveToFirst()) {
            tripDetailsList.add("Placa do Carro: " + cursor.getString(2));
            tripDetailsList.add("KM Inicial: " + cursor.getString(3));
            tripDetailsList.add("KM Final: " + cursor.getString(4));
            tripDetailsList.add("Estepe: " + (cursor.getInt(5) == 1 ? "Sim" : "Não"));
            tripDetailsList.add("Água: " + (cursor.getInt(6) == 1 ? "Sim" : "Não"));
            tripDetailsList.add("Óleo: " + (cursor.getInt(7) == 1 ? "Sim" : "Não"));
            tripDetailsList.add("Documentos: " + (cursor.getInt(8) == 1 ? "Sim" : "Não"));
            tripDetailsList.add("Pneus: " + (cursor.getInt(9) == 1 ? "Sim" : "Não"));
        }
        cursor.close();

        cursor = bd.rawQuery("SELECT * FROM localizacao WHERE id_viagem = " + tripId, null);
        if (cursor.moveToFirst()) {
            do {
                tripDetailsList.add("Longitude: " + cursor.getDouble(2));
                tripDetailsList.add("Latitude: " + cursor.getDouble(3));
                tripDetailsList.add("Velocidade: " + cursor.getDouble(4));
                tripDetailsList.add("Direção: " + cursor.getFloat(5));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
