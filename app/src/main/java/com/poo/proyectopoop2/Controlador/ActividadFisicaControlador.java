package com.poo.proyectopoop2.Controlador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.poo.proyectopoop2.Modelo.ActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActividadFisicaControlador {
    private ListaActividadFisicaModelo listaActividadModelo;
    private PerfilModelo perfilActual;
    private ExecutorService executorService;

    public ActividadFisicaControlador(Context context, PerfilModelo perfil) {
        this.listaActividadModelo = new ListaActividadFisicaModelo(context);
        this.perfilActual = perfil;
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                listaActividadModelo.deserializarArrayListActividadFisica(perfilActual);
            } catch (IOException | ClassNotFoundException e) {
                Log.e("ActividadFisicaCtrl", "Error al deserializar actividades", e);
            }
        });
    }

    /**
     * Obtiene la lista de actividades físicas del perfil actual.
     */
    public ArrayList<ActividadFisicaModelo> obtenerActividades() {
        return perfilActual.getActividadesFisicas();
    }

    /**
     * Verifica si los datos de la actividad están completos y correctos.
     */
    public int verificarDatosActividad(String actividad, String duracion, String horario, FechaModelo fecha) {
        if (actividad.isEmpty() || duracion.isEmpty() || horario.isEmpty() || fecha == null || fecha.getFecha().isEmpty()) {
            return 0;
        } else if (actividad.isEmpty()) {
            return 1;
        } else if (duracion.isEmpty()) {
            return 2;
        } else if (horario.isEmpty()) {
            return 3;
        } else if (fecha.getFecha().isEmpty()) {
            return 4;
        } else {
            return verificarActividad(actividad, duracion, horario, fecha);
        }
    }

    /**
     * Verifica si la actividad ya existe en el perfil.
     */
    private int verificarActividad(String actividad, String duracion, String horario, FechaModelo fecha) {

        for (ActividadFisicaModelo actividadExistente : perfilActual.getActividadesFisicas()) {
            if (actividadExistente.getActividad().equalsIgnoreCase(actividad) &&
                    actividadExistente.getDuracion().equalsIgnoreCase(duracion) &&
                    actividadExistente.getHorario().equalsIgnoreCase(horario) &&
                    actividadExistente.getFecha().equals(fecha)) {
                return 6; // Actividad ya registrada
            }
        }
        agregarActividad(actividad, duracion, horario, fecha);
        return 5; // Actividad agregada con éxito
    }


    /**
     * Agrega una actividad física al perfil actual y la guarda en archivo.
     */
    public void agregarActividad(String actividad, String duracion, String horario, FechaModelo fecha) {
        ActividadFisicaModelo nuevaActividad = new ActividadFisicaModelo(fecha, actividad, duracion, horario);
        perfilActual.getActividadesFisicas().add(nuevaActividad);

        try {
            listaActividadModelo.serializarArrayListActividadFisica(perfilActual);
        } catch (Exception e) {
            Log.e("ActividadFisicaCtrl", "Error al guardar actividad", e);
            throw new RuntimeException("Error al guardar actividad", e);
        }
    }
}