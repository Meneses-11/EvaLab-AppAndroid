package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Enfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.evaluadores.EvaluadorEnfermedad;

import java.util.ArrayList;
import android.os.Handler;
import java.util.logging.LogRecord;

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

        EvaluadorEnfermedad evaEnferm = new EvaluadorEnfermedad();
        evaEnferm.evaluarEnferm(enfermedad);

        if(enfermedad.isMostrar()) {
            holder.nombreEnfermedad.setText(enfermedad.getNombreEnf());
            holder.textoProbabilidad.setText(enfermedad.getInfoPorcentaje());
            holder.textoPorcentaje.setText(enfermedad.getPorcentaje()+"%");
            holder.pbGrafica.setProgress(enfermedad.getPorcentaje());

        }else{
            //holder.contViewEnferm.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(() -> {
                /*listaEnfermedades.remove(position);
                notifyItemRemoved(position);*/
                if (position < listaEnfermedades.size()) {
                    listaEnfermedades.remove(position);
                    notifyItemRemoved(position);
                }else{
                    Log.e(TAG, "onBindViewHolder: Entro y no eliminó");
                }
            }, 20);

            //break;
        }
    }

    @Override //Especifica el tamaño de la lista
    public int getItemCount() {
        return listaEnfermedades.size();
    }

    public class EnfermedadViewHolder extends RecyclerView.ViewHolder {
        TextView nombreEnfermedad, textoProbabilidad, textoPorcentaje;
        ProgressBar pbGrafica;
        //LinearLayout contCard;
        FrameLayout contViewEnferm;
        public EnfermedadViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreEnfermedad = itemView.findViewById(R.id.textViewNombreEnfermedad);
            textoProbabilidad = itemView.findViewById(R.id.textViewResultadoEnfermedad);
            pbGrafica = itemView.findViewById(R.id.progressBarPorcentEnfermedad);
            textoPorcentaje = itemView.findViewById(R.id.textViewPorcentEnfermedad);
            contViewEnferm = itemView.findViewById(R.id.contenedorVistaEnfermedad);
        }
    }
}
