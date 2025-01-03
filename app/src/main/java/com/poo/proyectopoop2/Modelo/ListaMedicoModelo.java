package com.poo.proyectopoop2.Modelo;
import java.util.ArrayList;
import java.util.List;
public class ListaMedicoModelo{
    private List<MedicoModelo> listaMedicos;
    public ListaMedicoModelo() {
        listaMedicos = new ArrayList<>();
    }

    public List<MedicoModelo> getListaMedicos() {
        return listaMedicos;
    }

    public void agregarMedico(MedicoModelo medico) {
        listaMedicos.add(medico);
    }
}

