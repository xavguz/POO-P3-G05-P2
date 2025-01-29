package com.poo.proyectopoop2.Modelo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
public class PerfilModelo implements Serializable {
    private static final long serialVersionUID = 758425513884995478L; // Mantener el mismo UID

    private int id;
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

    public PerfilModelo() {
        this.medicinas = new ArrayList<>();
        this.tomaMedicinas = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citaMedicas = new ArrayList<>();
        this.actividadesFisicas = new ArrayList<>();
    }


    public PerfilModelo(String nombreUsuario, String relacion) {
        this(nombreUsuario, relacion, null);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreUsuario) {
        this.nombre = nombreUsuario;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public void agregarMedicina(MedicinaModelo medicina) {
        if (!medicinas.contains(medicina)) {
            this.medicinas.add(medicina);
        }
    }

    public void agregarMedico(MedicoModelo medico) {
        if (!medicos.contains(medico)) {
            this.medicos.add(medico);
        }
    }

    public void agregarCitaMedica(CitaMedicaModelo cita) {
        if (!citaMedicas.contains(cita)) {
            this.citaMedicas.add(cita);
        }
    }

    public void agregarActividadFisica(ActividadFisicaModelo actividad) {
        if (!actividadesFisicas.contains(actividad)) {
            this.actividadesFisicas.add(actividad);
        }
    }
    public void setCitaMedicas(ArrayList<CitaMedicaModelo> citas) {
        this.citaMedicas = citas;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre + " (" + relacion + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilModelo that = (PerfilModelo) o;
        return id == that.id || (nombre.equalsIgnoreCase(that.nombre) && relacion.equalsIgnoreCase(that.relacion));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre.toLowerCase(), relacion.toLowerCase());
    }
}

