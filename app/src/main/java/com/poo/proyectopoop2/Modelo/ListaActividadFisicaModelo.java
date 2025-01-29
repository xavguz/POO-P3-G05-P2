package com.poo.proyectopoop2.Modelo;
import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;

public class ListaActividadFisicaModelo {
    private final Context context;

    public ListaActividadFisicaModelo(Context context) {
        this.context = context;
    }

    /**
     * Serializa la lista de actividades físicas de un perfil específico.
     */
    public void serializarArrayListActividadFisica(PerfilModelo perfil) throws IOException {
        File archivo = new File(context.getFilesDir(), "actividades_" + perfil.getId() + ".ser");

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfil.getActividadesFisicas());
        }
    }

    /**
     * Deserializa la lista de actividades físicas de un perfil específico.
     */
    public void deserializarArrayListActividadFisica(PerfilModelo perfil) throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "actividades_" + perfil.getId() + ".ser");

        if (!archivo.exists()) {
            perfil.getActividadesFisicas().clear();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = in.readObject();

            if (obj instanceof ArrayList<?>) {
                perfil.getActividadesFisicas().clear();
                perfil.getActividadesFisicas().addAll((ArrayList<ActividadFisicaModelo>) obj);
            } else {
                throw new ClassCastException("El archivo deserializado no es el correcto.");
            }
        }
    }

    /**
     * Agrega una actividad física al perfil y la guarda en archivo.
     */
    public void guardarActividadFisicaEnArchivo(PerfilModelo perfil, ActividadFisicaModelo actividadFisicaNueva) throws Exception {
        try {
            if (!perfil.getActividadesFisicas().contains(actividadFisicaNueva)) {
                perfil.getActividadesFisicas().add(actividadFisicaNueva);
                serializarArrayListActividadFisica(perfil);
            }
        } catch (IOException e) {
            Log.e("ListaActividadFisica", "Error al guardar la actividad: " + e.getMessage(), e);
            throw new Exception("No se pudo guardar la actividad física.");
        }
    }

    /**
     * Obtiene la lista de actividades físicas de un perfil específico.
     */
    public ArrayList<ActividadFisicaModelo> obtenerActividadesFisicas(PerfilModelo perfil) {
        return perfil.getActividadesFisicas();
    }
}
