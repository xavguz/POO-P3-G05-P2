package com.poo.proyectopoop2.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Medico_Activity;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.R;

public class AnadirMedicamento extends AppCompatActivity {

    private EditText nombreMedicamento;
    private EditText cantidad;
    private EditText presentacion;
    private EditText hora;
    private EditText dosis;
    MedicinaControlador medicinaControlador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitty_anadir_medicina);


        nombreMedicamento = findViewById(R.id.ingresarNombreMedicamento);
        cantidad = findViewById(R.id.ingresarCantidad);
        presentacion = findViewById(R.id.ingresarPresentacion);
        hora = findViewById(R.id.ingresarHoraToma);
        dosis = findViewById(R.id.ingresarDosis);
    }
}