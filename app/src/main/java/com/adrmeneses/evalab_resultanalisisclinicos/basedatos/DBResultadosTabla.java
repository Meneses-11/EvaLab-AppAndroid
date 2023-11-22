package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;

import java.util.ArrayList;

public class DBResultadosTabla extends MyDBHelper{
    Context context;

    public DBResultadosTabla(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método para insertar un registro en la tabla ResultadosTabla
    public long insertarResultado(int idUser, int idTipExam, int idParameter, double value, int idExamen){
        long id = 0;

        try{
            MyDBHelper myDBHelper = new MyDBHelper(context);
            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            ContentValues datos = new ContentValues();
            datos.put("idUsuario", idUser);
            datos.put("idTipExam", idTipExam);
            datos.put("idParametro", idParameter);
            datos.put("valorObtenido", value);
            datos.put("idExamen", idExamen);

            id = db.insert(TABLE_RESULTADOS, null, datos);

        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Resultados> leerResultados(int idExamen){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();

        ArrayList<Resultados> listaResultados = new ArrayList<>();
        Resultados resultado = null;

        String query = "SELECT * FROM "+TABLE_RESULTADOS+" WHERE idTipExam = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idExamen)});

        if(cursor.moveToFirst()){
            do{
                resultado = new Resultados();
                resultado.setIdResultado(cursor.getInt(0));
                resultado.setIdUsuario(cursor.getInt(1));
                resultado.setIdTipExamen(cursor.getInt(2));
                resultado.setIdParametro(cursor.getInt(3));
                resultado.setValorObtenido(cursor.getDouble(4));

                listaResultados.add(resultado);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return listaResultados;
    }

    //Método que obtiene el ultimo idExamen del Tipo de Examen que recive
    public long ultimoIdExamen(int idTipExamen){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        long idExamen = 0; //Valor predeterminado si no encuentra el id
        //Realiza la consulta
        String query = "SELECT idExamen FROM " + TABLE_RESULTADOS +
                " WHERE idTipExam = ? ORDER BY idExamen DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idTipExamen)});
        //Verifica siencontró un resultado
        if(cursor.moveToFirst()){
            idExamen = cursor.getInt(0);
        }
        //Cierra el cursor
        cursor.close();
        return idExamen;
    }
}
