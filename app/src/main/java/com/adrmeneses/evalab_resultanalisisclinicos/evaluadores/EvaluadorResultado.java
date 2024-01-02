package com.adrmeneses.evalab_resultanalisisclinicos.evaluadores;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;

public class EvaluadorResultado {
    private static final float PORC_MUY_BAJO = 0.788f;
    private static final float PORC_MUY_ALTO = 1.165f;
    private int verde, amarillo, rojo, gris;
    private float valorRefMin, valorRefMax;

    public void evaluarResult(Resultados resultado) {

        valorRefMin = Float.valueOf(resultado.getMinValor());
        valorRefMax = Float.valueOf(resultado.getMaxValor());

        verde = R.drawable.semaf_verd;
        amarillo = R.drawable.semaf_amar;
        rojo = R.drawable.semaf_rojo;
        gris = R.drawable.semaf_gris;

        float muyBajo = valorRefMin * PORC_MUY_BAJO;
        float muyAlto = valorRefMax * PORC_MUY_ALTO;

        Double valorObtenido = resultado.getValorObtenido();

        if(valorObtenido == null){
            resultado.setEstado("Sin Informacion");
            resultado.setSemaf(gris);
            resultado.setMedidaUnidad("");
        }else if(valorObtenido < muyBajo) {
            resultado.setEstado("Valor Muy Bajo");
            resultado.setSemaf(rojo);
        } else if (valorObtenido < valorRefMin) {
            resultado.setEstado("Valor Bajo");
            resultado.setSemaf(amarillo);
        } else if (valorObtenido > muyAlto) {
            resultado.setEstado("Valor Muy Alto");
            resultado.setSemaf(rojo);
        } else if (valorObtenido > valorRefMax){
            resultado.setEstado("Valor Alto");
            resultado.setSemaf(amarillo);
        }else {
            resultado.setEstado("Normal");
            resultado.setSemaf(verde);
        }
    }

}