package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class InformacionPerfil extends AppCompatActivity {
    UsuarioActivo usuarioActivo;
    private int idUsr;
    private TextView txtViewNombre, txtViewApellido, txtViewSexo, txtViewFecha, txtViewEdad, txtViewEstatura, txtViewPeso;
    private ImageView imgViewSexo;
    private Button btnEditar, btnCambiar;
    private int imgMasculino, imgFemenino;
    DBUsuarios dbUsuarios;

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

        imgMasculino = R.drawable.hombre;
        imgFemenino = R.drawable.mujer;

        mostrarDatos(dbUsuarios.obtenerInfUsuario(idUsr));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void mostrarDatos(String[] datos){
        String sexo = null;

        if(datos != null){
            txtViewNombre.setText(datos[1]);
            txtViewApellido.setText(datos[2]);
            if ("Masculino".equals(datos[4])){
                imgViewSexo.setImageResource(imgMasculino);
                txtViewSexo.setText(datos[4]);
            }else{
                imgViewSexo.setImageResource(imgFemenino);
                txtViewSexo.setText(datos[4]);
            }
            txtViewFecha.setText(convertirFecha(datos[3]));
            txtViewEdad.setText(obtenerAnios(convertirFecha(datos[3])));
            txtViewEstatura.setText(""+datos[5]);
            txtViewPeso.setText(""+datos[6]);

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


}