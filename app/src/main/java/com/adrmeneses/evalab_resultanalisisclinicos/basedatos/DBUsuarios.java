package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBUsuarios extends MyDBHelper{
    Context context;

    public DBUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta en la tabla Usuario
    public long insertaUsuario(String nombreUsuario, String apellidoUsuario, String sexoUsuario, Date fechaNacimiento, Double alturaUsuario, Double pesoUsuario){
        long id = 0;

        try{
            //Objeto de la clase MyDBHelper
            MyDBHelper myDBHelper = new MyDBHelper(context);       //Le pasa los datos de contexto
            SQLiteDatabase db = myDBHelper.getWritableDatabase();  //crea un objeto y le pasa un metodo para escribir en la bd

            //Crea un objeto en donde almacenará todos los tados a introducir en la tabla
            ContentValues datos = new ContentValues();
            //Le agregamos a la variable todos los datos que registraremos
            datos.put("nombre", nombreUsuario);                        //Se le agrega una llave y e valor
            datos.put("apellido", apellidoUsuario);                    //La llave es el nombre que tiene la columna en la bd
            datos.put("fechaNacimiento", fechaNacimiento.getTime());
            datos.put("sexo", sexoUsuario);
            datos.put("estatura", alturaUsuario);
            datos.put("peso", pesoUsuario);

            //Llama al método insert pasandole el nombre de la tabla y los datos
            id = db.insert(TABLE_USUARIOS, null, datos);//Retorna el id de la acción
        }catch (Exception ex){
            ex.toString();
        }
        //Retorna el id
        return id;
    }

}