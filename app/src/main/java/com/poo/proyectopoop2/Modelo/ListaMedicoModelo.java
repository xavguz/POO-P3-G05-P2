package com.poo.proyectopoop2.Modelo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListaMedicoModelo implements Serializable {
    private ArrayList<MedicoModelo> listaMedicos;
    private final Context context;

    public ListaMedicoModelo(Context context) {
        this.context = context;
        this.listaMedicos = new ArrayList<>();
    }

    public ArrayList<MedicoModelo> getListaMedicos() {
        return listaMedicos;
    }

    public void agregarMedico(MedicoModelo medico) {
        if (!listaMedicos.contains(medico)) {
            listaMedicos.add(medico);
        }
    }

    public void eliminarMedico(int posicion) {
        if (posicion >= 0 && posicion < listaMedicos.size()) {
            listaMedicos.remove(posicion);
        }
    }

    public MedicoModelo obtenerMedico(int posicion) {
        if (posicion >= 0 && posicion < listaMedicos.size()) {
            return listaMedicos.get(posicion);
        }
        return null;
    }

    public void serializarArrayListMedico() throws IOException {
        File archivo = new File(context.getFilesDir(), "listamedicos.ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(listaMedicos);
        }
    }

    public void deserializarArrayListMedico() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "listamedicos.ser");
        if (!archivo.exists()) {
            listaMedicos = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            listaMedicos = (ArrayList<MedicoModelo>) in.readObject();
        }
    }

    public void guardarMedicoEnArchivo(MedicoModelo medicoNuevo) throws Exception {
        if (!listaMedicos.contains(medicoNuevo)) {
            listaMedicos.add(medicoNuevo);
            serializarArrayListMedico();
    }
}
}

