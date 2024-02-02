package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        holder.nombreEnfermedad.setText(enfermedad.getNombreEnf());
        holder.textoProbabilidad.setText(enfermedad.getInfoPorcentaje());
        holder.textoPorcentaje.setText(enfermedad.getPorcentaje()+"%");
        holder.pbGrafica.setProgress(enfermedad.getPorcentaje());

        holder.viewImgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ventanaDialogo(enfermedad.getNombreEnf(), enfermedad.getInformacion());
            }
        });
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
        ImageView viewImgInfo;
        public EnfermedadViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreEnfermedad = itemView.findViewById(R.id.textViewNombreEnfermedad);
            textoProbabilidad = itemView.findViewById(R.id.textViewResultadoEnfermedad);
            pbGrafica = itemView.findViewById(R.id.progressBarPorcentEnfermedad);
            textoPorcentaje = itemView.findViewById(R.id.textViewPorcentEnfermedad);
            contViewEnferm = itemView.findViewById(R.id.contenedorVistaEnfermedad);
            viewImgInfo = itemView.findViewById(R.id.imgViewDescripcionEnfermedad);
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
