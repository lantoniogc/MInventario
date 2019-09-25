package com.minventario.modelos.algoritmos;

import com.minventario.modelos.tablas.TablaProbabilidad;
import com.minventario.modelos.variables.InventarioBase;
import com.minventario.modelos.variables.Resultados;
import com.minventario.vistas.VistaResultados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

public class Politica {
    private double costoIdeal = 999999999; // El costo ideal para la politica optima
    private int qIdeal = 0; // La cantidad de pedido para la politica optima.
    private int rIdeal = 0; // El punto de reorden para la politica optima.
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Resultados resultadosOptimos; // Resultados optimos
    
    public double getCostoIdeal() {
        return costoIdeal;
    }

    public int getqIdeal() {
        return qIdeal;
    }

    public int getrIdeal() {
        return rIdeal;
    }
    
    public Resultados getResultadosOptimos() {
        return resultadosOptimos;
    }
    
    /**
     * Llena la informacion del archivo y la tabla con los resultados.
     */
    public void llenarDatos(ModeloInventario model, BufferedWriter out, int cantidadActualFilasTabla) throws IOException {

        // Archivo
        out.write("Q = " + model.getVariableQ() + " / R = " + model.getVariableR());
        out.newLine();
        out.write("CC = " + df2.format(model.getResult().getCostoCompra()) + " / CF = " + df2.format(model.getResult().getCostoFaltante()));
        out.newLine();
        out.write("CO = " + df2.format(model.getResult().getCostoOrdenar()) + " / CI = " + df2.format(model.getResult().getCostoInventario()));
        out.newLine();
        out.write("Costo Total: " + df2.format(model.getResult().getCostoTotal()));
        out.newLine();
        out.newLine();
       
        // Tabla
        if (VistaResultados.jTableResultados != null){
            DefaultTableModel modelResultados = (DefaultTableModel) VistaResultados.jTableResultados.getModel();
            modelResultados.addRow(new Object[]{null, null, null, null, null, null, null});
            VistaResultados.jTableResultados.setValueAt(model.getVariableQ(), cantidadActualFilasTabla, 0);
            VistaResultados.jTableResultados.setValueAt(model.getVariableR(), cantidadActualFilasTabla, 1);
            VistaResultados.jTableResultados.setValueAt(model.getResult().getCostoCompra(), cantidadActualFilasTabla, 2);
            VistaResultados.jTableResultados.setValueAt(model.getResult().getCostoFaltante(), cantidadActualFilasTabla, 3);
            VistaResultados.jTableResultados.setValueAt(model.getResult().getCostoOrdenar(), cantidadActualFilasTabla, 4);
            VistaResultados.jTableResultados.setValueAt(model.getResult().getCostoInventario(), cantidadActualFilasTabla, 5);
            VistaResultados.jTableResultados.setValueAt(model.getResult().getCostoTotal(), cantidadActualFilasTabla, 6);            
        }
    }

    /**
     * Realiza el modelos para cada combinacion de Q y R posible.
     * 
     */
    public void obtenerValoresOptimos(InventarioBase inv, ArrayList<TablaProbabilidad> demanda,
                                      ArrayList<TablaProbabilidad> entrega, ArrayList<TablaProbabilidad> espera,
                                      ArrayList<Integer> demandaNumerosAleatorios,ArrayList<Integer> entregaNumerosAleatorios, 
                                      ArrayList<Integer> esperaNumerosAleatorios) {
        String carpetaEscritorio = System.getProperty("user.home");
        File textFile = new File(carpetaEscritorio + "/Desktop", "resultados.txt");
        int cantidadActualFilasTabla = 0;
                            
        try (BufferedWriter out = new BufferedWriter(new FileWriter(textFile))) {

            for (int i = inv.getMinQ(); i<= inv.getMaxQ(); i++) {
                for (int j = inv.getMinR(); j<= inv.getMaxR(); j++) {
                    ModeloInventario model = new ModeloInventario(i, j, inv, demanda, entrega, espera, 
                            demandaNumerosAleatorios, entregaNumerosAleatorios, esperaNumerosAleatorios);
                    llenarDatos(model, out, cantidadActualFilasTabla);
                    if (model.getResult().getCostoTotal() < costoIdeal) {
                        costoIdeal = model.getResult().getCostoTotal();
                        qIdeal = i;
                        rIdeal = j;
                        resultadosOptimos = model.getResult();
                    }
                    cantidadActualFilasTabla++;
                }
            }

            out.write("=== VALORES OPTIMOS ===");
            out.newLine();
            out.write("Q optimo = " + qIdeal + " / R optimo = " + rIdeal);
            out.newLine();
            out.write("Costo total optimo: " + df2.format(costoIdeal));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
