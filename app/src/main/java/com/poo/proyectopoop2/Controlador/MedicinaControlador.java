package com.poo.proyectopoop2.Controlador;

import android.content.Context;

import com.poo.proyectopoop2.Modelo.ListaMedicinaModelo;
import com.poo.proyectopoop2.Modelo.MedicinaModelo;

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
                listaMedicinaModelo.deserializarArrayListMedico();
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

    public int verificarMedicina(String nombre, String cantidad, String presentacion, String hora, String dosis){
        float cantidadDisponible = Float.parseFloat(cantidad);
        float dosisDisponible = Float.parseFloat(dosis);
        for (MedicinaModelo medicinaModelo : listaMedicinaModelo.getListaMedicinas()) {
            if (medicinaModelo.getNombreMedicamento().equalsIgnoreCase(nombre) &&
                    medicinaModelo.getUnidadesDisponibles() == cantidadDisponible &&
                    medicinaModelo.getPresentacion().equalsIgnoreCase(presentacion)&&
                    medicinaModelo.getHora().equalsIgnoreCase(hora) &&
                    medicinaModelo.getDosis() == dosisDisponible) {
                return 7;
            }
        }
        crearMedicina(nombre, cantidadDisponible,presentacion, hora,dosisDisponible);
        return 6;
    }

    public void crearMedicina(String nombre, float cantidad, String presentacion, String hora, float dosis){
        MedicinaModelo medicina = new MedicinaModelo(nombre,cantidad,presentacion,hora,dosis);

        listaMedicinaModelo.agregarMedicina(medicina);

        try {
            listaMedicinaModelo.serializarArrayListMedicina();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
