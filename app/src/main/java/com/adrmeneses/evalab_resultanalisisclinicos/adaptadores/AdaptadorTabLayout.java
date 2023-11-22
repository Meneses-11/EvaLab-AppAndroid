package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.adrmeneses.evalab_resultanalisisclinicos.ResultEnfermedades;
import com.adrmeneses.evalab_resultanalisisclinicos.ResultEstado;
import com.adrmeneses.evalab_resultanalisisclinicos.ResultResultados;

public class AdaptadorTabLayout extends FragmentStateAdapter {
    int idExamen, idTipExamen;

    public AdaptadorTabLayout(@NonNull FragmentActivity fragmentActivity, int idExamen, int idTipExamen) {
        super(fragmentActivity);
        this.idExamen = idExamen;
        this.idTipExamen = idTipExamen;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        Bundle datos = new Bundle();
        datos.putInt("idTipExamen", idTipExamen);
        datos.putInt("idExamen", idExamen);

        switch (position){
            case 0:
                fragment = new ResultResultados();
                fragment.setArguments(datos);
                return fragment;
            case 1:
                fragment = new ResultEnfermedades();
                fragment.setArguments(datos);
                return fragment;
            case 2:
                fragment = new ResultEstado();
                fragment.setArguments(datos);
                return fragment;
            default:
                fragment = new ResultResultados();
                fragment.setArguments(datos);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
