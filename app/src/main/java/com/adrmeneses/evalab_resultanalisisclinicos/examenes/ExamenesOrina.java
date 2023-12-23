package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adrmeneses.evalab_resultanalisisclinicos.R;

public class ExamenesOrina extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examenes_orina);
    }

    public void viewExamenGeneralOrina(View view){
        Intent lanzar = new Intent(this, ExamenGeneralOrina.class);
        startActivity(lanzar);
    }

}