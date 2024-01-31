package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EditarUsuario extends AppCompatActivity {
    UsuarioActivo usuarioActivo;
    Usuarios usuarios;
    private int idUsr;
    private TextInputEditText txtFieldNombre, txtFieldApellido, txtFieldEstatura, txtFieldPeso;
    private TextView txtViewFecha;
    private LinearLayout contSexMasc, contSexFem, contFecha, contEmbarazo;
    private Button btnCancelar, btnAcept;
    private CheckBox checkBoxEmbarazo;
    String sexo, fechaNacimien;
    Boolean editado, embarazo;
    DBUsuarios dbUsuarios;
    SimpleDateFormat formatoFecha;
    Date fechaNaciDate;



    @SuppressLint("MissingInflatedId")
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
        contEmbarazo = findViewById(R.id.editarUsuarioLayoutEmbarazo);
        checkBoxEmbarazo = findViewById(R.id.editarUsuarioCheckBoxEmbarazo);

        mostrarDatos(dbUsuarios.verUsuario(idUsr));

        contSexMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contSexMasc.setBackgroundResource(R.drawable.fondo_usuario_perfil_obscuro);
                contSexMasc.setElevation(8);
                contSexFem.setBackgroundResource(R.drawable.fondo_usuario_perfil);
                contSexFem.setElevation(0);
                sexo = "Masculino";
                contEmbarazo.setVisibility(View.GONE);
                checkBoxEmbarazo.setEnabled(false);
                embarazo = false;
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
                contEmbarazo.setVisibility(View.VISIBLE);
                checkBoxEmbarazo.setEnabled(true);
            }
        });
        checkBoxEmbarazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                embarazo = checkBoxEmbarazo.isChecked();
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

                    TimeZone zonaHoraria = TimeZone.getTimeZone("UTC");
                    formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    formatoFecha.setTimeZone(zonaHoraria);

                    try {
                        // Intenta parsear la cadena de fecha a un objeto Date
                        fechaNaciDate = formatoFecha.parse(fechaNacimien);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //embarazo = checkBoxEmbarazo.isChecked();
                    //Log.d(TAG, "onClick: embarazo: "+embarazo);
                    editado = dbUsuarios.editaUsuario(idUsr, txtFieldNombre.getText().toString(), txtFieldApellido.getText().toString(), sexo, fechaNaciDate, Double.parseDouble(txtFieldEstatura.getText().toString()), Double.parseDouble(txtFieldPeso.getText().toString()), embarazo);

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

        contFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long fechaLimite = MaterialDatePicker.todayInUtcMilliseconds() - 86400000L;
                long fechaPredeterminada = obtenerFechaPredeterminada();
                //Crea el calendario
                MaterialDatePicker<Long> calendario = MaterialDatePicker.Builder.datePicker().setTitleText("Seleccione su Fecha de Nacimiento").setSelection(fechaPredeterminada).build();
                calendario.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        if(selection > fechaLimite){
                            Toast.makeText(EditarUsuario.this, "Fecha Invalida", Toast.LENGTH_SHORT).show();
                        }else {
                            TimeZone zonaHoraria = TimeZone.getTimeZone("UTC");
                            formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            formatoFecha.setTimeZone(zonaHoraria);

                            // Ajusta el tiempo de la selección con la zona horaria
                            long utcTime = selection + zonaHoraria.getOffset(new Date().getTime());
                            String date = formatoFecha.format(new Date(utcTime));

                            txtViewFecha.setText(MessageFormat.format("{0}", date));
                            txtViewFecha.setTextColor(Color.BLACK);
                            fechaNacimien = date;

                            try{
                                fechaNaciDate = formatoFecha.parse(fechaNacimien);
                            }catch (Exception ex){
                                Log.e(TAG, "Hubo un Error");
                            }
                        }

                    }
                });
                calendario.show(getSupportFragmentManager(), "tag");
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
                contEmbarazo.setVisibility(View.GONE);
                checkBoxEmbarazo.setChecked(false);
                checkBoxEmbarazo.setEnabled(false);
                embarazo = false;
            }else{
                contSexMasc.setElevation(0);
                contSexFem.setBackgroundResource(R.drawable.fondo_usuario_perfil_obscuro);
                sexo = "Femenino";
                contEmbarazo.setVisibility(View.VISIBLE);
                checkBoxEmbarazo.setEnabled(true);
                checkBoxEmbarazo.setChecked(usuario.getEmbarazada());
                embarazo = usuario.getEmbarazada();
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
        fechaNaciDate = new Date(tiempoMilisegundos);
        // Especifica la zona horaria deseada
        TimeZone zonaHoraria = TimeZone.getTimeZone("UTC");

        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        formato.setTimeZone(zonaHoraria);

        String fecha = formato.format(fechaNaciDate);

        return fecha;
    }

    private long obtenerFechaPredeterminada() {
        // Utiliza la clase Calendar para establecer la fecha de nacimiento deseada
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.set(1990, Calendar.JANUARY, 1); // Establece la fecha al 1 de enero de 1990

        if(fechaNaciDate == null){
            // Obtiene la fecha de nacimiento en milisegundos
            return fechaNacimientoCal.getTimeInMillis();
        }else {
            return fechaNaciDate.getTime();
        }
    }
}