package com.minventario.modelos.variables;

public class InventarioBase{

    private int inventarioInicial; // Inventario inicial.
    private int unidadDeTiempo; // Dia, Semana, Mes, Trimestre, Semestre, Anual [1-6].
    private int factorTiempo; // Basado en la unidad de tiempo existira un factor multiplcativo para varios calculos.
    private int tiempoSimulacion; // Tiempo de simulacion dada la unidad de tiempo.
    private int minQ; // Cantidad minima de pedido.
    private int maxQ; // Cantidad maxima de pedido.
    private int minR; // Punto de reorden minimo.
    private int maxR; // Punto de reorden maximo.
    private int costoInventario;
    private int costoOrdernar;
    private int costoCompra;
    private int costoFaltanteConEspera;
    private int costoFaltanteSinEspera;
    private double costoDeInventarioUnitario;
    private int CantidadDemanda = 0;
    private int CantidadEspera = 0;
    private int CantidadEntrega = 0;

    public InventarioBase(int inventarioInicial, int unidadDeTiempo, int tiempoSimulacion, int costoInventario, int costoOrdernar, int costoCompra, int costoFaltanteConEspera, int costoFaltanteSinEspera) {
        this.inventarioInicial = inventarioInicial;
        this.unidadDeTiempo = unidadDeTiempo;
        this.tiempoSimulacion = tiempoSimulacion;
        this.costoInventario = costoInventario;
        this.costoOrdernar = costoOrdernar;
        this.costoCompra = costoCompra;
        this.costoFaltanteConEspera = costoFaltanteConEspera;
        this.costoFaltanteSinEspera = costoFaltanteSinEspera;
    }

    public int getInventarioInicial() {
        return inventarioInicial;
    }

    public void setInventarioInicial(int inventarioInicial) {
        this.inventarioInicial = inventarioInicial;
    }

    public int getUnidadDeTiempo() {
        return unidadDeTiempo;
    }

    public void setUnidadDeTiempo(int unidadDeTiempo) {
        this.unidadDeTiempo = unidadDeTiempo;
    }

    public int getTiempoSimulacion() {
        return tiempoSimulacion;
    }

    public void setTiempoSimulacion(int tiempoSimulacion) {
        this.tiempoSimulacion = tiempoSimulacion;
    }

    public int getMinQ() {
        return minQ;
    }

    public int getMaxQ() {
        return maxQ;
    }

    public int getMinR() {
        return minR;
    }

    public int getMaxR() {
        return maxR;
    }

    public int getCostoInventario() {
        return costoInventario;
    }

    public void setCostoInventario(int costoInventario) {
        this.costoInventario = costoInventario;
    }

    public int getCostoOrdernar() {
        return costoOrdernar;
    }

    public void setCostoOrdernar(int costoOrdernar) {
        this.costoOrdernar = costoOrdernar;
    }

    public int getCostoCompra() {
        return costoCompra;
    }

    public void setCostoCompra(int costoCompra) {
        this.costoCompra = costoCompra;
    }

    public int getCostoFaltanteConEspera() {
        return costoFaltanteConEspera;
    }

    public void setCostoFaltanteConEspera(int costoFaltanteConEspera) {
        this.costoFaltanteConEspera = costoFaltanteConEspera;
    }

    public int getCostoFaltanteSinEspera() {
        return costoFaltanteSinEspera;
    }

    public void setCostoFaltanteSinEspera(int costoFaltanteSinEspera) {
        this.costoFaltanteSinEspera = costoFaltanteSinEspera;
    }

    public double getCostoDeInventarioUnitario() {
        return costoDeInventarioUnitario;
    }

    public int getCantidadDemanda() {
        return CantidadDemanda;
    }

    public void setCantidadDemanda(int CantidadDemanda) {
        this.CantidadDemanda = CantidadDemanda;
    }

    public int getCantidadEspera() {
        return CantidadEspera;
    }

    public void setCantidadEspera(int CantidadEspera) {
        this.CantidadEspera = CantidadEspera;
    }

    public int getCantidadEntrega() {
        return CantidadEntrega;
    }

    public void setCantidadEntrega(int CantidadEntrega) {
        this.CantidadEntrega = CantidadEntrega;
    }
    
    public void calcularFactorTiempo() {
        if (unidadDeTiempo >= 1 && unidadDeTiempo <= 6){
            switch (unidadDeTiempo){
                case 1:
                    // Dias
                    factorTiempo = 1;
                    break;
                case 2:
                    // Semanas
                    factorTiempo = 7;
                    break;
                case 3:
                    // Meses
                    factorTiempo = 30;
                    break;
                case 4:
                    // Trimestre
                    factorTiempo = 90;
                    break;
                case 5:
                    // Semestre
                    factorTiempo = 180;
                    break;
                case 6:
                    // Anual
                    factorTiempo = 365;
                    break;
            }
            tiempoSimulacion = tiempoSimulacion * factorTiempo;
        }
    }

    public void calculaCostoInventarioUnitario() {
        costoDeInventarioUnitario = (costoInventario / 365.00);
    }

    public void calcularQR(int demandaMin, int demandaMax, int tiempoEntregaMin, int tiempoEntregaMax) {
        
        double costoAlmacenamiento = costoDeInventarioUnitario * costoCompra;
        minQ = (int) Math.sqrt((2 * costoOrdernar * demandaMin * (costoAlmacenamiento + costoFaltanteConEspera)) / costoAlmacenamiento * costoFaltanteConEspera);
        maxQ = (int) Math.sqrt((2 * costoOrdernar * demandaMax * (costoAlmacenamiento + costoFaltanteSinEspera)) / costoAlmacenamiento * costoFaltanteSinEspera);

        System.out.println("MinQ: " +minQ + " - MaxQ: " + maxQ);

        double t0Min = (double) minQ / (demandaMin);
        double LMin = (double) tiempoEntregaMin;
        if (LMin < t0Min) {
            minR = (int) (LMin * demandaMin);
        } else {
            int n = (int) (LMin / t0Min);
            double Le = LMin - (n * t0Min);
            minR = (int) (Le * demandaMin);
        }

        double t0Max = (double) maxQ / (demandaMax);
        double LMax = (double) tiempoEntregaMax;
        if (LMax < t0Max) {
            maxR = (int) (LMax * demandaMax);
        } else {
            int n = (int) (LMax / t0Max);
            double Le = LMax - (n * t0Max);
            maxR = (int) (Le * demandaMax);
        }

        System.out.println("MinR: " + minR + " - MaxR: " + maxR);
    }

}