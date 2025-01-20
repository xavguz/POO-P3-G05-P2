package com.poo.proyectopoop2;


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

import com.poo.proyectopoop2.Controlador.PerfilControlador;
import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeleccionarPerfilActivity_ extends AppCompatActivity {
    private ListaPerfilesModelo listaPerfilesModelo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil3);

        // Asegúrate de inicializar correctamente el modelo
        listaPerfilesModelo = new ListaPerfilesModelo(this);  // Inicialización

        ListView listaPerfiles = findViewById(R.id.lista_perfil);

        try {
            // Cargar perfiles desde el archivo
            ArrayList<PerfilModelo> perfiles = cargarPerfilesDesdeArchivo();

            if (perfiles.isEmpty()) {
                // Mostrar mensaje si no hay perfiles disponibles
                Toast.makeText(this, "No hay perfiles disponibles.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
                return;
            }

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

        } catch (Exception e) {
            // Manejo de excepciones al cargar perfiles
            System.out.println("Error al cargar perfiles: " + e.getMessage());
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private ArrayList<PerfilModelo> cargarPerfilesDesdeArchivo() throws IOException, ClassNotFoundException {
        // Deserializar los perfiles
        listaPerfilesModelo.deserializarArrayList();  // Aquí se asegura de que no sea null
        return listaPerfilesModelo.getPerfiles();
    }
}