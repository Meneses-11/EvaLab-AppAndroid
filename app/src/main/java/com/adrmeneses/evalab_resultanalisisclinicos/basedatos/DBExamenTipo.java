package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    // Método para verificar si la tabla Examen Tipo está vacía
    public boolean estaVaciaTablaExamenTipo() {
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        if (db != null) {
            String query = "SELECT COUNT(*) FROM ExamenTipo";      // Ejecuta la consulta para contar la cantidad de filas en la tabla ExamenTipo
            Cursor cursor = db.rawQuery(query, null);   // Crea objeto Cursor que ejecuta la consulta y apunta a los resultados
            //Verifica si el cursor se creo correctamente
            if (cursor != null) {
                cursor.moveToFirst();
                int count = cursor.getInt(0);   //Obtiene el valor del primer campo en la fila actual, que es el resultado de la función de agregación COUNT(*)
                cursor.close();                   //Cierra el cursor
                return count == 0;                //Retorna true si la tabla está vacía, false si tiene al menos una fila
            }
        }
        return true;  // En caso de error o si no se pudo abrir la base de datos
    }

    //Método que obtiene el id del Tipo de Examen que es (Sangre)
    @SuppressLint("Range")
    public long obtenerIdTipExam(String nombreExamen){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        long idTipExam = -1; //Valor predeterminado si no encuentra el id
        //Realiza la consulta
        String query = "SELECT idTipExam FROM " + TABLE_TIPO_EXAMEN +
                " WHERE nombreExamenTipo = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombreExamen});
        //Verifica siencontró un resultado
        if(cursor.moveToFirst()){
            idTipExam = cursor.getLong(cursor.getColumnIndex("idTipExam"));
        }
        //Cierra el cursor
        cursor.close();
        return idTipExam;
    }

}
