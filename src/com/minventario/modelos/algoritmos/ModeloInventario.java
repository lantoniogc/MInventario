package com.minventario.modelos.algoritmos;

import com.minventario.modelos.tablas.TablaFaltante;
import com.minventario.modelos.tablas.TablaProbabilidad;
import com.minventario.modelos.variables.FilaInventario;
import com.minventario.modelos.variables.InventarioBase;
import com.minventario.modelos.variables.Resultados;
import com.minventario.vistas.VistaAnalisis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;

public class ModeloInventario {

    private int variableQ; // Cantidad optima de pedido.
    private int variableR; // Punto de reorden.
    private InventarioBase inventario; // Valores importantes para la simulacion.
    private ArrayList<TablaProbabilidad> demanda; // Tabla para las demandas + probabilidad.
    private ArrayList<TablaProbabilidad> entrega; // Tabla para los tiempos de entrega + probabilidad.
    private ArrayList<TablaProbabilidad> espera; // Tabla para los tiempos de espera + probabilidad.
    private ArrayList<Integer> demandaNumerosAleatorios;
    private ArrayList<Integer> entregaNumerosAleatorios;
    private ArrayList<Integer> esperaNumerosAleatorios;
    private Resultados result; // Resultados de la simulacion.
    private FilaInventario filas; // Informacion de cada fila de la tabla (Uso limitado para facilitar la creacion de archivos)
    private int faltanteTotal; // Faltante total del modelos.
    private int perdidaTotal = 0; // Perdidas totales del modelos.
    private int demandaTotal = 0; // Demanda total del modelos.
    private int invPromedioTotal = 0; // Inventario promedio total del modelos.


    public ModeloInventario(int variableQ, int variableR, InventarioBase inventario, 
            ArrayList<TablaProbabilidad> demanda, ArrayList<TablaProbabilidad> entrega, 
            ArrayList<TablaProbabilidad> espera, ArrayList<Integer> demandaNumerosAleatorios,
            ArrayList<Integer> entregaNumerosAleatorios, ArrayList<Integer> esperaNumerosAleatorios) {
        this.variableQ = variableQ;
        this.variableR = variableR;
        this.inventario = inventario;
        this.demanda = demanda;
        this.entrega = entrega;
        this.espera = espera;
        this.demandaNumerosAleatorios = demandaNumerosAleatorios;
        this.entregaNumerosAleatorios = entregaNumerosAleatorios;
        this.esperaNumerosAleatorios = esperaNumerosAleatorios;

        // Una vez con las variables ya completas, se encarga de generar todos los datos necesarios para analisis.
        // Incluyendo el calculo de resultados.
        obtenerDatos();
        calcularResultados();
    }

    public int getVariableQ() {
        return variableQ;
    }

    public int getVariableR() {
        return variableR;
    }

    public Resultados getResult() {
        return result;
    }

    /**
     * Calcula todos los resultados (costes) de la simulacion dado los datos obtenidos.
     */
    public void calcularResultados() {
        double demandaEfectiva = demandaTotal - perdidaTotal;
        double costoCompra = inventario.getCostoCompra() * demandaEfectiva;
        double costoFaltante = (inventario.getCostoFaltanteConEspera() * faltanteTotal) + 
                (inventario.getCostoFaltanteSinEspera() * perdidaTotal);
        double costoOrdenar = inventario.getCostoOrdernar() * filas.getIndexOrden();
        double costoInventario = invPromedioTotal * inventario.getCostoDeInventarioUnitario();
        double costoTotal = costoCompra + costoFaltante + costoOrdenar + costoInventario;
        result = new Resultados(costoFaltante, costoOrdenar, costoCompra, costoInventario, costoTotal);
    }

