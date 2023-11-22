package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import static com.adrmeneses.evalab_resultanalisisclinicos.R.drawable.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;

import java.util.ArrayList;

public class AdaptadorListaResultados extends RecyclerView.Adapter<AdaptadorListaResultados.ResultadoViewHolder> {
    ArrayList<Resultados> listaResultados;

    public AdaptadorListaResultados(ArrayList<Resultados> listaResultados){
        this.listaResultados = listaResultados;
    }

    @NonNull
    @Override   //Asigna el diseño de cada elemento de la lista
    public ResultadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_fragment_resultado, null, false);
        return new ResultadoViewHolder(view);
    }

    @Override //Asigna los datos que tendrá
    public void onBindViewHolder(@NonNull ResultadoViewHolder holder, int position) {
        holder.viewNombreParametro.setText(String.valueOf(listaResultados.get(position).getParametroNombre()));
        holder.viewResultado.setText(String.valueOf(listaResultados.get(position).getValorObtenido())+String.valueOf(listaResultados.get(position).getMedidaUnidad()));
        holder.viewValReferencia.setText(String.valueOf(listaResultados.get(position).getMinValor())+"-"+String.valueOf(listaResultados.get(position).getMaxValor()));
        holder.viewSemafor.setImageResource(R.drawable.semaf_amar);
        holder.viewEstado.setText("Demasiado bajo");
    }

    @Override //Especifica el tamaño de la lista
    public int getItemCount() {
        return listaResultados.size();
    }

    public class ResultadoViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombreParametro, viewResultado, viewValReferencia, viewEstado;
        ImageView viewSemafor;

        public ResultadoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombreParametro = itemView.findViewById(R.id.textViewNombreParametro);
            viewResultado = itemView.findViewById(R.id.textViewResultadoParametro);
            viewValReferencia = itemView.findViewById(R.id.textViewReferenciaParametro);
            viewEstado = itemView.findViewById(R.id.textViewEstadoParametro);
            viewSemafor = itemView.findViewById(R.id.imgViewIconResultado);
        }
    }
}
