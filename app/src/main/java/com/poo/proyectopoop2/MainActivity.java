package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Botón para abrir la actividad de crear perfil
        Button btnCrearPerfilLayout = findViewById(R.id.crearPerfil);

        btnCrearPerfilLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrearPerfilActivity.class);
            startActivity(intent);
        });

        // Botón para abrir la actividad de seleccionar perfil
        Button btnSeleccionarPerfil = findViewById(R.id.seleccionarPerfil);
        btnSeleccionarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, SeleccionarPerfilActivity_.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String perfilSeleccionado = data.getStringExtra("perfilSeleccionado");
            if (perfilSeleccionado != null) {
                Toast.makeText(this, "Perfil seleccionado: " + perfilSeleccionado, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se seleccionó ningún perfil.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
