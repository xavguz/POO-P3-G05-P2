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

import com.poo.proyectopoop2.Modelo.ActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.ListaActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;

public class AdministrarActividadFisicaActivity extends AppCompatActivity {
    private ListaActividadFisicaModelo listaActividadFisicaModelo;
    private PerfilModelo perfilActual; // Perfil del usuario actual
    private ArrayAdapter<ActividadFisicaModelo> adapter;
    private ArrayList<ActividadFisicaModelo> actividadesFisicas;
    private ListView listaActividadesFisicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_actividad_fisica);

        // Recibir el perfil
        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AdministrarActividadFisica", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        listaActividadFisicaModelo = new ListaActividadFisicaModelo(this);

        // **Botón Volver**
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        // **Inicializar ListView**
        listaActividadesFisicas = findViewById(R.id.lista_actividad_fisica);

        // **Cargar actividades físicas**
        cargarActividadesFisicasDesdeArchivo();

        // **Manejar selección de una actividad física**
        listaActividadesFisicas.setOnItemClickListener((parent, view, position, id) -> {
            ActividadFisicaModelo actividadSeleccionada = actividadesFisicas.get(position);
            mostrarToast("Seleccionaste: " + actividadSeleccionada.getActividad());
        });

        // **Eliminar actividad con clic largo**
        listaActividadesFisicas.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarDialogoEliminarActividad(position);
            return true;
        });

        // **Botón para añadir una nueva actividad física**
        Button btnAgregarActividad = findViewById(R.id.btnanadir);
        btnAgregarActividad.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarActividadFisicaActivity.this, AnadirActividadFisicaActivity.class);
            intent.putExtra("perfil", perfilActual);
            startActivityForResult(intent, 1);
        });
    }

    /**
     * Cargar actividades físicas desde el archivo del perfil.
     */
    private void cargarActividadesFisicasDesdeArchivo() {
        try {
            listaActividadFisicaModelo.deserializarArrayListActividadFisica(perfilActual);
            actividadesFisicas = perfilActual.getActividadesFisicas();

            if (actividadesFisicas.isEmpty()) {
                mostrarToast("No hay actividades físicas registradas.");
            }

            // **Configurar el adaptador para la lista**
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, actividadesFisicas);
            listaActividadesFisicas.setAdapter(adapter);

        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdministrarActividadFisica", "Error al cargar actividades físicas: " + e.getMessage(), e);
            actividadesFisicas = new ArrayList<>();
            mostrarToast("Error al cargar actividades.");
        }
    }

    /**
     * Muestra un diálogo de confirmación para eliminar una actividad física.
     */
    private void mostrarDialogoEliminarActividad(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Actividad Física")
                .setMessage("¿Estás seguro de que deseas eliminar esta actividad física?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarActividad(position))
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Elimina una actividad física de la lista y actualiza el almacenamiento.
     */
    private void eliminarActividad(int position) {
        ActividadFisicaModelo actividadEliminada = perfilActual.getActividadesFisicas().remove(position);
        guardarActividadesEnArchivo(perfilActual);
        adapter.notifyDataSetChanged();
        mostrarToast("Actividad eliminada: " + actividadEliminada.getActividad());
    }

    /**
     * Guarda la lista de actividades físicas en el archivo.
     */
    public void guardarActividadesEnArchivo(PerfilModelo perfil) {
        try {
            listaActividadFisicaModelo.serializarArrayListActividadFisica(perfil);
        } catch (IOException e) {
            Log.e("ListaActividadFisicaModelo", "Error al guardar actividades físicas: " + e.getMessage(), e);
            e.printStackTrace(); // Imprimir detalles en la consola para depurar
        }
    }

    /**
     * Recibir el resultado de AnadirActividadFisicaActivity y actualizar la lista.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            perfilActual = (PerfilModelo) data.getSerializableExtra("perfil");
            cargarActividadesFisicasDesdeArchivo(); // Recargar lista
        }
    }

    /**
     * Método para mostrar mensajes con Toast.
     */
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

