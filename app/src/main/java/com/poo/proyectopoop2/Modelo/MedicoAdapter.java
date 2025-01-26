package com.poo.proyectopoop2.Modelo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poo.proyectopoop2.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.MedicoViewHolder> {
    private final List<MedicoModelo> listaMedicos;
    private final Context context;

    public MedicoAdapter(Context context, List<MedicoModelo> listaMedicos) {
        this.context = context;
        this.listaMedicos = listaMedicos;
    }

    @NonNull
    @Override
    public MedicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medico, parent, false);
        return new MedicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicoViewHolder holder, int position) {
        MedicoModelo medico = listaMedicos.get(position);
        holder.nombreMedico.setText(medico.getNombre());
        holder.especialidadMedico.setText(medico.getEspecialidades());
        // Puedes asignar imágenes dinámicas aquí si es necesario
    }

    @Override
    public int getItemCount() {
        return listaMedicos.size();
    }

    public static class MedicoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreMedico, especialidadMedico;
        ImageView imageViewMedico;

        public MedicoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreMedico = itemView.findViewById(R.id.textViewNombreMedico);
            especialidadMedico = itemView.findViewById(R.id.textViewEspecialidadMedico);
            imageViewMedico = itemView.findViewById(R.id.imageViewMedico);
        }
    }
}

