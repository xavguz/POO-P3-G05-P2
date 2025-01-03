package com.poo.proyectopoop2.Controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.R;

public class MedicoControlador extends AppCompatActivity {
    private ListaMedicoModelo listaMedicoModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_medico);

        listaMedicoModelo = new ListaMedicoModelo();

        ListView listViewMedicos = findViewById(R.id.listViewMedicos);
        Button btnAgregarMedico = findViewById(R.id.btnAgregarMedico);

        ArrayAdapter<MedicoModelo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMedicoModelo.getListaMedicos());
        listViewMedicos.setAdapter(adapter);

        btnAgregarMedico.setOnClickListener(v -> {
            Intent intent = new Intent(MedicoControlador.this, AnadirMedico.class);
            startActivity(intent);
        });
    }
}



