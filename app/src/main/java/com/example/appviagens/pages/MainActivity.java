package com.example.appviagens.pages;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appviagens.R;

public class MainActivity extends AppCompatActivity {
    private Button button, buttonViewTrips;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        createBd();

        button = findViewById(R.id.button);
        buttonViewTrips = findViewById(R.id.buttonViewTrips);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TripOneActivity.class);
                startActivity(intent);
            }
        });

        buttonViewTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TripListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createBd() {
        try {
            bd = openOrCreateDatabase("viagens", MODE_PRIVATE, null);

            // Tabela viagem
            bd.execSQL("CREATE TABLE IF NOT EXISTS viagem(" +
                    "id_viagem INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome_motorista TEXT," +
                    "date TEXT," +
                    "hora TEXT," +
                    "passageiros TEXT," +
                    "origem TEXT," +
                    "destino TEXT," +
                    "observacao TEXT);");

            // Tabela dados_carro
            bd.execSQL("CREATE TABLE IF NOT EXISTS dados_carro(" +
                    "id_dados_carro INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_viagem INTEGER," +
                    "placa TEXT," +
                    "km_inicial TEXT," +
                    "km_final TEXT," +
                    "estepe INTEGER," +
                    "agua INTEGER," +
                    "oleo INTEGER," +
                    "documentos INTEGER," +
                    "pneus INTEGER);"    );

            // Tabela localizacao
            bd.execSQL("CREATE TABLE IF NOT EXISTS localizacao(" +
                    "id_localizacao INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_viagem INTEGER," +
                    "longitude DOUBLE," +
                    "latitude DOUBLE," +
                    "velocidade DOUBLE," +
                    "direcao FLOAT);");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
