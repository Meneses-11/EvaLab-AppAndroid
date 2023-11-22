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

import java.util.ArrayList;

public class ResultResultados extends Fragment {


    RecyclerView viewListaResultado;
    ArrayList<Resultados> listArrayResultados;
    int identificadorExamen, identificadorTipExamen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        identificadorExamen = getArguments().getInt("idExamen", 0);
        identificadorTipExamen = getArguments().getInt("idTipExamen", 0);

        View rootView = inflater.inflate(R.layout.fragment_result_resultados, container, false);

        viewListaResultado = rootView.findViewById(R.id.viewListaResultResultados);
        viewListaResultado.setLayoutManager(new LinearLayoutManager(getContext()));

        DBResultadosTabla dbResultadosTabla = new DBResultadosTabla(getContext());

        listArrayResultados = new ArrayList<>();
        AdaptadorListaResultados adaptador = new AdaptadorListaResultados(dbResultadosTabla.leerResultados(identificadorTipExamen, identificadorExamen));
        viewListaResultado.setAdapter(adaptador);

        return rootView;
    }
}