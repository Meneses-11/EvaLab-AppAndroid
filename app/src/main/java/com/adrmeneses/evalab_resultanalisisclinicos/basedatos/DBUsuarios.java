package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;

import java.util.ArrayList;
import java.util.Date;

public class DBUsuarios extends MyDBHelper{
    Context context;

    public DBUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método que inserta en la tabla Usuario
    public long insertaUsuario(String nombreUsuario, String apellidoUsuario, String sexoUsuario, Date fechaNacimiento, Double alturaUsuario, Double pesoUsuario, Boolean embarazadaUsuario){
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
            datos.put("embarazada", embarazadaUsuario);

            //Llama al método insert pasandole el nombre de la tabla y los datos
            id = db.insert(TABLE_USUARIOS, null, datos);//Retorna el id de la acción
        }catch (Exception ex){
            ex.toString();
        }
        //Retorna el id
        return id;
    }

    //Método que edita en la tabla Usuario
    public boolean editaUsuario(int id, String nombreUsuario, String apellidoUsuario, String sexoUsuario, Date fechaNacimiento, Double alturaUsuario, Double pesoUsuario){
        boolean correcto = false;

        //Objeto de la clase MyDBHelper
        MyDBHelper myDBHelper = new MyDBHelper(context);       //Le pasa los datos de contexto
        SQLiteDatabase db = myDBHelper.getWritableDatabase();  //crea un objeto y le pasa un metodo para escribir en la bd

        try{
            db.execSQL("UPDATE "+TABLE_USUARIOS+" SET nombre = '"+nombreUsuario+"', apellido = '"+apellidoUsuario+"', fechaNacimiento = '"+fechaNacimiento.getTime()+"', sexo = '"+sexoUsuario+"', estatura = '"+alturaUsuario+"', peso = '"+pesoUsuario+"' WHERE idUsuario='"+id+"'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
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

    public Usuarios verUsuario(int idUsuario){
        Usuarios usuario = null;
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        if (db != null){
            String query = "SELECT * FROM "+TABLE_USUARIOS+" WHERE idUsuario = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

            if(cursor.moveToFirst()){
                usuario = new Usuarios();

                usuario.setId(cursor.getInt(0));
                usuario.setNombre(cursor.getString(1));
                usuario.setApellido(cursor.getString(2));
                usuario.setFecha(cursor.getString(3));
                usuario.setSexo(cursor.getString(4));
                usuario.setAltura(cursor.getDouble(5));
                usuario.setPeso(cursor.getDouble(6));
            }
            cursor.close();
        }

        return usuario;
    }

    public ArrayList<Usuarios> leerUsuarios() {
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();

        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        Usuarios usuarios = null;

        if(db != null) {
            String query = "SELECT * FROM " + TABLE_USUARIOS;
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                do {
                    usuarios = new Usuarios();
                    usuarios.setId(cursor.getInt(0));
                    usuarios.setNombre(cursor.getString(1));
                    usuarios.setApellido(cursor.getString(2));
                    usuarios.setFecha(cursor.getString(3));
                    if("Masculino".equals(cursor.getString(4))){
                        usuarios.setIdImg(R.drawable.usr_hombre);
                    }else {
                        usuarios.setIdImg(R.drawable.usr_mujer);
                    }
                    listaUsuarios.add(usuarios);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return listaUsuarios;
    }


}