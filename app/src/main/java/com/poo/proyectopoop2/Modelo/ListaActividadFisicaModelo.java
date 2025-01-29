package com.poo.proyectopoop2.Modelo;
import android.content.Context;
import java.io.*;
import java.util.ArrayList;

public class ListaActividadFisicaModelo {
    private ArrayList<ActividadFisicaModelo> listaActividadesFisicas;
    private final Context context;

    public ListaActividadFisicaModelo(Context context) {
        this.listaActividadesFisicas = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<ActividadFisicaModelo> getListaActividadesFisicas() {
        return listaActividadesFisicas;
    }

    public void agregarActividadFisica(ActividadFisicaModelo actividadFisica) {
        if (!listaActividadesFisicas.contains(actividadFisica)) {
            listaActividadesFisicas.add(actividadFisica);
        }
    }

    public void eliminarActividadFisica(int posicion) {
        if (posicion >= 0 && posicion < listaActividadesFisicas.size()) {
            listaActividadesFisicas.remove(posicion);
        }
    }

    public ActividadFisicaModelo obtenerActividadFisica(int posicion) {
        if (posicion >= 0 && posicion < listaActividadesFisicas.size()) {
            return listaActividadesFisicas.get(posicion);
        }
        return null;
    }

    public void serializarArrayListActividadFisica() throws IOException {
        File archivo = new File(context.getFilesDir(), "listaActividadesFisicas.ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(listaActividadesFisicas);
        }
    }

    public void deserializarArrayListActividadFisica() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "listaActividadesFisicas.ser");
        if (!archivo.exists()) {
            listaActividadesFisicas = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = in.readObject();

            if (obj instanceof ArrayList<?>) {
                listaActividadesFisicas = (ArrayList<ActividadFisicaModelo>) obj;
            } else {
                throw new ClassCastException("El archivo deserializado no es el correcto.");
            }
        }
    }

    public ArrayList<ActividadFisicaModelo> cargarActividadesFisicasDesdeArchivo() {
        try {
            deserializarArrayListActividadFisica();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            listaActividadesFisicas = new ArrayList<>();  // En caso de error, devolver una lista vacía
        }
        return listaActividadesFisicas;
    }

    public void guardarActividadFisicaEnArchivo(ActividadFisicaModelo actividadFisicaNueva) throws Exception {
        try {
            if (!listaActividadesFisicas.contains(actividadFisicaNueva)) {
                listaActividadesFisicas.add(actividadFisicaNueva);
                serializarArrayListActividadFisica();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("No se pudo guardar la actividad física.");
        }
    }
}
