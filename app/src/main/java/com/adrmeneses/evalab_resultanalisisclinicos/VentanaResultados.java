package com.adrmeneses.evalab_resultanalisisclinicos;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorTabLayout;
import com.google.android.material.tabs.TabLayout;

public class VentanaResultados extends AppCompatActivity {

    //Declara 2 objetos
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    AdaptadorTabLayout adaptadorTLayout;
    int examenId, tipExamenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_resultados);

        Intent intent = getIntent();
        examenId = intent.getIntExtra("idExamen", 0);
        tipExamenId = intent.getIntExtra("idTipExamen", 0);

        Log.d(TAG, "VentanaResultado: "+examenId+" "+tipExamenId);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        adaptadorTLayout = new AdaptadorTabLayout(this, examenId, tipExamenId);
        viewPager2.setAdapter(adaptadorTLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}