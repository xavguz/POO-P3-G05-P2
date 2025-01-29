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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Controlador.MedicinaControlador;
import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.util.ArrayList;
import java.util.Arrays;

public class AnadirMedicinaActivity extends AppCompatActivity {

    private EditText nombreMedicamento, cantidadMedicamento, horaTomaMedicamento, dosisMedicamento;
    private Spinner presentacionMedicamento;
    private PerfilModelo perfilActual;
    private ListaMedicinaModelo listaMedicinaModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitty_anadir_medicina); // Asegurar que sea el layout correcto

        listaMedicinaModelo = new ListaMedicinaModelo(this);
        perfilActual = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        if (perfilActual == null) {
            mostrarToast("Error: Perfil no recibido correctamente.");
            Log.e("AnadirMedicinaActivity", "Perfil no recibido correctamente.");
            finish();
            return;
        }

        // **Inicializar los elementos del layout**
        nombreMedicamento = findViewById(R.id.ingresarNombreMedicamento);
        cantidadMedicamento = findViewById(R.id.ingresarCantidad);
        presentacionMedicamento = findViewById(R.id.spinnerPresentacion);
        horaTomaMedicamento = findViewById(R.id.ingresarHoraToma);
        dosisMedicamento = findViewById(R.id.ingresarDosis);

        // **Configurar el Spinner**
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Pastillas", "Inyección", "Solución", "Gotas", "Inhalador", "Polvo"});
        presentacionMedicamento.setAdapter(adapter);

        // **Botón Volver**
        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirMedicinaActivity.this, AdministrarMedicinaActivity.class);
            intent.putExtra("perfil", perfilActual);
            startActivity(intent);
            finish();
        });

        // **Botón Guardar Medicina**
        Button btnGuardarMedicina = findViewById(R.id.btnGuardarMedicina);
        if (btnGuardarMedicina != null) {
            btnGuardarMedicina.setOnClickListener(v -> agregarMedicina());
        } else {
            mostrarToast("Error: No se encontró el botón para guardar medicina.");
        }
    }

    private void agregarMedicina() {
        String nombre = nombreMedicamento.getText().toString().trim();
        String cantidadStr = cantidadMedicamento.getText().toString().trim();
        String dosisStr = dosisMedicamento.getText().toString().trim();
        String presentacion = presentacionMedicamento.getSelectedItem().toString().trim();
        String horaToma = horaTomaMedicamento.getText().toString().trim();

        if (nombre.isEmpty() || cantidadStr.isEmpty() || dosisStr.isEmpty() || presentacion.isEmpty() || horaToma.isEmpty()) {
            mostrarToast("Todos los campos son obligatorios.");
            return;
        }

        try {
            float cantidad = Float.parseFloat(cantidadStr);
            float dosis = Float.parseFloat(dosisStr);

            MedicinaModelo nuevaMedicina = new MedicinaModelo(nombre, cantidad, presentacion, horaToma, dosis);
            listaMedicinaModelo.guardarMedicinasEnArchivo(perfilActual, nuevaMedicina);
            perfilActual.getMedicinas().add(nuevaMedicina);

            mostrarToast("Medicina agregada correctamente.");

            // Regresar a AdministrarMedicinaActivity actualizando la lista
            Intent intent = new Intent(AnadirMedicinaActivity.this, AdministrarMedicinaActivity.class);
            intent.putExtra("perfil", perfilActual);
            startActivity(intent);
            finish();

        } catch (NumberFormatException e) {
            mostrarToast("Error en formato de número.");
        } catch (Exception e) {
            mostrarToast("Error al guardar la medicina.");
            Log.e("AnadirMedicinaActivity", "Error al guardar medicina: " + e.getMessage(), e);
        }
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}