package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poo.proyectopoop2.Controlador.AnadirMedico;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoAdapter;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

import java.io.IOException;


public class Medico_Activity extends AppCompatActivity {
    ListaMedicoModelo listaMedicoModelo;
    private MedicoAdapter adapter;

    public  Medico_Activity(){};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_controlador);

        listaMedicoModelo = new ListaMedicoModelo(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMedicos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el adaptador correctamente y asignarlo al RecyclerView
        adapter = new MedicoAdapter(this, listaMedicoModelo.getListaMedicos());
        recyclerView.setAdapter(adapter);

        Button btnAgregarMedico = findViewById(R.id.btnAgregarMedico);
        btnAgregarMedico.setOnClickListener(v -> {
            Intent intent = new Intent(Medico_Activity.this, AnadirMedico.class);
            startActivityForResult(intent, 1);
        });

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(Medico_Activity.this, MenuActivity.class);
            startActivity(intent);
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String nombre = data.getStringExtra("nombre");
            String especialidad = data.getStringExtra("especialidad");
            String telefono = data.getStringExtra("telefono");
            String email = data.getStringExtra("email");
            String direccion = data.getStringExtra("direccion");

            MedicoModelo nuevoMedico = new MedicoModelo(nombre, especialidad, telefono, email, direccion);
            listaMedicoModelo.agregarMedico(nuevoMedico);

            try {
                listaMedicoModelo.serializarArrayListMedico();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }

            // Asegúrate de notificar al adaptador
            adapter.notifyDataSetChanged();
        }
    }
}