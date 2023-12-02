package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorListaResultados;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;
import com.adrmeneses.evalab_resultanalisisclinicos.usuarios.UsuarioActivo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import io.grpc.Context;

public class ResultResultados extends Fragment {

    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara las variables para los id's
    int idUsuario;
    int identificadorExamen, identificadorTipExamen;
    RecyclerView viewListaResultado;
    ArrayList<Resultados> listArrayResultados;
    AutoCompleteTextView autoCompletBuscarExamenResult;
    TextInputLayout textInputBuscarExamenResult;
    LinearLayout contenedorBoton;
    ScrollView contenedorLista;
    Button botonRegistrar;
    ArrayAdapter<String> adaptadorBuscarEx;
    AdaptadorListaResultados adaptadorLista;
    DBResultadosTabla dbResultadosTabla;
    DBExamenTipo dbExamenTipo;
    //Declara los arreglos que se usaran para las consultas
    int[] listaTipExa;
    String[][] listaExamenes;
    String[] opcionesExamenes;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ResulResultado: Método onCreate");
        //Guarda los id en las variables correspondientes
        identificadorExamen = getArguments().getInt("idExamen", 0);
        identificadorTipExamen = getArguments().getInt("idTipExamen", 0);
        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = (int)userActv.getIdUsuario();

        View rootView = inflater.inflate(R.layout.fragment_result_resultados, container, false);

        //Intancia los componentes gráficos
        autoCompletBuscarExamenResult = rootView.findViewById(R.id.autoCompletBuscarExamenResultados);
        textInputBuscarExamenResult = rootView.findViewById(R.id.txtFieldBuscarExamenResultados);
        contenedorLista = rootView.findViewById(R.id.vistaListaResultados);
        contenedorBoton = rootView.findViewById(R.id.vistaBtnRegisExamResult);
        botonRegistrar = rootView.findViewById(R.id.btnRegisExamResult);
        viewListaResultado = rootView.findViewById(R.id.viewListaResultResultados);
        viewListaResultado.setLayoutManager(new LinearLayoutManager(getContext())); //Organiza los elementos en una lista vertical estándar

        dbResultadosTabla = new DBResultadosTabla(getContext());
        dbExamenTipo = new DBExamenTipo(getContext());
        listArrayResultados = new ArrayList<>();


        //Evalua si no existen registros en la bd
        if(dbResultadosTabla.noHayResultados(idUsuario)){
            textInputBuscarExamenResult.setHint("No hay Examenes registrados");
            textInputBuscarExamenResult.setEnabled(false);

            contenedorBoton.setVisibility(View.VISIBLE);
            contenedorLista.setVisibility(View.INVISIBLE);

        }else{//Si hay registros en la bd hace las consultas necesarias y crea la lista
            contenedorBoton.setVisibility(View.INVISIBLE);
            contenedorLista.setVisibility(View.VISIBLE);

            textInputBuscarExamenResult.setHint(R.string.strFragResultBuscar);
            listaTipExa = dbResultadosTabla.obtenerTiposExamenes(idUsuario);                    //Lista que contiene lis id's de TipoExamen que hay de un Usuario especifico
            listaExamenes = dbResultadosTabla.obtenerIdNombreTipoExamen(listaTipExa, idUsuario);//Lista con los nombres y id del TipExamen, y los id de los examenes
            opcionesExamenes = new String[listaExamenes.length];                                //Inicializa el arreglo

            //Recorre el arreglo listaExamenes y llena la lista con las opciones de Examenes
            for (int m=0; m<listaExamenes.length; m++){
                opcionesExamenes[m]=listaExamenes[m][1]+"-"+listaExamenes[m][2];
            }

            opcionesListas(autoCompletBuscarExamenResult,adaptadorBuscarEx,opcionesExamenes);

        }

        //Evalua si los id's son 0
        if(identificadorExamen == 0 && identificadorExamen == 0){
            //
        }else{//si los id's son diferente de 0 pone la lista que corresponda a los id's
            autoCompletBuscarExamenResult.setText(opcionesExamenes[opcionesExamenes.length-1], false);
        }

        adaptadorLista = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
        viewListaResultado.setAdapter(adaptadorLista);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar = new Intent(getContext(), MenuCategorias.class);
                startActivity(lanzar);
                if (getActivity() != null){
                    getActivity().finish();
                }
            }
        });

        return rootView;
    }

    public void opcionesListas(AutoCompleteTextView lista, ArrayAdapter<String> adaptador, String[] arreglo){
        lista.setDropDownBackgroundResource(R.color.white);
        adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arreglo);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                int[] ides = obtenerIdOpcion(item);
                identificadorTipExamen = ides[0];
                identificadorExamen = ides[1];

                adaptadorLista = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
                viewListaResultado.setAdapter(adaptadorLista);


            }
        });
    }

    public int[] obtenerIdOpcion(String opcionSelect){
        boolean flag = false;
        int idTipEx, idEx;
        String acumNum = "", acumNombre = "";
        for (int j=0; j<opcionSelect.length(); j++) {
            char caracter = opcionSelect.charAt(j);

            if (flag){
                acumNum += caracter;
            }
            if(caracter == '-'){
                flag = true;
            }
            if(!flag){
                acumNombre += caracter;
            }
        }

        idTipEx = (int)dbExamenTipo.obtenerIdTipExam(acumNombre);
        idEx = Integer.parseInt(acumNum);

        return new int[]{idTipEx,idEx};
    }
/*
    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "ResulResultado: ex: "+identificadorExamen+" tipEx: "+identificadorTipExamen);
        //si los id's son diferente de 0 pone la lista que corresponda a los id's
        if(identificadorExamen != 0 || identificadorExamen != 0){
            autoCompletBuscarExamenResult.setText(opcionesExamenes[opcionesExamenes.length-1], false);
        }

        adaptadorLista = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
        viewListaResultado.setAdapter(adaptadorLista);

    }*/
}