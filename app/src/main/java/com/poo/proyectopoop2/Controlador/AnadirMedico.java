package com.poo.proyectopoop2.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.R;

public class AnadirMedico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_medico);

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etEspecialidad = findViewById(R.id.etEspecialidad);
        EditText etTelefono = findViewById(R.id.etTelefono);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etDireccion = findViewById(R.id.etDireccion);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nombre", etNombre.getText().toString());
            resultIntent.putExtra("especialidad", etEspecialidad.getText().toString());
            resultIntent.putExtra("telefono", etTelefono.getText().toString());
            resultIntent.putExtra("email", etEmail.getText().toString());
            resultIntent.putExtra("direccion", etDireccion.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}

