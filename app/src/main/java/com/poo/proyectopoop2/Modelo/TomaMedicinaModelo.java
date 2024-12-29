package com.poo.proyectopoop2.Modelo;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class TomaMedicinaModelo implements Serializable {
    private MedicinaModelo medicina;
    private FechaModelo fecha;

    public TomaMedicinaModelo(MedicinaModelo medicina, FechaModelo fecha){
        this.medicina = medicina;
        this.fecha = fecha;
    }

    public TomaMedicinaModelo(){}

    public MedicinaModelo getMedicina() {
        return medicina;
    }

    public void setMedicina(MedicinaModelo medicina) {
        this.medicina = medicina;
    }

    public FechaModelo getFecha() {
        return fecha;
    }

    public void setFecha(FechaModelo fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String toString(){
        return medicina.getNombreMedicamento() + " / " + fecha;
    }

}
