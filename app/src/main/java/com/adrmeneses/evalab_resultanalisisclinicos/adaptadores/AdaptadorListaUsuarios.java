package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AdaptadorListaUsuarios extends RecyclerView.Adapter<AdaptadorListaUsuarios.UsuarioViewHolder> {
    public AdaptadorListaUsuarios(ArrayList<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    ArrayList<Usuarios> listaUsuarios;

    @NonNull
    @Override
    public AdaptadorListaUsuarios.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_menu_usuarios, null, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaUsuarios.UsuarioViewHolder holder, int position) {
        Usuarios usuarios = listaUsuarios.get(position);

        holder.imgViewPerfil.setImageResource(usuarios.getIdImg());
        holder.txtViewNombre.setText(usuarios.getNombre());
        holder.txtViewApellido.setText(usuarios.getApellido());
        holder.txtViewFecha.setText(convertirFecha(usuarios.getFecha()));

        holder.contentOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.userActivo.setIdUsuario((long) usuarios.getId());
                // Finalizar la actividad
                if (view.getContext() instanceof AppCompatActivity) {
                    ((AppCompatActivity) view.getContext()).finish();
                } else {
                    Log.d(TAG, "El contexto no es una instancia de AppCompatActivity");
                }
            }
        });
    }

    private String convertirFecha(String fechaNacimiento){

        // Convierte el String a long (milisegundos)
        long tiempoMilisegundos = Long.parseLong(fechaNacimiento);
        // Crea un objeto Date usando el valor de tiempo en milisegundos
        Date fechaNaci = new Date(tiempoMilisegundos);

        // Especifica la zona horaria deseada
        TimeZone zonaHoraria = TimeZone.getTimeZone("UTC");

        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        formato.setTimeZone(zonaHoraria);

        String fecha = formato.format(fechaNaci);

        return fecha;

    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewNombre, txtViewApellido, txtViewFecha;
        ImageView imgViewPerfil;
        private LinearLayout contentOpcion;
        UsuarioActivo userActivo;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewNombre = itemView.findViewById(R.id.listaUsuarioTextViewNombre);
            txtViewApellido = itemView.findViewById(R.id.listaUsuarioTextViewApellido);
            txtViewFecha = itemView.findViewById(R.id.listaUsuarioTextViewFecha);
            imgViewPerfil = itemView.findViewById(R.id.listaUsuarioImgView);
            contentOpcion = itemView.findViewById(R.id.contenedorOpcionListaUsuarios);

            userActivo = UsuarioActivo.getInstance();
        }
    }
}
