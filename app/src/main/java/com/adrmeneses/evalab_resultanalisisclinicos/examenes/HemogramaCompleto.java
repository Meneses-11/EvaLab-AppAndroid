package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

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

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.VentanaResultados;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBEnfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBEnfermedadesParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBReferenciaValores;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

public class HemogramaCompleto extends MenuCategorias {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara los componentes visuales
    TextInputEditText txtHemoglobina, txtHematocrito, txtEritrocitos, txtVMG, txtHCM, txtReticulocitos, txtLeucocitos, txtNeutrofilos, txtLinfocitos, txtEosinofilos, txtBasofilos, txtMonocitos, txtPlaquetas, txtVolPlaquetario;
    final String NAME_EXAM = "hemograma";
    long idTipExam, idUsuario;
    //Arreglo que almacena todos los parámetros
    String[][] parametro2 = {{"Hemoglobina","12","18",null,null,null,null,null,null,null,null,"g/dL",""+R.string.strInfHCHemoglobina},{"Hematocrito","36","52",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCHematocrito},{"Eritrocitos", "4.5","6.5",null,null,null,null,null,null,null,null,"x10^6/uL",""+R.string.strInfHCEritrocitos},{"VMG", "80","100",null,null,null,null,null,null,null,null,"fL",""+R.string.strInfHCVGM},{"HCM", "27","33",null,null,null,null,null,null,null,null,"pg",""+R.string.strInfHCHCM},{"Reticulocitos", "0.5","1.5",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCReticulocitos},{"Leocucitos", "4","11",null,null,null,null,null,null,null,null,"x10^3/uL",""+R.string.strInfHCLeucocitos}, {"Neutrofilos", "40","75",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCNeutrofilo}, {"Linfocitos", "20","45",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCLinfocitos},{"Eosinofilos", "0","6",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCEosinofilos}, {"Basofilos", "0","2",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCBasofilos}, {"Monocitos", "2","10",null,null,null,null,null,null,null,null,"%",""+R.string.strInfHCMonocitos}, {"Plaquetas", "150","450",null,null,null,null,null,null,null,null,"x10^3/uL",""+R.string.strInfHCPlaquetas},{"Volumen Plaquetario Medio", "7.2","11.1",null,null,null,null,null,null,null,null,"fL",""+R.string.strInfHCVPM}};
    String[][] enfermedades = {{"Anemia","bajo","Hemoglobina","Eritrocitos"},{"Infeccion Bacteriana","alto","Leocucitos","Neutrofilos"},{"Infeccion Viral","alto","Leocucitos","Linfocitos"},{"Desordenes de Coagulacion","bajo","Plaquetas"},{"Problemas Renales","ambos","Hematocrito"},{ "Desordenes de la Medula Osea","ambos","Hemoglobina","Leocucitos","Plaquetas"},{ "Reacciones Alergicas o Asma","alto","Eosinofilos"},{"Infecciones Parasitarias","alto","Eosinofilos"}};
    //Arreglo que contiene todos los editText
    TextInputEditText[] textInputs = { txtHemoglobina, txtHematocrito, txtEritrocitos, txtVMG, txtHCM, txtReticulocitos, txtLeucocitos, txtNeutrofilos, txtLinfocitos, txtEosinofilos, txtBasofilos, txtMonocitos, txtPlaquetas, txtVolPlaquetario };
    //Arreglo que contiene el id de todos los componentes
    int[] componentes = {R.id.hemogramatxtFieldHemoglobina, R.id.hemogramatxtFieldHematocrito, R.id.hemogramatxtFieldEritrocito, R.id.hemogramatxtFieldVMG, R.id.hemogramatxtFieldHCM, R.id.hemogramatxtFieldReticulocitos, R.id.hemogramatxtFieldLeucocito, R.id.hemogramatxtFieldNeutrofilo, R.id.hemogramatxtFieldLinfocito, R.id.hemogramatxtFieldEosinofilos, R.id.hemogramatxtFieldBasofilos, R.id.hemogramatxtFieldMonocitos, R.id.hemogramatxtFieldPlaquetas, R.id.hemogramatxtFieldVolPlaquetario };
    private Button btnAnalizarHemograma;

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
        btnAnalizarHemograma = findViewById(R.id.hemogramaBotonAnalizar);

        //Almacena el id del TipoExamen en este caso, de Sangre
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id

        if (dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Ya fueron creados los registros en ExamenParametros de Hemograma");
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Ya fueron creados los registros en Valores Referencia de Hemograma");
            }else {
                llenadoTablaReferenciaValores(parametro2, idTipExam);
            }
        }else{
            llenadoTablaExamenParametro(parametro2, idTipExam);
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Ya fueron creados los registros en Valores Referencia de Hemograma");
            }else {
                llenadoTablaReferenciaValores(parametro2, idTipExam);
            }
        }

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades, (int)idTipExam);

        btnAnalizarHemograma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minimo3CamposLLenos(textInputs)) {
                    analizarExamen(textInputs, parametro2, idTipExam, idUsuario);
                }else{
                    ventanaDialogo("Campos Vacíos", "Llene por lo menos 3 campos");
                }
            }
        });

    }

}