package com.adrmeneses.evalab_resultanalisisclinicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
    }

    // Metodos que crean Intents para lanzar las vistas correspondeintes
    public void viewMenuAnaliClinic(View view){
        Intent lanzar = new Intent(this, MenuCategorias.class);
        startActivity(lanzar);
    }
    public void viewResultados(View view){
        Intent lanzar1 = new Intent(this, MenuOrganos.class);
        startActivity(lanzar1);
    }/*
    public void viewTiposAnalisis(View view){
        Intent lanzar2 = new Intent(this, TipAnalisisActivity.class);
        startActivity(lanzar2);
    }
    public void viewInformacion(View view){
        Intent lanzar3 = new Intent(this, InformacionActivity.class);
    }*/

}