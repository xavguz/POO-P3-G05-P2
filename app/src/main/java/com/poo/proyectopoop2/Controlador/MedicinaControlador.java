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

    public ArrayList<MedicinaModelo> obtenerListaMedicinas() {
        return listaMedicinaModelo.getListaMedicinas();
    }

    public MedicinaControlador(Context context){
        this.listaMedicinaModelo = new ListaMedicinaModelo(context);
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                listaMedicinaModelo.deserializarArrayListMedicina();
            } catch (IOException | ClassNotFoundException ignored) {
            }
        });
    }

    public int verificarDatosMedicina( String nombre, String cantidad, String presentacion, String hora, String dosis){

        if (nombre.isEmpty() &&  cantidad.isEmpty() && presentacion.isEmpty() && hora.isEmpty() && dosis.isEmpty()) {
            return 0;
        }
        else if (nombre.isEmpty()) {
            return 1;
        }
        else if (cantidad.isEmpty()) {
            return 2;
        }
        else if(presentacion.isEmpty()) {
            return 3;
        }
        else if(hora.isEmpty()) {
            return 4;
        }
        else if(dosis.isEmpty()) {
            return 5;
        }
        else {
            return verificarMedicina(nombre,cantidad,presentacion,hora,dosis);
        }
    }

    public int verificarMedicina(String nombre, String cantidad, String presentacion, String hora, String dosis) {
        float cantidadDisponible = 0;
        float dosisDisponible = 0;

        try {
            cantidadDisponible = Float.parseFloat(cantidad);
            dosisDisponible = Float.parseFloat(dosis);
        } catch (NumberFormatException e) {
            // Si no es un número, retornar un código de error (por ejemplo 8 para error en cantidad o dosis)
            return 8;
        }

        for (MedicinaModelo medicinaModelo : listaMedicinaModelo.getListaMedicinas()) {
            if (medicinaModelo.getNombreMedicamento().equalsIgnoreCase(nombre) &&
                    medicinaModelo.getUnidadesDisponibles() == cantidadDisponible &&
                    medicinaModelo.getPresentacion().equalsIgnoreCase(presentacion) &&
                    medicinaModelo.getHora().equalsIgnoreCase(hora) &&
                    medicinaModelo.getDosis() == dosisDisponible) {
                return 7; // Medicina ya existe
            }
        }

        // Si la validación pasa, crea la medicina
        crearMedicina(nombre, cantidadDisponible, presentacion, hora, dosisDisponible);
        return 6; // Medicina creada correctamente
    }
    public void crearMedicina(String nombreMedicamento, float unidadesDisponibles,
                              String presentacion, String hora, float dosis){
        MedicinaModelo medicina = new MedicinaModelo(nombreMedicamento,unidadesDisponibles,presentacion,hora,dosis);

        listaMedicinaModelo.agregarMedicina(medicina);

        try {
            listaMedicinaModelo.serializarArrayListMedicina();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
