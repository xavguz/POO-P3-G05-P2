package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Modelo.CitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaCitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.util.ArrayList;

public class AnadirCitaActivity extends AppCompatActivity {
    private EditText tituloCita, fechaCita, horaCita;
    private Spinner spinnerDoctor;
    private ListaCitaMedicaModelo listaCitaMedicaModelo;
    private PerfilModelo perfilActual;
    private ArrayList<MedicoModelo> listaMedicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_cita);

        // Recibir el perfil desde la actividad anterior
        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AnadirCitaMedicaActivity", "Perfil no recibido.");
            finish();
            return;
        }

        // Inicializar vistas
        tituloCita = findViewById(R.id.ingresarConsulta);
        fechaCita = findViewById(R.id.ingresarFecha);
        horaCita = findViewById(R.id.ingresarHora);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        listaCitaMedicaModelo = new ListaCitaMedicaModelo(this);

        // Cargar médicos en el Spinner
        cargarMedicosEnSpinner();

        // Configurar botón de volver
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        // Configurar botón de guardar
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> agregarCitaMedica());
    }

    private void cargarMedicosEnSpinner() {
        listaMedicos = perfilActual.getMedicos();

        if (listaMedicos.isEmpty()) {
            mostrarToast("No hay médicos registrados en este perfil.");
            return;
        }

        ArrayList<String> nombresMedicos = new ArrayList<>();
        for (MedicoModelo medico : listaMedicos) {
            nombresMedicos.add(medico.getNombre() + " (" + medico.getEspecialidades() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresMedicos);
        spinnerDoctor.setAdapter(adapter);
    }

    private void agregarCitaMedica() {
        String titulo = tituloCita.getText().toString().trim();
        String fechaSeleccionada = fechaCita.getText().toString().trim();
        String horaSeleccionada = horaCita.getText().toString().trim();
        int medicoSeleccionadoIndex = spinnerDoctor.getSelectedItemPosition();

        if (titulo.isEmpty() || fechaSeleccionada.isEmpty() || horaSeleccionada.isEmpty() || medicoSeleccionadoIndex == -1) {
            mostrarToast("Todos los campos son obligatorios.");
            return;
        }

        MedicoModelo doctorSeleccionado = listaMedicos.get(medicoSeleccionadoIndex);
        FechaModelo fechaModelo = new FechaModelo(fechaSeleccionada + " " + horaSeleccionada);

        CitaMedicaModelo nuevaCita = new CitaMedicaModelo(titulo, fechaModelo, doctorSeleccionado);

        // Verificar si ya existe la cita
        if (perfilActual.getCitaMedicas().contains(nuevaCita)) {
            mostrarToast("Esta cita ya está registrada.");
            return;
        }

        try {
            perfilActual.getCitaMedicas().add(nuevaCita);
            listaCitaMedicaModelo.guardarCitaMedicaEnArchivo(perfilActual, nuevaCita);

            mostrarToast("Cita guardada correctamente.");

            // Enviar perfil actualizado y cerrar
            Intent intent = new Intent();
            intent.putExtra("perfil", perfilActual);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            Log.e("ErrorGuardarCita", "Error al guardar cita: " + e.getMessage());
            mostrarToast("Error al guardar cita.");
        }
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}