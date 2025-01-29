package com.poo.proyectopoop2.Modelo;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaCitaMedicaModelo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CitaMedicaModelo> listaCitas;
    private final Context context;

    public ListaCitaMedicaModelo(Context context) {
        this.listaCitas = new ArrayList<>();
        this.context = context;
    }

    public List<CitaMedicaModelo> getListaCitas() {
        return listaCitas;
    }

    public void agregarCita(CitaMedicaModelo cita) {
        if (!listaCitas.contains(cita)) {
            listaCitas.add(cita);
        }
    }

    public void eliminarCita(int posicion) {
        if (posicion >= 0 && posicion < listaCitas.size()) {
            listaCitas.remove(posicion);
        }
    }

    public CitaMedicaModelo obtenerCita(int posicion) {
        if (posicion >= 0 && posicion < listaCitas.size()) {
            return listaCitas.get(posicion);
        }
        return null;
    }

    /**
     * Serializa y guarda la lista de citas de un perfil en un archivo.
     */
    public void serializarArrayListCitas(PerfilModelo perfil) throws IOException {
        File archivo = new File(context.getFilesDir(), "citas_" + perfil.getId() + ".ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfil.getCitaMedicas());
        }
    }

    /**
     * Deserializa la lista de citas de un perfil desde un archivo.
     */
    public void deserializarArrayListCitas(PerfilModelo perfil) throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "citas_" + perfil.getId() + ".ser");
        if (!archivo.exists()) {
            perfil.getCitaMedicas().clear();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                perfil.getCitaMedicas().clear();
                perfil.getCitaMedicas().addAll((List<CitaMedicaModelo>) obj);
            } else {
                throw new ClassCastException("Error al leer el archivo de citas.");
            }
        }
    }

    /**
     * Guarda una nueva cita en el archivo correspondiente al perfil.
     */
    public void guardarCitaMedicaEnArchivo(PerfilModelo perfil, CitaMedicaModelo citaNueva) throws Exception {
        try {
            if (!perfil.getCitaMedicas().contains(citaNueva)) {
                perfil.getCitaMedicas().add(citaNueva);
                serializarArrayListCitas(perfil);
            } else {
                throw new Exception("Cita médica ya registrada.");
            }
        } catch (IOException e) {
            Log.e("ListaCitaMedicaModelo", "Error al guardar la cita: " + e.getMessage(), e);
            throw new Exception("No se pudo guardar la cita médica.");
        }
    }
}


