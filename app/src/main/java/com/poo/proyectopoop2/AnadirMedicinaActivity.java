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
import com.poo.proyectopoop2.Modelo.MedicinaModelo;

import java.util.ArrayList;

public class AnadirMedicinaActivity extends AppCompatActivity {

    private EditText nombreMedicamento;
    private EditText cantidadMedicamento;
    private Spinner presentacionMedicamento;
    private EditText horaTomaMedicamento;
    private EditText dosisMedicamento;
    private ListView listViewMedicinas;
    private ArrayAdapter<MedicinaModelo> medicinaAdapter;
    private ListaMedicinaModelo medicinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitty_anadir_medicina);

        medicinas = new ListaMedicinaModelo(this);

        nombreMedicamento = findViewById(R.id.ingresarNombreMedicamento);
        cantidadMedicamento = findViewById(R.id.ingresarCantidad);
        presentacionMedicamento = findViewById(R.id.spinnerPresentacion);
        horaTomaMedicamento = findViewById(R.id.ingresarHoraToma);
        dosisMedicamento = findViewById(R.id.ingresarDosis);

        // Inicializar ListView y el ArrayAdapter
        listViewMedicinas = findViewById(R.id.lista_medicinas);
        ArrayList<MedicinaModelo> listaMedicinas = medicinas.cargarMedicinasDesdeArchivo();
        medicinaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMedicinas);
        listViewMedicinas.setAdapter(medicinaAdapter);

        MedicinaControlador medicinaControlador = new MedicinaControlador(this);

        presentacionMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AnadirMedicinaActivity.this, "Presentacion seleccionada: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> presentaciones = new ArrayList<>();
        presentaciones.add("Pastillas");
        presentaciones.add("Inyeccion");
        presentaciones.add("Solucion");
        presentaciones.add("Gotas");
        presentaciones.add("Inhalador");
        presentaciones.add("Polvo");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presentaciones);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        presentacionMedicamento.setAdapter(adapter);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AnadirMedicinaActivity.this, AdministrarMedicinaActivity.class);
            startActivity(intent);
        });

        Button btnCrear = findViewById(R.id.btnGuardarMedicina);
        // En el método btnCrear.setOnClickListener
        btnCrear.setOnClickListener(v -> {
            String nombre = nombreMedicamento.getText().toString().trim();
            // Verificar si 'cantidad' es un número válido
            String cantidadStr = cantidadMedicamento.getText().toString().trim();
            float cantidad = 0;
            try {
                cantidad = Float.parseFloat(cantidadStr);
            } catch (NumberFormatException e) {
                mostrarToast(R.string.cantidad_no_valida);
                return;
            }

            // Verificar si 'dosis' es un número válido
            String dosisStr = dosisMedicamento.getText().toString().trim();
            float dosis = 0;
            try {
                dosis = Float.parseFloat(dosisStr);
            } catch (NumberFormatException e) {
                mostrarToast(R.string.dosis_no_valida);
                return;
            }

            String presentacion = (presentacionMedicamento.getSelectedItem() != null) ?
                    presentacionMedicamento.getSelectedItem().toString().trim() : "";
            String horaToma = horaTomaMedicamento.getText().toString().trim();

            int valor = medicinaControlador.verificarDatosMedicina(nombre, cantidadStr, presentacion, horaToma, dosisStr);

            switch (valor) {
                case 0:
                    mostrarToast(R.string.No_se_ingreso_ningun_dato);
                    break;
                case 1:
                    mostrarToast(R.string.nombre_faltante_medicina);
                    break;
                case 2:
                    mostrarToast(R.string.cantidad_faltante);
                    break;
                case 3:
                    mostrarToast(R.string.presentacion_faltante);
                    break;
                case 4:
                    mostrarToast(R.string.hora_faltante);
                    break;
                case 5:
                    mostrarToast(R.string.dosis_faltante);
                    break;
                case 6:
                    try {
                        MedicinaModelo nuevaMedicina = new MedicinaModelo(nombre, cantidad, presentacion, horaToma, dosis);
                        medicinas.guardarMedicinasEnArchivo(nuevaMedicina);
                        mostrarToast(R.string.medicina_agregado);

                        // Actualizar el ListView después de agregar la medicina
                        medicinaAdapter.notifyDataSetChanged();

                        // Redirigir a AdministrarMedicosActivity
                        Intent intent = new Intent(AnadirMedicinaActivity.this, AdministrarMedicinaActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        // Aquí captura cualquier error
                        Log.e("AnadirMedicinaActivity", "Error al guardar medicina: " + e.getMessage(), e);
                        Toast.makeText(this, "Error al guardar el medicamento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 7:
                    mostrarToast(R.string.medicina_existente);
                    break;
                case 8:
                    mostrarToast(R.string.error_formato_numerico);
                    break;
            }
        });

    }

    private void mostrarToast(int mensaje) {
        Toast.makeText(this, getString(mensaje), Toast.LENGTH_SHORT).show();
    }
}


