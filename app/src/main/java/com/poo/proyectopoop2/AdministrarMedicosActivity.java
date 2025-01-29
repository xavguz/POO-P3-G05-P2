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

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicoAdapter;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;

public class AdministrarMedicosActivity extends AppCompatActivity {

    private ListaMedicoModelo listaMedicoModelo;
    private PerfilModelo perfil; // Perfil del usuario actual
    private ArrayList<MedicoModelo> medicos;
    private ArrayAdapter<MedicoModelo> adapter;
    private ListView listaMedicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_medicos);

        listaMedicoModelo = new ListaMedicoModelo(this);
        perfil = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfil == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AdministrarMedicos", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        // **Botón Volver**
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicosActivity.this, MenuActivity.class);
            intent.putExtra("perfil", perfil);
            startActivity(intent);
            finish();
        });

        // **Inicializar ListView**
        listaMedicos = findViewById(R.id.lista_medicos);
        medicos = cargarMedicosDesdeArchivo();

        if (medicos.isEmpty()) {
            mostrarToast("No hay médicos disponibles.");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicos);
        listaMedicos.setAdapter(adapter);

        // **Manejar selección de un médico**
        listaMedicos.setOnItemClickListener((parent, view, position, id) -> {
            MedicoModelo medicoSeleccionado = medicos.get(position);
            mostrarToast("Seleccionaste: " + medicoSeleccionado.getNombre());

            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("perfil", perfil);
            intent.putExtra("medicoSeleccionado", medicoSeleccionado.getNombre());
            startActivity(intent);
        });

        // **Eliminar médico con clic largo**
        listaMedicos.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarDialogoEliminarMedico(position);
            return true;
        });

        // **Botón para añadir un nuevo médico**
        Button btnAgregarMedico = findViewById(R.id.btnanadir);
        btnAgregarMedico.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicosActivity.this, AnadirMedicoActivity.class);
            intent.putExtra("perfil", perfil);
            startActivityForResult(intent, 1);
        });
    }

    /**
     * Cargar médicos desde el archivo del perfil.
     */
    private ArrayList<MedicoModelo> cargarMedicosDesdeArchivo() {
        try {
            listaMedicoModelo.deserializarArrayListMedico(perfil);
            return listaMedicoModelo.getListaMedicos();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdministrarMedico", "Error al cargar médicos: " + e.getMessage(), e);
            return new ArrayList<>(); // Devolver lista vacía en caso de error
        }
    }

    /**
     * Muestra un diálogo de confirmación para eliminar un médico.
     */
    private void mostrarDialogoEliminarMedico(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Médico")
                .setMessage("¿Estás seguro de que deseas eliminar este médico?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarMedico(position))
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Elimina un médico de la lista y actualiza el almacenamiento.
     */
    private void eliminarMedico(int position) {
        MedicoModelo medicoEliminado = perfil.getMedicos().remove(position);
        guardarMedicosEnArchivo(perfil);
        adapter.notifyDataSetChanged();
        mostrarToast("Médico eliminado: " + medicoEliminado.getNombre());
    }

    /**
     * Guarda la lista de médicos en el archivo.
     */
    public void guardarMedicosEnArchivo(PerfilModelo perfil) {
        try {
            listaMedicoModelo.serializarArrayListMedico(perfil);
        } catch (IOException e) {
            Log.e("ListaMedicoModelo", "Error al guardar médicos: " + e.getMessage(), e);
            e.printStackTrace(); // Imprimir detalles en la consola para depurar
        }
    }

    /**
     * Recibir el resultado de AnadirMedicoActivity y actualizar la lista.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            perfil = (PerfilModelo) data.getSerializableExtra("perfil");
            if (perfil != null) {
                medicos.clear();
                medicos.addAll(perfil.getMedicos());
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
