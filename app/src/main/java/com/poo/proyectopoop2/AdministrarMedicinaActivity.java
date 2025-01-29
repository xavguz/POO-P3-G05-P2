package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;

import java.io.IOException;
import java.util.ArrayList;

public class AdministrarMedicinaActivity extends AppCompatActivity {
    private ListaMedicinaModelo listaMedicinaModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_medicina);

        listaMedicinaModelo = new ListaMedicinaModelo(this);
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        ListView listaMedicinas = findViewById(R.id.lista_medicinas);

        // Cargar medicinas con manejo de errores
        ArrayList<MedicinaModelo> medicinas = cargarMedicinasDesdeArchivo();

        if (medicinas.isEmpty()) {
            Toast.makeText(this, "No hay medicinas disponibles.", Toast.LENGTH_SHORT).show();
            return;  // No cerramos la actividad
        }

        // Configurar el ListView
        ArrayAdapter<MedicinaModelo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicinas);
        listaMedicinas.setAdapter(adapter);

        // Manejar selección de una medicina
        listaMedicinas.setOnItemClickListener((parent, view, position, id) -> {
            MedicinaModelo medicinaSeleccionada = medicinas.get(position);
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("medicinaSeleccionada", medicinaSeleccionada.getNombreMedicamento());
            startActivity(intent);
        });
    }

    private ArrayList<MedicinaModelo> cargarMedicinasDesdeArchivo() {
        try {
            listaMedicinaModelo.deserializarArrayListMedicina();
            return listaMedicinaModelo.getListaMedicinas();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdministrarMedicina", "Error al cargar medicinas: " + e.getMessage(), e);
            return new ArrayList<>();  // Devolver lista vacía en caso de error
        }
    }
}

