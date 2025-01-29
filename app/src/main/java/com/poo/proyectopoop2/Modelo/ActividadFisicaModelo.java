package com.poo.proyectopoop2.Modelo;


import java.io.Serializable;

public class ActividadFisicaModelo implements Serializable {
    private FechaModelo fecha;
    private String actividad;
    private String duracion;
    private String horario;

    public ActividadFisicaModelo(FechaModelo fecha, String actividad, String duracion, String horario) {
        this.fecha = (fecha != null) ? fecha : new FechaModelo("00/00/0000");
        this.actividad = (actividad != null) ? actividad : "No definida";
        this.duracion = (duracion != null) ? duracion : "0";
        this.horario = (horario != null) ? horario : "No definido";
    }

    public FechaModelo getFecha() {
        return fecha;
    }

    public String getActividad() {
        return actividad;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        return fecha.getFecha() + " - " + horario + " / " + actividad + " / " + duracion;
    }
}
