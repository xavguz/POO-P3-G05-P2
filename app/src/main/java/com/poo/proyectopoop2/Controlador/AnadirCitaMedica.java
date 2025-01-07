package com.poo.proyectopoop2.Controlador;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.R;

import java.util.ArrayList;
public class AnadirCitaMedica extends AppCompatActivity {
    private ArrayList<MedicoModelo> listaDoctores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_citamedica);

        EditText etTitulo = findViewById(R.id.etTitulo);
        EditText etDia = findViewById(R.id.etDia);
        EditText etMes = findViewById(R.id.etMes);
        EditText etAno = findViewById(R.id.etAno);
        EditText etHora = findViewById(R.id.etHora);
        Spinner spinnerDoctor = findViewById(R.id.spinnerDoctor);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Cargar lista de médicos en el Spinner
        listaDoctores = (ArrayList<MedicoModelo>) getIntent().getSerializableExtra("listaMedicos");
        ArrayAdapter<MedicoModelo> doctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDoctores);
        spinnerDoctor.setAdapter(doctorAdapter);

        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString();
            String dia = etDia.getText().toString();
            String mes = etMes.getText().toString();
            String ano = etAno.getText().toString();
            String hora = etHora.getText().toString();
            MedicoModelo doctor = (MedicoModelo) spinnerDoctor.getSelectedItem();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("titulo", titulo);
            resultIntent.putExtra("dia", dia);
            resultIntent.putExtra("mes", mes);
            resultIntent.putExtra("ano", ano);
            resultIntent.putExtra("hora", hora);
            resultIntent.putExtra("doctor", doctor);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
