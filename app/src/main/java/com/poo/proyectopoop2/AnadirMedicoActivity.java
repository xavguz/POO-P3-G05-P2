package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.util.ArrayList;

public class AnadirMedicoActivity extends AppCompatActivity {
    private EditText nombreMedico, telefonoMedico, emailMedico, direccionMedico;
    private Spinner especialidad;
    private MedicoControlador medicoControlador;
    private ListaMedicoModelo listaMedicoModelo;
    private PerfilModelo perfilActual; // Perfil del usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_medico);

        // Recibir el perfil
        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast(getString(R.string.error_formato_numerico));
            Log.e("AnadirMedicoActivity", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        // Inicializar los elementos del layout
        nombreMedico = findViewById(R.id.ingresarNombreDoctor);
        especialidad = findViewById(R.id.spinnerEspecialidad);
        telefonoMedico = findViewById(R.id.ingresarNumero);
        emailMedico = findViewById(R.id.ingresarEmail);
        direccionMedico = findViewById(R.id.ingresarDireccion);
        medicoControlador = new MedicoControlador(this, perfilActual);
        listaMedicoModelo = new ListaMedicoModelo(this);

        // Configurar Spinner de Especialidades
        configurarSpinnerEspecialidades();

        // Botón "Volver"
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        // Botón "Guardar Médico"
        Button btnGuardarMedico = findViewById(R.id.btnGuardarMedico);
        btnGuardarMedico.setOnClickListener(v -> agregarMedico());
    }

    /**
     * Configurar el Spinner con las especialidades médicas.
     */
    private void configurarSpinnerEspecialidades() {
        ArrayList<String> especialidades = new ArrayList<>();
        especialidades.add("Medicina General");
        especialidades.add("Pediatría");
        especialidades.add("Cardiología");
        especialidades.add("Dermatología");
        especialidades.add("Endocrinología");
        especialidades.add("Oncología");
        especialidades.add("Gastroenterología");
        especialidades.add("Geriatría");
        especialidades.add("Neurología");
        especialidades.add("Psiquiatría");
        especialidades.add("Traumatología");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, especialidades);
        especialidad.setAdapter(adapter);
    }

    /**
     * Agregar un médico y actualizar la lista en AdministrarMedicosActivity.
     */
    private void agregarMedico() {
        String nombreM = nombreMedico.getText().toString().trim();
        String especialidadMed = especialidad.getSelectedItem().toString().trim();
        String telefono = telefonoMedico.getText().toString().trim();
        String emailM = emailMedico.getText().toString().trim();
        String direccion = direccionMedico.getText().toString().trim();

        int validacion = medicoControlador.verificarDatosMedico(nombreM, especialidadMed, telefono, emailM, direccion);

        switch (validacion) {
            case 0:
                mostrarToast(getString(R.string.No_se_ingreso_ningun_dato));
                return;
            case 1:
                mostrarToast(getString(R.string.nombre_faltante_medico));
                return;
            case 2:
                mostrarToast(getString(R.string.especialidad_faltante));
                return;
            case 3:
                mostrarToast(getString(R.string.telefono_faltante));
                return;
            case 4:
                mostrarToast(getString(R.string.email_invalido));
                return;
            case 5:
                mostrarToast(getString(R.string.direccion_faltante));
                return;
            case 6:
                try {
                    // Crear nuevo médico
                    MedicoModelo nuevoMedico = new MedicoModelo(nombreM, especialidadMed, telefono, emailM, direccion);

                    // Agregar médico al perfil
                    perfilActual.getMedicos().add(nuevoMedico);
                    listaMedicoModelo.guardarMedicoEnArchivo(perfilActual, nuevoMedico);

                    mostrarToast(getString(R.string.medico_agregado));

                    // Enviar el perfil actualizado de regreso
                    Intent intent = new Intent();
                    intent.putExtra("perfil", perfilActual);
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (Exception e) {
                    if ("Medico ya existe".equals(e.getMessage())) {
                        mostrarToast(getString(R.string.medico_existente));
                    } else {
                        mostrarToast("Error al guardar el médico: " + e.getMessage());
                    }
                }
                return;
            case 7:
                mostrarToast(getString(R.string.medico_existente));
                return;
        }
    }

    /**
     * Mostrar mensajes con Toast.
     */
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}