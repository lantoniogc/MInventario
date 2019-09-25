package com.minventario.modelos.variables;

public class FilaInventario {

    private int index; // Contador del tiempo.
    private int invInicial; // Inventario inicial.
    private double randomDemanda; // Numero aleatorio para la demanda.
    private int valorDemanda; // Demanda.
    private int invFinal; // Inventario final.
    private double invPromedio; // Inventario promedio.
    private int faltante; // Faltante
    private int indexOrden; // Contador de las ordenes.
    private double randomEntrega; // Numero aleatorio para el tiempo de entrega.
    private int valorEntrega; // Tiempo de entrega.
    private double randomEspera; // Numero aleatorio para el tiempo de espera.
    private int valorEspera; // Tiempo de espera.
    private int perdida; // Cuando es perdida se toma en cuenta.

    public FilaInventario(int index, int invInicial, double randomDemanda, int valorDemanda,
                           int invFinal, double invPromedio, int faltante, int indexOrden,
                           double randomEntrega, int valorEntrega, double randomEspera, int valorEspera,
                           int perdida) {
        this.index = index;
        this.invInicial = invInicial;
        this.randomDemanda = randomDemanda;
        this.valorDemanda = valorDemanda;
        this.invFinal = invFinal;
        this.invPromedio = invPromedio;
        this.faltante = faltante;
        this.indexOrden = indexOrden;
        this.randomEntrega = randomEntrega;
        this.valorEntrega = valorEntrega;
        this.randomEspera = randomEspera;
        this.valorEspera = valorEspera;
        this.perdida = perdida;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getInvInicial() {
        return invInicial;
    }

    public void setInvInicial(int invInicial) {
        this.invInicial = invInicial;
    }

    public double getRandomDemanda() {
        return randomDemanda;
    }

    public void setRandomDemanda(double randomDemanda) {
        this.randomDemanda = randomDemanda;
    }

    public int getValorDemanda() {
        return valorDemanda;
    }

    public void setValorDemanda(int valorDemanda) {
        this.valorDemanda = valorDemanda;
    }

    public int getInvFinal() {
        return invFinal;
    }

    public void setInvFinal(int invFinal) {
        this.invFinal = invFinal;
    }

    public double getInvPromedio() {
        return invPromedio;
    }

    public void setInvPromedio(double invPromedio) {
        this.invPromedio = invPromedio;
    }

    public int getFaltante() {
        return faltante;
    }

    public void setFaltante(int faltante) {
        this.faltante = faltante;
    }

    public int getIndexOrden() {
        return indexOrden;
    }

    public void setIndexOrden(int indexOrden) {
        this.indexOrden = indexOrden;
    }

    public double getRandomEntrega() {
        return randomEntrega;
    }

    public void setRandomEntrega(double randomEntrega) {
        this.randomEntrega = randomEntrega;
    }

    public int getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(int valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public double getRandomEspera() {
        return randomEspera;
    }

    public void setRandomEspera(double randomEspera) {
        this.randomEspera = randomEspera;
    }

    public int getValorEspera() {
        return valorEspera;
    }

    public void setValorEspera(int valorEspera) {
        this.valorEspera = valorEspera;
    }

    public int getPerdida() {
        return perdida;
    }

    public void setPerdida(int perdida) {
        this.perdida = perdida;
    }
}
