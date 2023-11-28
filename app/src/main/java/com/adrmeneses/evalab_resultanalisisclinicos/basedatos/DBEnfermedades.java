package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import org.checkerframework.checker.units.qual.C;

public class DBEnfermedades extends MyDBHelper{
    Context context;

    public DBEnfermedades(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta en la tabla Enferemedades
    public long insertaEnfermedad(String name){
        long id = 0;

        try{
            //Objeto de la clase MYDBHelper
            MyDBHelper myDBhelper = new MyDBHelper(context);
            SQLiteDatabase db = myDBhelper.getWritableDatabase();

            ContentValues datos = new ContentValues();
            datos.put("nombre", name);

            id = db.insert(TABLE_ENFERMEDADES, null, datos);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean existeEnfermedad(String nombre){
        int count = 0;
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE_ENFERMEDADES + " WHERE nombre = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombre});

        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }

        cursor.close();

        return count > 0;
    }

    //Método que obtiene el id de la enfermedad solicitada
    public long obtenerIdEnfermedad(String enfermedad){
        long enfermedadId = 0;
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        if(db != null){
            String query = "SELECT idEnfermedad FROM "+TABLE_ENFERMEDADES+" WHERE nombre = ?";
            Cursor cursor = db.rawQuery(query, new String[]{enfermedad});
            if (cursor != null){
                cursor.moveToFirst();
                enfermedadId = cursor.getInt(0);
            }
        }

        return enfermedadId;
    }

}