    /**
     * Obtiene los datos de cada fila, a traves del algoritmo de modelos de inventario probabilistico.
     */
    public void obtenerDatos() {
        boolean entregaActiva = false; // Existe una entrega activa?
        int diasEntregaActiva = 0; // Dias restantes para la entrega activa.
        int indexOrden = 0; // Numero de la orden.
        List<TablaFaltante> listaFaltante = new CopyOnWriteArrayList<>();
        int indexDemanda = 0; // Numero de las demanda contadas.
        int indexEntrega = 0; // Numero de los tiempos de entrega contados.
        int indexEspera = 0; // Numero de los tiempos de espera contados.

        // Ciclo for para aplicar los calculos necesarios del modelos.
        for (int i = 0; i< inventario.getTiempoSimulacion(); i++) {
            // Condiciones iniciales.
            int invInicial;
            int perdida = 0;
            if (i == 0) {
                invInicial = inventario.getInventarioInicial();
            } else {
                invInicial = filas.getInvFinal();
            }

            // Si aun queda dias para entregar la orden, entonces reduce los dias. Si llega la fecha limite realiza la entrega.
            if (entregaActiva && diasEntregaActiva != -1) {
                diasEntregaActiva--;
            }
            if (entregaActiva && diasEntregaActiva == -1){
                invInicial += variableQ;
                diasEntregaActiva = 0;
                entregaActiva = false;
            }
            
            int j = 0;
            while (j < listaFaltante.size()) {
                int pagoFaltante = invInicial - listaFaltante.get(j).getFaltante();
                // Si es igual a cero se pago el faltante actual, pero ya no queda inventario para seguir buscando mas faltantes.
                // Si es positivo quiere decir que queda inventario. Por lo tanto el faltante actual fue pagado. Se sigue buscando.
                // Si es negativo quiere decir que queda faltante, ya no es necesario seguir buscando porque el inventario es menor que el faltante y la deuda se paga parcialmente.
                if (pagoFaltante == 0) {
                    invInicial = 0;
                    faltanteTotal += listaFaltante.get(j).getFaltante();
                    listaFaltante.remove(0);
                    break;
                }
                if (pagoFaltante > 0) {
                    invInicial = pagoFaltante;
                    faltanteTotal += listaFaltante.get(j).getFaltante();
                    listaFaltante.remove(0);
                } else if (pagoFaltante < 0){
                    invInicial = 0;
                    faltanteTotal += invInicial;
                    listaFaltante.get(j).setFaltante(Math.abs(pagoFaltante));
                    break;
                }
                j++;
            }

            // Se verifica la existencia de algun faltante.
            // Si se acaban los dias de espera para el faltante, entonces pasa a ser una perdida.
            // Y se elimina el faltante de la lista.
            // Si el faltante actual no ha sido pagado, entonces se reducen sus dias de espera.
            for (TablaFaltante faltanteActual:listaFaltante) {
                if (faltanteActual.getDiasEspera() != 0) {
                    faltanteActual.setDiasEspera(faltanteActual.getDiasEspera() - 1);
                }

                if (faltanteActual.getDiasEspera() == 0) {
                    perdidaTotal += listaFaltante.get(0).getFaltante();
                    listaFaltante.remove(faltanteActual);
                }
            }

            // Genera una demanda aleatoria.
            int aleatorioDemanda = demandaNumerosAleatorios != null 
                    && !demandaNumerosAleatorios.isEmpty()
                    && demandaNumerosAleatorios.get(indexDemanda) >= 1.0? 
                    (int) demandaNumerosAleatorios.get(indexDemanda) : generarAleatorio();
            indexDemanda = indexDemanda < demandaNumerosAleatorios.size() - 1? indexDemanda + 1 : 0;
            int valorDemanda = calcularValor(aleatorioDemanda, 1);
            demandaTotal += valorDemanda;
            int faltante = 0;

            // Si el inventario final es menor que 0, quiere decir existe faltante.
            // Para evitar que el inventario final sea negativo en el faltante, se aplica valor absoluto sobre el inventario final.
            // Luego se suma al faltante.
            int invFinal = invInicial - valorDemanda;
            if (invFinal < 0) {
                faltante += Math.abs(invFinal);
                invFinal = 0;
            }

            // Calculo del inventario promedio.
            double invPromedio = (double) (invInicial + invFinal) / 2;
            invPromedioTotal += invPromedio;

            // Calculo de los valores del tiempo de entrega o tiempo de espera segun sea el caso.
            // Si existe faltante quiere decir que hay que esperar, sino hay que hacer un pedido de entrega (mientras no exista un pedido activo).
            int aleatorioEntrega = 0;
            int aleatorioEspera = 0;
            int valorEntrega = 0;
            int valorEspera = -1;

            // Si no hay ordenes pendientes y el inventario final es menor que el punto de reorden entonces genera una nueva orden.
            if (!entregaActiva && invFinal <= variableR){
                indexOrden++;
                aleatorioEntrega = entregaNumerosAleatorios != null 
                        && !entregaNumerosAleatorios.isEmpty() 
                        && entregaNumerosAleatorios.get(indexEntrega) >= 1.0 ? 
                        (int) entregaNumerosAleatorios.get(indexEntrega) : generarAleatorio(); 
                indexEntrega = indexEntrega < entregaNumerosAleatorios.size() - 1? indexEntrega + 1 : 0;
                valorEntrega = calcularValor(aleatorioEntrega, 2);
                diasEntregaActiva = valorEntrega;
                entregaActiva = true;
            }

            // Si existe faltante quiere decir que se genera un tiempo de espera para que el faltante sea pagado.
            // Si el tiempo de espera es igual a cero, quiere decir que el faltante es una perdida automaticamente.
            if (faltante > 0) {
                aleatorioEspera = esperaNumerosAleatorios != null 
                        && !esperaNumerosAleatorios.isEmpty() 
                        && esperaNumerosAleatorios.get(indexEspera) >= 1.0 ? 
                        (int) esperaNumerosAleatorios.get(indexEspera) : generarAleatorio(); 
                indexEspera = indexEspera < esperaNumerosAleatorios.size() - 1? indexEspera + 1 : 0;
                valorEspera = calcularValor(aleatorioEspera, 3);
                if (valorEspera > 0) {
                    listaFaltante.add(new TablaFaltante(faltante, valorEspera));
                } else {
                    perdidaTotal += faltante;
                    perdida = faltante;
                    faltante = 0;
                }
            }

            // Se conecta toda la informacion en la fila de la tabla.
            filas = new FilaInventario(i + 1, invInicial, aleatorioDemanda, valorDemanda, invFinal, invPromedio, faltante, indexOrden, aleatorioEntrega, valorEntrega, aleatorioEspera, valorEspera, perdida);
        
            // Si la tabla de analisis esta pidiendo datos, se llena la tabla de analisis.
            if (VistaAnalisis.jTableAnalisis != null) {
                llenarTablaAnalisis(i, filas);
            }
        }
    }

