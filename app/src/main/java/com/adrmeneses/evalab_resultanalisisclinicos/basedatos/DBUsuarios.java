package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    // Método para verificar si la tabla está vacía
    public boolean estaVaciaTablaUsuarios() {
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        if (db != null) {
            String query = "SELECT COUNT(*) FROM "+TABLE_USUARIOS;      // Ejecuta la consulta para contar la cantidad de filas en la tabla Usuarios
            Cursor cursor = db.rawQuery(query, null); // Crea objeto Cursor que ejecuta la consulta y apunta a los resultados
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

    //Método para obtener el Id del Usuario
    public long obtenerIdUsuario(){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        if(db != null){
            String query1 = "SELECT idUsuario FROM "+TABLE_USUARIOS+" ORDER BY idUsuario ASC LIMIT 1;";
            Cursor cursor = db.rawQuery(query1, null);
            if(cursor != null){
                cursor.moveToFirst();
                int datoCursor = cursor.getInt(0);
                return datoCursor;
            }
        }
        return 0;
    }

    public String[] obtenerInfUsuario(int idUsuario){
        String[] infUsuario;
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        if (db != null){
            String query = "SELECT * FROM "+TABLE_USUARIOS+" WHERE idUsuario = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

            if(cursor.moveToFirst()){
                infUsuario = new String[7];

                infUsuario[0] = String.valueOf(cursor.getInt(0));
                infUsuario[1] = cursor.getString(1);
                infUsuario[2] = cursor.getString(2);
                infUsuario[3] = cursor.getString(3);
                infUsuario[4] = cursor.getString(4);
                infUsuario[5] = String.valueOf(cursor.getDouble(5));
                infUsuario[6] = String.valueOf(cursor.getDouble(6));
            }else{
                infUsuario = new String[0];
            }
            cursor.close();
        }else {
            infUsuario = new String[0];
        }

        return infUsuario;
    }
}