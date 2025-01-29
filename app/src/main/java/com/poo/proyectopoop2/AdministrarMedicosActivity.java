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
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicoAdapter;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

import java.io.IOException;
import java.util.ArrayList;

public class AdministrarMedicosActivity extends AppCompatActivity {

    ListaMedicoModelo listaMedicoModelo;
    private MedicoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_medicos);

        listaMedicoModelo = new ListaMedicoModelo(this);
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        ListView listaMedicos = findViewById(R.id.lista_medicos);

        // Cargar medicinas con manejo de errores
        ArrayList<MedicoModelo> medicos = cargarMedicosDesdeArchivo();

        if (medicos.isEmpty()) {
            Toast.makeText(this, "No hay medicinas disponibles.", Toast.LENGTH_SHORT).show();
            return;  // No cerramos la actividad
        }

        // Configurar el ListView
        ArrayAdapter<MedicoModelo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicos);
        listaMedicos.setAdapter(adapter);

        // Manejar selección de una medicina
        listaMedicos.setOnItemClickListener((parent, view, position, id) -> {
            MedicoModelo medicoSeleccionado = medicos.get(position);
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("medicoSeleccionado", medicoSeleccionado.getNombre());
            startActivity(intent);
        });
    }

    private ArrayList<MedicoModelo> cargarMedicosDesdeArchivo() {
        try {
            listaMedicoModelo.deserializarArrayListMedico();
            return listaMedicoModelo.getListaMedicos();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdministrarMedico", "Error al cargar medicos: " + e.getMessage(), e);
            return new ArrayList<>();  // Devolver lista vacía en caso de error
        }
    }
}
