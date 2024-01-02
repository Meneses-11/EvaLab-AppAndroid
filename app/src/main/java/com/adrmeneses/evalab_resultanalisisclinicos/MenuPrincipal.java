package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.usuarios.InformacionPerfil;

import java.io.File;

public class MenuPrincipal extends AppCompatActivity {
    UsuarioActivo userActv;
    //Declara los objetos de las clases que usaremos para la BD
    MyDBHelper myDBhelper;
    DBExamenTipo dbExamenTipo;
    File dbFile;
    //Declara la variable que almacenará el intent
    Intent activityFormPrin;
    private Button btnPerfil, btnInterpretar, btnResultados;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        userActv = UsuarioActivo.getInstance();

        btnInterpretar = findViewById(R.id.menuPrincipalBtnInterpretar);
        btnResultados = findViewById(R.id.menuPrincipalBtnResultados);
        btnPerfil = findViewById(R.id.menuPrincipalBtnPerfil);

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
            if(dbExamenTipo.estaVaciaTablaExamenTipo()){
                datosTablaExamenTipo(dbExamenTipo); //Manda a llamar al método que agregará datos a la tabla
                Log.d(TAG, "Tabla ExamenTipo llenado con éxito");
            }else {
                Log.d(TAG, "La tabla ExamenTipo está llena");
            }
        }

        btnInterpretar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar = new Intent(MenuPrincipal.this, MenuCategorias.class);
                startActivity(lanzar);
            }
        });
        btnResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar1 = new Intent(MenuPrincipal.this, VentanaResultados.class);
                startActivity(lanzar1);
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzarPerf = new Intent(MenuPrincipal.this, InformacionPerfil.class);
                startActivity(lanzarPerf);
            }
        });
    }

    //Método que agregará datos a la tabla ExamenTipo
    public void datosTablaExamenTipo(DBExamenTipo dbExamenTipo){
        long id, id1, id2, id3, id4;//, id5, id6, id7, id8, id9, id10, id11, id12, id13, id14, id15;

        //Todos los registros que tendrá nuestra tabla
        id = dbExamenTipo.insertaExamenTipo("hemograma", "Sangre");                    //Recibe como parámetro un objeto de tipo DBExamenTipo (clase donde se realiza el CRUD)
        id1 = dbExamenTipo.insertaExamenTipo("Examen de Orina", "Orina");              //Llama al método de la clase que se encarga de insertar un registro
        id2 = dbExamenTipo.insertaExamenTipo("Funcion Hepatica", "Funcion Hepática");  //Manda el/los dato/s que se insertará/n en el registro
        id3 = dbExamenTipo.insertaExamenTipo("Tiroides", "Tiroides");                  //Retorna un valor de tipo long con el id de la acción
        id4 = dbExamenTipo.insertaExamenTipo("Funcion Renal", "Funcion Renal");


        //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
        if (id>0 && id1>0 && id2>0 && id3>0 && id4>0 /*&& id5>0 && id6>0 && id7>0 && id8>0 && id9>0 && id10>0 && id11>0 && id12>0 && id13>0 && id14>0 && id15>0*/){
            Log.d(TAG, "Registros guardados exitosamente");
        }else{//si un id no es mayor que 0, quiere decir que hubo un error y no se pudo hacer ese registro
            Log.e(TAG, "Hubo un ERROR");
        }

    }
}