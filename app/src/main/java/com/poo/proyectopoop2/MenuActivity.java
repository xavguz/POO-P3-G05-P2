package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.PerfilModelo;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtener el perfil completo desde el Intent
        PerfilModelo perfilSeleccionado = (PerfilModelo) getIntent().getSerializableExtra("perfil");

        // Verificar que el perfil no sea nulo
        if (perfilSeleccionado == null) {
            Toast.makeText(this, "Perfil no recibido correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Finaliza la actividad si el perfil no es válido
            return;
        }

        // Mostrar el nombre y la relación del perfil en la parte superior
        TextView nombrePerfil = findViewById(R.id.textNombreUsuario);
        TextView relacionPerfil = findViewById(R.id.textRelacionUsuario);

        nombrePerfil.setText(perfilSeleccionado.getNombre());
        relacionPerfil.setText(perfilSeleccionado.getRelacion());

        // Configurar los botones para navegar a otras actividades
        Button btnMedicamentos = findViewById(R.id.btnAdministrarMedicamentos);
        Button btnMedicos = findViewById(R.id.btnAdministrarMedicos);
        Button btnCitas = findViewById(R.id.btnAdministrarCitas);
        Button btnActividadFisica = findViewById(R.id.btnAdministrarActividadFisica);

        // Configurar los listeners de los botones para pasar el perfil completo
        configurarBoton(btnMedicamentos, perfilSeleccionado, AdministrarMedicinaActivity.class);
        configurarBoton(btnMedicos, perfilSeleccionado, AdministrarMedicosActivity.class);
        configurarBoton(btnCitas, perfilSeleccionado, AdministrarCitasMedicas.class);
        configurarBoton(btnActividadFisica, perfilSeleccionado, AdministrarActividadFisicaActivity.class);
    }

    // Método para configurar el listener de los botones
    private void configurarBoton(Button boton, final PerfilModelo perfil, final Class<?> claseDestino) {
        boton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, claseDestino);
            intent.putExtra("perfil", perfil); // Usar la misma clave "perfil"
            startActivity(intent);
        });
    }
}



