package com.example.appviagens.pages;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appviagens.R;

import java.util.ArrayList;

public class TripListActivity extends AppCompatActivity {
    private ListView listView;
    private SQLiteDatabase bd;
    private ArrayList<String> tripList;
    private ArrayList<Integer> tripIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        listView = findViewById(R.id.tripListView);
        tripList = new ArrayList<>();
        tripIds = new ArrayList<>();

        bd = openOrCreateDatabase("viagens", MODE_PRIVATE, null);
        loadTrips();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tripList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TripDetailActivity.class);
                intent.putExtra("tripId", tripIds.get(position));
                startActivity(intent);
            }
        });
    }

    private void loadTrips() {
        Cursor cursor = bd.rawQuery("SELECT id_viagem, nome_motorista, date, origem, destino FROM viagem", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String trip = "Motorista: " + cursor.getString(1) + "\nData: " + cursor.getString(2) + "\nOrigem: " + cursor.getString(3) + "\nDestino: " + cursor.getString(4);

            tripList.add(trip);
            tripIds.add(id);

            cursor.moveToNext();
        }
        cursor.close();
    }
}
