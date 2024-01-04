package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    //Declara las constantes para el nombre, la versión y las tablas de la BD
    private static final int DATABASE_VERSION = 1;
    public static final String DTABASE_NOMBRE = "EvaLabDB";
    public static final String TABLE_TIPO_EXAMEN = "ExamenTipo", TABLE_PARAMETROS_EXAMEN = "ExamenParametros", TABLE_VALORES_REFERENCIA = "ReferenciaValores", TABLE_RESULTADOS = "ResultadosTabla", TABLE_ENFERMEDADES = "Enfermedades", TABLE_PARAMETROS_ENFERMEDADES = "EnfermedadesParametros", TABLE_USUARIOS = "Usuarios";

    //Constructor
    public MyDBHelper(@Nullable Context context) {
        super(context, DTABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Crea la tabla Usuario
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_USUARIOS+" (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR NOT NULL, " +
                "apellido VARCHAR NOT NULL, " +
                "fechaNacimiento DATE NOT NULL," +
                "sexo VARCHAR NOT NULL," +
                "estatura DOUBLE NOT NULL," +
                "peso DOUBLE NOT NULL)");

        //Crea la tabla ExamenTipo
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_TIPO_EXAMEN+" (" +
                "idTipExam INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreExamenTipo VARCHAR NOT NULL," +
                "categoriaExamenTipo VARCHAR NOT NULL)");

        //Crea la tabla ExamenParametros
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PARAMETROS_EXAMEN + " (" +
                "idParametro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idTipExam INTEGER," +
                "nombreParametro VARCHAR NOT NULL," +
                "FOREIGN KEY(idTipExam) REFERENCES " + TABLE_TIPO_EXAMEN + "(idTipExam))");

        //Crea la tabla ReferenciaValores
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_VALORES_REFERENCIA + " (" +
                "idRefVal INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idTipExam INTEGER," +
                "idParametro INTEGER," +
                "valorMin VARCHAR," +
                "valorMax VARCHAR," +
                "valorMinHombre VARCHAR," +
                "valorMaxHombre VARCHAR," +
                "valorMinMujer VARCHAR," +
                "valorMaxMujer VARCHAR," +
                "valorMinAdult VARCHAR," +
                "valorMaxAdult VARCHAR," +
                "valorMinCh VARCHAR," +
                "valorMaxCh VARCHAR," +
                "unidadMedida VARCHAR," +
                "FOREIGN KEY(idTipExam) REFERENCES " + TABLE_TIPO_EXAMEN + "(idTipExam)," +
                "FOREIGN KEY(idParametro) REFERENCES " + TABLE_PARAMETROS_EXAMEN + "(idParametro))");

        //Crea la tabla Resultados
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_RESULTADOS + " (" +
                "idResultado INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER," +
                "idTipExam INTEGER," +
                "idParametro INTEGER," +
                "valorObtenido VARCHAR," +
                "idExamen INTEGER NOT NULL," +
                "FOREIGN KEY(idUsuario) REFERENCES " + TABLE_USUARIOS + "(idUsuario)," +
                "FOREIGN KEY(idTipExam) REFERENCES " + TABLE_TIPO_EXAMEN + "(idTipExam)," +
                "FOREIGN KEY(idParametro) REFERENCES " + TABLE_PARAMETROS_EXAMEN + "(idParametro))");

        //Crea la tabla Enfermedades
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_ENFERMEDADES+" (" +
                "idEnfermedad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR NOT NULL," +
                "referencia VARCHAR," +
                "descripcion VARCHAR)");

        //Crea la tabla EnfermedadesParametros
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PARAMETROS_ENFERMEDADES+" (" +
                "idEnfPar INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idParametro INTEGER," +
                "idEnfermedad INTEGER," +
                "refParametroEnfermedad VARCHAR, " +
                "FOREIGN KEY(idParametro) REFERENCES "+TABLE_PARAMETROS_EXAMEN+" (idParametro)," +
                "FOREIGN KEY(idEnfermedad) REFERENCES "+TABLE_ENFERMEDADES+" (idEnfermedad))");
    }

    //Se ejecuta cuando cambie la versión de la BD
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Borra todas las tablas de la bd
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_TIPO_EXAMEN);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_PARAMETROS_EXAMEN);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_VALORES_REFERENCIA);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_RESULTADOS);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_ENFERMEDADES);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_PARAMETROS_ENFERMEDADES);

        //Manada a llamar al método onCreate pasandole el objeto SQLiteDatabase para que vuelva a crear todas las tablas
        onCreate(sqLiteDatabase);
    }

}
