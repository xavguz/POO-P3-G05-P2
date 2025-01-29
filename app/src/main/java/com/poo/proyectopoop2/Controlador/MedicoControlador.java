package com.poo.proyectopoop2.Controlador;

import android.content.Context;
import android.util.Log;

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
    private PerfilModelo perfilActual;

    public MedicoControlador(Context context, PerfilModelo perfil) {
        this.listaMedicoModelo = new ListaMedicoModelo(context);
        this.perfilActual = perfil;

        try {
            listaMedicoModelo.deserializarArrayListMedico(perfilActual);
        } catch (IOException | ClassNotFoundException e) {
            Log.e("MedicoControlador", "Error al cargar médicos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener la lista de médicos del perfil actual.
     */
    public ArrayList<MedicoModelo> obtenerMedicos() {
        return perfilActual.getMedicos();
    }

    /**
     * Verifica si los datos del médico son correctos antes de guardarlo.
     */
    public int verificarDatosMedico(String nombre, String especialidad, String telefono, String email, String direccion) {
        if (nombre.isEmpty() && especialidad.isEmpty() && telefono.isEmpty() && email.isEmpty() && direccion.isEmpty()) {
            return 0;
        } else if (nombre.isEmpty()) {
            return 1;
        } else if (especialidad.isEmpty()) {
            return 2;
        } else if (telefono.isEmpty()) {
            return 3;
        } else if (email.isEmpty()) {
            return 4;
        } else if (direccion.isEmpty()) {
            return 5;
        } else {
            return verificarMedico(nombre, especialidad, telefono, email, direccion);
        }
    }

    /**
     * Verifica si un médico ya existe en la lista del perfil actual.
     */
    public int verificarMedico(String nombre, String especialidad, String telefono, String email, String direccion) {
        for (MedicoModelo medicoVerificar : perfilActual.getMedicos()) {
            if (medicoVerificar.getNombre().equalsIgnoreCase(nombre) &&
                    medicoVerificar.getEspecialidades().equalsIgnoreCase(especialidad) &&
                    medicoVerificar.getTelefono().equalsIgnoreCase(telefono) &&
                    medicoVerificar.getEmail().equalsIgnoreCase(email) &&
                    medicoVerificar.getDireccion().equalsIgnoreCase(direccion)) {
                return 7; // Médico duplicado
            }
        }

        crearMedico(nombre, especialidad, telefono, email, direccion);
        return 6; // Médico agregado exitosamente
    }

    /**
     * Crea un nuevo médico y lo guarda en la lista del perfil actual.
     */
    public void crearMedico(String nombre, String especialidad, String telefono, String email, String direccion) {
        MedicoModelo medico = new MedicoModelo(nombre, especialidad, telefono, email, direccion);

        perfilActual.getMedicos().add(medico);

        try {
            listaMedicoModelo.serializarArrayListMedico(perfilActual);
            Log.d("MedicoControlador", "Médico agregado y guardado correctamente: " + nombre);
        } catch (IOException e) {
            Log.e("MedicoControlador", "Error al guardar el médico: " + e.getMessage(), e);
        }
    }
}

