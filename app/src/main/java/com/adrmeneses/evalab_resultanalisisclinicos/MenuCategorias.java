package com.adrmeneses.evalab_resultanalisisclinicos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesFunHepatica;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesFunRenal;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesInfecciones;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesOrina;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesSangre;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesTiroides;

public class MenuCategorias extends AppCompatActivity {
    
    private LinearLayout btnExamenSangre, btnExamenOrina, btnExamenFunHepatica, btnExamenTiroides, btnExamenFunRenal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_categorias);

        btnExamenSangre = findViewById(R.id.menuCategoriasBotonExamenSangre);
        btnExamenOrina = findViewById(R.id.menuCategoriasBotonExamenOrina);
        btnExamenFunHepatica = findViewById(R.id.menuCategoriasBotonExamenFunHepatica);
        btnExamenTiroides = findViewById(R.id.menuCategoriasBotonExamenTiroides);
        btnExamenFunRenal = findViewById(R.id.menuCategoriasBotonExamenFunRenal);

        btnExamenSangre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar = new Intent(MenuCategorias.this, ExamenesSangre.class);
                startActivity(lanzar);
            }
        });
        btnExamenOrina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar1 = new Intent(MenuCategorias.this, ExamenesOrina.class);
                startActivity(lanzar1);
            }
        });
        btnExamenFunHepatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar2 = new Intent(MenuCategorias.this, ExamenesFunHepatica.class);
                startActivity(lanzar2);
            }
        });
        btnExamenTiroides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar3 = new Intent(MenuCategorias.this, ExamenesTiroides.class);
                startActivity(lanzar3);
            }
        });
        btnExamenFunRenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar4 = new Intent(MenuCategorias.this, ExamenesFunRenal.class);
                startActivity(lanzar4);
            }
        });
        
    }

    /*public void viewExamInfecciosa(View view){
        Intent lanzar5 = new Intent(this, ExamenesInfecciones.class);
        startActivity(lanzar5);
    }*/

}