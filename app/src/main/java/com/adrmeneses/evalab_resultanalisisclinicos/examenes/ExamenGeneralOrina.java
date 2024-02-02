package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;

public class ExamenGeneralOrina extends MenuCategorias {

    private String[][] arregloColorOrina = {{"Amarillo (todos)","Rojo", "Marrón","Negro"},{"1","2","2","3"}}, arreglloOlorOrina = {{"Lig. Ácido","Lig. Amoniaco", "Acetona o Fruta", "Fetido (Mal)"},{"1","1","2","3"}}, arregloAspectoOrina = {{"Claro", "Lig. Turbio", "Turbio"},{"1","2","3"}}, arregloPhOrina = {{"0 - 5.4","5.5 - 7","Más de 7"},{"2","1","3"}}, arregloDensidadOrina = {{"0", "0.001-1.007","1.008-1.032","1.033-1.055","Más de 1.055"},{"3","2","1","2","3"}}, arregloPosNegOrina = {{"Positivo","Negativo"},{"3","1"}}, arregloLeucocitosOrina = {{"0","1 - 6","7 - 20","más de 20"},{"1","1","2","3"}}, arregloEritrocitosOrina = {{"0","1 - 5","6 - 10","Más de 10"},{"1","1","2","3"}};
    AutoCompleteTextView listaColorOrina, listaOlorOrina, listaAspectoOrina, listaPHOrina, listaDensidadOrina, listaProteinasOrina, listaGlucosaOrina, listaCetonaOrina, listaBilirrubinaOrina, listaHemoglobinaOrina, listaNitritosOrina, listaLeucocitosOrina, listaEritrocitosOrina, listaCelulasOrina, listaCilindrosOrina, listaBacteriasOrina, listaFilamentosOrina;
    ArrayAdapter<String> adaptadorColor, adaptadorOlor, adaptadorAspecto, adaptadorPh, adaptadorDensidad, adaptadorLeucocitos, adaptadorEritrocitos, adaptadorPosNeg;

    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    final String NAME_EXAM = "Examen de Orina";
    long idTipExam, idUsuario;
    //Arreglo que almacena todos los parámetros
    Object[] parametros = {new Object[] {"Color",R.id.examGOAutoCompleteViewColorOrina,adaptadorColor,arregloColorOrina[0],arregloColorOrina[1],null,null,null,null,null,"g/dL",""+R.string.strInfEGOColor}, new Object[] {"Olor",R.id.examGOAutoCompleteViewOlorOrina,adaptadorOlor,arreglloOlorOrina[0],arreglloOlorOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGOOlor}, new Object[] {"Aspecto", R.id.examGOAutoCompleteViewAspectoOrina,adaptadorAspecto,arregloAspectoOrina[0],arregloAspectoOrina[1],null,null,null,null,null,"x10^6/uL",""+R.string.strInfEGOAspecto}, new Object[] {"Ph", R.id.examGOAutoCompleteViewPhOrina,adaptadorPh,arregloPhOrina[0],arregloPhOrina[1],null,null,null,null,null,"fL",""+R.string.strInfEGOPh}, new Object[] {"Densidad", R.id.examGOAutoCompleteViewDensidadOrina,adaptadorDensidad,arregloDensidadOrina[0],arregloDensidadOrina[1],null,null,null,null,null,"pg",""+R.string.strInfEGODensidad}, new Object[] {"Proteinas", R.id.examGOAutoCompleteViewProteinasOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGOProteinas}, new Object[] {"Glucosa", R.id.examGOAutoCompleteViewGlucosaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"x10^3/uL",""+R.string.strInfEGOGlucosa}, new Object[] {"Cuerpos Cetonicos", R.id.examGOAutoCompleteViewCetonaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGOCetonicos}, new Object[] {"Bilirrubina", R.id.examGOAutoCompleteViewBilirrubinaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGOBilirrubina}, new Object[] {"Hemoglobina Orina", R.id.examGOAutoCompleteViewHemoglobinaOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGOHemoglobina}, new Object[] {"Nitritos", R.id.examGOAutoCompleteViewNitritosOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGONitritos}, new Object[] {"Leucocitos Orina", R.id.examGOAutoCompleteViewLeucocitosOrina,adaptadorLeucocitos,arregloLeucocitosOrina[0],arregloLeucocitosOrina[1],null,null,null,null,null,"%",""+R.string.strInfEGOLeucocitos}, new Object[] {"Eritrocitos Orina", R.id.examGOAutoCompleteViewEritrocitosOrina,adaptadorEritrocitos,arregloEritrocitosOrina[0],arregloEritrocitosOrina[1],null,null,null,null,null,"x10^3/uL",""+R.string.strInfEGOEritrocitos},  new Object[] {"Celulas", R.id.examGOAutoCompleteViewCelulasOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"x10^3/uL",""+R.string.strInfEGOCelulas},  new Object[] {"Cilindros", R.id.examGOAutoCompleteViewCilindrosOrina,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"x10^3/uL",""+R.string.strInfEGOCilindros}, new Object[] {"Bacterias", R.id.examGOAutoCompleteViewBacterias,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"fL",""+R.string.strInfEGOBacterias}, new Object[] {"Filamentos de Muciana", R.id.examGOAutoCompleteViewFilamentos,adaptadorPosNeg,arregloPosNegOrina[0],arregloPosNegOrina[1],null,null,null,null,null,"fL",""+R.string.strInfEGOFilamentos}};
    //Arreglo que almacena toda la informacion de las enfermedades
    String[][] enfermedades = {{"Diabetes",null,""+R.string.strInfEnfermDiabetes,"Olor,2","Glucosa,3"},{"Infección Urinaria",null,""+R.string.strInfEnfermInfeccUrin,"Leucocitos Orina,2-3","Color,2-3","Olor,3"},{"Precencia de bácterias o células",null,""+R.string.strInfEnfermPresencBacte,"Aspecto,2"},{"Infección del tracto urinario",null,""+R.string.strInfEnfermInfeccTracUrinario,"Nitritos,3","Aspecto,3"},{"Acidosis",null,""+R.string.strInfEnfermAcidosis,"Ph,2"},{"Alcalosis",null,""+R.string.strInfEnfermAlcalosis,"Ph,3"},{"Deshidratación",null,""+R.string.strInfEnfermDeshidratacion,"Densidad,2"},{"Problemas Renales",null,""+R.string.strInfEnfermProbRen,"Proteinas,3","Eritrocitos Orina,2-3","Densidad,3"},{"Problemas hepáticos",null,""+R.string.strInfEnfermProbHepaticos,"Bilirrubina,3"}};
    //Arreglo que contiene todos los editText
    AutoCompleteTextView[] autoComleteTextViews = { listaColorOrina, listaOlorOrina, listaAspectoOrina, listaPHOrina, listaDensidadOrina, listaProteinasOrina, listaGlucosaOrina, listaCetonaOrina, listaBilirrubinaOrina, listaHemoglobinaOrina, listaNitritosOrina, listaLeucocitosOrina, listaEritrocitosOrina, listaCelulasOrina, listaCilindrosOrina, listaBacteriasOrina, listaFilamentosOrina };
    private Button btnAnalizarOrina;

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
        btnAnalizarOrina = findViewById(R.id.examenEGOBotonAnalizar);

        //Almacena el id del TipoExamen en este caso, de Orina
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id

        if (dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Ya fueron creados los registros en ExamenParametros de Hemograma");
        }else{
            //Crea un arreglo bidimensional String[][], ya que el método solo recibe ese tipo de dato
            String[][] nombreParametro = new String[parametros.length][];
            for (int j=0; j<parametros.length; j++){
                Object[] element = (Object[]) parametros[j];
                nombreParametro[j] = new String[]{(String) element[0], (String) element[element.length-1]};
            }
            //Manda a llamar al método enviandole en arreglo creado y el id del Tipo de Examen
            llenadoTablaExamenParametro(nombreParametro, idTipExam);
        }

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades, (int) idTipExam);

        btnAnalizarOrina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minimo3CamposLLenos(autoComleteTextViews)) {
                    analizarExamenOrina(autoComleteTextViews, parametros, idTipExam, idUsuario);
                }else{
                    ventanaDialogo("Campos Vacíos", "Llene por lo menos 3 campos");
                }
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
                ((Object[])parametros[posicionParametro])[5] = ""+item+"/"+valorOpcion[i];
            }
        });
    }

}