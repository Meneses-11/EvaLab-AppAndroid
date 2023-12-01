package com.adrmeneses.evalab_resultanalisisclinicos.entidades;

public class Resultados {
    private double valorObtenido;
    private String parametroNombre;
    private String minValor;
    private String maxValor;
    private String medidaUnidad;
    private String estado;
    private int semaf;
    private String enfermedadNombre;
    private int probabilidad;
    private String infProbabilidad;



    public double getValorObtenido() {
        return valorObtenido;
    }

    public void setValorObtenido(double valorObtenido) {
        this.valorObtenido = valorObtenido;
    }

    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
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

    public String getMedidaUnidad() {
        return medidaUnidad;
    }

    public void setMedidaUnidad(String medidaUnidad) {
        this.medidaUnidad = medidaUnidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getSemaf() {
        return semaf;
    }

    public void setSemaf(int semaf) {
        this.semaf = semaf;
    }

    public String getEnfermedadNombre() {
        return enfermedadNombre;
    }

    public void setEnfermedadNombre(String enfermedadNombre) {
        this.enfermedadNombre = enfermedadNombre;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getInfProbabilidad() {
        return infProbabilidad;
    }

    public void setInfProbabilidad(String infProbabilidad) {
        this.infProbabilidad = infProbabilidad;
    }
}