    /**
     * Calcula el valor de un dato dado un valor aleatorio.
     * Ejemplo: Permite elegir el valor en la tabla de la Demanda dado un valor aleatorio dado.
     */
    public int calcularValor(int valorAleatorio, int tipo) {
        double total = 0;
        int valor = 0;

        // Para variar entre los diferentes tipos de valores a calcular: Demanda, tiempo de entrega y tiempo de espera.
        // Se realiza una sumatoria.
        // Hasta que la probabilidad acumulada del tipo sea igual o mayor que el valor aleatorio dado.
        // Para luego obtener la posicion del valor de ese tipo en especifico y asignar el valor.
        switch (tipo){
            case 1:
                for (int j=0; j<demanda.size(); j++) {
                    total += demanda.get(j).getProbabilidad();
                    if (total >= valorAleatorio) {
                        valor = demanda.get(j).getValor();
                        break;
                    }
                }
                break;
            case 2:
                for (int j=0; j<entrega.size(); j++) {
                    total += entrega.get(j).getProbabilidad();
                    if (total >= valorAleatorio) {
                        valor = entrega.get(j).getValor();
                        break;
                    }
                }
                break;
            case 3:
                for (int j=0; j<espera.size(); j++) {
                    total += espera.get(j).getProbabilidad();
                    if (total >= valorAleatorio) {
                        valor = espera.get(j).getValor();
                        break;
                    }
                }
                break;
        }

        return valor;
    }

    /**
     * Genera una probabilidad aleatoria entre 1-100
     */
    public int generarAleatorio() {
        int valorAleatorio = new Random().nextInt(100) + 1;

        return valorAleatorio;
    }

    public void llenarTablaAnalisis(int i, FilaInventario fila) {
        VistaAnalisis.jTableAnalisis.setValueAt(i, i, 0);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getInvInicial(), i, 1);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getRandomDemanda(), i, 2);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getValorDemanda(), i, 3);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getInvFinal(), i, 4);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getInvPromedio(), i, 5);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getFaltante(), i, 6);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getIndexOrden(), i, 7);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getRandomEntrega(), i, 8);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getValorEntrega(), i, 9);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getRandomEspera(), i, 10);
        VistaAnalisis.jTableAnalisis.setValueAt(filas.getValorEspera(), i, 11);
    }
}
