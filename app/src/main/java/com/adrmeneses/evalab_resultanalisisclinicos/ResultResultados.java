package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
import android.widget.EditText;
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
    AutoCompleteTextView autoCompletBuscarExamen;
    TextInputLayout editTextBuscarExamen;
    ArrayAdapter<String> adaptadorBuscarEx;
    AdaptadorListaResultados adaptadorLista;
    DBResultadosTabla dbResultadosTabla;
    DBExamenTipo dbExamenTipo;
    //Declara los arreglos que se usaran para las consultas
    int[] listaTipExa;
    String[][] listaExamenes;
    String[] opcionesExamenes;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Guarda los id en las variables correspondientes
        identificadorExamen = getArguments().getInt("idExamen", 0);
        identificadorTipExamen = getArguments().getInt("idTipExamen", 0);
        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = (int)userActv.getIdUsuario();

        View rootView = inflater.inflate(R.layout.fragment_result_resultados, container, false);

        //Intancia los componentes gráficos
        autoCompletBuscarExamen = rootView.findViewById(R.id.autoCompletBuscarExamen);
        editTextBuscarExamen = rootView.findViewById(R.id.txtFieldBuscarExamen);
        viewListaResultado = rootView.findViewById(R.id.viewListaResultResultados);
        viewListaResultado.setLayoutManager(new LinearLayoutManager(getContext()));
        dbResultadosTabla = new DBResultadosTabla(getContext());
        dbExamenTipo = new DBExamenTipo(getContext());
        listArrayResultados = new ArrayList<>();

        //Evalua si los id's son 0
        if(identificadorExamen == 0 && identificadorExamen == 0){
            //Evalua si no existen registros en la bd
            if(dbResultadosTabla.noHayResultados(idUsuario)){
                editTextBuscarExamen.setHint("No hay registros");
                editTextBuscarExamen.setEnabled(false);
            }else{//Si hay registros en la bd hace las consultas necesarias y crea la lista
                editTextBuscarExamen.setHint(R.string.strFragResultBuscar);
                listaTipExa = dbResultadosTabla.obtenerTiposExamenes(idUsuario);                    //Lista que contiene lis id's de TipoExamen que hay de un Usuario especifico
                listaExamenes = dbResultadosTabla.obtenerIdNombreTipoExamen(listaTipExa, idUsuario);//Lista con los nombres y id del TipExamen, y los id de los examenes
                opcionesExamenes = new String[listaExamenes.length];                                //Inicializa el arreglo
                // Imprime cada elemento de la matriz
                /*for (String[] resultado : listaExamenes) {
                    opcionesExamenes[]
                    Log.d(TAG, "idTipExam: " + resultado[0] + ", nombreExamen: " + resultado[1] + ", idExamen: " + resultado[2]);
                }*/
                //Recorre el arreglo listaExamenes y llena la lista con las opciones de Examenes
                for (int m=0; m<listaExamenes.length; m++){
                    opcionesExamenes[m]=listaExamenes[m][1]+"-"+listaExamenes[m][2];
                    Log.d(TAG, "ListaFin: "+opcionesExamenes[m]);
                }

                opcionesListas(autoCompletBuscarExamen,adaptadorBuscarEx,R.id.autoCompletBuscarExamen,opcionesExamenes,rootView);
                //Log.d(TAG, "idTipExam: " + listaExamenes[0][0] + ", nombreExamen: " + listaExamenes[0][1] + ", idExamen: " + listaExamenes[0][2]);

            }
        }else{//si los id's son diferente de 0 pone la lista que corresponda a los id's
            adaptadorLista = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
            viewListaResultado.setAdapter(adaptadorLista);
        }

        return rootView;
    }

    public void opcionesListas(AutoCompleteTextView lista, ArrayAdapter<String> adaptador, int id, String[] arreglo, View view){
        lista = view.findViewById(id);
        lista.setDropDownBackgroundResource(R.color.white);
        adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arreglo);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getContext(), "Select: "+item+" int: "+i+" long: "+l, Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "El id es: "+obtenerIdOpcion(item));
                int[] ides = obtenerIdOpcion(item);
                identificadorTipExamen = ides[0];
                identificadorExamen = ides[1];

                adaptadorLista = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
                viewListaResultado.setAdapter(adaptadorLista);

                Log.d(TAG, "idTipExam: "+identificadorTipExamen+" idEx: "+identificadorExamen+" idUser: "+idUsuario);
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


}