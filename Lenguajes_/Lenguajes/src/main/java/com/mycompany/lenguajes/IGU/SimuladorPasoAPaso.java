/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.lenguajes.IGU;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SimuladorPasoAPaso extends javax.swing.JFrame {
 private Automata automata;
    private Lienzo lienzo;
    private String cadena;
    private int indiceActual = 0;
    private ArrayList<Estado> estadosActuales;

    public SimuladorPasoAPaso(Automata aut, Lienzo lienzo) {
    this.automata = aut;
    this.lienzo = lienzo;
    initComponents(); 
    
    this.setTitle("Simulación Paso a Paso");
 
    this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
    
    lblInfo.setText("Esperando cadena..."); 
}

    private void siguientePaso() {
        if (indiceActual < cadena.length()) {
            String simbolo = String.valueOf(cadena.charAt(indiceActual));
            
            // Actualizamos la etiqueta de información (debes crearla en el diseño)
            lblInfo.setText("Procesando símbolo: '" + simbolo + "' (" + (indiceActual + 1) + "/" + cadena.length() + ")");
            
            // Lógica del AFN
            estadosActuales = automata.mover(estadosActuales, simbolo);
            estadosActuales = automata.epsilonClosure(estadosActuales);
            
            indiceActual++;
            actualizarLienzo();
            
            if (indiceActual == cadena.length()) {
                jButton1.setEnabled(false); // Desactivar botón al terminar
                verificarResultadoFinal();
            }
        }
    }
    private void actualizarLienzo() {
    if (this.lienzo != null) {
        java.util.Set<Estado> conjunto = new java.util.HashSet<>(estadosActuales);
        this.lienzo.setEstadosActivos(conjunto);
        
        this.lienzo.revalidate(); 
        this.lienzo.repaint();
        
        // OPCIONAL: Imprime esto en consola para saber si está llegando aquí
        System.out.println("Enviando " + conjunto.size() + " estados al lienzo");
    }
}
    

    private void verificarResultadoFinal() {
        boolean aceptada = false;
        for (Estado e : estadosActuales) {
            if (e.esFinal) { aceptada = true; break; }
        }
        String msg = aceptada ? "¡Cadena ACEPTADA!" : "Cadena RECHAZADA";
        lblInfo.setText("Resultado: " + msg);
        JOptionPane.showMessageDialog(this, msg);

        // --- ESTO ES LO NUEVO ---
        indiceActual = 0;           // Reseteamos el contador
        estadosActuales = null;     // Liberamos los estados para que el botón vuelva al modo "Iniciar"
        jButton1.setText("Iniciar"); // Cambiamos el texto
        jButton1.setEnabled(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jLabel1.setText("Ingresa una cadena");

        jButton1.setText("Siguiente");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        lblInfo.setText("Listo para ejecutar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(156, Short.MAX_VALUE)
                .addComponent(lblInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(150, 150, 150))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(lblInfo))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
      jButton1ActionPerformed(evt);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
    if (estadosActuales == null) { 
        cadena = jTextField1.getText();
        if (cadena == null) cadena = ""; 

        // 1. Configuración inicial
        indiceActual = 0;
        ArrayList<Estado> inicial = new ArrayList<>();
        inicial.add(automata.estadoInicial);
        estadosActuales = automata.epsilonClosure(inicial);
        
        actualizarLienzo();

        // 2. Manejo de cadena vacía (Caso especial)
        if (cadena.isEmpty()) {
            verificarResultadoFinal();
            return; 
        }

        // 3. Preparar para el siguiente carácter
        jButton1.setText("Siguiente Paso"); 
        lblInfo.setText("Simulación iniciada. Procesando cadena...");
        
        // IMPORTANTE: Si quieres que el primer clic ya procese el primer carácter, 
        // podrías llamar a siguientePaso() aquí, pero lo normal es 
        // mostrar el estado inicial primero y esperar al segundo clic.
        
    } else {
        // Si estadosActuales YA TIENE algo, significa que ya iniciamos
        // y ahora sí queremos avanzar.
        siguientePaso();
    }
    }//GEN-LAST:event_jButton1ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblInfo;
    // End of variables declaration//GEN-END:variables
}
