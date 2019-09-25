package com.minventario.modelos;

import com.minventario.excepciones.TeoriaProbabilidadTotalException;
import com.minventario.modelos.tablas.TablaProbabilidad;
import com.minventario.vistas.VistaPrincipal;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        VistaPrincipal principal = new VistaPrincipal();
        principal.setSize(459, 430);
        principal.setLocationRelativeTo(null);
        principal.setTitle("MInventario: Herramienta de Modelos de Inventario");
        principal.setResizable(false);
        principal.setVisible(true);
    }
    
    public static void comprobarTeoriaProbabilidadTotal(ArrayList<TablaProbabilidad> tablaProbabilidad) {
        double probabilidadTotal = 0.0;
        for (TablaProbabilidad i:tablaProbabilidad) {
            probabilidadTotal += i.getProbabilidad();
        }
        
        if (probabilidadTotal >= 100.1 && probabilidadTotal <= 99.9) {
            try {
                throw new TeoriaProbabilidadTotalException("La suma de las probabilidades debe ser igual a 100 (1)");
            } catch(TeoriaProbabilidadTotalException e) {
                System.exit(1);
            }        
        }
    }
}
