package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBEnfermedadesParametros extends MyDBHelper{
    Context context;

    public DBEnfermedadesParametros(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta en la tabla EnferemedadesParametros
    public long insertaEnfermParametro(int idParametro, int idEnfermedad){
        long id = 0;

        try{
            //Objeto de la clase MYDBHelper
            MyDBHelper myDBhelper = new MyDBHelper(context);
            SQLiteDatabase db = myDBhelper.getWritableDatabase();

            ContentValues datos = new ContentValues();
            datos.put("idParametro", idParametro);
            datos.put("idEnfermedad", idEnfermedad);

            id = db.insert(TABLE_PARAMETROS_ENFERMEDADES, null, datos);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

}
