package com.poo.proyectopoop2.Controlador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerfilControlador implements Serializable {

    private ListaPerfilesModelo listaPerfilesModelo;
    private ExecutorService executorService;
    public PerfilControlador(Context context) {
        this.listaPerfilesModelo = new ListaPerfilesModelo(context);
        this.executorService = Executors.newSingleThreadExecutor();

        // Deserialización en un hilo separado
        executorService.submit(() -> {
            try {
                listaPerfilesModelo.deserializarArrayList();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Registra el error para ser revisado
            }
        });
    }

    public ArrayList<PerfilModelo> obtenerPerfiles() {
        return listaPerfilesModelo.getPerfiles();
    }

    public int verificarDatos(String nombre, String relacion, String email) {
        if (nombre.isEmpty() && relacion.isEmpty()) {
            return 0;
        } else if (nombre.isEmpty()) {
            return 1;
        } else if (relacion.isEmpty()) {
            return 2;
        } else {
            return verificarPerfil(nombre, relacion, email);
        }
    }

    public int verificarPerfil(String nombre, String relacion, String email) {
        // Revisa si el perfil ya existe
        for (PerfilModelo perfilVerificar : listaPerfilesModelo.getPerfiles()) {
            if (perfilVerificar.getNombre().equalsIgnoreCase(nombre) &&
                    perfilVerificar.getRelacion().equalsIgnoreCase(relacion)) {
                return 4;
            }
        }

        // Si no existe, creamos el perfil
        crearPerfil(nombre, relacion, email);
        return 3;
    }

    public void crearPerfil(String nombre, String relacion, String email) {
        // Crea un nuevo perfil, dependiendo de si el email es vacío o no
        PerfilModelo perfil = email.isEmpty()
                ? new PerfilModelo(nombre, relacion)
                : new PerfilModelo(nombre, relacion, email);

        // Agrega el perfil a la lista si no existe
        listaPerfilesModelo.anadirPerfiles(perfil);

        try {
            // Serializa la lista de perfiles después de agregar el nuevo
            listaPerfilesModelo.serializarArrayList();
        } catch (Exception e) {
            e.printStackTrace(); // Registra cualquier error al serializar
            throw new RuntimeException(e);
        }
    }

}


