package com.poo.proyectopoop2.Controlador;

import android.content.Context;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;
import com.poo.proyectopoop2.Modelo.PerfilModelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MedicinaControlador {
    private ListaMedicinaModelo listaMedicinaModelo;
    private final ExecutorService executorService;
    private PerfilModelo perfil; // Agregado para manejar el perfil del usuario

    public MedicinaControlador(Context context, PerfilModelo perfil){
        this.listaMedicinaModelo = new ListaMedicinaModelo(context);
        this.executorService = Executors.newSingleThreadExecutor();
        this.perfil = perfil;

        executorService.submit(() -> {
            try {
                listaMedicinaModelo.deserializarArrayListMedicina(perfil); // Cargar medicinas del perfil
            } catch (IOException | ClassNotFoundException ignored) {
            }
        });
    }

    public ArrayList<MedicinaModelo> obtenerListaMedicinas() {
        return perfil.getMedicinas(); // Obtener medicinas del perfil actual
    }

    public int verificarDatosMedicina(String nombre, String cantidad, String presentacion, String hora, String dosis){
        if (nombre.isEmpty() && cantidad.isEmpty() && presentacion.isEmpty() && hora.isEmpty() && dosis.isEmpty()) {
            return 0;
        }
        if (nombre.isEmpty()) return 1;
        if (cantidad.isEmpty()) return 2;
        if (presentacion.isEmpty()) return 3;
        if (hora.isEmpty()) return 4;
        if (dosis.isEmpty()) return 5;

        return verificarMedicina(nombre, cantidad, presentacion, hora, dosis);
    }

    public int verificarMedicina(String nombre, String cantidad, String presentacion, String hora, String dosis) {
        float cantidadDisponible, dosisDisponible;

        try {
            cantidadDisponible = Float.parseFloat(cantidad);
            dosisDisponible = Float.parseFloat(dosis);
        } catch (NumberFormatException e) {
            return 8; // Error en formato numérico
        }

        for (MedicinaModelo medicina : perfil.getMedicinas()) {
            if (medicina.getNombreMedicamento().equalsIgnoreCase(nombre) &&
                    medicina.getUnidadesDisponibles() == cantidadDisponible &&
                    medicina.getPresentacion().equalsIgnoreCase(presentacion) &&
                    medicina.getHora().equalsIgnoreCase(hora) &&
                    medicina.getDosis() == dosisDisponible) {
                return 7; // Medicina ya existe
            }
        }

        // Si la medicina no existe, se crea y guarda
        crearMedicina(nombre, cantidadDisponible, presentacion, hora, dosisDisponible);
        return 6; // Medicina creada correctamente
    }

    public void crearMedicina(String nombreMedicamento, float unidadesDisponibles,
                              String presentacion, String hora, float dosis) {
        MedicinaModelo medicina = new MedicinaModelo(nombreMedicamento, unidadesDisponibles, presentacion, hora, dosis);
        perfil.agregarMedicina(medicina); // Se agrega la medicina al perfil del usuario

        try {
            listaMedicinaModelo.serializarArrayListMedicina(perfil); // Guardar en el archivo del perfil
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la medicina: " + e.getMessage(), e);
        }
    }
}

