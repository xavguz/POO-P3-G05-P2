package com.poo.proyectopoop2.Modelo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListaMedicinaModelo {
    private ArrayList<MedicinaModelo> listaMedicinas;
    private final Context context;

    public ListaMedicinaModelo(Context context) {
        this.listaMedicinas = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<MedicinaModelo> getListaMedicinas() {
        return listaMedicinas;
    }

    public void agregarMedicina( MedicinaModelo medicina) {
        if (!listaMedicinas.contains(medicina)) {
            listaMedicinas.add(medicina);
        }
    }

    public void eliminarMedicina(int posicion) {
        if (posicion >= 0 && posicion < listaMedicinas.size()) {
            listaMedicinas.remove(posicion);
        }
    }

    public MedicinaModelo obtenerMedicina(int posicion) {
        if (posicion >= 0 && posicion < listaMedicinas.size()) {
            return listaMedicinas.get(posicion);
        }
        return null;
    }

    public void serializarArrayListMedicina() throws IOException {
        File archivo = new File(context.getFilesDir(), "listaMedicinas.ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(listaMedicinas);
        }
    }

    public void deserializarArrayListMedico() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "listaMedicinas.ser");
        if (!archivo.exists()) {
            listaMedicinas = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = in.readObject();

            if (obj instanceof ArrayList<?>) {
                listaMedicinas = (ArrayList<MedicinaModelo>) obj;
            } else {
                throw new ClassCastException("El archivo deserializado no es el correcto.");
            }
        }
    }

    public void guardarMedicinasEnArchivo(MedicinaModelo medinaNueva) throws Exception {
        if (!listaMedicinas.contains(medinaNueva)) {
            listaMedicinas.add(medinaNueva);
            serializarArrayListMedicina();
        }
    }
}
