package com.adrmeneses.evalab_resultanalisisclinicos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorListaResultados;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBResultadosTabla;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;
import com.adrmeneses.evalab_resultanalisisclinicos.usuarios.UsuarioActivo;

import java.util.ArrayList;

public class ResultResultados extends Fragment {

    //Declara la variable en la que se instanciara la clase UsuarioActivo
    UsuarioActivo userActv;
    int idUsuario;
    RecyclerView viewListaResultado;
    ArrayList<Resultados> listArrayResultados;
    int identificadorExamen, identificadorTipExamen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        identificadorExamen = getArguments().getInt("idExamen", 0);
        identificadorTipExamen = getArguments().getInt("idTipExamen", 0);
        //Instancia la clase UsuarioActivo
        userActv = UsuarioActivo.getInstance();
        idUsuario = (int)userActv.getIdUsuario();

        View rootView = inflater.inflate(R.layout.fragment_result_resultados, container, false);

        viewListaResultado = rootView.findViewById(R.id.viewListaResultResultados);
        viewListaResultado.setLayoutManager(new LinearLayoutManager(getContext()));

        DBResultadosTabla dbResultadosTabla = new DBResultadosTabla(getContext());

        listArrayResultados = new ArrayList<>();
        AdaptadorListaResultados adaptador = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen, idUsuario));
        viewListaResultado.setAdapter(adaptador);

        return rootView;
    }
}