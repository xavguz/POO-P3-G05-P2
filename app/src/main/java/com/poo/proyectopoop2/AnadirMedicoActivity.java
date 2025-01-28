package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoAdapter;

import java.util.ArrayList;

public class AnadirMedicoActivity extends AppCompatActivity {

    private Spinner especialidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anadir_medico);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirMedicoActivity.this, AdministrarMedicosActivity.class);
            startActivity(intent);
        });
        especialidad = findViewById(R.id.spinnerEspecialidad);

        especialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AnadirMedicoActivity.this, "Especialidad seleccionada: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> especialidades = new ArrayList<>();
        especialidades.add("Medicina General");
        especialidades.add("Pediatria");
        especialidades.add("Cardiologia");
        especialidades.add("Dermatologia");
        especialidades.add("Endocrinologia");
        especialidades.add("Oncologo");
        especialidades.add("Gastroenterologia");
        especialidades.add("Geriatria");
        especialidades.add("Neurologia");
        especialidades.add("Psiquiatria");
        especialidades.add("Traumatologia");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,especialidades);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        especialidad.setAdapter(adapter);

    }
}