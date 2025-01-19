package com.poo.proyectopoop2;

import static com.poo.proyectopoop2.Modelo.ListaPerfilesModelo.cargarPerfilesDesdeArchivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.util.ArrayList;
import java.util.List;

public class SeleccionarPerfilActivity_ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil3);

        ListView listaPerfiles = findViewById(R.id.lista_perfil);

        try{
            ArrayList<PerfilModelo> perfiles = cargarPerfilesDesdeArchivo();

            if (perfiles.isEmpty()) {
                // Mostrar mensaje si no hay perfiles disponibles
                Toast.makeText(this, "No hay perfiles disponibles.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
                return;}
            // Configurar adaptador para mostrar los perfiles
            ArrayAdapter<PerfilModelo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, perfiles);
            listaPerfiles.setAdapter(adapter);

            // Manejar la selección de un perfil
            listaPerfiles.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                PerfilModelo perfilSeleccionado = perfiles.get(position);
                Intent intent = new Intent();
                intent.putExtra("perfilSeleccionado", perfilSeleccionado.getNombre());
                setResult(RESULT_OK, intent);
                finish();
            });
    }catch (Exception e){
                System.out.println(("Error al cargar perfiles: " + e.getMessage()));
                setResult(RESULT_CANCELED);
                finish();
        }
    }
}


