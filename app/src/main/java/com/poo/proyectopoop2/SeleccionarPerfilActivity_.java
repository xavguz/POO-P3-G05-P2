package com.poo.proyectopoop2;


import android.content.Intent;
import android.os.Bundle;

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
    private ListaPerfilesModelo listaPerfilesModelo = new ListaPerfilesModelo(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil3);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarPerfilActivity_.this, MainActivity.class);
            startActivity(intent);
        });


        ListView listaPerfiles = findViewById(R.id.lista_perfil);

        try {
            ArrayList<PerfilModelo> perfiles = cargarPerfilesDesdeArchivo();

            if (perfiles.isEmpty()) {
                Toast.makeText(this, "No hay perfiles disponibles.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
                return;
            }

            ArrayAdapter<PerfilModelo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, perfiles);
            listaPerfiles.setAdapter(adapter);

            listaPerfiles.setOnItemClickListener((parent, view, position, id) -> {
                PerfilModelo perfilSeleccionado = perfiles.get(position);

                // Enviar al menú con el perfil seleccionado
                Intent intent = new Intent(SeleccionarPerfilActivity_.this, MenuActivity.class);
                intent.putExtra("perfilSeleccionado", perfilSeleccionado.getNombre());
                startActivity(intent);
            });

        } catch (Exception e) {
            System.out.println("Error al cargar perfiles: " + e.getMessage());
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private ArrayList<PerfilModelo> cargarPerfilesDesdeArchivo() throws IOException, ClassNotFoundException {
        listaPerfilesModelo.deserializarArrayList();
        return listaPerfilesModelo.getPerfiles();
    }
}
