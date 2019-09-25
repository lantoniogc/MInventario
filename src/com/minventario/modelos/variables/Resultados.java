package com.minventario.modelos.variables;

public class Resultados {

    private double costoFaltante;
    private double costoOrdenar;
    private double costoCompra;
    private double costoInventario;
    private double costoTotal;

    public Resultados(double costoFaltante, double costoOrdenar, double costoCompra, double costoInventario, double costoTotal) {
        this.setCostoFaltante(costoFaltante);
        this.setCostoOrdenar(costoOrdenar);
        this.setCostoCompra(costoCompra);
        this.setCostoInventario(costoInventario);
        this.setCostoTotal(costoTotal);
    }

    public double getCostoFaltante() {
        return costoFaltante;
    }

    public void setCostoFaltante(double costoFaltante) {
        this.costoFaltante = costoFaltante;
    }

    public double getCostoOrdenar() {
        return costoOrdenar;
    }

    public void setCostoOrdenar(double costoOrdenar) {
        this.costoOrdenar = costoOrdenar;
    }

    public double getCostoCompra() {
        return costoCompra;
    }

    public void setCostoCompra(double costoCompra) {
        this.costoCompra = costoCompra;
    }

    public double getCostoInventario() {
        return costoInventario;
    }

    public void setCostoInventario(double costoInventario) {
        this.costoInventario = costoInventario;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
