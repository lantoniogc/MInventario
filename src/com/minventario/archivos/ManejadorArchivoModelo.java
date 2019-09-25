package com.minventario.archivos;

import com.minventario.excepciones.FormatoArchivoException;
import com.minventario.modelos.tablas.TablaFrecuencia;
import com.minventario.modelos.tablas.TablaProbabilidad;
import com.minventario.modelos.variables.InventarioBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Scanner;
import java.util.Collections;

public class ManejadorArchivoModelo {

    private Scanner in = null;
    private ArrayList<TablaProbabilidad> demanda = new ArrayList<>();
    private ArrayList<TablaProbabilidad> espera = new ArrayList<>();
    private ArrayList<TablaProbabilidad> entrega = new ArrayList<>();
    private InventarioBase inv = new InventarioBase(0,6,4, 0, 0, 0, 0, 0);
    private int costoPenalizacion = 0;


    public ArrayList<TablaProbabilidad> getDemanda() {
        return demanda;
    }

    public ArrayList<TablaProbabilidad> getEspera() {
        return espera;
    }

    public ArrayList<TablaProbabilidad> getEntrega() {
        return entrega;
    }
    
    public InventarioBase getInv() {
        return inv;
    }

    /**
     * Lectura de archivo dado un nombre de archivo.
     */
    public void leerArchivo(File archivo) throws FormatoArchivoException {
        try {
            in = new Scanner(archivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        boolean ocupado = false;
        int tipo = 0;
        List<TablaFrecuencia> valores = new CopyOnWriteArrayList<>();

        // Agrega cada numero a la tabla de frecuencia y la inicializa.
        while(in.hasNextLine()) {
            String line = in.nextLine();

            if (line.contains("-")) {
                throw new FormatoArchivoException("No pueden introducirse valores negativos al archivo");
            }

            if (!ocupado && line.contains("DemandaFrecuencia")){
                ocupado = true;
                tipo = 1;
            }

            if (!ocupado && line.contains("EntregaFrecuencia")){
                ocupado = true;
                tipo = 2;
            }

            if (!ocupado && line.contains("EsperaFrecuencia")){
                ocupado = true;
                tipo = 3;
            }
            
            if (!ocupado) {
                obtenerValorVariable(line);
            }

            if (ocupado && !line.contains("[]") && !line.contains("DemandaFrecuencia") && !line.contains("EntregaFrecuencia")
               && (!line.contains("EsperaFrecuencia"))) {
                int valor = Integer.parseInt(line);
                valores.add(new TablaFrecuencia(valor, 0));
            }

            if (line.contains("[]")){
                calcularFrecuencia(valores);
                calcularProbabilidad(valores, tipo);
                valores.clear();
                ocupado = false;
            }
        }
        in.close();
    }

    /**
     * Suma la frecuencia de cada numero. Incluyendo su propia aparicion.
     */
    public void calcularFrecuencia(List<TablaFrecuencia> valores){
        for (int i=0; i<valores.size();i++) {
            for (int f=0; f<valores.size();f++) {
                if (i == f){
                    valores.get(i).setFrecuencia(valores.get(f).getFrecuencia() + 1);
                    valores.get(i).setRevisado(true);
                }
                if (i != f && valores.get(i).getValor() == valores.get(f).getValor() && !valores.get(f).isRevisado()){
                    valores.get(i).setFrecuencia(valores.get(f).getFrecuencia() + 1);
                    valores.get(i).setRevisado(true);
                }
            }
        }
    }


    /**
     * Calcula la probabilidad de cada numero.
     * Frecuencia de cada numero / Frecuencia total.
     */
    public void calcularProbabilidad(List<TablaFrecuencia> valores, int tipo){
        int frecuenciaTotal = 0;
        double totalProbabilidad = 0;

        for (TablaFrecuencia i:valores){
            frecuenciaTotal += i.getFrecuencia();
        }

        switch (tipo) {
            case 1:
                for (TablaFrecuencia i:valores){
                    double probabilidad = (double) i.getFrecuencia() / frecuenciaTotal;
                    double probabilidadBaseCien = (probabilidad * 100);
                    totalProbabilidad += probabilidad * 100;
                    demanda.add(new TablaProbabilidad(i.getValor(), probabilidadBaseCien));
                    inv.setCantidadDemanda(inv.getCantidadDemanda() + 1);
                }
            break;

            case 2:
                for (TablaFrecuencia i:valores){
                    double probabilidad = (double) i.getFrecuencia() / frecuenciaTotal;
                    double probabilidadBaseCien = (probabilidad * 100);
                    totalProbabilidad += probabilidad * 100;
                    entrega.add(new TablaProbabilidad(i.getValor(), probabilidadBaseCien));
                    inv.setCantidadEntrega(inv.getCantidadEntrega() + 1);
                }
            break;

            case 3:
                for (TablaFrecuencia i:valores){
                    double probabilidad = (double) i.getFrecuencia() / frecuenciaTotal;
                    double probabilidadBaseCien = (probabilidad * 100);
                    totalProbabilidad += probabilidad * 100;
                    espera.add(new TablaProbabilidad(i.getValor(), probabilidadBaseCien));
                    inv.setCantidadEspera(inv.getCantidadEspera() + 1);
                }
            break;
        }
    }
    
    public void obtenerValorVariable(String line) {
        int posicionValorVariable = line.indexOf(":") + 2;
        int valorVariable = Integer.parseInt(line.substring(posicionValorVariable));
        boolean formatoValido = false;
        
        // InventarioBase
        if (line.contains("InventarioInicial")) {
            inv.setInventarioInicial(valorVariable);
        }

        // Costos
        if (line.contains("CostoMantenerInventario")) {
            inv.setCostoInventario(valorVariable);
        }
        
        if (line.contains("CostoOrdenar")) {
            inv.setCostoOrdernar(valorVariable);
        }
        
        if (line.contains("CostoCompra")) {
            double factorMultiplicativoCostoCompra;
            int inventario;
            inv.setCostoCompra(valorVariable);
            factorMultiplicativoCostoCompra = (double) inv.getCostoInventario() / 100;
            inventario = (int) (inv.getCostoCompra() * factorMultiplicativoCostoCompra);
            inv.setCostoInventario(inventario);
        }
        
        if (line.contains("CostoPenalizacion")) {
            double factorMultiplicativoCostoCompra;
            int penalizacion;
            factorMultiplicativoCostoCompra = (double) valorVariable / 100;
            penalizacion = (int) (inv.getCostoCompra() * factorMultiplicativoCostoCompra);
            inv.setCostoFaltanteConEspera(penalizacion);
        }
        
        if (line.contains("PrecioVenta")) {
            int perdidaGanancia = (int) valorVariable - (inv.getCostoCompra());
            int costoSinEspera = perdidaGanancia - costoPenalizacion;
            inv.setCostoFaltanteSinEspera(costoSinEspera);
        }
    }
}
