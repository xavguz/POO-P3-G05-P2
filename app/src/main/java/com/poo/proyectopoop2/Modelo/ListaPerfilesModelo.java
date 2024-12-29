package com.poo.proyectopoop2.Modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ListaPerfilesModelo implements Serializable {
    private ArrayList<PerfilModelo> perfiles;

    public ListaPerfilesModelo(){
        this.perfiles = new ArrayList<>();
    }

    public ArrayList<PerfilModelo> getPerfiles() {
        return perfiles;
    }

    public void addPerfiles(PerfilModelo perfil) {
        perfiles.add(perfil);
    }

    public void serializarArrayList() throws Exception {
        FileOutputStream fout = new FileOutputStream("listaPerfiles.ser");
        ObjectOutputStream out = new ObjectOutputStream(fout);

        out.writeObject(perfiles);
        out.flush();
    }

    @SuppressWarnings("unchecked")
    public void deserializarArrayList() throws IOException, ClassNotFoundException  {
        FileInputStream fin = new FileInputStream("listaPerfiles.ser");
        ObjectInputStream in = new ObjectInputStream(fin);

        perfiles = (ArrayList<PerfilModelo>) in.readObject();
        in.close();
    }
}

