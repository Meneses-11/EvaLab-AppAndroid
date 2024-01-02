package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Enfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;

import java.util.ArrayList;

public class DBResultadosTabla extends MyDBHelper{
    Context context;

    public DBResultadosTabla(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Método para insertar un registro en la tabla ResultadosTabla
    public long insertarResultado(int idUser, int idTipExam, int idParameter, String value, int idExamen){
        long id = 0;

        try{
            MyDBHelper myDBHelper = new MyDBHelper(context);
            SQLiteDatabase db = myDBHelper.getWritableDatabase();

            ContentValues datos = new ContentValues();
            datos.put("idUsuario", idUser);
            datos.put("idTipExam", idTipExam);
            datos.put("idParametro", idParameter);
            datos.put("valorObtenido", value);
            datos.put("idExamen", idExamen);

            id = db.insert(TABLE_RESULTADOS, null, datos);

        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Resultados> leerResultados(int idTipExamen, int idExamen, int idUser){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();

        ArrayList<Resultados> listaResultados = new ArrayList<>();
        Resultados resultado = null;

        String query;
        Cursor cursor;

        switch (idTipExamen) {
            case 2:
                query = "SELECT ExamenParametros.nombreParametro, ResultadosTabla.valorObtenido " +
                        "FROM (ResultadosTabla NATURAL JOIN ExamenParametros) " +
                        "WHERE idTipExam = ? AND idExamen = ? AND idUsuario = ?";

                cursor = db.rawQuery(query, new String[]{String.valueOf(idTipExamen), String.valueOf(idExamen), String.valueOf(idUser)});

                if (cursor.moveToFirst()) {
                    do {
                        resultado = new Resultados();
                        resultado.setParametroNombre(cursor.getString(0));
                        resultado.setValorObtenido(cursor.getDouble(1));

                        listaResultados.add(resultado);
                    } while (cursor.moveToNext());
                }

                cursor.close();
                break;
            default:
                //String query = "SELECT * FROM "+TABLE_RESULTADOS+" WHERE idTipExam = ? AND idExamen = ?";
                query = "SELECT ExamenParametros.nombreParametro, ResultadosTabla.valorObtenido, ReferenciaValores.valorMin, " +
                        "ReferenciaValores.valorMax, ReferenciaValores.unidadMedida " +
                        "From ((ResultadosTabla NATURAL JOIN ExamenParametros) NATURAL JOIN ReferenciaValores) " +
                        "WHERE idTipExam = ? AND idExamen = ? AND idUsuario = ?";

                cursor = db.rawQuery(query, new String[]{String.valueOf(idTipExamen), String.valueOf(idExamen), String.valueOf(idUser)});

                if (cursor.moveToFirst()) {
                    do {
                        resultado = new Resultados();
                        resultado.setParametroNombre(cursor.getString(0));

                        // Verifica si el valor es nulo antes de intentar obtenerlo
                        if (!cursor.isNull(1)) {
                            resultado.setValorObtenido(cursor.getDouble(1));
                        } else {
                            resultado.setValorObtenido(null);
                        }

                        resultado.setMinValor(cursor.getString(2));
                        resultado.setMaxValor(cursor.getString(3));
                        resultado.setMedidaUnidad(cursor.getString(4));

                        listaResultados.add(resultado);
                    } while (cursor.moveToNext());
                }

                cursor.close();
        }

        return listaResultados;
    }

    public ArrayList<Enfermedades> leerEnfermedades(int idTipExamen, int idExamen, int idUser){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        String[][] datos;

        ArrayList<Enfermedades> listaEnfermedades = new ArrayList<>();
        Enfermedades enfermedad = null;

        String query = "SELECT enf.nombre, enf.referencia, enf.descripcion," +
                "GROUP_CONCAT(result.valorObtenido)," +
                "GROUP_CONCAT(valRef.valorMin)," +
                "GROUP_CONCAT(valRef.valorMax) " +
                "FROM ((("+TABLE_RESULTADOS+" result JOIN "+TABLE_VALORES_REFERENCIA+" valRef ON result.idParametro = valRef.idParametro) " +
                "JOIN "+TABLE_PARAMETROS_ENFERMEDADES+" enfPar ON result.idParametro = enfPar.idParametro) " +
                "JOIN "+TABLE_ENFERMEDADES+" enf ON enfPar.idEnfermedad = enf.idEnfermedad) " +
                "WHERE result.idTipExam = ? AND result.idExamen = ? AND result.idUsuario = ? " +
                "GROUP BY enf.idEnfermedad, enf.nombre, enf.referencia, enf.descripcion";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idTipExamen), String.valueOf(idExamen), String.valueOf(idUser)});

        if(cursor.moveToFirst()){
            do{
                enfermedad = new Enfermedades();
                enfermedad.setNombreEnf(cursor.getString(0));
                enfermedad.setReferencia(cursor.getString(1));
                enfermedad.setInformacion(cursor.getString(2));

                String[] valores = cursor.getString(3).split(",");
                String[] valMin = cursor.getString(4).split(",");
                String[] valMax = cursor.getString(5).split(",");
                datos = new String[valores.length][3];
                for(int i=0; i<valores.length; i++){
                    datos[i][0] = valores[i];
                    datos[i][1] = valMin[i];
                    datos[i][2] = valMax[i];
                }
                enfermedad.setValObtenidos(datos);

                listaEnfermedades.add(enfermedad);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaEnfermedades;
    }

    //Método que obtiene el ultimo idExamen del Tipo de Examen que recive
    public long ultimoIdExamen(int idTipExamen, int idUsuario){
        MyDBHelper myDBhelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        long idExamen = 0; //Valor predeterminado si no encuentra el id
        //Realiza la consulta
        String query = "SELECT idExamen FROM " + TABLE_RESULTADOS +
                " WHERE idTipExam = ? AND idUsuario = ? ORDER BY idExamen DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idTipExamen), String.valueOf(idUsuario)});
        //Verifica siencontró un resultado
        if(cursor.moveToFirst()){
            idExamen = cursor.getInt(0);
        }
        //Cierra el cursor
        cursor.close();
        return idExamen;
    }

    // Método para verificar si noy hay ningun registro del usuario
    public boolean noHayResultados(int userId) {
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        if (db != null) {
            String query = "SELECT COUNT(*) FROM "+TABLE_RESULTADOS+" WHERE idUsuario = ?";      // Ejecuta la consulta para contar la cantidad de filas en la tabla Resultados
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)}); // Crea objeto Cursor que ejecuta la consulta y apunta a los resultados
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

    public int[] obtenerTiposExamenes(int userId) {
        int[] listaId;
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();

        if (db != null) {
            String query = "SELECT DISTINCT idTipExam FROM " + TABLE_RESULTADOS + " WHERE idUsuario = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                int count = cursor.getCount();
                listaId = new int[count];
                int i = 0;
                do {
                    listaId[i] = cursor.getInt(0);
                    i++;
                } while (cursor.moveToNext());
            } else {
                // No hay resultados
                listaId = new int[0];
            }
            cursor.close();
        } else {
            // Error al abrir la base de datos
            listaId = new int[0];
        }
        return listaId;
    }

