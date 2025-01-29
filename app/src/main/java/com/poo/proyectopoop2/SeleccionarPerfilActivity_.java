package com.poo.proyectopoop2;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;


public class SeleccionarPerfilActivity_ extends AppCompatActivity {
    private ListaPerfilesModelo listaPerfilesModelo;
    private ArrayList<PerfilModelo> perfiles;
    private ArrayAdapter<PerfilModelo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil3);

        listaPerfilesModelo = new ListaPerfilesModelo(this);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarPerfilActivity_.this, MainActivity.class);
            startActivity(intent);
        });

        ListView listaPerfiles = findViewById(R.id.lista_perfil);

        perfiles = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, perfiles);
        listaPerfiles.setAdapter(adapter);

        cargarPerfilesDesdeArchivo();

        listaPerfiles.setOnItemClickListener((parent, view, position, id) -> {
            PerfilModelo perfilSeleccionado = perfiles.get(position);

            // Pasar el perfil completo en lugar de solo el nombre
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("perfil", perfilSeleccionado);  // 'perfil' es el objeto de tipo PerfilModelo
            startActivity(intent);
        });
    }

    private void cargarPerfilesDesdeArchivo() {
        try {
            listaPerfilesModelo.deserializarArrayList();
            ArrayList<PerfilModelo> perfilesCargados = listaPerfilesModelo.getPerfiles();

            // Limpiar la lista para evitar duplicados
            perfiles.clear();

            // Agregar perfiles sin duplicados
            for (PerfilModelo perfil : perfilesCargados) {
                if (!perfiles.contains(perfil)) {
                    perfiles.add(perfil);
                }
            }

            // Notificar cambios en el adapter
            adapter.notifyDataSetChanged();

        } catch (IOException | ClassNotFoundException e) {
            Log.e("SeleccionarPerfil", "Error al cargar perfiles: " + e.getMessage(), e);
            Toast.makeText(this, "Error al cargar perfiles.", Toast.LENGTH_SHORT).show();
        }
    }
}



