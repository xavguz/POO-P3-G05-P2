package com.poo.proyectopoop2.Controlador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.poo.proyectopoop2.Modelo.ActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.FechaModelo;
import com.poo.proyectopoop2.Modelo.ListaActividadFisicaModelo;
import com.poo.proyectopoop2.Modelo.ListaFisicaModelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActividadFisicaControlador {
    private ListaActividadFisicaModelo listaActividadModelo;
    private ExecutorService executorService;

    public ActividadFisicaControlador(Context context) {
        this.listaActividadModelo = new ListaActividadFisicaModelo(context);
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                listaActividadModelo.deserializarArrayListActividadFisica();
            } catch (IOException | ClassNotFoundException e) {
                Log.e("ActividadFisicaCtrl", "Error al deserializar actividades", e);
            }
        });
    }

    public ArrayList<ActividadFisicaModelo> obtenerActividades() {
        return listaActividadModelo.getListaActividadesFisicas();
    }

    public int verificarDatosActividad(String actividad, String duracion, String horario, FechaModelo fecha) {
        if (actividad.isEmpty() || duracion.isEmpty() || horario.isEmpty() || fecha == null) {
            return 0; // Datos incompletos
        }

        return verificarActividad(actividad, duracion, horario, fecha);
    }

    private int verificarActividad(String actividad, String duracion, String horario, FechaModelo fecha) {
        for (ActividadFisicaModelo actividadExistente : listaActividadModelo.getListaActividadesFisicas()) {
            if (actividadExistente.getActividad().equalsIgnoreCase(actividad) &&
                    actividadExistente.getDuracion().equalsIgnoreCase(duracion) &&
                    actividadExistente.getHorario().equalsIgnoreCase(horario) &&
                    actividadExistente.getFecha().equals(fecha)) {
                return 7; // Actividad ya registrada
            }
        }
        agregarActividad(actividad, duracion, horario, fecha);
        return 6; // Actividad agregada con éxito
    }

    public void agregarActividad(String actividad, String duracion, String horario, FechaModelo fecha) {
        ActividadFisicaModelo nuevaActividad = new ActividadFisicaModelo(fecha, actividad, duracion, horario);
        listaActividadModelo.agregarActividadFisica(nuevaActividad);

        try {
            listaActividadModelo.serializarArrayListActividadFisica();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar actividad", e);
        }
    }
}