package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Enfermedades;

import java.util.ArrayList;

public class AdaptadorListaEnfermedades extends RecyclerView.Adapter<AdaptadorListaEnfermedades.EnfermedadViewHolder> {

    ArrayList<Enfermedades> listaEnfermedades;

    public AdaptadorListaEnfermedades(ArrayList<Enfermedades> listaEnfermedades) {
        this.listaEnfermedades = listaEnfermedades;
    }

    @NonNull
    @Override //Asigna el diseño de cada elemento de la lista
    public EnfermedadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_fragment_enfermedad, null, false);
        return new EnfermedadViewHolder(view);
    }

    @Override //Asigna los datos que tendrá
    public void onBindViewHolder(@NonNull EnfermedadViewHolder holder, int position) {
        Enfermedades enfermedad = listaEnfermedades.get(position);

        /*EvaluadorEnfermedad evaEnf = new EvaluadorEnfermedad();
        evaEnf.evaluarEnf(enfermedad);*/

        holder.nombreEnfermedad.setText(enfermedad.getNombreEnf());
        holder.textoProbabilidad.setText(enfermedad.getReferencia());
        holder.textoPorcentaje.setText((int) enfermedad.getValorObtenido()+"%");
        holder.pbGrafica.setProgress((int) enfermedad.getValorObtenido());
    }

    @Override //Especifica el tamaño de la lista
    public int getItemCount() {
        return listaEnfermedades.size();
    }

    public class EnfermedadViewHolder extends RecyclerView.ViewHolder {
        TextView nombreEnfermedad, textoProbabilidad, textoPorcentaje;
        ProgressBar pbGrafica;
        public EnfermedadViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreEnfermedad = itemView.findViewById(R.id.textViewNombreEnfermedad);
            textoProbabilidad = itemView.findViewById(R.id.textViewResultadoEnfermedad);
            pbGrafica = itemView.findViewById(R.id.progressBarPorcentEnfermedad);
            textoPorcentaje = itemView.findViewById(R.id.textViewPorcentEnfermedad);
        }
    }
}