    public String[][] obtenerIdNombreTipoExamen(int[] idTipExam, int userId) {
        String[][] listaExamenes;
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();

        // Inicializa la lista global antes de comenzar el bucle
        ArrayList<String[]> listaResultados = new ArrayList<>();

        if (db != null) {
            for (int j = 0; j < idTipExam.length; j++) {
                String query = "SELECT DISTINCT ResultadosTabla.idTipExam,ExamenTipo.nombreExamenTipo,ResultadosTabla.idExamen FROM " + TABLE_RESULTADOS + " NATURAL JOIN " + TABLE_TIPO_EXAMEN + " WHERE idUsuario = ? AND idTipExam = ?";
                Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(idTipExam[j])});

                if (cursor.moveToFirst()) {
                    do {
                        // Agregamos cada resultado a la lista global
                        String[] resultado = new String[3];
                        resultado[0] = cursor.getString(0);
                        resultado[1] = cursor.getString(1);
                        resultado[2] = cursor.getString(2);
                        listaResultados.add(resultado);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            // Convertimos la lista global a una matriz antes de retornarla
            listaExamenes = new String[listaResultados.size()][3];
            listaExamenes = listaResultados.toArray(listaExamenes);
        } else {
            // Error al abrir la base de datos
            listaExamenes = new String[0][0];
        }
        return listaExamenes;
    }

}