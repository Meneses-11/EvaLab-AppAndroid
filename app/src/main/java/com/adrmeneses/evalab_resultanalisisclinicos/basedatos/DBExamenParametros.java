package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import androidx.annotation.Nullable;


import java.util.Date;

public class DBExamenParametros extends MyDBHelper{
    Context context;
    public DBExamenParametros(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta en la tabla ExamenParametros
    public long insertaParametro(int idTipoExamen, String nameParametro){
        long id = 0;

        try{
            //Objeto de la clase MyDBHelper
            MyDBHelper myDBHelper = new MyDBHelper(context);       //Le pasa los datos de contexto
            SQLiteDatabase db = myDBHelper.getWritableDatabase();  //crea un objeto y le pasa un metodo para escribir en la bd

            //Crea un objeto en donde almacenará todos los tados a introducir en la tabla
            ContentValues datos = new ContentValues();
            //Le agregamos a la variable todos los datos que registraremos
            datos.put("idTipExam", idTipoExamen);                  //Se le agrega una llave y e valor
            datos.put("nombreParametro", nameParametro);           //La llave es el nombre que tiene la columna en la bd

            //Llama al método insert pasandole el nombre de la tabla y los datos
            id = db.insert(TABLE_PARAMETROS_EXAMEN, null, datos);//Retorna el id de la acción
        }catch (Exception ex){
            ex.toString();
        }
        //Retorna el id
        return id;
    }

    // Método para verificar si existen registros con un idTipExam específico
    public boolean existeConIdTipExam(long idTipExam) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();  //Instancía la bd
        //Declara la consulta
        String query = "SELECT COUNT(*) FROM " + TABLE_PARAMETROS_EXAMEN +
                " WHERE idTipExam = ?";
        //Ejecuta la consulta y le pasa el idTipExam
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idTipExam)});

        //Evalua si el cursor se creó bien
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        //Cierra el cursor
        cursor.close();

        return count > 0; //Retorna true si el contador tiene de 1 registro en adelante y falso si no tiene ni siquiera 1
    }

    //Método que obtiene el id del parametro solicitado
    public long obtenerIdParametro(String parametro){
        long parametroId = 0;
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        if(db != null){
            String query = "SELECT idParametro FROM "+TABLE_PARAMETROS_EXAMEN+" WHERE nombreParametro = ?";
            Cursor cursor = db.rawQuery(query, new String[]{parametro});
            if (cursor != null){
                cursor.moveToFirst();
                parametroId = cursor.getInt(0);
            }
        }

        return parametroId;
    }

}
