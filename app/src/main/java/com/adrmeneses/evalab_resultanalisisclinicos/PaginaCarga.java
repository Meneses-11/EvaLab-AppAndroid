package com.adrmeneses.evalab_resultanalisisclinicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;

import java.io.File;

public class PaginaCarga extends AppCompatActivity {
    File dbFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_carga);

        //Constante para la duración de la pantalla
        final int DURACION = 2500;

        dbFile = getApplicationContext().getDatabasePath(MyDBHelper.DTABASE_NOMBRE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(dbFile.exists()){
                    /*Intent intent = new Intent(PaginaCarga.this, MenuPrincipal.class);
                    startActivity(intent);
                    finish();*/
                    Intent intent = new Intent(PaginaCarga.this, FormularioPrincipal.class);
                    startActivity(intent);
                    finish();
                } else{
                    Intent intent = new Intent(PaginaCarga.this, MenuPrincipal.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, DURACION);

    }
}