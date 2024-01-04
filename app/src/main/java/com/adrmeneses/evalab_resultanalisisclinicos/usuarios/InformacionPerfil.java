package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.FormularioPrincipal;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class InformacionPerfil extends AppCompatActivity {
    UsuarioActivo usuarioActivo;
    Usuarios usuarios;
    private int idUsr;
    private TextView txtViewNombre, txtViewApellido, txtViewSexo, txtViewFecha, txtViewEdad, txtViewEstatura, txtViewPeso;
    private ImageView imgViewSexo;
    private Button btnEditar, btnCambiar;
    private int imgMasculino, imgFemenino;
    private LinearLayout contenedorEmbarazo;
    DBUsuarios dbUsuarios;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_perfil);

        dbUsuarios = new DBUsuarios(this);
        usuarioActivo = UsuarioActivo.getInstance();
        idUsr = (int)usuarioActivo.getIdUsuario();

        txtViewNombre = findViewById(R.id.infPerfilTextViewNombre);
        txtViewApellido = findViewById(R.id.infPerfilTextViewApellido);
        txtViewSexo = findViewById(R.id.infPerfilTextViewSexo);
        txtViewFecha = findViewById(R.id.infPerfilTextViewFecha);
        txtViewEdad = findViewById(R.id.infPerfilTextViewEdad);
        txtViewEstatura = findViewById(R.id.infPerfilTextViewAltura);
        txtViewPeso = findViewById(R.id.infPerfilTextViewPeso);
        imgViewSexo = findViewById(R.id.infPerfilImgViewSexo);
        btnEditar = findViewById(R.id.infPerfilBotonEditar);
        btnCambiar = findViewById(R.id.infPerfilBotonCambiar);
        contenedorEmbarazo = findViewById(R.id.perfilUserContEmbarazo);

        imgMasculino = R.drawable.hombre;
        imgFemenino = R.drawable.mujer;

        mostrarDatos(dbUsuarios.verUsuario(idUsr));

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarios = new Usuarios();

                Intent lanzarEdit = new Intent(InformacionPerfil.this, EditarUsuario.class);
                startActivity(lanzarEdit);

            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent lanzarCambio = new Intent(InformacionPerfil.this, MenuUsuarios.class);
                startActivity(lanzarCambio);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void mostrarDatos(Usuarios usuario){

        if(usuario != null){
            txtViewNombre.setText(usuario.getNombre());
            txtViewApellido.setText(usuario.getApellido());
            if ("Masculino".equals(usuario.getSexo())){
                imgViewSexo.setImageResource(imgMasculino);
                txtViewSexo.setText(usuario.getSexo());
                contenedorEmbarazo.setVisibility(View.GONE);
            }else{
                imgViewSexo.setImageResource(imgFemenino);
                txtViewSexo.setText(usuario.getSexo());
                if(usuario.getEmbarazada()){
                    contenedorEmbarazo.setVisibility(View.VISIBLE);
                }else {
                    contenedorEmbarazo.setVisibility(View.GONE);
                }
            }
            txtViewFecha.setText(convertirFecha(usuario.getFecha()));
            txtViewEdad.setText(obtenerAnios(convertirFecha(usuario.getFecha())));
            txtViewEstatura.setText(""+usuario.getAltura());
            txtViewPeso.setText(""+usuario.getPeso());

        }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String obtenerAnios(String datoFecha){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaNac = LocalDate.parse(datoFecha, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        return String.valueOf(periodo.getYears());
    }

    @Override
    protected void onResume() {
        super.onResume();

        idUsr = (int)usuarioActivo.getIdUsuario();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mostrarDatos(dbUsuarios.verUsuario(idUsr));
        }
    }
}