package com.poo.proyectopoop2.Controlador;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.R;
import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

public class AnadirMedico extends AppCompatActivity {
    private ListaMedicoModelo listaMedicoModelo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_anadir_medico);

            listaMedicoModelo = new ListaMedicoModelo();

            EditText etNombre = findViewById(R.id.etNombre);
            EditText etEspecialidad = findViewById(R.id.etEspecialidad);
            EditText etTelefono = findViewById(R.id.etTelefono);
            EditText etEmail = findViewById(R.id.etEmail);
            EditText etDireccion = findViewById(R.id.etDireccion);
            Button btnGuardar = findViewById(R.id.btnGuardar);

            btnGuardar.setOnClickListener(v -> {
                String nombre = etNombre.getText().toString().trim();
                String especialidad = etEspecialidad.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String direccion = etDireccion.getText().toString().trim();

                if (nombre.isEmpty() || especialidad.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
                    Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                MedicoModelo nuevoMedico = new MedicoModelo(nombre, especialidad, telefono, email, direccion);
                listaMedicoModelo.agregarMedico(nuevoMedico);
                Toast.makeText(this, "Médico añadido exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
}

