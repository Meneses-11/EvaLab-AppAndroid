package com.adrmeneses.evalab_resultanalisisclinicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        //Instanciam de las clases para la BD
        myDBhelper = new MyDBHelper(MenuPrincipal.this);
        dbExamenTipo = new DBExamenTipo(MenuPrincipal.this);
        dbFile = getApplicationContext().getDatabasePath(MyDBHelper.DTABASE_NOMBRE);

        //Condicional que revisa si ya fue creada la bd
        if (dbFile.exists()){
            Toast.makeText(MenuPrincipal.this, "La BD ya existe", Toast.LENGTH_SHORT).show();
        }else {//Si aun no se ha creado, entra y la crea
            //Crea la BD
            SQLiteDatabase bd = myDBhelper.getWritableDatabase();
            //Si no hubo ningun error entra
            if (bd != null) {
                datosTablaExamenTipo(dbExamenTipo); //Manda a llamar al método que agregará datos a la primera tabla
                Toast.makeText(MenuPrincipal.this, "BASE DE DATOS CREADA EXITOSAMENTE", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MenuPrincipal.this, "HUBO UN ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Registros guardados exitosamente", Toast.LENGTH_SHORT).show();
        }else{//si un id no es mayor que 0, quiere decir que hubo un error y no se pudo hacer ese registro
            Toast.makeText(this, "Hubo un ERROR", Toast.LENGTH_SHORT).show();
        }

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