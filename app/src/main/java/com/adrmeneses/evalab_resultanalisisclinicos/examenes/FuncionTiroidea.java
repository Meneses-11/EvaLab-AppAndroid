package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

public class FuncionTiroidea extends MenuCategorias {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara los componentes visuales
    private TextInputEditText txtT3Total, txtT3Libre, txtT4Total, txtT4Libre, txtTSH;
    private Button btnAnalizarTiroides;
    private final String NAME_EXAM = "Tiroides";
    //Arreglo que almacena todos los parámetros Nombre-Neonatos-Embarazadas-Adultos-Adolescentes
    private String[][] parametrosTiroides = {{"T4 Total","6.4","12.6",null,null,"4.4","11.6","4.4","11.6","4.2","13","ug/dL",""+R.string.strInfTiroidesT4Total},{"T3 Total","0.7","1.9",null,null,"0.52","1.85","0.52","1.85","0.8","2.1","ng/mL",""+R.string.strInfTiroidesT3Total},{"TSH","0.1","39",null,null,"0.1","3","0.28","5.6","0.28","5.6","mUI/L",""+R.string.strInfTiroidesTSH},{"T4 Libre","0.9","2.2",null,null,".76","2.24",".8","2",".8","2","ng/dL",""+R.string.strInfTiroidesT4Libre},{"T3 Libre","0.9","2.2",null,null,"1.8","4.2","1.4","4.2",".8","2","pg/mL",""+R.string.strInfTiroidesT3Libre}};
    //Arreglo para almacenar los parametros de las Enfermedades {{"Nombre","Referencia",...Parametros...},{"Infeccion Bacteriana","alto","Leocucitos","Neutrofilos"}}
    String[][] enfermedades = {{"Hipertiroidismo",null,"T4 Total,alto","T3 Total,alto","TSH,bajo","T4 Libre,alto","T3 Libre,alto"},{"Hipotiroidismo",null,"T4 Total,bajo","T3 Total,bajo","TSH,alto","T4 Libre,bajo","T3 Libre,bajo"}};
    TextInputEditText[] textInputs = {txtT4Total, txtT3Total, txtTSH, txtT4Libre, txtT3Libre};
    int[] idTextInputs = {R.id.funTiroidesTextFieldT4T,R.id.funTiroidesTextFieldT3T,R.id.funTiroidesTextFieldTSH,R.id.funTiroidesTextFieldT4L,R.id.funTiroidesTextFieldT3L};
    //Variable para guardar el id del usuarios actual y del tipo de examen
    private long idUsuario, idTipExam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funcion_tiroidea);

        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = userActv.getIdUsuario();

        //Asigna los componentes a su variable correspondiente
        for (int j=0; j<idTextInputs.length; j++){
            textInputs[j] = findViewById(idTextInputs[j]);
        }
        btnAnalizarTiroides = findViewById(R.id.funTiroidesBotonAnalizar);

        //Almacena el id del TipoExamen en este caso de Tiroides
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id


        if(dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Yafueron creados los registros en ExamenParametros de Tiroides");
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Yafueron creados los registros en ReferenciaValores de Tiroides");
            }else {
                llenadoTablaReferenciaValores(parametrosTiroides, idTipExam);
            }
        }else {
            llenadoTablaExamenParametro(parametrosTiroides, idTipExam);
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Yafueron creados los registros en ReferenciaValores de Tiroides");
            }else {
                llenadoTablaReferenciaValores(parametrosTiroides, idTipExam);
            }
        }

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades, (int) idTipExam);

        btnAnalizarTiroides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(camposLLenos(textInputs)){
                    analizarExamen(textInputs,parametrosTiroides,idTipExam,idUsuario);
                }else {
                    ventanaDialogo("Datos Erroneos", "Introduzca la información solicitada");
                }
            }
        });

    }


}