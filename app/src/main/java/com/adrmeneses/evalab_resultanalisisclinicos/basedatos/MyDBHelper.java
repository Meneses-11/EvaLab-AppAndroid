package com.adrmeneses.evalab_resultanalisisclinicos.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DTABASE_NOMBRE = "EvaLabDB";
    public static final String TABLE_TIPO_EXAMEN = "ExamenTipo";

    //Constructor
    public MyDBHelper(@Nullable Context context) {
        super(context, DTABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_TIPO_EXAMEN+" (" +
                "idTipExam INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreExamenTipo VARCHAR NOT NULL)");
    }

    //Se ejecuta cunado cambie la versión de tu DB
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_TIPO_EXAMEN);
        onCreate(sqLiteDatabase);

    }
}
