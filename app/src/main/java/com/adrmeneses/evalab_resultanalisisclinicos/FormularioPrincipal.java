package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.usuarios.InformacionPerfil;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FormularioPrincipal extends AppCompatActivity {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara las variables que se usaran para los componentes gráficos
    RadioGroup radioGroupSexo;
    LinearLayout contenedorFechaNacimiento, ventana1, ventana2, ventana3;
    TextView txtFechaNacimiento;
    SimpleDateFormat formatoFecha;
    Button btnAntes, btnNext;
    TextInputEditText txtNombre, txtApellido, txtEstatura, txtPeso;
    CheckBox checkBoxEmbarazo;
    //Declara variables que sirven para almacenar el id de la opción en el RadioButton y una para llevar el control de la visibilidad de los componentes
    int checkRadioButtonId, nVentana = 1;
    //Decara las variables en las que se almacenará los datos que se introduciran en la bd
    String nombreUsr, apellidoUsr, sexoUsr;
    Date fechNaciUsr;
    Double pesoUsr, alturaUsr;
    //Declara la variable para instanciar la clase DBUsuarios
    DBUsuarios dbUsuarios;
    //Variable para el Intent
    Intent activityMenuPrin;
    private Boolean primero = true, isEmbarazada = false;
    private OpcionElegida opcElegida;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_principal);

        //Asigna a la variable la clase DBUsuarios
        dbUsuarios = new DBUsuarios(this);
        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        opcElegida = OpcionElegida.getInstance();
        //Asigna la activity MenuPrincipal al Intent
        activityMenuPrin = new Intent(FormularioPrincipal.this, MenuPrincipal.class);

        //Asigna los valores de los componentes a la variable correspondiente
        btnAntes = findViewById(R.id.btnAnterior);
        btnNext = findViewById(R.id.btnSiguiente);
        ventana1 = findViewById(R.id.layoutOpciones1);
        ventana2 = findViewById(R.id.layoutOpciones2);
        ventana3 = findViewById(R.id.layoutOpciones3);
        txtNombre = findViewById(R.id.txtFieldNombre);
        txtApellido = findViewById(R.id.txtFieldApellido);
        txtEstatura = findViewById(R.id.txtFieldEstatura);
        txtPeso = findViewById(R.id.txtFieldPseo);
        contenedorFechaNacimiento = findViewById(R.id.layoutFechaNacimiento);
        txtFechaNacimiento = findViewById(R.id.labelTxtFecha);
        radioGroupSexo = findViewById(R.id.radioGroupSex);
        checkBoxEmbarazo = findViewById(R.id.checkBoxEmbarazada);

        checkRadioButtonId = radioGroupSexo.getCheckedRadioButtonId();  //Retorna el id del item seleccionado

        //Oculta los componentes correspondientes al entrar a la activity
        funBtnVentanas(nVentana, ventana1, ventana2, ventana3, btnAntes, btnNext, txtNombre, txtApellido);

        //Se activa al presionar el boton Siguiente
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nVentana += 1;  //Aumenta el contador para el control de visibilidad de los componentes
                //Manda a llamar el método y le pasa todos los componentes que se visulizaran
                funBtnVentanas(nVentana, ventana1, ventana2, ventana3, btnAntes, btnNext, txtNombre, txtApellido);
            }
        });
        //Se activa al presionar el boton Anterior
        btnAntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nVentana -= 1;  //Disminuye el contador para el control de visibilidad de los componentes
                //Manda a llamar el método y le pasa todos los componentes que se visulizaran
                funBtnVentanas(nVentana, ventana1, ventana2, ventana3, btnAntes, btnNext, txtNombre, txtApellido);
            }
        });

        //Se ejecuta al seleccionar alguna opción del radioGrup
        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Evalúa ¿Qué opción se seleccionó?
                switch (i){
                    case R.id.radBtnM:
                        sexoUsr = "Masculino";  //Agrega a la variable la opción Masculino
                        checkBoxEmbarazo.setEnabled(false);
                        checkBoxEmbarazo.setChecked(false);
                        break;
                    case R.id.radBtnF:
                        sexoUsr = "Femenino";  //Agrega a la variable la opción Femenino
                        checkBoxEmbarazo.setEnabled(true);
                        break;
                    default:
                        Log.d(TAG, "El id es: "+i);
                }
            }
        });

        //Se ejecuta al presionar sobre el layout donde se encuentra el calendario y el label
        contenedorFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long fechaLimite = MaterialDatePicker.todayInUtcMilliseconds() - 31536000000L;
                // Obtiene la fecha de nacimiento actual del usuario o cualquier otra fecha que desees mostrar inicialmente
                long fechaPredeterminada = obtenerFechaNacimiento();

                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Seleccione su Fecha de Nacimiento").setSelection(fechaPredeterminada).build();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        if(selection > fechaLimite){
                            Toast.makeText(FormularioPrincipal.this, "Fecha Invalida", Toast.LENGTH_SHORT).show();
                        }else {
                            TimeZone zonaHoraria = TimeZone.getTimeZone("UTC");
                            formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            formatoFecha.setTimeZone(zonaHoraria);

                            // Ajusta el tiempo de la selección con la zona horaria
                            long utcTime = selection + zonaHoraria.getOffset(new Date().getTime());
                            String date = formatoFecha.format(new Date(utcTime));

                            txtFechaNacimiento.setText(MessageFormat.format("{0}", date));
                            txtFechaNacimiento.setTextColor(Color.BLACK);
                            try {
                                fechNaciUsr = formatoFecha.parse(date);
                            } catch (Exception ex) {
                                Log.e(TAG, "Error al convertir la fecha");
                            }
                        }
                    }
                });
                datePicker.show(getSupportFragmentManager(), "tag");
            }
        });
    }

    public void funBtnVentanas(int nVent, LinearLayout ventana1, LinearLayout ventana2, LinearLayout ventana3, Button btnAntes, Button btnNext, TextView nombre, TextView apellido){
        switch (nVent){
            case 1:
                ventana1.setVisibility(View.VISIBLE);
                ventana2.setVisibility(View.INVISIBLE);
                ventana3.setVisibility(View.INVISIBLE);
                btnAntes.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setText("Siguiente");
                break;
            case 2:
                nombreUsr = nombre.getText().toString();
                apellidoUsr = apellido.getText().toString();
                if (nombreUsr.isEmpty() || nombreUsr == " " || nombreUsr == null || apellidoUsr.isEmpty() || apellidoUsr == " " || apellidoUsr == null){
                    Toast.makeText(this, "Por favor Introduzca sus datos", Toast.LENGTH_SHORT).show();
                    nVentana = 1;
                }else{
                    ventana1.setVisibility(View.INVISIBLE);
                    ventana2.setVisibility(View.VISIBLE);
                    ventana3.setVisibility(View.INVISIBLE);
                    btnAntes.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnNext.setText("Siguiente");
                }
                break;
            case 3:
                if(sexoUsr == null || fechNaciUsr == null){
                    Toast.makeText(this, "Por favor Introduzca sus datos", Toast.LENGTH_SHORT).show();
                    nVentana = 2;
                } else {
                    ventana1.setVisibility(View.INVISIBLE);
                    ventana2.setVisibility(View.INVISIBLE);
                    ventana3.setVisibility(View.VISIBLE);
                    btnAntes.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                    btnNext.setText("Aceptar");
                    isEmbarazada = checkBoxEmbarazo.isChecked();
                }
                break;
            case 4:
                String straltura = txtEstatura.getText().toString();
                String strpeso = txtPeso.getText().toString();

                if(straltura.isEmpty() || strpeso.isEmpty()){
                    Toast.makeText(this, "Por favor Introduzca datos válidos", Toast.LENGTH_SHORT).show();
                    nVentana = 3;
                } else{
                    try {
                        alturaUsr = Double.parseDouble(straltura);
                        pesoUsr = Double.parseDouble(strpeso);
                        if (alturaUsr < 3) {
                            Log.d(TAG, "funBtnVentanas: idUser: "+userActv.getIdUsuario());
                            if(userActv.getIdUsuario() > 0) {
                                primero = false;
                            }else{
                                primero = true;
                            }
                            llenarUsuario(dbUsuarios);
                        }else {
                            Toast.makeText(this, "Ingrese un altura válida", Toast.LENGTH_SHORT).show();
                            nVentana = 3;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Los valores ingresados no son válidos", Toast.LENGTH_SHORT).show();
                        nVentana = 3;
                    }
                }
                break;
            default:
                Log.e(TAG, "Hubo un error");
        }
    }

    //Método que agregá datos a la tabla Usuarios
    public void llenarUsuario(DBUsuarios dbUsuarios){
        long id;

        id = dbUsuarios.insertaUsuario(nombreUsr, apellidoUsr, sexoUsr, fechNaciUsr, alturaUsr, pesoUsr, isEmbarazada);

        if(id>0){
            //Toast.makeText(this, "Agregado con Éxito", Toast.LENGTH_SHORT).show();
            userActv.setIdUsuario(id); //Le manda el usuario a la clase UsuarioActivo
            if(primero){
                Log.d(TAG, "Bienvenido: Usuario "+userActv.getIdUsuario());
                startActivity(activityMenuPrin);
                finish();
            }else {
                Intent intent = new Intent(this, InformacionPerfil.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                opcElegida.setNombreExamen("");
                opcElegida.setExamenId(0);
                opcElegida.setExamenTipId(0);
                Toast.makeText(this, "Bienveido "+nombreUsr, Toast.LENGTH_SHORT).show();
                finish();

            }
        }else {
            Log.e(TAG, "Hubo un ERROR");
            nVentana = 3;
        }
    }

    private long obtenerFechaNacimiento() {
        // Utiliza la clase Calendar para establecer la fecha de nacimiento deseada
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.set(1990, Calendar.JANUARY, 1); // Establece la fecha al 1 de enero de 1990

        if(fechNaciUsr == null){
            // Obtiene la fecha de nacimiento en milisegundos
            return fechaNacimientoCal.getTimeInMillis();
        }else {
            return fechNaciUsr.getTime();
        }
    }

}