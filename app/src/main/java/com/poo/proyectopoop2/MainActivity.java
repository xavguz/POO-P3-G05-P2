package com.poo.proyectopoop2;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Controlador.PerfilControlador;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void botonCrearPerfil(View view) {
        setContentView(R.layout.activity_crearperfil);
    }

    public void botonVolver(View view) {
        setContentView(R.layout.activity_main);
    }
}