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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Controlador.CitaMedicaControlador;
import com.poo.proyectopoop2.Modelo.ActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

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
    private EditText fecha;  // Campo de texto para ingresar la fecha
    private Spinner sp2;  // Spinner para horarios
    private EditText duracion;  // Campo de texto para ingresar la duración (ya no se usará)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_actividad_fisica);

        // Configurar botón de volver
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirActividadFisicaActivity.this, AdministrarActividadFisicaActivity.class);
            startActivity(intent);
        });

        // Inicializar vistas
        sp1 = findViewById(R.id.spinnerActividad);
        fecha = findViewById(R.id.ingresarFecha);
        duracion = findViewById(R.id.ingresarConsulta); // Este campo ya no se usará para duraciones

        // Configurar spinner de actividades físicas
        ArrayList<String> actividades = new ArrayList<>();
        actividades.add("Caminar");
        actividades.add("Trotar");
        actividades.add("Correr");
        actividades.add("Funcional");
        actividades.add("Crossfit");
        actividades.add("Entrenamiento de fuerza");
        actividades.add("Nadar");

        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, actividades);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp1.setAdapter(adapter1);

        // Configurar spinner de horarios (antes era de duraciones)
        ArrayList<String> horarios = new ArrayList<>();
        horarios.add("Mañana");
        horarios.add("Tarde");
        horarios.add("Noche");

        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, horarios);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp2.setAdapter(adapter2);

        // Configurar acción al seleccionar una actividad
        sp1.setOnItemSelectedListener((parent, view, position, id) -> {
            String item1 = parent.getItemAtPosition(position).toString();
            Toast.makeText(AnadirActividadFisicaActivity.this, "Actividad seleccionada: " + item1, Toast.LENGTH_SHORT).show();
        });
    }

    // Método para manejar el guardado de la actividad física
    public void guardarActividadFisica(View view) {
        String actividadSeleccionada = sp1.getSelectedItem().toString();
        String fechaSeleccionada = fecha.getText().toString();
        String horarioSeleccionado = sp2.getSelectedItem().toString();  // Ahora seleccionamos el horario

        // Validar que todos los campos estén completos
        if (fechaSeleccionada.isEmpty() || horarioSeleccionado.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto de actividad física (si tu modelo necesita el horario, actualízalo aquí)
        FechaModelo fechaModelo = new FechaModelo(fechaSeleccionada);  // Se asume que FechaModelo está correctamente implementado
        ActividadFisicaModelo actividadFisica = new ActividadFisicaModelo(fechaModelo, actividadSeleccionada, horarioSeleccionado, fechaSeleccionada);

        // Guardar la actividad física (esto puede incluir el proceso de serialización si es necesario)
        // Aquí puedes agregar la lógica para almacenar esta actividad física en un archivo o base de datos

        // Confirmación de guardado
        Toast.makeText(this, "Actividad física guardada con éxito.", Toast.LENGTH_SHORT).show();

        // Regresar a la actividad anterior
        Intent intent = new Intent(AnadirActividadFisicaActivity.this, AdministrarActividadFisicaActivity.class);
        startActivity(intent);
    }
}
