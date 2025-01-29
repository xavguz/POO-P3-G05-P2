package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdministrarMedicinaActivity extends AppCompatActivity {
    private ListaMedicinaModelo listaMedicinaModelo;
    private PerfilModelo perfil;
    private ArrayList<MedicinaModelo> medicinas;
    private ArrayAdapter<MedicinaModelo> adapter;
    private ListView listaMedicinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_medicina);

        listaMedicinaModelo = new ListaMedicinaModelo(this);
        perfil = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfil == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AdministrarMedicina", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        // **Botón Volver**
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicinaActivity.this, MenuActivity.class);
            intent.putExtra("perfil", perfil);
            startActivity(intent);
            finish();
        });

        // **Inicializar ListView**
        listaMedicinas = findViewById(R.id.lista_medicinas);
        medicinas = cargarMedicinasDesdeArchivo();

        if (medicinas.isEmpty()) {
            mostrarToast("No hay medicinas disponibles.");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicinas);
        listaMedicinas.setAdapter(adapter);

        // **Manejar selección de una medicina**
        listaMedicinas.setOnItemClickListener((parent, view, position, id) -> {
            MedicinaModelo medicinaSeleccionada = medicinas.get(position);
            mostrarToast("Seleccionaste: " + medicinaSeleccionada.getNombreMedicamento());
        });

        // **Eliminar medicina con clic largo**
        listaMedicinas.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarDialogoEliminarMedicina(position);
            return true;
        });

        // **Botón para añadir una nueva medicina**
        Button btnAgregarMedicina = findViewById(R.id.btnanadir);
        btnAgregarMedicina.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicinaActivity.this, AnadirMedicinaActivity.class);
            intent.putExtra("perfil", perfil);
            startActivityForResult(intent, 1);
        });
    }

    /**
     * Cargar medicinas desde el archivo del perfil.
     */
    private ArrayList<MedicinaModelo> cargarMedicinasDesdeArchivo() {
        try {
            listaMedicinaModelo.deserializarArrayListMedicina(perfil);
            return listaMedicinaModelo.getListaMedicinas();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdministrarMedicina", "Error al cargar medicinas: " + e.getMessage(), e);
            return new ArrayList<>(); // Devolver lista vacía en caso de error
        }
    }

    /**
     * Muestra un diálogo de confirmación para eliminar una medicina.
     */
    private void mostrarDialogoEliminarMedicina(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Medicina")
                .setMessage("¿Estás seguro de que deseas eliminar esta medicina?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarMedicina(position))
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Elimina una medicina de la lista y actualiza el almacenamiento.
     */
    private void eliminarMedicina(int position) {
        MedicinaModelo medicinaEliminada = perfil.getMedicinas().remove(position);
        guardarMedicinasEnArchivo(perfil);
        adapter.notifyDataSetChanged();
        mostrarToast("Medicina eliminada: " + medicinaEliminada.getNombreMedicamento());
    }

    /**
     * Guarda la lista de medicinas en el archivo.
     */
    public void guardarMedicinasEnArchivo(PerfilModelo perfil) {
        try {
            listaMedicinaModelo.serializarArrayListMedicina(perfil);
        } catch (IOException e) {
            Log.e("ListaMedicinaModelo", "Error al guardar medicinas: " + e.getMessage(), e);
            e.printStackTrace(); // Imprimir detalles en la consola para depurar
        }
    }

    /**
     * Recibir el resultado de AnadirMedicinaActivity y actualizar la lista.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            perfil = (PerfilModelo) data.getSerializableExtra("perfil");
            if (perfil != null) {
                medicinas.clear();
                medicinas.addAll(perfil.getMedicinas());
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Método para mostrar mensajes con Toast.
     */
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}





