
package com.poo.proyectopoop2.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.poo.proyectopoop2.Modelo.CitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaCitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.R;

import java.util.ArrayList;

public class CitaMedicaControlador extends AppCompatActivity {
    private ListaCitaMedicaModelo listaCitaModelo;
    private ArrayAdapter<CitaMedicaModelo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_citas_medicas);

        listaCitaModelo = new ListaCitaMedicaModelo();

        ListView listViewCitas = findViewById(R.id.rv);
        Button btnAgregarCita = findViewById(R.id.btnanadir);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCitaModelo.getListaCitas());
        listViewCitas.setAdapter(adapter);

        // Botón para agregar una nueva cita
        btnAgregarCita.setOnClickListener(v -> {
            Intent intent = new Intent(CitaMedicaControlador.this, AnadirCitaMedica.class);
            startActivityForResult(intent, 1); // Código de solicitud para manejar el resultado
        });

        // Eliminación de una cita al hacer clic largo en un ítem de la lista
        listViewCitas.setOnItemLongClickListener((parent, view, position, id) -> {
            listaCitaModelo.eliminarCita(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(CitaMedicaControlador.this, "Cita eliminada", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Recuperar datos de la actividad AnadirCita
            String titulo = data.getStringExtra("titulo");
            String fecha = data.getStringExtra("Fecha");
            String hora = data.getStringExtra("hora");
            MedicoModelo doctor = (MedicoModelo) data.getSerializableExtra("doctor");

            CitaMedicaModelo nuevaCita = new CitaMedicaModelo(titulo, new FechaModelo(fecha, hora), doctor);
            listaCitaModelo.agregarCita(nuevaCita);

            // Actualizar la lista
            adapter.notifyDataSetChanged();
        }
    }
}

