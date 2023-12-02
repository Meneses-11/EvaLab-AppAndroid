package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;

import java.io.File;

public class PaginaCarga extends AppCompatActivity {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara los objetos de las clases que se usaran para la BD
    MyDBHelper myDBhelper;
    DBExamenTipo dbExamenTipo;
    DBUsuarios dbUsuarios;
    File dbFile;
    //Declara las variables de los Intents
    Intent activityMenuPrin, activityFormuPrin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_carga);

        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();

        //Constante para la duración de la pantalla
        final int DURACION = 2500;

        //Instancias de las clases para la BD
        myDBhelper = new MyDBHelper(PaginaCarga.this);
        dbExamenTipo = new DBExamenTipo(PaginaCarga.this);
        dbUsuarios = new DBUsuarios(PaginaCarga.this);
        dbFile = getApplicationContext().getDatabasePath(MyDBHelper.DTABASE_NOMBRE);

        //Asigna la activity a la variable correspondiente
        activityFormuPrin = new Intent(PaginaCarga.this, FormularioPrincipal.class);
        activityMenuPrin = new Intent(PaginaCarga.this, MenuPrincipal.class);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Evalúa si existe la bd
                if(dbFile.exists()){//Si existe manda a la activity MenuPrincipal
                    if(dbUsuarios.estaVaciaTablaUsuarios()){
                        startActivity(activityFormuPrin);
                        finish();
                    }else {
                        if(userActv.getIdUsuario() == 0){userActv.setIdUsuario(dbUsuarios.obtenerIdUsuario());}
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

}