package com.poo.proyectopoop2.Modelo;

import java.io.Serializable;

public class FechaModelo implements Serializable {
    private String dia;
    private String mes;
    private String año;
    private String hora;

    public FechaModelo(String dia, String mes, String año, String hora){
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.hora = hora;
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
}

