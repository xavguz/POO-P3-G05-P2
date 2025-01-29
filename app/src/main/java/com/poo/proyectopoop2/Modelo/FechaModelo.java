package com.poo.proyectopoop2.Modelo;

import java.io.Serializable;

public class FechaModelo implements Serializable {
    private String dia;
    private String mes;
    private String año;
    private String hora;
    private String fecha;

    public FechaModelo(String dia, String mes, String año, String hora){
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.hora = hora;
    }

    public FechaModelo(String fecha, String hora){
        this.fecha = fecha;
        this.hora = hora;

    }

    public  FechaModelo (String fecha){
        this.fecha=fecha;
    }

    public String getDia() {
        return dia;
    }
    public void setDia(String dia){
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMes() {
        return mes;
    }

    public String getAño() {
        return año;
    }

    @Override
    public String toString(){
        return dia + "/"+ mes + "/" + año + " - " + hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}

