package com.poo.proyectopoop2.Modelo;

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

    public ListaPerfilesModelo() {
        this.perfiles = new ArrayList<>();
    }

    public ArrayList<PerfilModelo> getPerfiles() {
        return perfiles;
    }

    public void addPerfiles(PerfilModelo perfil) {
        perfiles.add(perfil);
    }


    public void serializarArrayList() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("listaPerfiles.ser"))) {
            out.writeObject(perfiles);
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializarArrayList() throws IOException, ClassNotFoundException {
        File archivo = new File("listaPerfiles.ser");
        if (!archivo.exists()) {
            perfiles = new ArrayList<>(); // Si no existe, inicializa una nueva lista
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            perfiles = (ArrayList<PerfilModelo>) in.readObject();
        }
    }

    public static ArrayList<PerfilModelo> cargarPerfilesDesdeArchivo() throws IOException, ClassNotFoundException {
        File archivo = new File("listaPerfiles.ser");

        if (!archivo.exists()) {
            return new ArrayList<>(); // Devuelve una lista vacía si el archivo no existe
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (ArrayList<PerfilModelo>) in.readObject();
        }
    }

    public static void guardarPerfilEnArchivo(PerfilModelo perfilNuevo) throws Exception {
        if (perfilNuevo == null) {
            throw new IllegalArgumentException("El perfil no puede ser nulo.");
        }

        ArrayList<PerfilModelo> lista = cargarPerfilesDesdeArchivo();

        if (lista.contains(perfilNuevo)) {
            throw new Exception("El perfil ya existe en el archivo.");
        }

        lista.add(perfilNuevo);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("listaPerfiles.ser"))) {
            out.writeObject(lista);
        }
    }
}