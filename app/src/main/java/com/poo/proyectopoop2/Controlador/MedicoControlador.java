package com.poo.proyectopoop2.Controlador;

import android.content.Context;

import com.poo.proyectopoop2.Modelo.ListaMedicoModelo;
import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.MedicoModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MedicoControlador implements Serializable {
    private ListaMedicoModelo listaMedicoModelo;
    private ExecutorService executorService;

    public ArrayList<MedicoModelo> obtenerMedicos() {
        return listaMedicoModelo.getListaMedicos();
    }

    public MedicoControlador(Context context){
        this.listaMedicoModelo = new ListaMedicoModelo(context);
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                listaMedicoModelo.deserializarArrayListMedico();
            } catch (IOException | ClassNotFoundException ignored) {
            }
        });
    }

    public int verificarDatosMedico( String nombre, String especialidad, String telefono, String email, String direccion){

        if (nombre.isEmpty() &&  especialidad.isEmpty() && telefono.isEmpty() && email.isEmpty() && direccion.isEmpty()) {
            return 0;
        }
        else if (nombre.isEmpty()) {
            return 1;
        }
        else if (especialidad.isEmpty()) {
            return 2;
        }
        else if(telefono.isEmpty()) {
            return 3;
        }
        else if(email.isEmpty()) {
            return 4;
        }
        else if(direccion.isEmpty()) {
            return 5;
        }
        else {
            return verificarMedico(nombre,especialidad,telefono,email,direccion);
        }
    }

    public int verificarMedico(String nombre, String especialidad, String telefono, String email, String direccion){
        for (MedicoModelo medicoVerificar : listaMedicoModelo.getListaMedicos()) {
            if (medicoVerificar.getNombre().equalsIgnoreCase(nombre) &&
                    medicoVerificar.getEspecialidades().equalsIgnoreCase(especialidad) &&
                    medicoVerificar.getTelefono().equalsIgnoreCase(telefono)&&
                    medicoVerificar.getEmail().equalsIgnoreCase(email) &&
                    medicoVerificar.getDireccion().equalsIgnoreCase(direccion)) {
                return 7;
            }
        }
        crearMedico(nombre, especialidad,telefono, email,direccion);
        return 6;
    }

    public void crearMedico(String nombre, String especialidad, String telefono, String email, String direccion){
        MedicoModelo medico = new MedicoModelo(nombre,especialidad,telefono,email,direccion);

        listaMedicoModelo.agregarMedico(medico);

        try {
            listaMedicoModelo.serializarArrayListMedico();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

