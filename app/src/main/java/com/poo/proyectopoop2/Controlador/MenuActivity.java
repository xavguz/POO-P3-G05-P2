package com.poo.proyectopoop2.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Medico_Activity;
import com.poo.proyectopoop2.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtener el nombre del perfil seleccionado
        String perfilSeleccionado = getIntent().getStringExtra("perfilSeleccionado");

        // Mostrar el nombre del perfil en la parte superior
        TextView nombrePerfil = findViewById(R.id.textNombreUsuario);
        nombrePerfil.setText(perfilSeleccionado);

        // Configurar botones
        Button btnMedicamentos = findViewById(R.id.btnAdministrarMedicamentos);
        Button btnMedicos = findViewById(R.id.btnAdministrarMedicos);
        Button btnCitas = findViewById(R.id.btnAdministrarCitas);
        Button btnActividadFisica = findViewById(R.id.btnAdministrarActividadFisica);

        btnMedicamentos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AnadirMedico.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });

        btnMedicos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, Medico_Activity.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });

        btnCitas.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CitaMedicaControlador.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });

        btnActividadFisica.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AnadirCitaMedica.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });
    }
}
