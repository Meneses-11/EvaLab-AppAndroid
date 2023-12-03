package com.adrmeneses.evalab_resultanalisisclinicos.evaluadores;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;

public class EvaluadorResultado {
    private static final float PORC_MUY_BAJO = 0.788f;
    private static final float PORC_MUY_ALTO = 1.165f;
    public int verde, amarillo, rojo;
    float valorRefMin, valorRefMax;

    public void evaluarResult(Resultados resultado) {

        valorRefMin = Float.valueOf(resultado.getMinValor());
        valorRefMax = Float.valueOf(resultado.getMaxValor());

        verde = R.drawable.verd_semaf;
        amarillo = R.drawable.amar_semaf;
        rojo = R.drawable.rojo_semaf;

        float muyBajo = valorRefMin * PORC_MUY_BAJO;
        float muyAlto = valorRefMax * PORC_MUY_ALTO;

        float valorObtenido = (float)resultado.getValorObtenido();

        if(valorObtenido < muyBajo) {
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