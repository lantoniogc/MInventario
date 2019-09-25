/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minventario.vistas;

import com.minventario.archivos.ManejadorArchivo;
import com.minventario.archivos.ManejadorArchivoModelo;
import com.minventario.excepciones.FormatoArchivoException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author AntonioPC
 */
public class VistaPrincipal extends javax.swing.JFrame {
    
    /**
     * Creates new form VistaPrincipal
     */
    public VistaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonArchivos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonDatos = new javax.swing.JButton();
        jCheckBoxModeloEspecial = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(42, 42, 54));

        jPanel1.setBackground(new java.awt.Color(42, 42, 54));

        jButtonArchivos.setBackground(new java.awt.Color(97, 98, 130));
        jButtonArchivos.setForeground(new java.awt.Color(255, 255, 255));
        jButtonArchivos.setText("Buscar archivo");
        jButtonArchivos.setMaximumSize(new java.awt.Dimension(167, 23));
        jButtonArchivos.setMinimumSize(new java.awt.Dimension(167, 23));
        jButtonArchivos.setPreferredSize(new java.awt.Dimension(167, 23));
        jButtonArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArchivosActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(118, 132, 158));
        jLabel9.setText("MInventario");

        jLabel2.setForeground(new java.awt.Color(118, 132, 158));
        jLabel2.setText("Busca el archivo con el formato explicado en el Manual de Usuario");

        jButtonDatos.setBackground(new java.awt.Color(97, 98, 130));
        jButtonDatos.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDatos.setText("Cargar datos");
        jButtonDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDatosActionPerformed(evt);
            }
        });

        jCheckBoxModeloEspecial.setBackground(new java.awt.Color(42, 42, 54));
        jCheckBoxModeloEspecial.setForeground(new java.awt.Color(118, 132, 158));
        jCheckBoxModeloEspecial.setText("Modelo especial");

        jLabel5.setForeground(new java.awt.Color(118, 132, 158));
        jLabel5.setText("Manual de Usuario");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(118, 132, 158));
        jLabel3.setText("O puedes cargar los datos manualmente");

        jLabel1.setForeground(new java.awt.Color(118, 132, 158));
        jLabel1.setText("Bienvenido a la herramienta de generación de Modelos de Inventario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonArchivos, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(jButtonDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jCheckBoxModeloEspecial)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(108, 108, 108))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addComponent(jLabel5)
                    .addContainerGap(254, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxModeloEspecial)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(308, 308, 308)
                    .addComponent(jLabel5)
                    .addContainerGap(24, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonArchivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArchivosActionPerformed
        // TODO add your handling code here:
        JFileChooser jFileChooser1 = new JFileChooser();
        int result = jFileChooser1.showOpenDialog(jFileChooser1);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (jCheckBoxModeloEspecial.isSelected()) {
                
                ManejadorArchivoModelo arch = new ManejadorArchivoModelo();
                try {
                    arch.leerArchivo(jFileChooser1.getSelectedFile());
                } catch (FormatoArchivoException ex) {
                    Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.setVisible(false);
                VistaVariablesEntrada entrada = new VistaVariablesEntrada(arch.getInv(), arch.getDemanda(), 
                        arch.getEntrega(), arch.getEspera());
                entrada.llenarCampos();
                entrada.setLocationRelativeTo(null);
                entrada.setTitle("MInventario - Variables de Entrada");
                entrada.setVisible(true);
                this.dispose();
                
            } else {
                
                ManejadorArchivo arch = new ManejadorArchivo();
                try {
                    arch.leerArchivo(jFileChooser1.getSelectedFile());
                } catch (FormatoArchivoException ex) {
                    Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setVisible(false);
                VistaVariablesEntrada entrada = new VistaVariablesEntrada(arch.getInv(), arch.getDemanda(), 
                        arch.getEntrega(), arch.getEspera());
                entrada.llenarCampos();
                entrada.setLocationRelativeTo(null);
                entrada.setTitle("MInventario - Variables de Entrada");
                entrada.setVisible(true);
                this.dispose();
            }

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No!");
        }
    }//GEN-LAST:event_jButtonArchivosActionPerformed

    private void jButtonDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDatosActionPerformed

        this.setVisible(false);
        VistaVariablesEntrada entrada = new VistaVariablesEntrada();
        entrada.setLocationRelativeTo(null);
        entrada.setTitle("MInventario - Variables de Entrada");
        entrada.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonDatosActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(getClass().getResource("/ManualUsuario.pdf").toURI());
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println("No hay ninguna aplicacion que pueda abrir el Manual de Uusario");
            } catch (URISyntaxException ex) {
                Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    }//GEN-LAST:event_jLabel5MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonArchivos;
    private javax.swing.JButton jButtonDatos;
    private javax.swing.JCheckBox jCheckBoxModeloEspecial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}