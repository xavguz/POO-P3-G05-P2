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

public class ListaPerfilesModelo implements Serializable {
    private ArrayList<PerfilModelo> perfiles;
    private final Context context;
    public ListaPerfilesModelo(Context context) {
        this.context = context;
        this.perfiles = new ArrayList<>();

    }

    public ArrayList<PerfilModelo> getPerfiles() {
        return perfiles;
    }

    public void anadirPerfiles(PerfilModelo perfil) {
        // Verificar si el perfil ya existe antes de agregarlo
        if (!perfiles.contains(perfil)) {
            perfiles.add(perfil);
        }
    }


    public void serializarArrayList() throws IOException {
        // Eliminar duplicados antes de guardar
        Set<PerfilModelo> uniqueSet = new HashSet<>(perfiles);
        perfiles = new ArrayList<>(uniqueSet);

        File archivo = new File(context.getFilesDir(), "listaPerfiles_.ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfiles);

        }
    }

    @SuppressWarnings("unchecked")
    public void deserializarArrayList() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "listaPerfiles_.ser");
        if (!archivo.exists()) {
            perfiles = new ArrayList<>(); // Si no existe, inicializa una nueva lista
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            perfiles = (ArrayList<PerfilModelo>) in.readObject();
        }

        // Eliminar duplicados, si es necesario
        Set<PerfilModelo> uniqueSet = new HashSet<>(perfiles);
        perfiles = new ArrayList<>(uniqueSet);
    }

    public void guardarPerfilEnArchivo(PerfilModelo perfilNuevo) throws Exception {
        // Verificar si el perfil ya existe antes de agregarlo
        if (!perfiles.contains(perfilNuevo)) {
            perfiles.add(perfilNuevo);
            serializarArrayList(); // Guardar la lista de perfiles al archivo

        }
    }
}
