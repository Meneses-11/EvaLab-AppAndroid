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

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;

public class ExamenesSangre extends AppCompatActivity {
    //Declara las variables para la clase DBHelper y para la Base de Datos
    MyDBHelper myDBHelper;
    SQLiteDatabase database;
    //Variable que contendrá el id del tipo de examen
    //long idTipExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examenes_sangre);

        //Instancía la clase MyDBHelper y la Base de Datos
        myDBHelper = new MyDBHelper(this);
        database = myDBHelper.getWritableDatabase();
    }

    public void viewHemogramaCompleto(View view){
        Intent lanzar = new Intent(this, HemogramaCompleto.class);
        startActivity(lanzar);
    }

    @SuppressLint("Range")
    public long obtenerIdTipExam(String nombreExamen){
        long idTipExam = -1; //Valor predeterminado si no encuentra el id

        //Realiza la consulta
        String query = "SELECT idTipExam FROM " + MyDBHelper.TABLE_TIPO_EXAMEN +
                " WHERE nombreExamenTipo = ?";
        Cursor cursor = database.rawQuery(query, new String[]{nombreExamen});

        //Verifica siencontró un resultado
        if(cursor.moveToFirst()){
            idTipExam = cursor.getLong(cursor.getColumnIndex("idTipExam"));
        }

        //Cierra el cursor
        cursor.close();

        return idTipExam;
    }

}