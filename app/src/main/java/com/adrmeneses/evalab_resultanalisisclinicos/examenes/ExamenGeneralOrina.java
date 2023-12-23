package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.MyDBHelper;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.io.File;
import java.sql.SQLData;

public class ExamenGeneralOrina extends AppCompatActivity {

    private String[] colorOrina = {"Amarillo (todos)","Rojo", "Marrón","Negro"}, olorOrina = {"Lig. Ácido","Lig. Amoniaco", "Acetona/Fruta", "Fetido (Mal)"}, aspectoOrina = {"Claro", "Lig. Turbio", "Turbio"}, leucocitosOrina = {"0","1-6","7-20","más de 20"}, PosNegOrina = {"Positivo","Negativo"};
    AutoCompleteTextView listaColorOrina, listaOlorOrina, listaAspectoOrina, listaLeucocitosOrina, listaCelulasOrina, listaCilindrosOrina, listaProteinasOrina, listaHemoglobinaOrina, listaGlucosaOrina, listaCetonaOrina, listaBilirrubinaOrina, listaNitritosOrina;
    ArrayAdapter<String> adaptadorColor, adaptadorOlor, adaptadorAspecto, adaptadorLeucocitos, adaptadorPosNeg;
    EditText textFieldColorOrina;
    Button btnAnalizar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examen_general_orina);

        btnAnalizar = findViewById(R.id.btnEGOAnalizar);

        opcionesListas(listaOlorOrina, adaptadorOlor, R.id.txtFieldOlorOrina, olorOrina);
        opcionesListas(listaColorOrina, adaptadorColor, R.id.txtFieldColorOrina, colorOrina);
        opcionesListas(listaAspectoOrina, adaptadorAspecto, R.id.txtFieldAspectoOrina, aspectoOrina);
        opcionesListas(listaLeucocitosOrina, adaptadorLeucocitos, R.id.txtFieldLeucocitosOrina, leucocitosOrina);
        opcionesListas(listaCelulasOrina, adaptadorPosNeg, R.id.txtFieldCelulasOrina, PosNegOrina);
        opcionesListas(listaCilindrosOrina, adaptadorPosNeg, R.id.txtFieldCilindrosOrina, PosNegOrina);
        opcionesListas(listaProteinasOrina, adaptadorPosNeg, R.id.txtFieldProteinasOrina, PosNegOrina);
        opcionesListas(listaHemoglobinaOrina, adaptadorPosNeg, R.id.txtFieldHemoglobinaOrina, PosNegOrina);
        opcionesListas(listaGlucosaOrina, adaptadorPosNeg, R.id.txtFieldGlucosaOrina, PosNegOrina);
        opcionesListas(listaCetonaOrina, adaptadorPosNeg, R.id.txtFieldCetonaOrina, PosNegOrina);
        opcionesListas(listaBilirrubinaOrina, adaptadorPosNeg, R.id.txtFieldBilirrubinaOrina, PosNegOrina);
        opcionesListas(listaNitritosOrina, adaptadorPosNeg, R.id.txtFieldNitritosOrina, PosNegOrina);

        btnAnalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void opcionesListas(AutoCompleteTextView lista, ArrayAdapter<String> adaptador, int id, String[] arreglo){
        lista = findViewById(id);
        lista.setDropDownBackgroundResource(R.color.white);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arreglo);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Select: "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }

}