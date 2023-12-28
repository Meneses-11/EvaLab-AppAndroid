package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenGeneralOrina;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesFunHepatica;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesFunRenal;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesInfecciones;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesOrina;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesSangre;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.ExamenesTiroides;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.FuncionHepatica;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.FuncionTiroidea;
import com.adrmeneses.evalab_resultanalisisclinicos.examenes.HemogramaCompleto;

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
                Intent lanzar = new Intent(MenuCategorias.this, HemogramaCompleto.class);
                startActivity(lanzar);
            }
        });
        btnExamenOrina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar1 = new Intent(MenuCategorias.this, ExamenGeneralOrina.class);
                startActivity(lanzar1);
            }
        });
        btnExamenFunHepatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent lanzar2 = new Intent(MenuCategorias.this, ExamenesFunHepatica.class);
                startActivity(lanzar2);*/
                Intent lanzar2 = new Intent(MenuCategorias.this, FuncionHepatica.class);
                startActivity(lanzar2);
            }
        });
        btnExamenTiroides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent lanzar3 = new Intent(MenuCategorias.this, ExamenesTiroides.class);
                startActivity(lanzar3);*/
                Intent lanzar3 = new Intent(MenuCategorias.this, FuncionTiroidea.class);
                startActivity(lanzar3);
            }
        });
        btnExamenFunRenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent lanzar4 = new Intent(MenuCategorias.this, ExamenesFunRenal.class);
                startActivity(lanzar4);*/
            }
        });
        
    }

    //Método que agregará datos a la tabla Resultados
    public void llenarTablaResultados(DBResultadosTabla dbResultadosTabla, int usuarioId, int examenTipId, int parameterId, double valor, int examenId){
        long id;
        //int identExamen = ((int) dbResultadosTabla.ultimoIdExamen(examenId))+1;

        //Todos los registros que tendrá nuestra tabla
        id = dbResultadosTabla.insertarResultado(usuarioId,examenTipId,parameterId,valor,examenId);

        //Evalúa si todos los id son mayores que 0, es decir, que se hayan creado exitosamente
        if (id>0){
        }else{//si un id no es mayor que 0, quiere decir que hubo un error y no se pudo hacer ese registro
            Log.e(TAG, "Hubo un ERROR");
        }

    }

}