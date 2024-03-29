package com.adrmeneses.evalab_resultanalisisclinicos.evaluadores;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Enfermedades;

public class EvaluadorEnfermedad {

    private String reference, textoPorcentaje, opcionElegida;
    private String[][] datosResultados;
    private double valMin, valMax, valObten, puntMedio, porcentRef, acumPorcent;
    private int contador, porcentaje, valorOpcionElegida;
    private int[][] rangoPorcentajesMax= {{100,95,20,10},{110,101,40,21},{120,111,55,41},{130,120,65,56},{140,130,75,66},{150,141,85,76}};
    private int[][] rangoPorcentajesMin= {{105,100,20,10},{99,90,40,21},{89,80,55,41},{79,70,65,56},{69,60,75,66},{59,50,85,76}};
/*
         150 + 95            |          50 - 95
Porcentaje      Porcentaje   | Porcentaje      Porcentaje
con respecto        de       | con respecto        de
al valor max   probabilidad  | al valor min   probabilidad
  141-150   ===   86-90      |    50-59    ===   86-90
  130-140   ===   71-85      |    60-69    ===   71-85
  120-130   ===   66-70      |    70-79    ===   66-70
  111-120   ===   45-65      |    80-89    ===   45-65
  101-110   ===   21-40      |    90-99    ===   21-40
  95-100   ====   10-20      |   100-105   ===   10-20
*/

    public void evaluarEnferm(Enfermedades enfermedad){
        valMin = 0; valMax = 0; valObten = 0;
        porcentaje = 0;
        porcentRef = 0;
        acumPorcent = 0;
        contador = 0;
        textoPorcentaje = "";
        enfermedad.setMostrar(true); //Le manda true para que se muestre en la vista

        if(enfermedad.getValObtenidos() != null) {
            reference = enfermedad.getReferencia();
            datosResultados = enfermedad.getValObtenidos();//[12,20,18][4.5,20,6.5][15,20,22]
            switch (reference) {
                case "alto":
                    for (String[] resultAlt : datosResultados) {
                        valObten = Double.parseDouble(resultAlt[0]);
                        valMax = Double.valueOf(resultAlt[2]);
                        contador += 1;
                        porcentRef = valObten * 100 / valMax;
                        analizarArribaValMax();
                    }
                    break;
                case "bajo":
                    for (String[] resultBaj : datosResultados) {
                        valObten = Double.valueOf(resultBaj[0]);
                        valMin = Double.valueOf(resultBaj[1]);
                        contador += 1;
                        porcentRef = valObten * 100 / valMin;
                        analizarAbajoValMin();
                    }
                    break;
                case "ambos":
                    for (String[] resultAm : datosResultados) {
                        valObten = Double.valueOf(resultAm[0]);
                        valMin = Double.valueOf(resultAm[1]);
                        valMax = Double.valueOf(resultAm[2]);
                        contador += 1;

                        puntMedio = valMin + ((valMax - valMin) / 2);

                        if (valObten > puntMedio) {
                            porcentRef = valObten * 100 / valMax;
                            analizarArribaValMax();
                        } else {
                            porcentRef = valObten * 100 / valMin;
                            analizarAbajoValMin();
                        }
                    }
                    break;
                default:
                    Log.e(TAG, "evaluarEnferm: Hubo un error con la referencia de: " + enfermedad.getNombreEnf());
            }
            porcentaje = (int) (acumPorcent / contador);
            textoPorcentaje = evaluarInfoPorcentaje(porcentaje, enfermedad);

            enfermedad.setPorcentaje(porcentaje);
            enfermedad.setInfoPorcentaje(textoPorcentaje);
        }else{
            enfermedad.setMostrar(false);
            textoPorcentaje = "";
        }
    }

