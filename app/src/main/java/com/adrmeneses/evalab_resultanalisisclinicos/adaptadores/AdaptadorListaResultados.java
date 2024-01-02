package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import static com.adrmeneses.evalab_resultanalisisclinicos.R.drawable.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;
import com.adrmeneses.evalab_resultanalisisclinicos.evaluadores.EvaluadorResultado;

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
        Resultados resultado = listaResultados.get(position);

        if(holder.dbExamenParametros.obtenerIdDeTipExamDelParametro(resultado.getParametroNombre()) == holder.dbExamenTipo.obtenerIdTipExam("Examen de Orina")){
            EvaluadorResultado evaluador = new EvaluadorResultado();
            evaluador.evaluarResultOrina(resultado);

            holder.viewContenedorValores1.setVisibility(View.INVISIBLE);
            holder.viewContenedorValores2.setVisibility(View.VISIBLE);
            holder.viewEstado.setText(resultado.getEstado());
            holder.viewSemafor.setImageResource(resultado.getSemaf());
            holder.viewNombreParametro.setText(String.valueOf(listaResultados.get(position).getParametroNombre()));
            holder.viewResultado2.setText(listaResultados.get(position).getValorObtenido());
        }else {
            EvaluadorResultado evaluador = new EvaluadorResultado();
            evaluador.evaluarResult(resultado);

            holder.viewContenedorValores1.setVisibility(View.VISIBLE);
            holder.viewContenedorValores2.setVisibility(View.INVISIBLE);
            holder.viewEstado.setText(resultado.getEstado());
            holder.viewSemafor.setImageResource(resultado.getSemaf());
            holder.viewNombreParametro.setText(String.valueOf(listaResultados.get(position).getParametroNombre()));
            holder.viewResultado.setText(String.valueOf(listaResultados.get(position).getValorObtenido()) + String.valueOf(listaResultados.get(position).getMedidaUnidad()));
            holder.viewValReferencia.setText(String.valueOf(listaResultados.get(position).getMinValor()) + "-" + String.valueOf(listaResultados.get(position).getMaxValor()));
        }
    }

    @Override //Especifica el tamaño de la lista
    public int getItemCount() {
        return listaResultados.size();
    }

    public class ResultadoViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombreParametro, viewResultado, viewValReferencia, viewEstado, viewResultado2;
        ImageView viewSemafor;
        TableLayout viewContenedorValores1;
        LinearLayout viewContenedorValores2;
        DBExamenParametros dbExamenParametros;
        DBExamenTipo dbExamenTipo;

        public ResultadoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombreParametro = itemView.findViewById(R.id.textViewNombreParametro);
            viewResultado = itemView.findViewById(R.id.textViewResultadoParametro);
            viewResultado2 = itemView.findViewById(R.id.textViewResultadoParametro2);
            viewValReferencia = itemView.findViewById(R.id.textViewReferenciaParametro);
            viewEstado = itemView.findViewById(R.id.textViewEstadoParametro);
            viewSemafor = itemView.findViewById(R.id.imgViewIconResultado);
            viewContenedorValores1 = itemView.findViewById(R.id.listaResultadoContenedorValores1);
            viewContenedorValores2 = itemView.findViewById(R.id.listaResultadoContenedorValores2);
            dbExamenParametros = new DBExamenParametros(itemView.getContext());
            dbExamenTipo = new DBExamenTipo(itemView.getContext());
        }
    }
}
