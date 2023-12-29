package com.adrmeneses.evalab_resultanalisisclinicos.examenes;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adrmeneses.evalab_resultanalisisclinicos.MenuCategorias;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.OpcionElegida;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;

public class FuncionHepatica extends MenuCategorias {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara los componentes visuales
    private TextInputEditText txtALT, txtAST, txtALP, txtGGT, txtTP, txtAlbumina, txtBilirrD, txtBilirrT;
    private Button btnAnalizarFunHepatica;
    private final String NAME_EXAM = "Funcion Hepatica";
    //Arreglo que almacena todos los parámetros Nombre-Neonatos-Embarazadas-Adultos-Adolescentes
    private String[][] parametrosFunHepatica = {{"Alanina Transaminasa","3","30",null,null,null,null,null,null,null,null,"UI/L"},{"Aspartato Transaminasa","11","32",null,null,null,null,null,null,null,null,"U/L"},{"Fosfatasa Alcalina","31","105",null,null,null,null,null,null,null,null,"U/L"},{"Gamma glutamil transferasa","2","65",null,null,null,null,null,null,null,null,"U/L"},{"Tiepo de protombina","10","15",null,null,null,null,null,null,null,null,"seg"},{"Albumina","3.5","5",null,null,null,null,null,null,null,null,"g/dL"},{"Bilirrubina Directa","0.01","0.3",null,null,null,null,null,null,null,null,"mg/dL"},{"Bilirrubina Total","0.3","1",null,null,null,null,null,null,null,null,"mg/dL"}};
    //Arreglo para almacenar los parametros de las Enfermedades {{"Nombre","Referencia",...Parametros...},{"Infeccion Bacteriana","alto","Leocucitos","Neutrofilos"}}
    String[][] enfermedades = {{"Anemia","bajo","Hemoglobina","Eritrocitos"}};
    TextInputEditText[] textInputs = {txtALT, txtAST, txtALP, txtGGT, txtTP, txtAlbumina, txtBilirrD, txtBilirrT};
    int[] idTextInputs = {R.id.funHepaticaTextFieldAlanina,R.id.funHepaticaTextFieldAspartato,R.id.funHepaticaTextFieldFosfatasa,R.id.funHepaticaTextFieldGamma,R.id.funHepaticaTextFieldProtombina,R.id.funHepaticaTextFieldAlbumina,R.id.funHepaticaTextFieldBiliDirec,R.id.funHepaticaTextFieldBiliTotal};
    //Variable para guardar el id del usuarios actual y del tipo de examen
    private long idUsuario, idTipExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funcion_hepatica);

        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = userActv.getIdUsuario();

        //Asigna los componentes a su variable correspondiente
        for (int j=0; j<idTextInputs.length; j++){
            textInputs[j] = findViewById(idTextInputs[j]);
        }
        btnAnalizarFunHepatica = findViewById(R.id.funHepaticaBotonAnalizar);

        //Almacena el id del TipoExamen en este caso de Tiroides
        idTipExam = dbExamenTipo.obtenerIdTipExam(NAME_EXAM); //manda a llamar el metodo que retorna el id

        if(dbExamenParametros.existeConIdTipExam(idTipExam)){
            Log.d(TAG, "Yafueron creados los registros en ExamenParametros de Funcion Hepatica");
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Yafueron creados los registros en ReferenciaValores de Funcion Hepatica");
            }else {
                llenadoTablaReferenciaValores(parametrosFunHepatica, idTipExam);
            }
        }else {
            llenadoTablaExamenParametro(parametrosFunHepatica, idTipExam);
            if(dbReferenciaValores.existeConIdTipExam(idTipExam)){
                Log.d(TAG, "Yafueron creados los registros en ReferenciaValores de Funcion Hepatica");
            }else {
                llenadoTablaReferenciaValores(parametrosFunHepatica, idTipExam);
            }
        }

        llenadoTablaEnfermedades(enfermedades);
        llenadoTablaEnfermedadesParam(enfermedades);

        btnAnalizarFunHepatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CamposLLenos(textInputs)){
                    analizarExamen(textInputs,parametrosFunHepatica,idTipExam,idUsuario);
                }else {
                    ventanaDialogo("Datos Erroneos", "Introduzca la información solicitada");
                }
            }
        });
    }
}