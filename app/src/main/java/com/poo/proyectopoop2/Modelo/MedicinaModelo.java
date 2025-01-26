package com.poo.proyectopoop2.Modelo;

import java.io.Serializable;

public class MedicinaModelo implements Serializable {

    private String nombreMedicamento;
    private float unidadesDisponibles;
    private String presentacion;
    private String hora;
    private float dosis;

    public MedicinaModelo(String nombreMedicamento, float unidadesDisponibles,
                    String presentacion, String hora, float dosis){
        this.nombreMedicamento = nombreMedicamento;
        this.unidadesDisponibles = unidadesDisponibles;
        this.presentacion = presentacion;
        this.hora = hora;
        this.dosis = dosis;
    }

    public MedicinaModelo(){}
    
    public void mostrarMedicina(){
        System.out.println("Nombre del medicamento: " + nombreMedicamento);
        System.out.println("Unidades disponibles: " + unidadesDisponibles);
        System.out.println("Presentacion: " + presentacion);
        System.out.println("Hora: " + hora);
        System.out.println("Dosis: " + dosis);
    }

    public String getNombreMedicamento(){
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento){
        this.nombreMedicamento = nombreMedicamento;
    }

    public float getUnidadesDisponibles(){
        return unidadesDisponibles;
    }

    public void getUnidadesDisponibles(float unidadesDisponibles){
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getPresentacion(){
        return presentacion;
    }

    public void setPresentacion(String presentacion){
        this.presentacion = presentacion;
    }

    public String getHora(){
        return hora;
    }

    public void setHora(String hora){
        this.hora = hora;
    }

    public float getDosis(){
        return dosis;
    }

    public void setDosis(float dosis){
        this.dosis = dosis;
    }

    @Override
    public String toString(){
        return nombreMedicamento +" / "+ unidadesDisponibles +" / "+ presentacion +" / "+ hora +" / "+ dosis;
    }
}

