package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

public class AnadirMedicinaActivity extends AppCompatActivity {

    private EditText nombreMedicamento;
    private EditText cantidadMedicamento;
    private EditText presentacionMedicamento;
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
        presentacionMedicamento = findViewById(R.id.ingresarPresentacion);
        horaTomaMedicamento = findViewById(R.id.ingresarHoraToma);
        dosisMedicamento = findViewById(R.id.ingresarDosis);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirMedicinaActivity.this, AdministrarMedicinaActivity.class);
            startActivity(intent);
        });

        Button btnCrear = findViewById(R.id.btnGuardarMedicina);
        btnCrear.setOnClickListener(v -> {
            String nombre = nombreMedicamento.getText().toString().trim();
            float cantidad = Float.parseFloat(cantidadMedicamento.getText().toString().trim());
            String presentacion = presentacionMedicamento.getText().toString().trim();
            String horaToma = horaTomaMedicamento.getText().toString().trim();
            float dosis = Float.parseFloat(dosisMedicamento.getText().toString().trim());
            MedicinaModelo nuevaMedicina = new MedicinaModelo(nombre,cantidad,presentacion,horaToma,dosis);
            medicinas.agregarMedicina(nuevaMedicina);

        });
    }
}