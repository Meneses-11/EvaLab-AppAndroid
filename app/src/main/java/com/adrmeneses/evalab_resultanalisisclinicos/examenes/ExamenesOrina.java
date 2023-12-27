package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;

public class ExamenesOrina extends AppCompatActivity {
    private TextView opcionEGO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examenes_orina);

        opcionEGO = findViewById(R.id.examOrinaOpcEGO);

        opcionEGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar = new Intent(ExamenesOrina.this, ExamenGeneralOrina.class);
                startActivity(lanzar);
            }
        });
    }

}