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
import java.util.List;

public class ListaPerfilesModelo implements Serializable {
    private ArrayList<PerfilModelo> perfiles;
    private final Context context;
    public ListaPerfilesModelo(Context context) {
        this.context = context;
        this.perfiles = new ArrayList<>();
        try {
            deserializarArrayList(); // Intenta cargar perfiles automáticamente
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejo de errores al cargar el archivo
        }

    }

    public ArrayList<PerfilModelo> getPerfiles() {
        return perfiles;
    }

    public void addPerfiles(PerfilModelo perfil) {
        perfiles.add(perfil);
    }


    public void serializarArrayList() throws IOException {
        File archivo = new File(context.getFilesDir(), "listaPerfiles.ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfiles);
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializarArrayList() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "listaPerfiles.ser");
        if (!archivo.exists()) {
            perfiles = new ArrayList<>(); // Si no existe, inicializa una nueva lista
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            perfiles = (ArrayList<PerfilModelo>) in.readObject();
        }
    }

    public void guardarPerfilEnArchivo(PerfilModelo perfilNuevo) throws Exception {
        perfiles.add(perfilNuevo);
        serializarArrayList();
    }
}
