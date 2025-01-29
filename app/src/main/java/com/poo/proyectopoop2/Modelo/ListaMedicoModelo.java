package com.poo.proyectopoop2.Modelo;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListaMedicoModelo implements Serializable {
    private ArrayList<MedicoModelo> listaMedicos;
    private final Context context;

    public ListaMedicoModelo(Context context) {
        this.context = context;
        this.listaMedicos = new ArrayList<>();
    }

    public ArrayList<MedicoModelo> getListaMedicos() {
        return listaMedicos;
    }

    public void agregarMedico(MedicoModelo medico) {
        if (!listaMedicos.contains(medico)) {
            listaMedicos.add(medico);
        }
    }

    public void eliminarMedico(int posicion) {
        if (posicion >= 0 && posicion < listaMedicos.size()) {
            listaMedicos.remove(posicion);
        }
    }

    public MedicoModelo obtenerMedico(int posicion) {
        if (posicion >= 0 && posicion < listaMedicos.size()) {
            return listaMedicos.get(posicion);
        }
        return null;
    }

    /**
     * Serializa la lista de médicos específica de un perfil.
     */
    public void serializarArrayListMedico(PerfilModelo perfil) throws IOException {
        File archivo = new File(context.getFilesDir(), "medicos_" + perfil.getId() + ".ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(perfil.getMedicos()); // Guardar solo los médicos del perfil
        }
    }

    /**
     * Deserializa la lista de médicos específica de un perfil.
     */
    @SuppressWarnings("unchecked")
    public void deserializarArrayListMedico(PerfilModelo perfil) throws IOException, ClassNotFoundException {
        File archivo = new File(context.getFilesDir(), "medicos_" + perfil.getId() + ".ser");

        if (!archivo.exists()) {
            perfil.getMedicos().clear(); // Si no hay archivo, aseguramos una lista vacía
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            ArrayList<MedicoModelo> medicos = (ArrayList<MedicoModelo>) in.readObject();
            perfil.getMedicos().clear();
            perfil.getMedicos().addAll(medicos); // Cargar los médicos en el perfil correspondiente
        }

        // **Eliminar duplicados en la lista del perfil**
        Set<MedicoModelo> uniqueSet = new HashSet<>(perfil.getMedicos());
        perfil.getMedicos().clear();
        perfil.getMedicos().addAll(uniqueSet);
    }

    /**
     * Guarda un médico en la lista de un perfil específico.
     */
    public void guardarMedicoEnArchivo(PerfilModelo perfil, MedicoModelo medicoNuevo) throws Exception {
        deserializarArrayListMedico(perfil); // Cargar lista antes de modificarla

        if (!perfil.getMedicos().contains(medicoNuevo)) {
            perfil.getMedicos().add(medicoNuevo);
            serializarArrayListMedico(perfil); // Guardar lista actualizada
            Log.d("GuardarMedico", "Médico guardado: " + medicoNuevo.getNombre());
        } else {
            Log.d("GuardarMedico", "Médico duplicado, no se guardó: " + medicoNuevo.getNombre());
        }
    }
}

