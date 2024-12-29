package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Controlador.PerfilControlador;


public class CrearPerfilActivity extends AppCompatActivity {
    PerfilControlador perfilControlador;
    private EditText nombreUsuario;
    private EditText relacionUsuario;
    private EditText emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_perfil);

        nombreUsuario = findViewById(R.id.ingresarUsuario);
        relacionUsuario = findViewById(R.id.ingresarRelacion);
        emailUsuario = findViewById(R.id.ingresarEmail);
        perfilControlador = new PerfilControlador();


        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(CrearPerfilActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button btnCrear = findViewById(R.id.crear);
        btnCrear.setOnClickListener(v -> {
            String nombre = nombreUsuario.getText().toString().trim();
            String relacion = relacionUsuario.getText().toString().trim();
            String email = emailUsuario.getText().toString().trim();
            int valor = perfilControlador.verificarDatos(nombre,relacion,email);

            switch (valor){
                case 0:
                    Toast.makeText(this, this.getString(R.string.nombre_relacion_faltante),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, this.getString(R.string.nombre_faltante),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, this.getString(R.string.relacion_faltante),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, this.getString(R.string.perfil_creado),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CrearPerfilActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    Toast.makeText(this, this.getString(R.string.perfil_existente),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}