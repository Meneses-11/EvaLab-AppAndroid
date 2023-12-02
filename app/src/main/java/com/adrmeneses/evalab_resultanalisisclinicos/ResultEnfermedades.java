package com.adrmeneses.evalab_resultanalisisclinicos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorListaEnfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorListaResultados;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBExamenTipo;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Enfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.usuarios.UsuarioActivo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ResultEnfermedades extends Fragment {
    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    //Declara las variables para los id's
    int idUsuario;
    int identificadorExamen, identificadorTipExamen;
    //Declara las variables para los componentes gráficos
    RecyclerView vistaListaEnferm;
    AutoCompleteTextView autoCompletBuscarExamenEnferm;
    TextInputLayout textInputBuscarExamenEnferm;
    ScrollView contLista;
    LinearLayout contBoton;
    Button btnRegis;
    //Declara los modelos de la BD
    DBResultadosTabla dbResultadosTabla;
    DBExamenTipo dbExamenTipo;
    //Declara un Array de tipo Enfermedades
    ArrayList<Enfermedades> enfermedadesArrayList;
    //Declara el adaptador de la lista de opciones
    ArrayAdapter<String> adapterBuscarExEnferm;
    //Declara el adaptador de la lista de enfermedades
    AdaptadorListaEnfermedades adaptadorEnfer;
    //Declara las variables para listas de id's, nombres y opciones
    int[] listaTipExa;
    String[][] listaExamenes;
    String[] opcionesExamenes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result_enfermedades, container, false);

        //Guarda los id en las variables correspondientes
        identificadorExamen = getArguments().getInt("idExamen", 0);
        identificadorTipExamen = getArguments().getInt("idTipExamen", 0);
        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = (int)userActv.getIdUsuario();


        //Instancia los componentes gráficos
        autoCompletBuscarExamenEnferm = rootView.findViewById(R.id.autoCompletBuscarExamenEnfermedades);
        textInputBuscarExamenEnferm = rootView.findViewById(R.id.txtFieldBuscarExamenEnfermedades);
        contLista = rootView.findViewById(R.id.vistaListaEnfermedades);
        contBoton = rootView.findViewById(R.id.vistaBtnRegisExamEnferm);
        btnRegis = rootView.findViewById(R.id.btnRegisExamEnferm);
        vistaListaEnferm = rootView.findViewById(R.id.viewListaResultEnfermedades);
        vistaListaEnferm.setLayoutManager(new LinearLayoutManager(getContext()));  //Organiza los elementos en una lista vertical estándar

        //Instancia los Modelos de la BD
        dbResultadosTabla = new DBResultadosTabla(getContext());
        dbExamenTipo = new DBExamenTipo(getContext());

        //Instancia del ArrayList tipo Enfermedades
        enfermedadesArrayList = new ArrayList<>();

        //Evalua si no existen registros en la bd
        if(dbResultadosTabla.noHayResultados(idUsuario)) {

            textInputBuscarExamenEnferm.setHint("No hay Examenes registrados");
            textInputBuscarExamenEnferm.setEnabled(false);
            contBoton.setVisibility(View.VISIBLE);
            contLista.setVisibility(View.INVISIBLE);

        }else{//Si hay registros en la bd hace las consultas necesarias y crea la lista

            contBoton.setVisibility(View.INVISIBLE);
            contLista.setVisibility(View.VISIBLE);
            textInputBuscarExamenEnferm.setHint(R.string.strFragResultBuscar);

            listaTipExa = dbResultadosTabla.obtenerTiposExamenes(idUsuario);                    //Lista que contiene lis id's de TipoExamen que hay de un Usuario especifico
            listaExamenes = dbResultadosTabla.obtenerIdNombreTipoExamen(listaTipExa, idUsuario);//Lista con los nombres y id del TipExamen, y los id de los examenes
            opcionesExamenes = new String[listaExamenes.length];                                //Inicializa el arreglo que contendrá las opciones

            //Recorre el arreglo listaExamenes y llena la lista con las opciones de Examenes
            for (int m=0; m<listaExamenes.length; m++){
                opcionesExamenes[m]=listaExamenes[m][1]+"-"+listaExamenes[m][2];
            }
            //Manda a llamar al método para llenar las opciones del AutoComplet
            opcListas(autoCompletBuscarExamenEnferm,adapterBuscarExEnferm,opcionesExamenes);
        }

        //si los id's son diferente de 0 pone la lista que corresponda a los id's
        if(identificadorExamen != 0 || identificadorExamen != 0){
            autoCompletBuscarExamenEnferm.setText(opcionesExamenes[opcionesExamenes.length-1], false);
        }

        //Instancia el adaptador de la Lista
        adaptadorEnfer = new AdaptadorListaEnfermedades(dbResultadosTabla.leerEnfermedades(identificadorTipExamen,identificadorExamen,idUsuario));
        vistaListaEnferm.setAdapter(adaptadorEnfer);  //Asigna el adaptador al RecyclerView

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzar = new Intent(getContext(), MenuCategorias.class);
                startActivity(lanzar);
                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        });

        return rootView;
    }

    //Método que coloca las opciones en el AutoComplet
    public void opcListas(AutoCompleteTextView lista, ArrayAdapter<String> adaptador, String[] arreglo){
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

                //Instancia el adaptador de la Lista
                adaptadorEnfer = new AdaptadorListaEnfermedades(dbResultadosTabla.leerEnfermedades(identificadorTipExamen,identificadorExamen,idUsuario));
                vistaListaEnferm.setAdapter(adaptadorEnfer);  //Asigna el adaptador al RecyclerView
                //adaptadorLista = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
                //viewListaResultado.setAdapter(adaptadorLista);
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