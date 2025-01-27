package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Modelo.MedicinaModelo;

public class AdministrarMedicinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_medicina);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicinaActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        Button anadir = findViewById(R.id.btnanadir);
        anadir.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicinaActivity.this, AnadirMedicinaActivity.class);
            startActivity(intent);
        });

    }
}