package com.poo.proyectopoop2.Modelo;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import java.io.*;
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

    public void agregarMedicina(MedicinaModelo medicina) {
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

    private File obtenerArchivoMedicina(PerfilModelo perfil) {
        String idPerfil = String.valueOf(perfil.getId());
        return new File(context.getFilesDir(), "medicinas_" + idPerfil + ".ser");
    }

    public void serializarArrayListMedicina(PerfilModelo perfil) throws IOException {
        File archivo = obtenerArchivoMedicina(perfil);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfil.getMedicinas());
        }
    }

    public void deserializarArrayListMedicina(PerfilModelo perfil) throws IOException, ClassNotFoundException {
        File archivo = obtenerArchivoMedicina(perfil);
        if (!archivo.exists()) {
            perfil.getMedicinas().clear();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            ArrayList<MedicinaModelo> medicinas = (ArrayList<MedicinaModelo>) in.readObject();
            perfil.getMedicinas().clear();
            perfil.getMedicinas().addAll(medicinas);
            this.listaMedicinas = new ArrayList<>(perfil.getMedicinas());
        }
    }

    public ArrayList<MedicinaModelo> cargarMedicinasDesdeArchivo(PerfilModelo perfil) {
        try {
            deserializarArrayListMedicina(perfil);
            listaMedicinas = new ArrayList<>(perfil.getMedicinas());
        } catch (IOException | ClassNotFoundException e) {
            Log.e("ListaMedicinaModelo", "Error al cargar medicinas: " + e.getMessage(), e);
            listaMedicinas = new ArrayList<>();
        }
        return listaMedicinas;
    }

    public void guardarMedicinasEnArchivo(PerfilModelo perfil, MedicinaModelo medicinaNueva) {
        try {
            if (!perfil.getMedicinas().contains(medicinaNueva)) {
                perfil.agregarMedicina(medicinaNueva);
                serializarArrayListMedicina(perfil);
            }
        } catch (IOException e) {
            Log.e("ListaMedicinaModelo", "Error al guardar la medicina para el perfil: " + e.getMessage(), e);
        }
    }
}
