package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExamenGeneralOrina extends MenuCategorias {

    /*Map<String, Integer> valoresOpcionesColor = Map.ofEntries(
            Map.entry("Amarillo (todos)", 1),
            Map.entry("Rojo", 2),
            // Agrega más opciones y valores según sea necesario
            Map.entry("Negro", 3)
    );*/
    //HashMap<String, Integer> valoresOpcionesColor = {new Object[]{"Amarillo (todos)",1},  new Object[]{"Rojo", 2}};
    private String[][] arregloColorOrina = {{"Amarillo (todos)","Rojo", "Marrón","Negro"},{"1","2","2","3"}}, arreglloOlorOrina = {{"Lig. Ácido","Lig. Amoniaco", "Acetona/Fruta", "Fetido (Mal)"},{"1","2","2","3"}}, arregloAspectoOrina = {{"Claro", "Lig. Turbio", "Turbio"},{"1","2","3"}}, arregloPhOrina = {{"0 - 5.4","5.5 - 7","Más de 7"},{"2","1","3"}}, arregloDensidadOrina = {{"0", "0.001-1.007","1.008-1.032","1.033-1.055","Más de 1.055"},{"3","2","1","2","3"}}, arregloPosNegOrina = {{"Positivo","Negativo"},{"3","1"}}, arregloLeucocitosOrina = {{"0","1 - 6","7 - 20","más de 20"},{"1","1","2","3"}}, arregloEritrocitosOrina = {{"0","1 - 5","6 - 10","Más de 10"},{"1","1","2","3"}};
    String[][] valoresOpciones = {{"Amarillo (todos)","Rojo", "Marrón","Negro"},{"Lig. Ácido","Lig. Amoniaco", "Acetona/Fruta", "Fetido (Mal)"},{"Claro", "Lig. Turbio", "Turbio"},{"0 - 5.4","5.5 - 7","Más de 7"},{"0", "0.001-1.007","1.008-1.032","1.033-1.055","Más de 1.055"},{"Positivo","Negativo"},{"0","1 - 6","7 - 20","más de 20"},{"0","1 - 5","6 - 10","Más de 10"}};
    AutoCompleteTextView listaColorOrina, listaOlorOrina, listaAspectoOrina, listaPHOrina, listaDensidadOrina, listaProteinasOrina, listaGlucosaOrina, listaCetonaOrina, listaBilirrubinaOrina, listaHemoglobinaOrina, listaNitritosOrina, listaLeucocitosOrina, listaEritrocitosOrina, listaCelulasOrina, listaCilindrosOrina, listaBacteriasOrina, listaFilamentosOrina;
    ArrayAdapter<String> adaptadorColor, adaptadorOlor, adaptadorAspecto, adaptadorPh, adaptadorDensidad, adaptadorLeucocitos, adaptadorEritrocitos, adaptadorPosNeg;

    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara la variable en la que se instanciara la clase Opcion Elejida
    OpcionElegida opcElegida;
    final String NAME_EXAM = "EGO";
    long idTipExam, idUsuario;
    //Arreglo que almacena todos los parámetros
    Object[] parametros = {new Object[] {"Color",R.id.examGOAutoCompleteViewColorOrina,adaptadorColor,arregloColorOrina[0],arregloColorOrina[1],null,null,null,null,null,"g/dL"}, new Object[] {"Olor",R.id.examGOAutoCompleteViewOlorOrina,adaptadorOlor,arreglloOlorOrina[0],arreglloOlorOrina[1],null,null,null,null,null,"%"}, new Object[] {"Aspecto", R.id.examGOAutoCompleteViewAspectoOrina,adaptadorAspecto,arregloAspectoOrina[0],arregloAspectoOrina[1],null,null,null,null,null,"x10^6/uL"}, new Object[] {"Ph", R.id.examGOAutoCompleteViewPhOrina,adaptadorPh,arregloPhOrina[0],arregloPhOrina[1],null,null,null,null,null,"fL"}, new Object[] {"Densidad", R.id.examGOAutoCompleteViewDensidadOrina,adaptadorDensidad,arregloDensidadOrina[0],arregloDensidadOrina[1],null,null,null,null,null,"pg"}, new Object[] {"Proteinas", R.id.examGOAutoCompleteViewProteinasOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%"}, new Object[] {"Glucosa", R.id.examGOAutoCompleteViewGlucosaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"x10^3/uL"}, new Object[] {"Cuerpos Cetonicos", R.id.examGOAutoCompleteViewCetonaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%"}, new Object[] {"Bilirrubina", R.id.examGOAutoCompleteViewBilirrubinaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%"}, new Object[] {"Hemoglobina", R.id.examGOAutoCompleteViewHemoglobinaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%"}, new Object[] {"Nitritos", R.id.examGOAutoCompleteViewNitritosOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%"}, new Object[] {"Leucocitos", R.id.examGOAutoCompleteViewLeucocitosOrina,adaptadorLeucocitos,arregloLeucocitosOrina[0],arregloLeucocitosOrina[1],null,null,null,null,null,"%"}, new Object[] {"Eritrocitos", R.id.examGOAutoCompleteViewEritrocitosOrina,adaptadorEritrocitos,arregloEritrocitosOrina[0],arregloEritrocitosOrina[1],null,null,null,null,null,"x10^3/uL"},  new Object[] {"Celulas", R.id.examGOAutoCompleteViewCelulasOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"x10^3/uL"},  new Object[] {"Cilindros", R.id.examGOAutoCompleteViewCilindrosOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"x10^3/uL"}, new Object[] {"Bacterias", R.id.examGOAutoCompleteViewBacterias,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"fL"}, new Object[] {"Filamentos de Muciana", R.id.examGOAutoCompleteViewFilamentos,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"fL"}};
    String[][] enfermedades = {{"Anemia","bajo","Hemoglobina","Eritrocitos"},{"Infeccion Bacteriana","alto","Leocucitos","Neutrofilos"},{"Infeccion Viral","alto","Leocucitos","Linfocitos"},{"Desordenes de Coagulacion","bajo","Plaquetas"},{"Problemas Renales","ambos","Hematocrito"},{ "Desordenes de la Medula Osea","ambos","Hemoglobina","Leocucitos","Plaquetas"},{ "Reacciones Alergicas o Asma","alto","Eosinofilos"},{"Infecciones Parasitarias","alto","Eosinofilos"}};
    //Arreglo que contiene todos los editText
    AutoCompleteTextView[] autoComleteTextViews = { listaColorOrina, listaOlorOrina, listaAspectoOrina, listaPHOrina, listaDensidadOrina, listaProteinasOrina, listaGlucosaOrina, listaCetonaOrina, listaBilirrubinaOrina, listaHemoglobinaOrina, listaNitritosOrina, listaLeucocitosOrina, listaEritrocitosOrina, listaCelulasOrina, listaCilindrosOrina, listaBacteriasOrina, listaFilamentosOrina };
    private Button btnAnalizarHemograma;
    Button btnAnalizar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examen_general_orina);

        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = userActv.getIdUsuario();

        for(int i=0; i<parametros.length; i++) {
            Object[] elemento = (Object[]) parametros[i];
            opcionesListas(autoComleteTextViews[i], (int)elemento[1], (ArrayAdapter<String>) elemento[2], (String[]) elemento[3], i, (String[]) elemento[4]);
            autoComleteTextViews[i] = findViewById((int)elemento[1]);
        }
        btnAnalizar = findViewById(R.id.examenEGOBotonAnalizar);

        //Almacena el id del TipoExamen en este caso, de Orina
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id

        if (dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Ya fueron creados los registros en ExamenParametros de Hemograma");
        }else{
            //Crea un arreglo bidimensional String[][], ya que el método solo recibe ese tipo de dato
            String[][] nombreParametro = new String[parametros.length][];
            for (int j=0; j<parametros.length; j++){
                Object[] element = (Object[]) parametros[j];
                nombreParametro[j] = new String[]{(String) element[0]};
            }
            //Manda a llamar al método enviandole en arreglo creado y el id del Tipo de Examen
            llenadoTablaExamenParametro(nombreParametro, idTipExam);
        }

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades);

        btnAnalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analizarExamenOrina(autoComleteTextViews,parametros,idTipExam,idUsuario);
            }
        });

    }

    public void opcionesListas(AutoCompleteTextView lista, int id, ArrayAdapter<String> adaptador, String[] arreglo, int posicionParametro, String[] valorOpcion){
        lista = findViewById(id);
        lista.setDropDownBackgroundResource(R.color.white);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arreglo);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Select: "+posicionParametro+" posicion: "+i, Toast.LENGTH_SHORT).show();
                ((Object[])parametros[posicionParametro])[5] = ""+item+","+valorOpcion[i];
            }
        });
    }

}