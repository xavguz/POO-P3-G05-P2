package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Modelo.CitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.ListaCitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;

public class AdministrarCitasMedicas extends AppCompatActivity {
    private ListaCitaMedicaModelo listaCitaModelo;
    private ArrayAdapter<CitaMedicaModelo> adapter;
    private PerfilModelo perfilActual;
    private ListView listViewCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_citas_medicas);

        // 🔹 Recibir el perfil actual
        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AdministrarCitas", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        listaCitaModelo = new ListaCitaMedicaModelo(this);
        cargarCitasDesdeArchivo();

        // 🔹 Configurar ListView
        listViewCitas = findViewById(R.id.listaCitas);

        // Asegurar que la lista de citas no es nula
        if (perfilActual.getCitaMedicas() == null) {
            perfilActual.setCitaMedicas(new ArrayList<>());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, perfilActual.getCitaMedicas());
        listViewCitas.setAdapter(adapter);

        // 🔹 Configurar eliminación de citas con clic largo
        listViewCitas.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarDialogoEliminarCita(position);
            return true;
        });

        // 🔹 Botón para añadir una nueva cita
        Button btnAgregarCita = findViewById(R.id.btnanadir);
        btnAgregarCita.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarCitasMedicas.this, AnadirCitaActivity.class);
            intent.putExtra("perfil", perfilActual);
            startActivityForResult(intent, 1);
        });
    }

    /**
     * 🔹 Muestra un diálogo de confirmación antes de eliminar una cita.
     */
    private void mostrarDialogoEliminarCita(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cita")
                .setMessage("¿Estás seguro de que deseas eliminar esta cita?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarCita(position))
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * 🔹 Elimina una cita médica y actualiza la lista y el almacenamiento.
     */
    private void eliminarCita(int position) {
        if (perfilActual.getCitaMedicas() != null && position >= 0 && position < perfilActual.getCitaMedicas().size()) {
            perfilActual.getCitaMedicas().remove(position);
            guardarCitasEnArchivo(perfilActual);
            adapter.notifyDataSetChanged(); // 🔹 Actualizar la UI
            listViewCitas.invalidateViews(); // 🔹 Forzar actualización visual
            mostrarToast("Cita eliminada correctamente.");
        } else {
            mostrarToast("Error al eliminar la cita.");
        }
    }

    /**
     * 🔹 Carga las citas desde el archivo asociado al perfil.
     */
    private void cargarCitasDesdeArchivo() {
        try {
            listaCitaModelo.deserializarArrayListCitas(perfilActual);
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdministrarCitas", "Error al cargar citas: " + e.getMessage(), e);
        }
    }

    /**
     * 🔹 Guarda las citas del perfil en el archivo correspondiente.
     */
    public void guardarCitasEnArchivo(PerfilModelo perfil) {
        try {
            listaCitaModelo.serializarArrayListCitas(perfil);
        } catch (IOException e) {
            Log.e("ListaCitaMedicaModelo", "Error al guardar citas: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 🔹 Manejo de la nueva cita agregada
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            CitaMedicaModelo nuevaCita = (CitaMedicaModelo) data.getSerializableExtra("nuevaCita");

            if (nuevaCita != null) {
                perfilActual.getCitaMedicas().add(nuevaCita);
                guardarCitasEnArchivo(perfilActual);
                adapter.notifyDataSetChanged();
                listViewCitas.invalidateViews(); // 🔹 Forzar actualización visual
                Log.d("onActivityResult", "Cita añadida correctamente: " + nuevaCita.toString());
            } else {
                Log.e("onActivityResult", "Error: Cita recibida es null");
            }
        }
    }

    /**
     * 🔹 Muestra un mensaje Toast.
     */
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

