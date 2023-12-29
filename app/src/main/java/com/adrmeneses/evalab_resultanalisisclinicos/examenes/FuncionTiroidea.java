package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBEnfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBEnfermedadesParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenParametros;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBReferenciaValores;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

public class FuncionTiroidea extends MenuCategorias {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara la variable en la que se instanciara la clase Opcion Elejida
    OpcionElegida opcElegida;
    //Declara los componentes visuales
    private TextInputEditText txtT3Total, txtT3Libre, txtT4Total, txtT4Libre, txtTSH;
    private Button btnAnalizarTiroides;
    private final String NAME_EXAM = "Tiroides";
    //Arreglo que almacena todos los parámetros Nombre-Neonatos-Embarazadas-Adultos-Adolescentes
    private String[][] parametrosTiroides = {{"T4 Total","6.4","12.6",null,null,null,null,"4.4","11.6","4.2","13","ug/dL"},{"T3 Total","0.7","1.9",null,null,null,null,"0.52","1.85","0.8","2.1","ng/mL"},{"TSH","0.1","39",null,null,"0.1","3","0.28","5.6","0.28","5.6","mUI/L"},{"T4 Libre","0.9","2.2",null,null,".76","2.24",".8","2",".8","2","ng/dL"},{"T3 Libre","0.9","2.2",null,null,"1.8","4.2","1.4","4.2",".8","2","pg/mL"}};
    //Arreglo para almacenar los parametros de las Enfermedades {{"Nombre","Referencia",...Parametros...},{"Infeccion Bacteriana","alto","Leocucitos","Neutrofilos"}}
    String[][] enfermedades = {{"Anemia","bajo","Hemoglobina","Eritrocitos"},{"Infeccion Bacteriana","alto","Leocucitos","Neutrofilos"},{"Infeccion Viral","alto","Leocucitos","Linfocitos"},{"Desordenes de Coagulacion","bajo","Plaquetas"},{"Problemas Renales","ambos","Hematocrito"},{ "Desordenes de la Medula Osea","ambos","Hemoglobina","Leocucitos","Plaquetas"},{ "Reacciones Alergicas o Asma","alto","Eosinofilos"},{"Infecciones Parasitarias","alto","Eosinofilos"}};
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

        //Instancia la clase OpcionElegida
        opcElegida = OpcionElegida.getInstance();

        //Asigna los componentes a su variable correspondiente
        for (int j=0; j<idTextInputs.length; j++){
            textInputs[j] = findViewById(idTextInputs[j]);
        }
        btnAnalizarTiroides = findViewById(R.id.funTiroidesBotonAnalizar);

        //Almacena el id del TipoExamen en este caso de Tiroides
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades);

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

        btnAnalizarTiroides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CamposLLenos(textInputs)){
                    analizarExamen(textInputs,parametrosTiroides,idTipExam,idUsuario);
                }else {
                    ventanaDialogo("Datos Erroneos", "Introduzca la información solicitada");
                }
            }
        });

    }


}