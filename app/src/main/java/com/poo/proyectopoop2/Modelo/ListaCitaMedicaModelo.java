package com.poo.proyectopoop2.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaCitaMedicaModelo implements Serializable {
    private List<CitaMedicaModelo> listaCitas;

    public ListaCitaMedicaModelo() {
        listaCitas = new ArrayList<>();
    }

    public List<CitaMedicaModelo> getListaCitas() {
        return listaCitas;
    }

    public void agregarCita(CitaMedicaModelo cita) {
        listaCitas.add(cita);
    }

    public void eliminarCita(int posicion) {
        if (posicion >= 0 && posicion < listaCitas.size()) {
            listaCitas.remove(posicion);
        }
    }

    public CitaMedicaModelo obtenerCita(int posicion) {
        if (posicion >= 0 && posicion < listaCitas.size()) {
            return listaCitas.get(posicion);
        }
        return null;
    }
}

