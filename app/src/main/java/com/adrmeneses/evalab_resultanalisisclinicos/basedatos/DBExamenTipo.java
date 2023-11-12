package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBExamenTipo extends MyDBHelper{
    Context context;

    //Constructor
    public DBExamenTipo(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta registros en la tabla ExamenTipo
    public long insertaExamenTipo(String nombreExamen, String categoriaExamen){
        long id = 0;

        try {
            //Objeto de la clase MYDBHelper
            MyDBHelper myDBHelper = new MyDBHelper(context);             //Le pasa lo que hay en contexto
            SQLiteDatabase db = myDBHelper.getWritableDatabase();        //Crea un objeto y le asignamos el método de la clase MyDBHelper

            //Crea un objeto que puede almacenar varios valores
            ContentValues values = new ContentValues();
            values.put("nombreExamenTipo", nombreExamen);                //Almacena la llave y el dato
            values.put("categoriaExamenTipo", categoriaExamen);          //Almacena la llave y el dato
            //Llama al método insert de la clase MyDBHelper, le pasa el nombre de la tabla y los valores
            id = db.insert(TABLE_TIPO_EXAMEN, null, values);//Regresa un valor de tipo long (id de la acción)
        }catch (Exception ex){
            ex.toString();
        }
        //Retorna el id previamente almacenado
        return id;
    }

}
