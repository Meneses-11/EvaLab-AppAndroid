package com.adrmeneses.evalab_resultanalisisclinicos.entidades;

public class Enfermedades {
    private String nombreEnf;
    private int porcentaje;
    private String referencia;
    private String informacion;
    private String[][] valObtenidos;
    private String infoPorcentaje;
    private boolean mostrar;


    public String getNombreEnf() {
        return nombreEnf;
    }

    public void setNombreEnf(String nombreEnf) {
        this.nombreEnf = nombreEnf;
    }

    public int getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getInformacion() {
        return informacion;
    }
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String[][] getValObtenidos() {
        return valObtenidos;
    }
    public void setValObtenidos(String[][] valObtenidos) {
        this.valObtenidos = valObtenidos;
    }

    public String getInfoPorcentaje() {
        return infoPorcentaje;
    }
    public void setInfoPorcentaje(String infoPorcentaje) {
        this.infoPorcentaje = infoPorcentaje;
    }

    public boolean isMostrar() {
        return mostrar;
    }
    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }
}
