package com.poo.proyectopoop2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poo.proyectopoop2.Controlador.AnadirMedico;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoAdapter;
import com.poo.proyectopoop2.Modelo.MedicoModelo;

import java.io.IOException;

public class AdministrarMedicosActivity extends AppCompatActivity {

    ListaMedicoModelo listaMedicoModelo;
    private MedicoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrar_medicos);

        ImageButton btnVolver = findViewById(R.id.volver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicosActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        Button anadir = findViewById(R.id.btnanadir);
        anadir.setOnClickListener(v -> {
            Intent intent = new Intent(AdministrarMedicosActivity.this, AnadirMedicoActivity.class);
            startActivity(intent);
        });
    }
}
