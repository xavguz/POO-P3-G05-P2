package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtener el nombre del perfil seleccionado
        String perfilSeleccionado = getIntent().getStringExtra("perfilSeleccionado");
        // Mostrar el nombre del perfil en la parte superior
        TextView nombrePerfil = findViewById(R.id.textNombreUsuario);
        TextView relacionPeril = findViewById(R.id.textRelacionUsuario);
        nombrePerfil.setText(perfilSeleccionado);

        // Configurar botones
        Button btnMedicamentos = findViewById(R.id.btnAdministrarMedicamentos);
        Button btnMedicos = findViewById(R.id.btnAdministrarMedicos);
        Button btnCitas = findViewById(R.id.btnAdministrarCitas);
        Button btnActividadFisica = findViewById(R.id.btnAdministrarActividadFisica);


        btnMedicos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AdministrarMedicosActivity.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });

        btnCitas.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AdministrarCitasMedicas.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });

        btnActividadFisica.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AdministrarActividadFisicaActivity.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });

        btnMedicamentos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AdministrarMedicinaActivity.class);
            intent.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intent);
        });
    }
}
