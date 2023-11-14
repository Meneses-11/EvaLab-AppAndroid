package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;

public class HemogramaCompleto extends ExamenesSangre {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara los componentes visuales
    TextInputEditText txtHemoglobina, txtHematocrito, txtEritrocitos, txtVMG, txtHCM, txtReticulocitos, txtLeucocitos, txtNeutrofilos, txtLinfocitos, txtEosinofilos, txtBasofilos, txtMonocitos, txtPlaquetas, txtVolPlaquetario;
    final String NAME_EXAM = "hemograma";
    long idTipExam, idUsuario;
    //Declara la variable de la clase DBExamenParametros y DBResultadosTabla
    DBExamenParametros dbExamenParametros;
    DBResultadosTabla dbResultadosTabla;
    //Arreglo que almacena todos los parámetros
    String[] parametros= {"Hemoglobina","Hematocrito","Eritrocitos","VMG","HCM","Reticulocitos","Leocucitos","Neutrofilos","Linfocitos","Eosinofilos","Basofilos","Monocitos","Plaquetas","Volumen Plaquetario Medio"};
    //Arreglo que contiene todos los editText
    TextInputEditText[] textInputs = { txtHemoglobina, txtHematocrito, txtEritrocitos, txtVMG, txtHCM, txtReticulocitos, txtLeucocitos, txtNeutrofilos, txtLinfocitos, txtEosinofilos, txtBasofilos, txtMonocitos, txtPlaquetas, txtVolPlaquetario };
    //Arreglo que contiene el id de todos los componentes
    int[] componentes = {R.id.txtFieldHemoglobina, R.id.txtFieldHematocrito, R.id.txtFieldEritrocito, R.id.txtFieldVMG, R.id.txtFieldHCM, R.id.txtFieldReticulocitos, R.id.txtFieldLeucocito, R.id.txtFieldNeutrofilo, R.id.txtFieldLinfocito, R.id.txtFieldEosinofilos, R.id.txtFieldBasofilos, R.id.txtFieldMonocitos, R.id.txtFieldPlaquetas, R.id.txtFieldVolPlaquetario };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hemograma_completo);

        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = userActv.getIdUsuario();

        //Asigna los componentes a su variable correspondiente
        for (int j=0; j<componentes.length; j++){
            textInputs[j] = findViewById(componentes[j]);
        }
        
        //Asigna la clase a la variable correspondiente
        dbExamenParametros = new DBExamenParametros(this);
        dbResultadosTabla = new DBResultadosTabla(this);

        //Almacena el id del TipoExamen en este caso, de Sangre
        idTipExam = obtenerIdTipExam(NAME_EXAM); //manda a llamar el mpetodo que retorna el id


        if (dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Ya fueron creados los registros con id: "+idTipExam);
        }else{
            llenadoTablaExamenParametro(dbExamenParametros);
        }

    }

    //Método que agregará datos a la tabla ExamenParámetros
    public void llenadoTablaExamenParametro(DBExamenParametros dbExamenParametros){
        long id;

        //For que recorre el arreglo y va llenando la tabla
        for(int i=0; i<parametros.length; i++){
            id = dbExamenParametros.insertaParametro(Integer.parseInt(String.valueOf(idTipExam)), parametros[i]);
            //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
            if(id > 0){
                Log.d(TAG, "Registro guardado con éxtio");
            }else{
                Log.e(TAG, "Hubo un error en: "+i);
            }
        }

    }

    //Se ejcuta al presionar el boton Analizar
    public void analizarHemograma(View view){
        if(CamposLLenos(textInputs)){
            for(int a=0; a<textInputs.length; a++){
                llenarTablaResultados(dbResultadosTabla, Integer.parseInt(String.valueOf(idUsuario)),
                        Integer.parseInt(String.valueOf(idTipExam)), Integer.parseInt(String.valueOf(obtenerIdParametro(parametros[a]))),
                        Double.parseDouble(textInputs[a].getText().toString()));
            }
        }else {
            ventanaDialogo();
        }
    }

    //Método que verifica que todos los campos estén llenos
    private boolean CamposLLenos(TextInputEditText[] txtInputs) {
        boolean todosCompletos = true;
        //For each que recorre el arreglo con todos los EditText
        for (TextInputEditText textInput : txtInputs) {
            //Asigna lo que hay en el editText a una variable
            String texto = textInput.getText().toString().trim();
            if (texto.isEmpty()) {      //Evalúa si la variable está vacía
                todosCompletos = false; //Si es así asigna false a la bandera
                break;
            }
        }
        return todosCompletos;
    }

    private void ventanaDialogo(){
        //Infla el diseño personalizado
        View dialogoPersonalizado = getLayoutInflater().inflate(R.layout.alert_dialog_datos_invalidos, null);

        //Crea el AertDialog
        AlertDialog dialogo = new AlertDialog.Builder(this).create(); //Crea un objeto de tipo AlertDialog
        dialogo.setView(dialogoPersonalizado);                                       //Le asigna la ventana personalizada

        // Obtiene las vistas del diseño personalizado
        TextView tituloTextView = dialogoPersonalizado.findViewById(R.id.txtTituloDialogo);
        TextView mensajeTextView = dialogoPersonalizado.findViewById(R.id.txtMensajeDialogo);
        Button aceptarButton = dialogoPersonalizado.findViewById(R.id.btnAceptarDialogo);
        ImageView imagenDialogo = dialogoPersonalizado.findViewById(R.id.imgDialogo);

        //Configura la información a mostrar
        tituloTextView.setText("Datos Erroneos");
        mensajeTextView.setText("Introduzca la información solicitada");
        imagenDialogo.setImageDrawable(getDrawable(R.drawable.msj_error));

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