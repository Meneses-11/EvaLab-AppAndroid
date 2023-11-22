package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBReferenciaValores extends MyDBHelper{
    Context context;


    public DBReferenciaValores(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta registros en la tabla ReferenciaValores
    public long insertaReferenciaValores(int tipExamId, int parametroId, String valorMin, String valorMax, String valorMinHombre, String valorMaxHombre, String valorMinMujer, String valorMaxMujer, String valorMinAdult, String valorMaxAdult, String valorMinCh, String valorMaxCh, String unidadMedida){
        long id=0;

        try{
            //Objeto de la clase MYDBHelper
            MyDBHelper myDBHelper = new MyDBHelper(context);             //Le pasa lo que hay en contexto
            SQLiteDatabase db = myDBHelper.getWritableDatabase();        //Crea un objeto y le asignamos el método de la clase MyDBHelper

            //Crea un objeto que puede almacenar varios valores
            ContentValues values = new ContentValues();
            values.put("idTipExam", tipExamId);              //Almacena la llave y el dato
            values.put("idParametro", parametroId);          //Almacena la llave y el dato
            values.put("valorMin", valorMin);
            values.put("valorMax", valorMax);
            values.put("valorMinHombre", valorMinHombre);
            values.put("valorMaxHombre", valorMaxHombre);
            values.put("valorMinMujer", valorMinMujer);
            values.put("valorMaxMujer", valorMaxMujer);
            values.put("valorMinAdult", valorMinAdult);
            values.put("valorMaxAdult", valorMaxAdult);
            values.put("valorMinCh", valorMinCh);
            values.put("valorMaxCh", valorMaxCh);
            values.put("unidadMedida", unidadMedida);
            //Llama al método insert de la clase MyDBHelper, le pasa el nombre de la tabla y los valores
            id = db.insert(TABLE_VALORES_REFERENCIA, null, values);//Regresa un valor de tipo long (id de la acción)
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    // Método para verificar si existen registros con un idTipExam específico
    public boolean existeConIdTipExam(long idTipExam) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();  //Instancía la bd
        //Declara la consulta
        String query = "SELECT COUNT(*) FROM " + TABLE_VALORES_REFERENCIA +
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

}
