package com.poo.proyectopoop2.Modelo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class PerfilModelo implements Serializable {

    private int id; //identificador unico
    private String nombre;
    private String relacion;
    private String email;
    private ArrayList<MedicinaModelo> medicinas;
    private ArrayList<TomaMedicinaModelo> tomaMedicinas;
    private ArrayList<MedicoModelo> medicos;
    private ArrayList<CitaMedicaModelo> citaMedicas;
    private ArrayList<ActividadFisicaModelo> actividadesFisicas;
    public static int ultimoId = 0;
    
    public PerfilModelo(String nombre, String relacion, String email){
        id= ultimoId +1;
        this.nombre = nombre;
        this.relacion = relacion;
        this.email = email;
        this.medicinas = new ArrayList<>();
        this.tomaMedicinas = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citaMedicas = new ArrayList<>();
        this.actividadesFisicas = new ArrayList<>();
        ultimoId= id;
    }
    public PerfilModelo(){
        this.medicinas = new ArrayList<>();
        this.tomaMedicinas = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citaMedicas = new ArrayList<>();
        this.actividadesFisicas = new ArrayList<>();
    }

    public PerfilModelo(String nombreUsuario, String relacion){
        this(nombreUsuario, relacion,null);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreUsuario){
        this.nombre = nombreUsuario;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion){
        this.relacion = relacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public ArrayList<MedicinaModelo> getMedicinas() {
        return medicinas;
    }

    public ArrayList<MedicoModelo> getMedicos() {
        return medicos;
    }

    public ArrayList<CitaMedicaModelo> getCitaMedicas() {
        return citaMedicas;
    }

    public ArrayList<ActividadFisicaModelo> getActividadesFisicas() {
        return actividadesFisicas;
    }

    public ArrayList<TomaMedicinaModelo> getTomaMedicinas() {
        return tomaMedicinas;
    }

    
    @NonNull
    @Override
    public String toString(){
        return nombre + " (" + relacion + ")";
    }

    @Override
    public boolean equals(Object p) {
        if (p instanceof PerfilModelo) {
            PerfilModelo perfil = (PerfilModelo) p;
            return Objects.equals(nombre.toLowerCase(), perfil.nombre.toLowerCase()) &&
                    Objects.equals(relacion.toLowerCase(), perfil.relacion.toLowerCase());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase(), relacion.toLowerCase());
    }




}
