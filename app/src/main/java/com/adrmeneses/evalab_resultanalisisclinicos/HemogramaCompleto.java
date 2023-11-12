package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;

public class HemogramaCompleto extends ExamenesSangre {
    final String NAME_EXAM = "hemograma";
    long idTipExam;
    //Declara la variable de la clase DBExamenParametros
    DBExamenParametros dbExamenParametros;
    //Arreglo que almacena todos los parámetros
    String[] parametros= {"Hemoglobina","Hematocrito","Eritrocitos","VMG","HCM","Reticulocitos","Leocucitos","Neutrofilos","Linfocitos","Eosinofilos","Basofilos","Monocitos","Plaquetas","Volumen Plaquetario Medio"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hemograma_completo);

        //Asigna la clase a la variable
        dbExamenParametros = new DBExamenParametros(this);

        idTipExam = obtenerIdTipExam(NAME_EXAM);
        Log.d(TAG, "El id es: "+idTipExam);

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


}