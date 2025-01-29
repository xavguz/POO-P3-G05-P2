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

import com.poo.proyectopoop2.Controlador.MedicoControlador;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

import java.util.ArrayList;

public class AnadirMedicoActivity extends AppCompatActivity {
    private EditText nombreMedico;
    private Spinner especialidad;
    private EditText telefonoMedico;
    private EditText emailMedico;
    private EditText direccionMedico;
    private MedicoControlador medicoControlador;
    private ListaMedicoModelo listaMedicoModelo;

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
        nombreMedico = findViewById(R.id.ingresarNombreDoctor);
        especialidad = findViewById(R.id.spinnerEspecialidad);
        telefonoMedico = findViewById(R.id.ingresarNumero);
        emailMedico = findViewById(R.id.ingresarEmail);
        direccionMedico = findViewById(R.id.ingresarDireccion);
        medicoControlador = new MedicoControlador(this);
        listaMedicoModelo = new ListaMedicoModelo(this);

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
        Button btnGuardarMedico = findViewById(R.id.btnGuardarMedico);
        btnGuardarMedico.setOnClickListener(v -> {
            String nombreM = nombreMedico.getText().toString().trim();
            String especialidadMed = especialidad.getSelectedItem().toString().trim();
            String telefono = telefonoMedico.getText().toString().trim();
            String emailM = emailMedico.getText().toString().trim();
            String direccion = direccionMedico.getText().toString().trim();

            int valor = medicoControlador.verificarDatosMedico(nombreM, especialidadMed, telefono, emailM, direccion);

            switch (valor) {
                case 0:
                    mostrarToast(R.string.No_se_ingreso_ningun_dato);
                    break;
                case 1:
                    mostrarToast(R.string.nombre_faltante_medico);
                    break;
                case 2:
                    mostrarToast(R.string.especialidad_faltante);
                    break;
                case 3:
                    mostrarToast(R.string.telefono_faltante);
                    break;
                case 4:
                    mostrarToast(R.string.email_invalido);
                    break;
                case 5:
                    mostrarToast(R.string.direccion_faltante);
                    break;
                case 6:
                    try {
                        // Crear nuevo médico
                        MedicoModelo nuevoMedico = new MedicoModelo(nombreM, especialidadMed, telefono, emailM, direccion);

                        // Agregar médico a la lista y serializar
                        listaMedicoModelo.guardarMedicoEnArchivo(nuevoMedico);

                        mostrarToast(R.string.medico_agregado);

                        // Redirigir a AdministrarMedicosActivity
                        Intent intent = new Intent(AnadirMedicoActivity.this, AdministrarMedicosActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        if ("Medico ya existe".equals(e.getMessage())) {
                            mostrarToast(R.string.medico_existente);
                        } else {
                            Toast.makeText(this, "Error al guardar el médico: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 7:
                    mostrarToast(R.string.medico_existente);
                    break;
            }
        });
    }

    private void mostrarToast(int mensaje) {
        Toast.makeText(this, getString(mensaje), Toast.LENGTH_SHORT).show();
    }
}
