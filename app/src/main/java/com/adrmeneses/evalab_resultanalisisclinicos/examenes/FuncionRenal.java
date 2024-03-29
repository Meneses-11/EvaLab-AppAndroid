package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

public class FuncionRenal extends MenuCategorias {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara los componentes visuales
    TextInputEditText txtCreatininaSer, txtCreatininaUri, txtBUN, txtProteinuria, txtMicroalbuminuria, txtSodio, txtPotasio, txtCloro, txtBicarbonato, txtAcidoUri, txtFosfoSer;
    final String NAME_EXAM = "Funcion Renal";
    long idTipExam, idUsuario;
    //Arreglo que almacena todos los parámetros // | Nombre | Normal | >13AñosHombres | >13 años Mujeres | Mayores 60 años | 1-13 años |
    String[][] parametros = {{"Creatinina Sérica","0.1","0.4","0.7","1.3","0.6","1.2","0.7","1.5","0.3","0.7","mg/dL",""+R.string.strInfRenalCreatSer},{"Creatinina Urinaria","0","200","0","200","0","200","0","200","0","200","mg/g",""+R.string.strInfRenalCreatUrin},{"BUN","8","23","8","23","8","23","8","23","8","23","mg/dL",""+R.string.strInfRenalBUN},{"Proteinuria","0","150","150","300","150","300","150","300","150","300","mg/día",""+R.string.strInfRenalProteinuria},{"Microalbuminuria","0","30","0","30","0","30","0","30","0","30","mg/día",""+R.string.strInfRenalMicroalbuminuria},{"Sodio","135","145","135","145","135","145","135","145","135","145","mEq/L",""+R.string.strInfRenalSodio},{"Potasio","3.5","5","3.5","5","3.5","5","3.5","5","3.5","5","mEq/L",""+R.string.strInfRenalPotasio},{"Cloro","95","105","95","105","95","105","95","105","95","105","mEq/L",""+R.string.strInfRenalCloro},{"Bicarbonato","22","29","22","29","22","29","22","29","22","29","mEq/L",""+R.string.strInfRenalBicarbonato},{"Ácido úrico","2","6","3.5","7.2","2.6","6.0","2.6","7.2","2.6","7.0","mg/dL",""+R.string.strInfRenalAcdUrico},{"Fósforo Sérico","3.8","6.5","2.5","4.5","2.5","4.5","2.5","4.5","2.5","4.5","mg/dL",""+R.string.strInfRenalFosfoSer}};
    // Arreglo que contiene toda la información de las enfermedades
    String[][] enfermedades = {{"Insuficiencia renal crónica",null,""+R.string.strInfEnfermInsufRenCron,"Creatinina Sérica,alto","Creatinina Urinaria,alto","BUN,alto"},{"Glomerulonefritis",null,""+R.string.strInfEnfermGlomerulonefritis,"Proteinuria,alto","Microalbuminuria,alto"},{"Acidosis Tubular Renal",null,""+R.string.strInfEnfermAcidTubRen,"Bicarbonato,bajo","Potasio,alto"},{"Insuficiencia renal aguda",null,""+R.string.strInfEnfermInsuficRenAguda,"BUN,alto","Creatinina Sérica,alto","Creatinina Urinaria,alto"}};
    //Arreglo que contiene todos los editText
    TextInputEditText[] textInputs = { txtCreatininaSer, txtCreatininaUri, txtBUN, txtProteinuria, txtMicroalbuminuria, txtSodio, txtPotasio, txtCloro, txtBicarbonato, txtAcidoUri, txtFosfoSer };
    //Arreglo que contiene el id de todos los componentes
    int[] componentes = {R.id.funRenalTextFieldCreatininaSer, R.id.funRenalTextFieldCreatininaUri, R.id.funRenalTextFieldBUN, R.id.funRenalTextFieldProteinuria, R.id.funRenalTextFieldMicroal, R.id.funRenalTextFieldSodio, R.id.funRenalTextFieldPotasio, R.id.funRenalTextFieldCloro, R.id.funRenalTextFieldBicarbonato, R.id.funRenalTextFieldAcidoUr, R.id.funRenalTextFieldFosfo};
    private Button btnAnalizarFunRenal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funcion_renal);

        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = userActv.getIdUsuario();

        //Asigna los componentes a su variable correspondiente
        for (int j=0; j<componentes.length; j++){
            textInputs[j] = findViewById(componentes[j]);
        }
        btnAnalizarFunRenal = findViewById(R.id.funRenalBotonAnalizar);

        //Almacena el id del TipoExamen en este caso de Funcion Renal
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id

        if (dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Ya fueron creados los registros en ExamenParametros de Funcion Renal");
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Ya fueron creados los registros en Valores Referencia de Funcion Renal");
            }else {
                llenadoTablaReferenciaValores(parametros, idTipExam);
            }
        }else{
            llenadoTablaExamenParametro(parametros, idTipExam);
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Ya fueron creados los registros en Valores Referencia de Funcion Renal");
            }else {
                llenadoTablaReferenciaValores(parametros, idTipExam);
            }
        }

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades, (int)idTipExam);

        btnAnalizarFunRenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minimo3CamposLLenos(textInputs)) {
                    analizarExamen(textInputs, parametros, idTipExam, idUsuario);
                }else{
                    ventanaDialogo("Campos Vacíos", "Llene por lo menos 3 campos");
                }
            }
        });



    }
}