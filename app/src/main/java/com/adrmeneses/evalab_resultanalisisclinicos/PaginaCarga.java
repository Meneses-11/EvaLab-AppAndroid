package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;

import java.io.File;

public class PaginaCarga extends AppCompatActivity {
    //Declara los objetos de las clases que se usaran para la BD
    MyDBHelper myDBhelper;
    DBExamenTipo dbExamenTipo;
    File dbFile;
    //Declara las variables de los Intents
    Intent activityMenuPrin, activityFormuPrin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_carga);

        //Constante para la duración de la pantalla
        final int DURACION = 2500;

        //Instancias de las clases para la BD
        myDBhelper = new MyDBHelper(PaginaCarga.this);
        dbExamenTipo = new DBExamenTipo(PaginaCarga.this);
        dbFile = getApplicationContext().getDatabasePath(MyDBHelper.DTABASE_NOMBRE);

        //Asigna la activity a la variable correspondiente
        activityFormuPrin = new Intent(PaginaCarga.this, FormularioPrincipal.class);
        activityMenuPrin = new Intent(PaginaCarga.this, MenuPrincipal.class);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Evalúa si existe la bd
                if(dbFile.exists()){//Si existe manda a la activity MenuPrincipal
                    if(estaVaciaTablaUsuarios()){
                        startActivity(activityFormuPrin);
                        finish();
                    }else {
                        startActivity(activityMenuPrin);
                        finish();
                    }
                } else{             //Si no existe la crea y manda al formulario principal
                    //Crea la base de datos
                    SQLiteDatabase bd = myDBhelper.getWritableDatabase();
                    //Si no hubo ningun error entra
                    if (bd == null) {
                        Toast.makeText(PaginaCarga.this, "Hubo un ERROR al crear la Base de Datos", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "PaginaCarga: BD Creada exitosamente");
                    }
                    startActivity(activityFormuPrin);
                    finish();
                }

            }
        }, DURACION);

    }

    // Método para verificar si la tabla Usuarios está vacía
    private boolean estaVaciaTablaUsuarios() {
        SQLiteDatabase db = myDBhelper.getReadableDatabase();
        if (db != null) {
            String query = "SELECT COUNT(*) FROM Usuarios";      // Ejecuta la consulta para contar la cantidad de filas en la tabla Usuarios
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
}