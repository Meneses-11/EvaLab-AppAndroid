package com.adrmeneses.evalab_resultanalisisclinicos.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.adrmeneses.evalab_resultanalisisclinicos.fragmentos.ResultEnfermedades;
//import com.adrmeneses.evalab_resultanalisisclinicos.fragmentos.ResultEstado;
import com.adrmeneses.evalab_resultanalisisclinicos.fragmentos.ResultResultados;

public class AdaptadorTabLayout extends FragmentStateAdapter {

    public AdaptadorTabLayout(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ResultResultados();
            //case 1: return new ResultEnfermedades();
            //case 2: return new ResultEstado();
            default: return new ResultEnfermedades();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
