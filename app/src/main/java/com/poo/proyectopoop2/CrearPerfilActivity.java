package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Controlador.PerfilControlador;
import com.poo.proyectopoop2.Modelo.PerfilModelo;
import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;

import java.io.File;
import java.util.ArrayList;


public class CrearPerfilActivity extends AppCompatActivity {
    PerfilControlador perfilControlador;
    private EditText nombreUsuario;
    private EditText relacionUsuario;
    private EditText emailUsuario;
    private ListaPerfilesModelo listaPerfilesModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_perfil);

        listaPerfilesModelo = new ListaPerfilesModelo(this);
        perfilControlador = new PerfilControlador(this);

        nombreUsuario = findViewById(R.id.ingresarUsuario);
        relacionUsuario = findViewById(R.id.ingresarRelacion);
        emailUsuario = findViewById(R.id.ingresarEmail);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(CrearPerfilActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual para evitar que el usuario regrese
        });

        Button btnCrear = findViewById(R.id.crear);
        btnCrear.setOnClickListener(v -> {
            String nombre = nombreUsuario.getText().toString().trim();
            String relacion = relacionUsuario.getText().toString().trim();
            String email = emailUsuario.getText().toString().trim();
            int valor = perfilControlador.verificarDatos(nombre, relacion, email);

            switch (valor) {
                case 0:
                    Toast.makeText(this, getString(R.string.nombre_relacion_faltante), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, getString(R.string.nombre_faltante), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, getString(R.string.relacion_faltante), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    try {
                        // Crear el nuevo perfil
                        PerfilModelo nuevoPerfil = new PerfilModelo(nombre, relacion, email);

                        // Agregar perfil al archivo
                        listaPerfilesModelo.guardarPerfilEnArchivo(nuevoPerfil);


                        // Regresar a la actividad principal
                        Intent intent = new Intent(CrearPerfilActivity.this, MainActivity.class);
                        intent.putExtra("perfilCreado", nuevoPerfil); // Puedes pasar el perfil creado si lo deseas
                        startActivity(intent);

                        // Finalizar la actividad actual para que no pueda regresar
                        finish();

                        Toast.makeText(this, getString(R.string.perfil_creado), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        if (e.getMessage().equals("Perfil ya existe")) {
                            Toast.makeText(this, getString(R.string.perfil_existente), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al guardar el perfil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 4:
                    Toast.makeText(this, getString(R.string.perfil_existente), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
