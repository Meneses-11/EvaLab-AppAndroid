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
    public void viewExamOrina(View view){
        Intent lanzar1 = new Intent(this, ExamenesOrina.class);
        startActivity(lanzar1);
    }
    public void viewExamFunHepatica(View view){
        Intent lanzar2 = new Intent(this, ExamenesFunHepatica.class);
        startActivity(lanzar2);
    }
    public void viewExamTiroides(View view){
        Intent lanzar3 = new Intent(this, ExamenesTiroides.class);
        startActivity(lanzar3);
    }
    public void viewExamFunRenal(View view){
        Intent lanzar4 = new Intent(this, ExamenesFunRenal.class);
        startActivity(lanzar4);
    }
    public void viewExamInfecciosa(View view){
        Intent lanzar5 = new Intent(this, ExamenesInfecciones.class);
        startActivity(lanzar5);
    }

}