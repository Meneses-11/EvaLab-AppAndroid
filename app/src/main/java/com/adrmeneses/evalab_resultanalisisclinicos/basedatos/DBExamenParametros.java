package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

}
