package com.poo.proyectopoop2.Modelo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class MedicoModelo implements Serializable {
    private String nombre;
    private String especialidades;
    private String telefono;
    private String email;
    private String direccion;

    public MedicoModelo(String nombre, String especialidades,
                  String telefono, String email, String direccion){
        this.nombre = nombre;
        this.especialidades = especialidades;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }
    
    public MedicoModelo(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @NonNull
    @Override
    public String toString(){
        return "Nombre: " + nombre + //
                    ", Especialidad: "+ especialidades + //
                    ", Numero de Contacto: "+ telefono;
    }

    @Override
    public boolean equals(Object medico) {
        if (medico instanceof MedicoModelo) {
            MedicoModelo medico1 = (MedicoModelo) medico;
            return Objects.equals(nombre.toLowerCase(), medico1.nombre.toLowerCase()) &&
                    Objects.equals(especialidades.toLowerCase(), medico1.especialidades.toLowerCase()) &&
                    Objects.equals(telefono, medico1.telefono) &&
                    Objects.equals(email, medico1.email) &&
                    Objects.equals(direccion, medico1.direccion);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase(), especialidades.toLowerCase(), telefono.toLowerCase(), email.toLowerCase(), direccion.toLowerCase());
    }
}

