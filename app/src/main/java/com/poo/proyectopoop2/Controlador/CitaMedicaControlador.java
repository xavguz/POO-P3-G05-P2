
package com.poo.proyectopoop2.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.AnadirCitaActivity;
import com.poo.proyectopoop2.Modelo.CitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaCitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;
import com.poo.proyectopoop2.R;

import java.io.IOException;
import java.util.ArrayList;

public class CitaMedicaControlador extends AppCompatActivity {
    private ListaCitaMedicaModelo listaCitaModelo;
    private ArrayAdapter<CitaMedicaModelo> adapter;
    private PerfilModelo perfilActual;  // Perfil del usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_citas_medicas);

        // Recibir el perfil actual
        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("CitaMedicaControlador", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        listaCitaModelo = new ListaCitaMedicaModelo(this);

        // Cargar citas del perfil
        cargarCitasDesdeArchivo();

        // Configurar ListView
        ListView listViewCitas = findViewById(R.id.listaCitas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, perfilActual.getCitaMedicas());
        listViewCitas.setAdapter(adapter);

        // Botón para añadir una nueva cita
        Button btnAgregarCita = findViewById(R.id.btnanadir);
        btnAgregarCita.setOnClickListener(v -> {
            Intent intent = new Intent(CitaMedicaControlador.this, AnadirCitaActivity.class);
            intent.putExtra("perfil", perfilActual);  // Pasar perfil para asignarle citas
            startActivityForResult(intent, 1);
        });

        // Eliminar cita al hacer clic largo en un ítem de la lista
        listViewCitas.setOnItemLongClickListener((parent, view, position, id) -> {
            perfilActual.getCitaMedicas().remove(position);
            guardarCitasEnArchivo();  // Guardar cambios después de eliminar
            adapter.notifyDataSetChanged();
            mostrarToast("Cita eliminada correctamente.");
            return true;
        });
    }

    /**
     * Maneja el resultado al volver de la pantalla para agregar una cita.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String titulo = data.getStringExtra("titulo");
            String fechaStr = data.getStringExtra("Fecha");
            String horaStr = data.getStringExtra("hora");
            MedicoModelo doctor = (MedicoModelo) data.getSerializableExtra("doctor");

            if (titulo != null && fechaStr != null && horaStr != null && doctor != null) {
                CitaMedicaModelo nuevaCita = new CitaMedicaModelo(titulo, new FechaModelo(fechaStr, horaStr), doctor);
                perfilActual.getCitaMedicas().add(nuevaCita);
                guardarCitasEnArchivo();
                adapter.notifyDataSetChanged();
            } else {
                mostrarToast("Error al recibir los datos de la cita.");
            }
        }
    }

    /**
     * Carga las citas desde el archivo asociado al perfil.
     */
    private void cargarCitasDesdeArchivo() {
        try {
            listaCitaModelo.deserializarArrayListCitas(perfilActual);
        } catch (IOException | ClassNotFoundException e) {
            Log.e("CitaMedicaControlador", "Error al cargar citas: " + e.getMessage(), e);
        }
    }

    /**
     * Guarda las citas del perfil en el archivo correspondiente.
     */
    private void guardarCitasEnArchivo() {
        try {
            listaCitaModelo.serializarArrayListCitas(perfilActual);
        } catch (IOException e) {
            Log.e("CitaMedicaControlador", "Error al guardar citas: " + e.getMessage(), e);
        }
    }

    /**
     * Muestra un mensaje Toast.
     */
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

