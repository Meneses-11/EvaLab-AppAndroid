package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;

public class ExamenesSangre extends AppCompatActivity {
    //Declara las variables para la clase DBHelper y para la Base de Datos
    MyDBHelper myDBHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examenes_sangre);

        //Instancía la clase MyDBHelper y la Base de Datos
        myDBHelper = new MyDBHelper(this);
        database = myDBHelper.getWritableDatabase();
    }

    //Se activa al presionar el boton Hemograma Completo
    public void viewHemogramaCompleto(View view){
        Intent lanzar = new Intent(this, HemogramaCompleto.class);
        startActivity(lanzar);
    }


    //Método que agregará datos a la tabla Resultados
    public void llenarTablaResultados(DBResultadosTabla dbResultadosTabla, int usuarioId, int examenId, int parameterId, double valor){
        long id;

        //Todos los registros que tendrá nuestra tabla
        id = dbResultadosTabla.insertarResultado(usuarioId,examenId,parameterId,valor);

        //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
        if (id>0){
        }else{//si un id no es mayor que 0, quiere decir que hubo un error y no se pudo hacer ese registro
            Log.e(TAG, "Hubo un ERROR");
        }

    }

}