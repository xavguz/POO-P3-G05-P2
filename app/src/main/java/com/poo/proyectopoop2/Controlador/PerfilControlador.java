package com.poo.proyectopoop2.Controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

import com.poo.proyectopoop2.Modelo.ListaPerfilesModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerfilControlador {

    private ListaPerfilesModelo listaPerfilesModelo;
    private ExecutorService executorService;

    public ArrayList<PerfilModelo> obtenerPerfiles() {
        return listaPerfilesModelo.getPerfiles();
    }

    public PerfilControlador(){
        this.listaPerfilesModelo = new ListaPerfilesModelo();
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                listaPerfilesModelo.deserializarArrayList();
            } catch (IOException | ClassNotFoundException ignored) {
            }
        });
    }

    public int verificarDatos(String nombre, String relacion, String email){

        if (nombre.isEmpty() && relacion.isEmpty()) {
            return 0;
        }
        else if (nombre.isEmpty()) {
            return 1;
        }
        else if (relacion.isEmpty()) {
            return 2;
        }
        else {
            return verificarPerfil(nombre,relacion,email);
        }
    }

    public int verificarPerfil(String nombre, String relacion, String email){
        for (PerfilModelo perfilVerificar : listaPerfilesModelo.getPerfiles()) {
            if (perfilVerificar.getNombre().equalsIgnoreCase(nombre) &&
                    perfilVerificar.getRelacion().equalsIgnoreCase(relacion)) {
                return 4;
            }
        }
        crearPerfil(nombre, relacion, email);
        return 3;
    }

    public void crearPerfil(String nombre, String relacion, String email){
        PerfilModelo perfil = email.isEmpty()
                ? new PerfilModelo(nombre, relacion)
                : new PerfilModelo(nombre, relacion, email);

        listaPerfilesModelo.addPerfiles(perfil);

        try {
            listaPerfilesModelo.serializarArrayList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}