    private void analizarArribaValMax(){
        if(porcentRef > 150){//95
            acumPorcent += 86;
        }else if(porcentRef > 140){//86-90
            acumPorcent += calcularPorcentaje(rangoPorcentajesMax[5][0],rangoPorcentajesMax[5][1],rangoPorcentajesMax[5][2],rangoPorcentajesMax[5][3],porcentRef);
        } else if (porcentRef > 130) {//71-85
            acumPorcent += calcularPorcentaje(rangoPorcentajesMax[4][0],rangoPorcentajesMax[4][1],rangoPorcentajesMax[4][2],rangoPorcentajesMax[4][3],porcentRef);
        } else if (porcentRef > 120) {//66-70
            acumPorcent += calcularPorcentaje(rangoPorcentajesMax[3][0],rangoPorcentajesMax[3][1],rangoPorcentajesMax[3][2],rangoPorcentajesMax[3][3],porcentRef);
        } else if (porcentRef > 110) {//45-65
            acumPorcent += calcularPorcentaje(rangoPorcentajesMax[2][0],rangoPorcentajesMax[2][1],rangoPorcentajesMax[2][2],rangoPorcentajesMax[2][3],porcentRef);
        } else if (porcentRef > 100) {//21-40
            acumPorcent += calcularPorcentaje(rangoPorcentajesMax[1][0],rangoPorcentajesMax[1][1],rangoPorcentajesMax[1][2],rangoPorcentajesMax[1][3],porcentRef);
        } else if (porcentRef > 95) {//10-20
            acumPorcent += calcularPorcentaje(rangoPorcentajesMax[0][0],rangoPorcentajesMax[0][1],rangoPorcentajesMax[0][2],rangoPorcentajesMax[0][3],porcentRef);
        }
    }
    private void analizarAbajoValMin(){
        if (porcentRef < 50){//95
            acumPorcent += 86;
        }else if (porcentRef < 60){//86-90
            acumPorcent += calcularPorcentaje(rangoPorcentajesMin[5][0],rangoPorcentajesMin[5][1],rangoPorcentajesMin[5][2],rangoPorcentajesMin[5][3],porcentRef);
        }else if (porcentRef < 70){//71-85
            acumPorcent += calcularPorcentaje(rangoPorcentajesMin[4][0],rangoPorcentajesMin[4][1],rangoPorcentajesMin[4][2],rangoPorcentajesMin[4][3],porcentRef);
        }else if (porcentRef < 80){//66-70
            acumPorcent += calcularPorcentaje(rangoPorcentajesMin[3][0],rangoPorcentajesMin[3][1],rangoPorcentajesMin[3][2],rangoPorcentajesMin[3][3],porcentRef);
        }else if (porcentRef < 90){//45-65
            acumPorcent += calcularPorcentaje(rangoPorcentajesMin[2][0],rangoPorcentajesMin[2][1],rangoPorcentajesMin[2][2],rangoPorcentajesMin[2][3],porcentRef);
        }else if (porcentRef < 100){//21-40
            acumPorcent += calcularPorcentaje(rangoPorcentajesMin[1][0],rangoPorcentajesMin[1][1],rangoPorcentajesMin[1][2],rangoPorcentajesMin[1][3],porcentRef);
        }else if (porcentRef < 105){//10-20
            acumPorcent += calcularPorcentaje(rangoPorcentajesMin[0][0],rangoPorcentajesMin[0][1],rangoPorcentajesMin[0][2],rangoPorcentajesMin[0][3],porcentRef);
        }
    }

    private int calcularPorcentaje(int datoMax, int datoMin, int porcentMax, int porcentMin, double datoCalcu){
        int porcentajeEnferm = 0;

        porcentajeEnferm = (int) (porcentMin + ((porcentMax-porcentMin)*(datoCalcu-datoMin)/(datoMax-datoMin)));

        return porcentajeEnferm;
    }
    
    private String evaluarInfoPorcentaje(int porcentaje, Enfermedades enfermedad){
        if(porcentaje > 0 && porcentaje < 21){
            return "Probabilidad Baja";
        } else if (porcentaje > 20 && porcentaje < 67) {
            return "Probabilidad Media";
        } else if (porcentaje > 66) {
            return "Probabilidad Alta";
        } else {
            enfermedad.setMostrar(false);
            return "";
        }
    }


