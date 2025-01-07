package com.poo.proyectopoop2.Controlador;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.R;

public class MedicoControlador extends AppCompatActivity {
    private ListaMedicoModelo listaMedicoModelo;
    private ArrayAdapter<MedicoModelo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medico);

        listaMedicoModelo = new ListaMedicoModelo();

        ListView listViewMedicos = findViewById(R.id.listViewMedicos);
        Button btnAgregarMedico = findViewById(R.id.btnAgregarMedico);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMedicoModelo.getListaMedicos());
        listViewMedicos.setAdapter(adapter);

        // Botón para agregar un médico
        btnAgregarMedico.setOnClickListener(v -> {
            Intent intent = new Intent(MedicoControlador.this, AnadirMedico.class);
            startActivityForResult(intent, 1); // Código de solicitud para manejar resultado
        });

        // Eliminación de médico al hacer clic largo en un ítem de la lista
        listViewMedicos.setOnItemLongClickListener((parent, view, position, id) -> {
            listaMedicoModelo.eliminarMedico(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(MedicoControlador.this, "Médico eliminado", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Recuperar datos enviados desde la actividad AnadirMedico
            String nombre = data.getStringExtra("nombre");
            String especialidad = data.getStringExtra("especialidad");
            String telefono = data.getStringExtra("telefono");
            String email = data.getStringExtra("email");
            String direccion = data.getStringExtra("direccion");

            MedicoModelo nuevoMedico = new MedicoModelo(nombre, especialidad, telefono, email, direccion);
            listaMedicoModelo.agregarMedico(nuevoMedico);

            // Actualizar la lista
            adapter.notifyDataSetChanged();
        }
    }
}



