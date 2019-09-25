package com.minventario.modelos.tablas;

public class TablaFrecuencia {

    private int valor;
    private int frecuencia;
    private boolean revisado = false;

    public TablaFrecuencia(int valor, int frecuencia) {
        this.valor = valor;
        this.frecuencia = frecuencia;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }
}
