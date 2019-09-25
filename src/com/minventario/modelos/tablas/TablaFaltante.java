package com.minventario.modelos.tablas;

public class TablaFaltante {

    private int faltante;
    private int diasEspera;

    public TablaFaltante(int faltante, int diasEspera) {
        this.setFaltante(faltante);
        this.setDiasEspera(diasEspera);
    }

    public int getFaltante() {
        return faltante;
    }

    public void setFaltante(int faltante) {
        this.faltante = faltante;
    }

    public int getDiasEspera() {
        return diasEspera;
    }

    public void setDiasEspera(int diasEspera) {
        this.diasEspera = diasEspera;
    }
}