    public void evaluarEnfermOrina(Enfermedades enfermedad){
        acumPorcent = 0;
        contador = 0;
        textoPorcentaje = "";
        enfermedad.setMostrar(true); //Le manda true para que se muestre en la vista

        if(enfermedad.getValObtenidos() != null) {
            datosResultados = enfermedad.getValObtenidos();//["Lig. Amoniaco/1","2"]["Negativo/1","3"]//////["Amarillo (todos)/1","2-3"]["Lig. Amoniaco/1","3"]["1 - 6/1","2-3"]

            for (String[] datoResult : datosResultados) {
                String[] datosOpcElegida = datoResult[0].split("/");
                String[] valorReferencia = datoResult[1].split("-");

                opcionElegida = datosOpcElegida[0];
                valorOpcionElegida = Integer.parseInt(datosOpcElegida[1]);

                switch (valorOpcionElegida){
                    case 1:
                        acumPorcent += 0;
                        contador += 1;
                        break;
                    case 2:
                        for (String numRef: valorReferencia) {
                            if(numRef == "2"){
                                acumPorcent += 60;
                                contador += 1;
                            }
                        }
                        break;
                    case 3:
                        for (String numRef: valorReferencia) {
                            if(numRef.equals("3")){
                                acumPorcent += 80;
                                contador += 1;
                            }
                        }
                        break;
                    default:
                        Log.e(TAG, "evaluarEnferm: Hubo un error con la referencia de: " + enfermedad.getNombreEnf());
                }
            }
            porcentaje = (int) (acumPorcent / contador);
            textoPorcentaje = evaluarInfoPorcentaje(porcentaje, enfermedad);

            enfermedad.setPorcentaje(porcentaje);
            enfermedad.setInfoPorcentaje(textoPorcentaje);
        }else {
            enfermedad.setMostrar(false);
            textoPorcentaje = "";
        }
    }


    public void evaluarEnfermTiroides(Enfermedades enfermedad){
        valMin = 0; valMax = 0; valObten = 0;
        porcentaje = 0;
        porcentRef = 0;
        acumPorcent = 0;
        contador = 0;
        textoPorcentaje = "";
        enfermedad.setMostrar(true); //Le mandamos true para que se muestre en la vista

        if(enfermedad.getValObtenidos() != null) {
            datosResultados = enfermedad.getValObtenidos();//[12,20,18,alto][4.5,20,6.5,bajo][15,20,22,alto]

            for (String[] resultado: datosResultados) {
                reference = resultado[3];

                switch (reference) {
                    case "alto":
                        valObten = Double.parseDouble(resultado[0]);
                        valMax = Double.valueOf(resultado[2]);
                        contador += 1;
                        porcentRef = valObten * 100 / valMax;
                        analizarArribaValMax();

                        break;
                    case "bajo":
                        valObten = Double.valueOf(resultado[0]);
                        valMin = Double.valueOf(resultado[1]);
                        contador += 1;
                        porcentRef = valObten * 100 / valMin;
                        analizarAbajoValMin();
                        break;
                    case "ambos":
                        valObten = Double.valueOf(resultado[0]);
                        valMin = Double.valueOf(resultado[1]);
                        valMax = Double.valueOf(resultado[2]);
                        contador += 1;

                        puntMedio = valMin + ((valMax - valMin) / 2);

                        if (valObten > puntMedio) {
                            porcentRef = valObten * 100 / valMax;
                            analizarArribaValMax();
                        } else {
                            porcentRef = valObten * 100 / valMin;
                            analizarAbajoValMin();
                        }
                        break;
                    default:
                        Log.e(TAG, "evaluarEnferm: Hubo un error con la referencia de: " + enfermedad.getNombreEnf());
                        break;
                }

            }

            porcentaje = (int) (acumPorcent / contador);
            textoPorcentaje = evaluarInfoPorcentaje(porcentaje, enfermedad);

            enfermedad.setPorcentaje(porcentaje);
            enfermedad.setInfoPorcentaje(textoPorcentaje);
        }else{
            enfermedad.setMostrar(false);
            textoPorcentaje = "";
        }
    }

}
