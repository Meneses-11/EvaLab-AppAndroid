package com.adrmeneses.evalab_resultanalisisclinicos.contenedore;

public class OpcionElegida {
    private static OpcionElegida instance;
    private int examenId;
    private int examenTipId;
    private String nombreExamen;

    private OpcionElegida(){}

    public static synchronized OpcionElegida getInstance() {
        if (instance == null) {
            instance = new OpcionElegida();
        }
        return instance;
    }

    public void setExamenId(int examenId) {
        this.examenId = examenId;
    }
    public int getExamenId() {
        return examenId;
    }

    public void setExamenTipId(int examenTipId) {
        this.examenTipId = examenTipId;
    }
    public int getExamenTipId() {
        return examenTipId;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }
    public String getNombreExamen() {
        return nombreExamen;
    }

}
