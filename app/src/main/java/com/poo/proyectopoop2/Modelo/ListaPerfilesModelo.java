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
        } else {
            Log.d("ListaPerfilesModelo", "Perfil ya existe, no se agrega: " + perfil.getNombre());
        }
    }

    public void serializarArrayList() throws IOException {
        // Eliminar duplicados antes de guardar
        Set<PerfilModelo> uniqueSet = new HashSet<>(perfiles);
        perfiles = new ArrayList<>(uniqueSet);

        // Verifica si el archivo existe
        File archivo = new File(context.getFilesDir(), "listaPerfiles_1.ser");
        if (!archivo.exists()) {
            archivo.createNewFile();  // Si no existe, lo crea
            Log.d("ListaPerfilesModelo", "Archivo de perfiles creado.");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfiles);
            Log.d("ListaPerfilesModelo", "Perfiles serializados correctamente.");
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializarArrayList() throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "listaPerfiles_1.ser");
        if (!archivo.exists()) {
            perfiles = new ArrayList<>(); // Si no existe, inicializa una nueva lista
            Log.d("ListaPerfilesModelo", "No se encontró el archivo, se inicializa lista vacía.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            perfiles = (ArrayList<PerfilModelo>) in.readObject();
            Log.d("ListaPerfilesModelo", "Perfiles deserializados correctamente.");
        }
    }

    public void guardarPerfilEnArchivo(PerfilModelo perfilNuevo) throws Exception {
        deserializarArrayList(); // Cargar lista antes de modificarla

        // Verificar si el perfil ya existe antes de agregarlo
        if (!perfiles.contains(perfilNuevo)) {
            perfiles.add(perfilNuevo);
            serializarArrayList(); // Guardar lista actualizada
            Log.d("GuardarPerfil", "Perfil guardado: " + perfilNuevo.getNombre());
        } else {
            Log.d("GuardarPerfil", "Perfil duplicado, no se guardó: " + perfilNuevo.getNombre());
            throw new Exception("Perfil ya existe"); // Lanza una excepción si el perfil ya existe
        }
    }
}
