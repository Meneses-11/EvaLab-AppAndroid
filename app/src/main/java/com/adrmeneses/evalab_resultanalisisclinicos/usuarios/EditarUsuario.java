package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EditarUsuario extends AppCompatActivity {
    UsuarioActivo usuarioActivo;
    Usuarios usuarios;
    private int idUsr;
    private TextInputEditText txtFieldNombre, txtFieldApellido, txtFieldEstatura, txtFieldPeso;
    private TextView txtViewFecha;
    private LinearLayout contSexMasc, contSexFem, contFecha;
    private Button btnCancelar, btnAcept;
    String sexo, fechaNacimien;
    Boolean editado;
    DBUsuarios dbUsuarios;
    SimpleDateFormat formatoFecha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_usuario);

        dbUsuarios = new DBUsuarios(this);
        usuarioActivo = UsuarioActivo.getInstance();
        idUsr = (int)usuarioActivo.getIdUsuario();

        txtFieldNombre = findViewById(R.id.editarUsuarioTextFieldNombre);
        txtFieldApellido = findViewById(R.id.editarUsuarioTextFieldApellido);
        txtViewFecha = findViewById(R.id.editarUsuarioTextViewFecha);
        contFecha = findViewById(R.id.editarUsuarioLayoutFechaNacimiento);
        txtFieldEstatura = findViewById(R.id.editarUsuarioTextFieldAltura);
        txtFieldPeso = findViewById(R.id.editarUsuarioTextFieldPeso);
        contSexMasc = findViewById(R.id.editarUsuarioLayoutSexoMasc);
        contSexFem = findViewById(R.id.editarUsuarioLayoutSexoFeme);
        btnCancelar = findViewById(R.id.editarUsuarioBotonCancelar);
        btnAcept = findViewById(R.id.editarUsuarioBotonAceptar);

        mostrarDatos(dbUsuarios.verUsuario(idUsr));

        contSexMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contSexMasc.setBackgroundResource(R.drawable.fondo_usuario_perfil_obscuro);
                contSexMasc.setElevation(8);
                contSexFem.setBackgroundResource(R.drawable.fondo_usuario_perfil);
                contSexFem.setElevation(0);
                sexo = "Masculino";
            }
        });
        contSexFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contSexFem.setBackgroundResource(R.drawable.fondo_usuario_perfil_obscuro);
                contSexFem.setElevation(8);
                contSexMasc.setBackgroundResource(R.drawable.fondo_usuario_perfil);
                contSexMasc.setElevation(0);
                sexo = "Femenino";
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtFieldNombre.getText().toString().equals("") && !txtFieldApellido.getText().toString().equals("") && !txtFieldEstatura.getText().toString().equals("") && !txtFieldPeso.getText().toString().equals("") && !txtViewFecha.getText().toString().equals("")) {

                    Date fechaDate = null;

                    TimeZone zonaHoraria = TimeZone.getTimeZone("UTC");
                    formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    formatoFecha.setTimeZone(zonaHoraria);

                    try {
                        // Intenta parsear la cadena de fecha a un objeto Date
                        fechaDate = formatoFecha.parse(fechaNacimien);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    editado = dbUsuarios.editaUsuario(idUsr, txtFieldNombre.getText().toString(), txtFieldApellido.getText().toString(), sexo, fechaDate, Double.parseDouble(txtFieldEstatura.getText().toString()), Double.parseDouble(txtFieldPeso.getText().toString()));

                    if (editado){
                        Toast.makeText(EditarUsuario.this, "Guardado con Éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(EditarUsuario.this, "Hubo un problema", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditarUsuario.this, "Debe llenar los campos obligatorios", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void mostrarDatos(Usuarios usuario){

        if(usuario != null){
            txtFieldNombre.setText(usuario.getNombre());
            txtFieldApellido.setText(usuario.getApellido());
            if ("Masculino".equals(usuario.getSexo())){
                contSexMasc.setBackgroundResource(R.drawable.fondo_usuario_perfil_obscuro);
                contSexFem.setElevation(0);
                sexo = "Masculino";
            }else{
                contSexMasc.setElevation(0);
                contSexFem.setBackgroundResource(R.drawable.fondo_usuario_perfil_obscuro);
                sexo = "Femenino";
            }
            fechaNacimien = convertirFecha(usuario.getFecha());
            txtViewFecha.setText(convertirFecha(usuario.getFecha()));
            txtFieldEstatura.setText(""+usuario.getAltura());
            txtFieldPeso.setText(""+usuario.getPeso());

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
}