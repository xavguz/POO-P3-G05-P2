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

import java.util.ArrayList;

public class AnadirActividadFisicaActivity extends AppCompatActivity {

    private Spinner sp1;
    private Spinner sp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anadir_actividad_fisica);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirActividadFisicaActivity.this, AdministrarActividadFisicaActivity.class);
            startActivity(intent);
        });

        sp1 = findViewById(R.id.spinnerActividad);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(AnadirActividadFisicaActivity.this, "Actividad seleccionada: " + item1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> actividades = new ArrayList<>();
        actividades.add("Caminar");
        actividades.add("Trotar");
        actividades.add("Correr");
        actividades.add("Funcional");
        actividades.add("Crossfit");
        actividades.add("Entramiento de fuerza");
        actividades.add("Nadar");

        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,actividades);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp1.setAdapter(adapter1);



        sp2 = findViewById(R.id.spinnerHorario);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent2, View view, int position, long id) {
                String item2 = parent2.getItemAtPosition(position).toString();
                Toast.makeText(AnadirActividadFisicaActivity.this, "Horario seleccionada: " + item2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent2) {

            }
        });

        ArrayList<String> horario = new ArrayList<>();
        horario.add("Mañana");
        horario.add("Tarde");
        horario.add("Noche");

        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,horario);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp2.setAdapter(adapter2);
    }
}