package com.poo.proyectopoop2.Controlador;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.poo.proyectopoop2.AdministrarMedicosActivity;
import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.R;

public class AnadirMedico extends AppCompatActivity {
    private EditText nombreMedico;
    private Spinner especialidadMedico;
    private EditText telefonoMedico;
    private EditText emailMedico;
    private EditText dirreccionMedico;
    MedicoControlador medicoControlador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_medico);


        nombreMedico = findViewById(R.id.ingresarNombreDoctor);
        especialidadMedico = findViewById(R.id.spinnerDoctor);
        telefonoMedico = findViewById(R.id.ingresarNumero);
        emailMedico= findViewById(R.id.ingresarEmail);
        dirreccionMedico= findViewById(R.id.ingresarDireccion);
        medicoControlador = new MedicoControlador(this);

        Button btnGuardarMedico = findViewById(R.id.btnGuardar);
        btnGuardarMedico.setOnClickListener(v -> {
            String nombreM = nombreMedico.getText().toString().trim();
            String especialidad = especialidadMedico.getPrompt().toString().trim();
            String telefono = telefonoMedico.getText().toString().trim();
            String emailM = emailMedico.getText().toString().trim();
            String direccion = dirreccionMedico.getText().toString().trim();

            int valor = medicoControlador.verificarDatosMedico(nombreM,especialidad,telefono,emailM,direccion);

            switch (valor){
                case 0:
                    Toast.makeText(this, this.getString(R.string.No_se_ingreso_ningun_dato),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, this.getString(R.string.nombre_faltante_medico),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, this.getString(R.string.especialidad_faltante),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, this.getString(R.string.telefono_faltante),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(this, this.getString(R.string.email_invalido),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(this, this.getString(R.string.direccion_faltante),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 6 :
                    try {
                        // Crear el nuevo perfil
                        MedicoModelo nuevomedico = new MedicoModelo(nombreM, especialidad, telefono,emailM,direccion);

                        // Ruta del directorio donde se almacenará el archivo
                        ListaMedicoModelo listamedicoModelo = new ListaMedicoModelo(this);

                        // Agregar perfil al archivo
                        listamedicoModelo.guardarMedicoEnArchivo(nuevomedico);

                        Toast.makeText(this, this.getString(R.string.medico_agregado),
                                Toast.LENGTH_SHORT).show();

                        // Regresar a la actividad principal
                        Intent intent = new Intent(AnadirMedico.this, AdministrarMedicosActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        if (e.getMessage().equals("Medico ya existe")) {
                            Toast.makeText(this, this.getString(R.string.medico_existente),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al guardar el perfil: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                    break;
                case 7:
                    Toast.makeText(this, this.getString(R.string.medico_existente),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }
}