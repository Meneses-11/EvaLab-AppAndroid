package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        holder.viewImgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ventanaDialogo(listaResultados.get(position).getParametroNombre(), listaResultados.get(position).getDescripcion());
            }
        });
    }

    @Override //Especifica el tamaño de la lista
    public int getItemCount() {
        return listaResultados.size();
    }

    public class ResultadoViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombreParametro, viewResultado, viewValReferencia, viewEstado, viewResultado2;
        ImageView viewSemafor, viewImgInfo;
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
            viewImgInfo = itemView.findViewById(R.id.imgViewDescripcionParametro);
            dbExamenParametros = new DBExamenParametros(itemView.getContext());
            dbExamenTipo = new DBExamenTipo(itemView.getContext());
        }

        //Método que arroja un mensaje de Error en la pantalla
        public void ventanaDialogo(String titulo, String texto){
            //Infla el diseño personalizado
            View dialogoPersonalizado = LayoutInflater.from(itemView.getContext()).inflate(R.layout.alert_dialog_descripcion_parametro, null);

            //Crea el AertDialog
            AlertDialog dialogo = new AlertDialog.Builder(itemView.getContext()).create(); //Crea un objeto de tipo AlertDialog
            dialogo.setView(dialogoPersonalizado);                               //Le asigna la ventana personalizada
            dialogo.getWindow().setBackgroundDrawableResource(R.drawable.fondo_informacion_parametro);

            // Obtiene las vistas del diseño personalizado
            TextView tituloTextView = dialogoPersonalizado.findViewById(R.id.txtTituloDialogoParametro);
            TextView mensajeTextView = dialogoPersonalizado.findViewById(R.id.txtMensajeDialogoParametro);
            Button aceptarButton = dialogoPersonalizado.findViewById(R.id.btnAceptarDialogoParametro);

            //Configura la información a mostrar
            tituloTextView.setText(""+titulo);
            mensajeTextView.setText(""+texto);

            //Agrega un boton y le pone el texto que llevara
            aceptarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogo.dismiss();
                }
            });
            dialogo.show();                        //Pone el Dialog en la pantalla
        }
    }

}
