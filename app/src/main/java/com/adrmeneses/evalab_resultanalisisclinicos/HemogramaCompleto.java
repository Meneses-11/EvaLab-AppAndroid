package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBReferenciaValores;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.usuarios.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

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
    DBExamenTipo dbExamenTipo;
    DBReferenciaValores dbReferenciaValores;
    //Arreglo que almacena todos los parámetros
    String[] parametros= {"Hemoglobina","Hematocrito","Eritrocitos","VMG","HCM","Reticulocitos","Leocucitos","Neutrofilos","Linfocitos","Eosinofilos","Basofilos","Monocitos","Plaquetas","Volumen Plaquetario Medio"};
    String[][] unidades={{"12","18"},{"36","52"},{"4.5","6.5"},{"80","100"},{"27","33"},{"0.5","1.5"},{"4","11"},{"40","75"},{"20","45"},{"0","6"},{"0","2"},{"2","10"},{"150","450"},{"7.2","11.1"}};
    //String[][] parametrosx={["Hemoglobina"][""],["Hematocrito"][""],["Eritrocitos"][""],["VMG"][""],["HCM"][""],["Reticulocitos"][""],["Leocucitos"][""],["Neutrofilos"][""],["Linfocitos"][""],["Eosinofilos"][""],["Basofilos"][""],["Monocitos"][""],["Plaquetas"][""],["Volumen Plaquetario Medio"][""]};
    String[][] parametro2 = {{"Hemoglobina","12","18","g/dL"},{"Hematocrito","36","52","%"},{"Eritrocitos", "4.5","6.5","x10^6/uL"},{"VMG", "80","100","fL"},{"HCM", "27","33","pg"},{"Reticulocitos", "0.5","1.5","%"},{"Leocucitos", "4","11","x10^3/uL"}, {"Neutrofilos", "40","75","%"}, {"Linfocitos", "20","45","%"},{"Eosinofilos", "0","6","%"}, {"Basofilos", "0","2","%"}, {"Monocitos", "2","10","%"}, {"Plaquetas", "150","450","x10^3/uL"},{"Volumen Plaquetario Medio", "7.2","11.1","fL"}};
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
        dbExamenTipo = new DBExamenTipo(this);
        dbReferenciaValores = new DBReferenciaValores(this);

        //Almacena el id del TipoExamen en este caso, de Sangre
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id


        if (dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Ya fueron creados los registros en ExamenParametros con id: "+idTipExam);
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Ya fueron creados los registros en Valores Referencia con id: "+idTipExam);
            }else {
                llenadoTablaReferenciaValores(dbExamenParametros);
            }
        }else{
            llenadoTablaExamenParametro(dbExamenParametros);
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Ya fueron creados los registros en Valores Referencia con id: "+idTipExam);
            }else {
                llenadoTablaReferenciaValores(dbExamenParametros);
            }
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
    //Método que agregará datos a la tabla ReferenciaValores
    public void llenadoTablaReferenciaValores(DBExamenParametros dbExamenParametros){
        long id;

        //For que recorre el arreglo y va llenando la tabla
        for(int i=0; i<parametro2.length; i++){
            id = dbReferenciaValores.insertaReferenciaValores((int)idTipExam, (int) dbExamenParametros.obtenerIdParametro(parametro2[i][0]),parametro2[i][1],parametro2[i][2],null,null,null,null,null,null,null,null,parametro2[i][3]);
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
            int identExamen = ((int) dbResultadosTabla.ultimoIdExamen(Integer.parseInt(String.valueOf(idTipExam))))+1;
            for(int a=0; a<textInputs.length; a++){
                llenarTablaResultados(dbResultadosTabla, Integer.parseInt(String.valueOf(idUsuario)),
                        Integer.parseInt(String.valueOf(idTipExam)), Integer.parseInt(String.valueOf(dbExamenParametros.obtenerIdParametro(parametros[a]))),
                        Double.parseDouble(textInputs[a].getText().toString()),identExamen);
            }
            Intent lanzar = new Intent(this,VentanaResultados.class);
            lanzar.putExtra("idExamen", identExamen);
            lanzar.putExtra("idTipExamen", (int) idTipExam);
            startActivity(lanzar);
            Log.d(TAG, "Hemograma: "+identExamen+" "+(int)idTipExam);
            finish();
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