package com.adrmeneses.evalab_resultanalisisclinicos.evaluadores;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Resultados;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils;

public class EvaluadorResultado {
    private static final float PORC_MUY_BAJO = 0.788f;
    private static final float PORC_MUY_ALTO = 1.165f;
    private int verde, amarillo, rojo, gris, valorOpcion;
    private String opcElegida;
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

        String valoObten = resultado.getValorObtenido();
        if(valoObten != null){
            try {
            Double valorObtenido = Double.parseDouble(valoObten);

            if (valorObtenido < muyBajo) {
                resultado.setEstado("Valor Muy Bajo");
                resultado.setSemaf(rojo);
            } else if (valorObtenido < valorRefMin) {
                resultado.setEstado("Valor Bajo");
                resultado.setSemaf(amarillo);
            } else if (valorObtenido > muyAlto) {
                resultado.setEstado("Valor Muy Alto");
                resultado.setSemaf(rojo);
            } else if (valorObtenido > valorRefMax) {
                resultado.setEstado("Valor Alto");
                resultado.setSemaf(amarillo);
            } else {
                resultado.setEstado("Normal");
                resultado.setSemaf(verde);
            }
            } catch (NumberFormatException e) {
                Log.e(TAG, "evaluarResult: Error");
                resultado.setEstado("Sin Informacion");
                resultado.setSemaf(gris);
                resultado.setMedidaUnidad("");
            }
        }else {
            resultado.setEstado("Sin Informacion");
            resultado.setSemaf(gris);
            resultado.setMedidaUnidad("");
        }



    }

    public void evaluarResultOrina(Resultados resultado){
        verde = R.drawable.semaf_verd;
        amarillo = R.drawable.semaf_amar;
        rojo = R.drawable.semaf_rojo;
        gris = R.drawable.semaf_gris;

        try {
            String valorObtenido = resultado.getValorObtenido();
            String valOpc = resultado.getMedidaUnidad();

            if (valorObtenido != null && valOpc != null) {
                //String[] valorObtenidoArray = valorObtenido.toString().split(",");
                opcElegida = valorObtenido;
                valorOpcion = Integer.parseInt(valOpc);
            } else {
                opcElegida = "Sin Datos";
                valorOpcion = 0;
            }
        } catch (NullPointerException | NumberFormatException ex) {

            opcElegida = "Sin Datos";
            valorOpcion = 0;
        }

        switch (valorOpcion){
            case 0:
                resultado.setValorObtenido(opcElegida);
                resultado.setEstado("Sin Informacion");
                resultado.setSemaf(gris);
                break;
            case 1:
                resultado.setValorObtenido(opcElegida);
                resultado.setEstado("Normal");
                resultado.setSemaf(verde);
                break;
            case 2:
                resultado.setValorObtenido(opcElegida);
                resultado.setEstado("Preocupante");
                resultado.setSemaf(amarillo);
                break;
            case 3:
                resultado.setValorObtenido(opcElegida);
                resultado.setEstado("MAL");
                resultado.setSemaf(rojo);
                break;
        }


    }

}