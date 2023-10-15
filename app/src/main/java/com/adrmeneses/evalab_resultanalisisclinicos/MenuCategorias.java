package com.adrmeneses.evalab_resultanalisisclinicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuCategorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_categorias);
    }

    // Metodos que crean Intents para lanzar las vistas correspondeintes
    public void viewExamSangre(View view){
        Intent lanzar = new Intent(this, ExamenesSangre.class);
        startActivity(lanzar);
    }

}