package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBEnfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBEnfermedadesParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBReferenciaValores;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenGeneralOrina;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.FuncionHepatica;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.FuncionRenal;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.FuncionTiroidea;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.HemogramaCompleto;
import com.google.android.material.textfield.TextInputEditText;

public class MenuCategorias extends AppCompatActivity {
    
    private LinearLayout btnExamenSangre, btnExamenOrina, btnExamenFunHepatica, btnExamenTiroides, btnExamenFunRenal;
    //Declara las variables de las clases de la Base de Datos
    protected DBExamenParametros dbExamenParametros;
    protected DBResultadosTabla dbResultadosTabla;
    protected DBExamenTipo dbExamenTipo;
    protected DBReferenciaValores dbReferenciaValores;
    protected DBEnfermedades dbEnfermedades;
    protected DBEnfermedadesParametros dbEnfermedadesParametros;
    private OpcionElegida opcElegida;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_categorias);

        //Instancia la clase OpcionElegida
        opcElegida = OpcionElegida.getInstance();

        //Asigna la clase a la variable correspondiente
        dbExamenParametros = new DBExamenParametros(this);
        dbResultadosTabla = new DBResultadosTabla(this);
        dbExamenTipo = new DBExamenTipo(this);
        dbReferenciaValores = new DBReferenciaValores(this);
        dbEnfermedades = new DBEnfermedades(this);
        dbEnfermedadesParametros = new DBEnfermedadesParametros(this);

        btnExamenSangre = findViewById(R.id.menuCategoriasBotonExamenSangre);
        btnExamenOrina = findViewById(R.id.menuCategoriasBotonExamenOrina);
        btnExamenFunHepatica = findViewById(R.id.menuCategoriasBotonExamenFunHepatica);
        btnExamenTiroides = findViewById(R.id.menuCategoriasBotonExamenTiroides);
        btnExamenFunRenal = findViewById(R.id.menuCategoriasBotonExamenFunRenal);

        btnExamenSangre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar = new Intent(MenuCategorias.this, HemogramaCompleto.class);
                startActivity(lanzar);
            }
        });
        btnExamenOrina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar1 = new Intent(MenuCategorias.this, ExamenGeneralOrina.class);
                startActivity(lanzar1);
            }
        });
        btnExamenFunHepatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar2 = new Intent(MenuCategorias.this, FuncionHepatica.class);
                startActivity(lanzar2);
            }
        });
        btnExamenTiroides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar3 = new Intent(MenuCategorias.this, FuncionTiroidea.class);
                startActivity(lanzar3);
            }
        });
        btnExamenFunRenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar4 = new Intent(MenuCategorias.this, FuncionRenal.class);
                startActivity(lanzar4);
            }
        });
        
    }

    //Método que agregará datos a la tabla ExamenParámetros
    public void llenadoTablaExamenParametro(String[][] parametros, long idTipExam){
        long id;

        //For que recorre el arreglo y va llenando la tabla
        for(int i=0; i<parametros.length; i++){
            String informacion = null;

            if (parametros[i][parametros[i].length-1] != null) {
                informacion = getResources().getString(Integer.parseInt(parametros[i][parametros[i].length - 1]));
            }

            id = dbExamenParametros.insertaParametro((int)idTipExam, parametros[i][0], informacion);
            //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
            if(id <= 0){
                Log.e(TAG, "Hubo un error en id:"+i+" del examen:"+(int)idTipExam);
            }
        }

    }
    //Método que agrega registros a la tabla ReferenciaValores
    public void llenadoTablaReferenciaValores(String[][] parametros, long idTipExam){
        long id;

        //For que recorre el arreglo y llena la tabla
        for (int i=0; i<parametros.length; i++){
            id = dbReferenciaValores.insertaReferenciaValores((int)idTipExam, (int)dbExamenParametros.obtenerIdParametro(parametros[i][0]), parametros[i][1], parametros[i][2], parametros[i][3], parametros[i][4], parametros[i][5], parametros[i][6], parametros[i][7], parametros[i][8], parametros[i][9], parametros[i][10], parametros[i][11]);
            if(id <= 0){
                Log.e(TAG, "Hubo un erro al llenar TablaReferencia en examen "+(int)idTipExam+" en "+parametros[i][0]);
            }
        }

    }

    //Método que agrega registros a la tabla Enfermedades
    public void llenadoTablaEnfermedades(String[][] enfermedades){
        long id = 0;

        for (String[] enferm: enfermedades) {
            if(!dbEnfermedades.existeEnfermedad(enferm[0])){
                String informacion = null;
                if(enferm[2] != null){
                    informacion = getResources().getString(Integer.parseInt(enferm[2]));
                }
                id = dbEnfermedades.insertaEnfermedad(enferm[0],enferm[1],informacion);
                if(id <= 0) {
                    Log.e(TAG, "llenadoTablaEnferParam: Enfermedad no registrado");
                }
            }
        }
    }
    //Método que agrega registros a la tabla EnfermedadesParametros
    public void llenadoTablaEnfermedadesParam(String[][] enfermedades, int idTipExam){
        long id = 0;
        String nombreExamen = "";
        if(idTipExam != 0) {
            nombreExamen = dbExamenTipo.obtenerNombreTipExam(idTipExam);
        }

        for (String [] enfermedad : enfermedades) {
            //Evalua si no ya hay registros con el idEnfermedad en la tabla EnfermedadesParametros
            if(!dbEnfermedadesParametros.existeRegistrosConIdEnfermedad(dbEnfermedades.obtenerIdEnfermedad(enfermedad[0]))) {
                int parametroId = 0, enfermedadId = 0;
                switch (nombreExamen) {
                    case "Funcion Renal":
                    case "Tiroides":
                    case "Examen de Orina":
                        for (int j = 3; j < enfermedad.length; j++) {
                            String[] infEnferm = enfermedad[j].split(",");
                            String parametro = infEnferm[0];
                            String infEnferParam = infEnferm[1];
                            parametroId = (int) dbExamenParametros.obtenerIdParametro(parametro);
                            enfermedadId = (int) dbEnfermedades.obtenerIdEnfermedad(enfermedad[0]);
                            id = dbEnfermedadesParametros.insertaEnfermParametro(parametroId, enfermedadId, infEnferParam);
                            if (id <= 0) {
                                Log.e(TAG, "llenadoTablaEnferParam: Datos no registrads");
                            }
                        }
                        break;
                    default:
                        for (int j = 3; j < enfermedad.length; j++) {
                            parametroId = (int) dbExamenParametros.obtenerIdParametro(enfermedad[j]);
                            enfermedadId = (int) dbEnfermedades.obtenerIdEnfermedad(enfermedad[0]);
                            id = dbEnfermedadesParametros.insertaEnfermParametro(parametroId, enfermedadId, null);
                            if (id <= 0) {
                                Log.e(TAG, "llenadoTablaEnferParam: Datos no registrads");
                            }
                        }
                }
            }
        }

    }
    //Método que agrega registros a la tabla EnfermedadesParametros
    public void llenadoTablaEnfermedadesParamOrina(String[][] enfermedades){
        long id = 0;

        for (String [] enfermedad : enfermedades) {
            //Evalua si no ya hay registros con el idEnfermedad en la tabla EnfermedadesParametros
            if(!dbEnfermedadesParametros.existeRegistrosConIdEnfermedad(dbEnfermedades.obtenerIdEnfermedad(enfermedad[0]))) {
                for (int j = 2; j < enfermedad.length; j++) {
                    int parametroId = 0, enfermedadId = 0;
                    parametroId = (int) dbExamenParametros.obtenerIdParametro(enfermedad[j]);
                    enfermedadId = (int) dbEnfermedades.obtenerIdEnfermedad(enfermedad[0]);
                    id = dbEnfermedadesParametros.insertaEnfermParametro(parametroId, enfermedadId, null);
                    if (id <= 0) {
                        Log.e(TAG, "llenadoTablaEnferParam: Datos no registrads");
                    }
                }
            }
        }

    }


    //Se ejcuta al presionar el boton Analizar
    public void analizarExamen(TextInputEditText[] textInputs, String[][] parametros, long idTipExam, long idUsuario){
        int identExamen = ((int) dbResultadosTabla.ultimoIdExamen((int)idTipExam, (int)idUsuario))+1;
        for(int a=0; a<textInputs.length; a++){
            String valorObten = null;
            if (!textInputs[a].getText().toString().trim().isEmpty()){
                valorObten = (textInputs[a].getText().toString());
            }
            llenarTablaResultados((int)idUsuario,
                    (int)idTipExam, (int)dbExamenParametros.obtenerIdParametro(parametros[a][0]), valorObten, identExamen);
        }
        Intent lanzar = new Intent(this, VentanaResultados.class);
        opcElegida.setExamenId(identExamen);
        opcElegida.setExamenTipId((int) idTipExam);
        opcElegida.setNombreExamen(dbExamenTipo.obtenerNombreTipExam((int) idTipExam));
        startActivity(lanzar);
        finish();

    }
    //Se ejcuta al presionar el boton Analizar del EGO
    public void analizarExamenOrina(AutoCompleteTextView[] autoCompleteTxt, Object[] parametros, long idTipExam, long idUsuario){
        int identExamen = ((int) dbResultadosTabla.ultimoIdExamen((int)idTipExam, (int)idUsuario))+1;
        for(int a=0; a<autoCompleteTxt.length; a++){
            Object[] elemento = (Object[]) parametros[a];
            llenarTablaResultados((int)idUsuario,
                    (int)idTipExam, (int)dbExamenParametros.obtenerIdParametro((String) elemento[0]),
                    (String) elemento[5], identExamen);
        }
        Intent lanzar = new Intent(this, VentanaResultados.class);
        opcElegida.setExamenId(identExamen);
        opcElegida.setExamenTipId((int) idTipExam);
        opcElegida.setNombreExamen(dbExamenTipo.obtenerNombreTipExam((int) idTipExam));
        startActivity(lanzar);
        finish();

    }
    //Método que agregará datos a la tabla Resultados
    public void llenarTablaResultados(int usuarioId, int examenTipId, int parameterId, String valor, int examenId){
        long id;

        //Todos los registros que tendrá nuestra tabla
        id = dbResultadosTabla.insertarResultado(usuarioId,examenTipId,parameterId,valor,examenId);

        //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
        if (id>0){
        }else{//si un id no es mayor que 0, quiere decir que hubo un error y no se pudo hacer ese registro
            Log.e(TAG, "Hubo un ERROR");
        }

    }



    //Método que verifica que todos los campos estén llenos
    public boolean camposLLenos(TextInputEditText[] txtInputs) {
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
    //Método que verifica que al menos 3 campos estén llenos
    public boolean minimo3CamposLLenos(View[] arregloViews) {
        boolean minimo3 = true;
        int contador = 0;
        //For each que recorre el arreglo con todos los EditText
        for (View componente : arregloViews) {
            if (componente instanceof TextInputEditText) {
                TextInputEditText editText = (TextInputEditText) componente;
                //Asigna lo que hay en el editText a una variable
                String texto = editText.getText().toString().trim();
                if (!texto.isEmpty()) {      //Evalúa si la variable no está vacía
                    contador += 1;
                }
            } else if (componente instanceof AutoCompleteTextView) {
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) componente;
                //Asigna lo que hay en el editText a una variable
                String texto = autoCompleteTextView.getText().toString().trim();
                if (!texto.isEmpty()) {      //Evalúa si la variable está vacía
                    contador += 1;
                }
            }
        }
        if(contador > 2){
            minimo3 = true;
        }else {
            minimo3 = false;
        }

        return minimo3;
    }

    //Método que arroja un mensaje de Error en la pantalla
    public void ventanaDialogo(String titulo, String texto){
        //Infla el diseño personalizado
        View dialogoPersonalizado = getLayoutInflater().inflate(R.layout.alert_dialog_datos_invalidos, null);

        //Crea el AertDialog
        AlertDialog dialogo = new AlertDialog.Builder(this).create(); //Crea un objeto de tipo AlertDialog
        dialogo.setView(dialogoPersonalizado);                               //Le asigna la ventana personalizada

        // Obtiene las vistas del diseño personalizado
        TextView tituloTextView = dialogoPersonalizado.findViewById(R.id.txtTituloDialogo);
        TextView mensajeTextView = dialogoPersonalizado.findViewById(R.id.txtMensajeDialogo);
        Button aceptarButton = dialogoPersonalizado.findViewById(R.id.btnAceptarDialogo);
        ImageView imagenDialogo = dialogoPersonalizado.findViewById(R.id.imgDialogo);

        //Configura la información a mostrar
        tituloTextView.setText(titulo);
        mensajeTextView.setText(texto);
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