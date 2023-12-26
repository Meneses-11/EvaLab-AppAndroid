package com.adrmeneses.evalab_resultanalisisclinicos.entidades;

import java.util.Date;

public class Usuarios {
    private int id;
    private String nombre;
    private String apellido;
    private String fecha;
    private int idImg;
    private String sexo;
    private Double altura;
    private Double peso;
    private Date fechaDate;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdImg() {
        return idImg;
    }
    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getAltura() {
        return altura;
    }
    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Date getFechaDate() {
        return fechaDate;
    }
    public void setFechaDate(Date fechaDate) {
        this.fechaDate = fechaDate;
    }
}
