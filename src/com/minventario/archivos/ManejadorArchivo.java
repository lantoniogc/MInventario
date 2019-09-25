package com.minventario.archivos;

import com.minventario.excepciones.FormatoArchivoException;
import com.minventario.modelos.variables.InventarioBase;
import com.minventario.modelos.tablas.TablaProbabilidad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class ManejadorArchivo {

    private Scanner in = null;
    private ArrayList<TablaProbabilidad> demanda = new ArrayList<>();
    private ArrayList<TablaProbabilidad> entrega = new ArrayList<>();
    private ArrayList<TablaProbabilidad> espera = new ArrayList<>();
    private InventarioBase inv = new InventarioBase(0,0,0, 0, 0, 0, 0, 0);

    public ArrayList<TablaProbabilidad> getDemanda() {
        return demanda;
    }

    public ArrayList<TablaProbabilidad> getEntrega() {
        return entrega;
    }

    public ArrayList<TablaProbabilidad> getEspera() {
        return espera;
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

        while(in.hasNextLine()) {
            String line = in.nextLine();

            if (line.contains("-")) {
                throw new FormatoArchivoException("No pueden introducirse valores negativos al archivo");
            }

            // Si es una tabla tiene que tener coma (,) y corchetes ([])
            // De otro modo es solo una variable.
            if(line.contains(",") && line.contains("[") && line.contains("]")) {
                obtenerValoresTabla(line);
            } else {
                obtenerValorVariable(line);
            }
        }
        in.close();
    }

    /**
     * El formato para arreglos es [valorA,valorB].
     * Esta funcion utiliza este formato para obtener ambos valores de cada valor y probabilidad para las tablas.
     * Para cada par de valores (valor, probabilidad) lo agrega a su respectiva lista.
     */
    public void obtenerValoresTabla(String line) throws FormatoArchivoException {
        char[] lineArray = line.toCharArray();

        int count = 0;
        String valueA = "/";
        String valueB = "/";
        for (int i = 0; i<lineArray.length; i++) {
            if (lineArray[i] == '[') {
                i++;
                StringBuilder sb = new StringBuilder();
                while (lineArray[i] != ',') {
                    sb.append(lineArray[i]);
                    i++;
                }
                valueA = sb.toString();
            }
            if (lineArray[i] == ',') {
                i++;
                StringBuilder sb = new StringBuilder();
                while (lineArray[i] != ']') {
                    sb.append(lineArray[i]);
                    i++;
                }
                valueB = sb.toString();
            }
            if (!valueA.contains("/") && !valueB.contains("/")) {
                agregarATabla(line, Integer.parseInt(valueA),Double.parseDouble(valueB));
                valueA = "/";
                valueB = "/";
            }
        }
    }

    /**
     * Agrega el valor correspondiente calculado a la lista segun el nombre de la variable en el archivo.
     */
    public void agregarATabla(String line, int valueA, double valueB) throws FormatoArchivoException {
        boolean formatoValido = false;

        if (line.contains("DemandaDiaria")) {
            demanda.add(new TablaProbabilidad(valueA, valueB));
            inv.setCantidadDemanda(inv.getCantidadDemanda() + 1);
            formatoValido = true;
        }

        if (line.contains("TiempoEntrega")) {
            entrega.add(new TablaProbabilidad(valueA, valueB));
            inv.setCantidadEntrega(inv.getCantidadEntrega() + 1);
            formatoValido = true;
        }

        if (line.contains("TiempoEspera")) {
            espera.add(new TablaProbabilidad(valueA, valueB));
            inv.setCantidadEspera(inv.getCantidadEspera() + 1);
            formatoValido = true;
        }

        if (!formatoValido) {
            throw new FormatoArchivoException("El nombre de la variable para la tabla no existe o es incorrecto.");
        }
    }

    /**
     * Asigna el valor correspondiente calculado a la variable segun el nombre de la variable en el archivo.
     */
    public void obtenerValorVariable(String line) throws FormatoArchivoException {
        int posicionValorVariable = line.indexOf(":") + 2;
        int valorVariable = Integer.parseInt(line.substring(posicionValorVariable));
        boolean formatoValido = false;

        // InventarioBase
        if (line.contains("InventarioInicial")) {
            inv.setInventarioInicial(valorVariable);
            formatoValido = true;
        }
        if (line.contains("UnidadTiempo")) {
            if (valorVariable == 0) {
                throw new FormatoArchivoException("La unidad de tiempo no puede ser cero.");
            }
            inv.setUnidadDeTiempo(valorVariable);
            formatoValido = true;
        }
        if (line.contains("TiempoSimulacion")) {
            if (valorVariable == 0) {
                throw new FormatoArchivoException("El tiempo de simulacion no puede ser cero.");
            }
            inv.setTiempoSimulacion(valorVariable);
            formatoValido = true;
        }

        // Costos
        if (line.contains("CostoInventario")) {
            inv.setCostoInventario(valorVariable);
            formatoValido = true;
        }
        if (line.contains("CostoOrdenar")) {
            inv.setCostoOrdernar(valorVariable);
            formatoValido = true;
        }
        if (line.contains("CostoCompra")) {
            inv.setCostoCompra(valorVariable);
            formatoValido = true;
        }
        if (line.contains("CostoFaltanteConEspera")) {
            inv.setCostoFaltanteConEspera(valorVariable);
            formatoValido = true;
        }
        if (line.contains("CostoFaltanteSinEspera")) {
            inv.setCostoFaltanteSinEspera(valorVariable);
            formatoValido = true;
        }

        if (!formatoValido) {
            throw new FormatoArchivoException("El nombre de la variable no existe o es incorrecto.");
        }
    }
}
