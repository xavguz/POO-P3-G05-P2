package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

import java.util.ArrayList;

public class AnadirMedicinaActivity extends AppCompatActivity {

    private EditText nombreMedicamento;
    private EditText cantidadMedicamento;
    private Spinner presentacionMedicamento;
    private EditText horaTomaMedicamento;
    private EditText dosisMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitty_anadir_medicina);

        ListaMedicinaModelo medicinas = new ListaMedicinaModelo(this);

        nombreMedicamento = findViewById(R.id.ingresarNombreMedicamento);
        cantidadMedicamento = findViewById(R.id.ingresarCantidad);
        presentacionMedicamento = findViewById(R.id.spinnerPresentacion);
        horaTomaMedicamento = findViewById(R.id.ingresarHoraToma);
        dosisMedicamento = findViewById(R.id.ingresarDosis);


        presentacionMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AnadirMedicinaActivity.this, "Presentacion seleccionada: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> presentaciones = new ArrayList<>();
        presentaciones.add("Pastillas");
        presentaciones.add("Inyeccion");
        presentaciones.add("Solucion");
        presentaciones.add("Gotas");
        presentaciones.add("Inhalador");
        presentaciones.add("Polvo");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,presentaciones);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        presentacionMedicamento.setAdapter(adapter);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirMedicinaActivity.this, AdministrarMedicinaActivity.class);
            startActivity(intent);
        });

        Button btnCrear = findViewById(R.id.btnGuardarMedicina);
        btnCrear.setOnClickListener(v -> {
            String nombre = nombreMedicamento.getText().toString().trim();
            float cantidad = Float.parseFloat(cantidadMedicamento.getText().toString().trim());
            String presentacion = presentacionMedicamento.getPrompt().toString().trim();
            String horaToma = horaTomaMedicamento.getText().toString().trim();
            float dosis = Float.parseFloat(dosisMedicamento.getText().toString().trim());
            MedicinaModelo nuevaMedicina = new MedicinaModelo(nombre,cantidad,presentacion,horaToma,dosis);
            medicinas.agregarMedicina(nuevaMedicina);

        });
    }
}