package com.adrmeneses.evalab_resultanalisisclinicos.entidades;

public class Resultados {
    private int idResultado;
    private int idUsuario;
    private int idTipExamen;
    private int idParametro;
    private double valorObtenido;


    public int getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(int idResultado) {
        this.idResultado = idResultado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTipExamen() {
        return idTipExamen;
    }

    public void setIdTipExamen(int idTipExamen) {
        this.idTipExamen = idTipExamen;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public double getValorObtenido() {
        return valorObtenido;
    }

    public void setValorObtenido(double valorObtenido) {
        this.valorObtenido = valorObtenido;
    }
}
