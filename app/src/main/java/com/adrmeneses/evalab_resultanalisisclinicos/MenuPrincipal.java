package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;

import java.io.File;

public class MenuPrincipal extends AppCompatActivity {
    //Declara los objetos de las clases que usaremos para la BD
    MyDBHelper myDBhelper;
    DBExamenTipo dbExamenTipo;
    File dbFile;
    //Declara la variable que almacenará el intent
    Intent activityFormPrin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        //Instancias de las clases para la BD
        myDBhelper = new MyDBHelper(MenuPrincipal.this);
        dbExamenTipo = new DBExamenTipo(MenuPrincipal.this);
        dbFile = getApplicationContext().getDatabasePath(MyDBHelper.DTABASE_NOMBRE);
        //Asigna la activity al intent
        activityFormPrin = new Intent(MenuPrincipal.this, FormularioPrincipal.class);

        //Condicional que revisa si ya fue creada la bd
        if (!dbFile.exists()){
            //Crea la BD
            SQLiteDatabase bd = myDBhelper.getWritableDatabase();
            //Si no hubo ningun error entra
            if (bd != null) {
                datosTablaExamenTipo(dbExamenTipo); //Manda a llamar al método que agregará datos a la tabla
                startActivity(activityFormPrin);
                finish();
                Log.d(TAG, "BD creada exitosamente");

            } else {
                Log.e(TAG, "Hubo un Error al crear la BD");
            }
        }else{
            Log.d(TAG, "La bd ya existe");
            //Evalua si está o no vacía la tabla ExamenTipo
            if(estaVaciaTablaExamenTipo()){
                datosTablaExamenTipo(dbExamenTipo); //Manda a llamar al método que agregará datos a la tabla
                Log.d(TAG, "Tabla ExamenTipo llenado con éxito");
            }else {
                Log.d(TAG, "La tabla ExamenTipo está llena");
            }
        }
    }

    // Metodos que crean Intents para lanzar las vistas correspondeintes
    public void viewMenuAnaliClinic(View view){
        Intent lanzar = new Intent(this, MenuCategorias.class);
        startActivity(lanzar);
    }
    public void viewResultados(View view){
        Intent lanzar1 = new Intent(this, MenuOrganos.class);
        startActivity(lanzar1);
    }

    //Método que agregará datos a la tabla ExamenTipo
    public void datosTablaExamenTipo(DBExamenTipo dbExamenTipo){
        long id, id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15;

        //Todos los registros que tendrá nuestra tabla
        id = dbExamenTipo.insertaExamenTipo("hemograma", "sangre");             //Recibe como parámetro un objeto de tipo DBExamenTipo (clase donde se realiza el CRUD)
        id1 = dbExamenTipo.insertaExamenTipo("perfil_lipidico", "sangre");      //Llama al método de la clase que se encarga de insertar un registro
        id2 = dbExamenTipo.insertaExamenTipo("glucosa", "sangre");              //Manda el/los dato/s que se insertará/n en el registro
        id3 = dbExamenTipo.insertaExamenTipo("electrolitos_sericos", "sangre"); //Retorna un valor de tipo long con el id de la acción
        id4 = dbExamenTipo.insertaExamenTipo("ego", "orina");
        id5 = dbExamenTipo.insertaExamenTipo("urocultivo", "orina");
        id6 = dbExamenTipo.insertaExamenTipo("alt", "funcion_hepatica");
        id7 = dbExamenTipo.insertaExamenTipo("ast", "funcion_hepatica");
        id8 = dbExamenTipo.insertaExamenTipo("bilirrubina", "funcion_hepatica");
        id9 = dbExamenTipo.insertaExamenTipo("tsh", "tiroides");
        id10 = dbExamenTipo.insertaExamenTipo("t4", "tiroides");
        id11 = dbExamenTipo.insertaExamenTipo("bun", "funcion_renal");
        id12 = dbExamenTipo.insertaExamenTipo("creatinina_serica", "funcion_renal");
        id13 = dbExamenTipo.insertaExamenTipo("vih", "infecciones");
        id14 = dbExamenTipo.insertaExamenTipo("hepatitis", "infecciones");
        id15 = dbExamenTipo.insertaExamenTipo("sifilis", "infecciones");

        //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
        if (id>0 && id1>0 && id2>0 && id3>0 && id4>0 && id5>0 && id6>0 && id7>0 && id8>0 && id9>0 && id10>0 && id11>0 && id12>0 && id13>0 && id14>0 && id15>0){
            Log.d(TAG, "Registros guardados exitosamente");
        }else{//si un id no es mayor que 0, quiere decir que hubo un error y no se pudo hacer ese registro
            Log.e(TAG, "Hubo un ERROR");
        }

    }

    // Método para verificar si la tabla Usuarios está vacía
    private boolean estaVaciaTablaExamenTipo() {
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

    /*
    public void viewTiposAnalisis(View view){
        Intent lanzar2 = new Intent(this, TipAnalisisActivity.class);
        startActivity(lanzar2);
    }
    public void viewInformacion(View view){
        Intent lanzar3 = new Intent(this, InformacionActivity.class);
    }
    */

}