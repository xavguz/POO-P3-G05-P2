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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Controlador.ActividadFisicaControlador;
import com.poo.proyectopoop2.Controlador.CitaMedicaControlador;
import com.poo.proyectopoop2.Modelo.ActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.CitaMedicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AnadirActividadFisicaActivity extends AppCompatActivity {
    private Spinner sp1;  // Spinner para actividades físicas
    private EditText fecha;
    private Spinner sp2;  // Spinner para horarios
    private EditText duracion;
    private ActividadFisicaControlador actividadFisicaControlador;
    private ListaActividadFisicaModelo listaActividadFisicaModelo;
    private PerfilModelo perfilActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_actividad_fisica);

        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AnadirActividadFisica", "Perfil no recibido.");
            finish();
            return;
        }

        sp1 = findViewById(R.id.spinnerActividad);
        fecha = findViewById(R.id.ingresarFecha);
        sp2 = findViewById(R.id.spinnerHorario);
        duracion = findViewById(R.id.ingresarConsulta);

        actividadFisicaControlador = new ActividadFisicaControlador(this, perfilActual);
        listaActividadFisicaModelo = new ListaActividadFisicaModelo(this);

        configurarSpinnerActividades();
        configurarSpinnerHorarios();

        // Botón para guardar actividad
        Button btnGuardarActividad = findViewById(R.id.btnGuardar);
        btnGuardarActividad.setOnClickListener(v -> agregarActividad());
    }

    private void configurarSpinnerActividades() {
        ArrayList<String> actividades = new ArrayList<>();
        actividades.add("Caminar");
        actividades.add("Trotar");
        actividades.add("Correr");
        actividades.add("Funcional");
        actividades.add("Crossfit");
        actividades.add("Entrenamiento de fuerza");
        actividades.add("Nadar");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, actividades);
        sp1.setAdapter(adapter);
    }

    private void configurarSpinnerHorarios() {
        ArrayList<String> horarios = new ArrayList<>();
        horarios.add("Mañana");
        horarios.add("Tarde");
        horarios.add("Noche");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, horarios);
        sp2.setAdapter(adapter);
    }

    private void agregarActividad() {
        String actividadSeleccionada = (sp1.getSelectedItem() != null) ? sp1.getSelectedItem().toString().trim() : "";
        String fechaSeleccionada = fecha.getText().toString().trim();
        String horarioSeleccionado = (sp2.getSelectedItem() != null) ? sp2.getSelectedItem().toString().trim() : "";
        String duracionSeleccionada = duracion.getText().toString().trim();

        if (actividadSeleccionada.isEmpty() || fechaSeleccionada.isEmpty() || horarioSeleccionado.isEmpty() || duracionSeleccionada.isEmpty()) {
            mostrarToast("Todos los campos son obligatorios.");
            return;
        }

        FechaModelo fechaModelo = new FechaModelo(fechaSeleccionada);

        int validacion = actividadFisicaControlador.verificarDatosActividad(actividadSeleccionada, duracionSeleccionada, horarioSeleccionado, fechaModelo);

        if (validacion == 5) { // Actividad agregada con éxito
            try {
                ActividadFisicaModelo nuevaActividad = new ActividadFisicaModelo(fechaModelo, actividadSeleccionada, duracionSeleccionada, horarioSeleccionado);
                perfilActual.getActividadesFisicas().add(nuevaActividad);
                listaActividadFisicaModelo.guardarActividadFisicaEnArchivo(perfilActual, nuevaActividad);

                mostrarToast("Actividad guardada correctamente.");

                // Devolver el perfil actualizado
                Intent intent = new Intent();
                intent.putExtra("perfil", perfilActual);
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                Log.e("ErrorGuardarActividad", "Error al guardar actividad: " + e.getMessage());
                mostrarToast("Error al guardar actividad.");
            }
        } else if (validacion == 6) {
            mostrarToast("Esta actividad ya está registrada.");
        }
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
