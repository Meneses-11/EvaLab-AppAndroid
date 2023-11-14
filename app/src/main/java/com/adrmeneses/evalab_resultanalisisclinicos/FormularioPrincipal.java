package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_principal);

        //Asigna a la variable la clase DBUsuarios
        dbUsuarios = new DBUsuarios(this);
        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
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
                        //Toast.makeText(FormularioPrincipal.this, "Selecciono Masculino", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radBtnF:
                        sexoUsr = "Femenino";  //Agrega a la variable la opción Femenino
                        //Toast.makeText(FormularioPrincipal.this, "Seleccionó Femenino", Toast.LENGTH_SHORT).show();
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
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

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
                        } catch(Exception ex){
                            Log.e(TAG, "Error al convertir la fecha");
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

        id = dbUsuarios.insertaUsuario(nombreUsr, apellidoUsr, sexoUsr, fechNaciUsr, alturaUsr, pesoUsr);

        if(id>0){
            //Toast.makeText(this, "Agregado con Éxito", Toast.LENGTH_SHORT).show();
            userActv.setIdUsuario(id); //Le manda el usuario a la clase UsuarioActivo
            startActivity(activityMenuPrin);
            finish();
        }else {
            Log.e(TAG, "Hubo un ERROR");
            nVentana = 3;
        }
    }



}