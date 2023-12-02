package com.adrmeneses.evalab_resultanalisisclinicos.entidades;

public class Enfermedades {
    private String nombreEnf;
    private int porcentaje;
    private double valorObtenido;
    private String minValor;
    private String maxValor;
    private String referencia;
    private String informacion;


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

    public double getValorObtenido() {
        return valorObtenido;
    }

    public void setValorObtenido(double valorObtenido) {
        this.valorObtenido = valorObtenido;
    }

    public String getMinValor() {
        return minValor;
    }

    public void setMinValor(String minValor) {
        this.minValor = minValor;
    }

    public String getMaxValor() {
        return maxValor;
    }

    public void setMaxValor(String maxValor) {
        this.maxValor = maxValor;
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
}
