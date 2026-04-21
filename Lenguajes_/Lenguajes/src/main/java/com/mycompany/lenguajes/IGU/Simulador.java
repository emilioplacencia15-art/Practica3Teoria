
package com.mycompany.lenguajes.IGU;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.JFileChooser;

public class Simulador extends javax.swing.JFrame{

    Lienzo lienzo;
    private Automata automata;

    String modo = "nada";

      public Simulador() {
        initComponents();
        lienzo = new Lienzo();

        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(lienzo, BorderLayout.CENTER);

        jButton5.addActionListener(e -> abrirTestAutomata());
    }
    
     private Estado obtenerInicialSeguro() {
        Estado inicial = null;

        for (Estado e : lienzo.estados) {
            if (e.esInicial) {
                if (inicial == null) {
                    inicial = e;
                } else {
                    
                    e.esInicial = false;
                }
            }
        }
        return inicial;
    }
     
    @SuppressWarnings("unchecked")
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Estados");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Seleccionar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Transicion");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Eliminar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Test");

        jButton6.setText("Tabla de transiciones");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jButton7.setText("Importar");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        jButton8.setText("Exportar");
        jButton8.addActionListener(this::jButton8ActionPerformed);

        jButton9.setText("Convertir AFN a AFD");
        jButton9.addActionListener(this::jButton9ActionPerformed);

        jButton10.setText("Automata Lamda a AFN");
        jButton10.addActionListener(this::jButton10ActionPerformed);

        jButton11.setText("Minimizar AFD");
        jButton11.addActionListener(this::jButton11ActionPerformed);

        jButton12.setText("Ver λ-Clausura");
        jButton12.addActionListener(this::jButton12ActionPerformed);

        jButton13.setText("Ver paso a paso");
        jButton13.addActionListener(this::jButton13ActionPerformed);

        jButton14.setText("Ver tabla");
        jButton14.addActionListener(this::jButton14ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(12, 12, 12)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton14)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(jButton12)
                                .addGap(32, 32, 32)
                                .addComponent(jButton13)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton13)))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       lienzo.modo = "estado";
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       lienzo.modo = "nada";
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    lienzo.modo = "transicion";        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    lienzo.modo = "eliminar";        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
     Estado inicial = obtenerInicialSeguro();
    Automata aut = new Automata(lienzo.estados, lienzo.transiciones, inicial);
    Transiciones ventanaTabla = new Transiciones(aut, lienzo);  
    ventanaTabla.setVisible(true); // Asegúrate de que sea visible
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
 JFileChooser fc = new JFileChooser();
    fc.addChoosableFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter("Autómata", "json", "xml", "jff")
    );

    if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();

        try {
            Automata aut = null;
            String name = file.getName().toLowerCase();

            if (name.endsWith(".json")) {
                aut = IOAutomata.importarJSON(file);
            } else if (name.endsWith(".jff")) {
                aut = IOAutomata.importarJFF(file);
            } else if (name.endsWith(".xml")) {
                aut = IOAutomata.importarXML(file);
            }

            if (aut != null) {

                for (Estado e : aut.estados) {
                    e.esInicial = false;
                }

                if (aut.estadoInicial != null) {
                    for (Estado e : aut.estados) {
                        if (e.nombre.equals(aut.estadoInicial.nombre)) {
                            e.esInicial = true;
                            break;
                        }
                    }
                }

                lienzo.estados = aut.estados;
                lienzo.transiciones = aut.transiciones;

                lienzo.revalidate();
                lienzo.repaint();

                JOptionPane.showMessageDialog(this, "Archivo cargado correctamente");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al importar: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
          JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);

        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            try {
                Estado inicial = obtenerInicialSeguro();

// asegurar consistencia global
                for (Estado e : lienzo.estados) {
                   e.esInicial = false;
                }   
                if (inicial != null) {
                inicial.esInicial = true;
                }

                Automata aut = new Automata(lienzo.estados, lienzo.transiciones, inicial);
                IOAutomata.exportarJSON(aut, file);

                JOptionPane.showMessageDialog(this, "Exportado correctamente");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error exportando: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
                                              Estado inicial = obtenerInicialSeguro();
    if (inicial == null) {
        JOptionPane.showMessageDialog(this, "El autómata debe tener un estado inicial.");
        return;
    }

    Automata actual = new Automata(
        new ArrayList<>(lienzo.estados), 
        new ArrayList<>(lienzo.transiciones),
        inicial
    );

    Convertir conv = new Convertir(actual);
    Automata afd = conv.convertirAFNtoAFD();

    lienzo.estados = afd.estados;
    lienzo.transiciones = afd.transiciones;
    
    lienzo.setEstadosActivos(null); 
    
    for (Estado e : lienzo.estados) {
        if (e.nombre.equals(afd.estadoInicial.nombre)) {
            e.esInicial = true;
        }
    }

    lienzo.repaint();
    JOptionPane.showMessageDialog(this, "Conversión a AFD completada.");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
   
        Automata actual = new Automata(
            lienzo.estados,
            lienzo.transiciones,
            obtenerInicialSeguro()
        );

        EliminarLambda elim = new EliminarLambda(actual);
        Automata sinLambda = elim.eliminar();

        lienzo.estados = sinLambda.estados;
        lienzo.transiciones = sinLambda.transiciones;

        lienzo.repaint();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
     if (lienzo.estados.isEmpty()) return;

    // 1. Obtener el autómata actual del dibujo
    Automata actual = lienzo.getAutomata();
    
    // 2. Procesar minimización
    Minimizar min = new Minimizar(actual);
    Automata minimizado = min.minimizarAFD();

    // 3. ¡LA MAGIA! Abrir la ventana nueva con el minimizado
    VentanaResultado vr = new VentanaResultado(minimizado, "Resultado de Minimización (M)");
    vr.setVisible(true);
    
    // El lienzo principal se queda intacto, así puedes comparar "lado a lado"
    JOptionPane.showMessageDialog(this, "Se ha generado el autómata minimizado en una nueva ventana.");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
    
    if (lienzo.estados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Dibuja un autómata primero.");
        return;
    }

    // 2. Intentar obtener el estado seleccionado en el lienzo
    Estado seleccionado = lienzo.getEstadoSeleccionado();

    if (seleccionado == null) {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un estado en el lienzo (hazle clic) para ver su clausura.");
        return;
    }

    // 3. Calcular la clausura
    Automata aut = lienzo.getAutomata();
    java.util.Set<Estado> clausura = aut.calcularClausuraIndividual(seleccionado);

    // 4. Resaltar visualmente en el lienzo (esto lo pintará de amarillo)
    lienzo.setEstadosActivos(clausura);

    // 5. Mostrar reporte en texto
    StringBuilder sb = new StringBuilder();
    for (Estado e : clausura) {
        sb.append(e.nombre).append(" ");
    }
    JOptionPane.showMessageDialog(this, "λ-Clausura de " + seleccionado.nombre + ": { " + sb.toString().trim() + " }");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
     Estado inicial = obtenerInicialSeguro();
    
    if (inicial == null) {
        JOptionPane.showMessageDialog(this, "Por favor, define un estado inicial primero.");
        return;
    }

    // Creamos el objeto autómata con los datos actuales del lienzo
    Automata miAutomata = new Automata(lienzo.estados, lienzo.transiciones, inicial);
    
    // Se lo pasamos a la ventana que creamos antes
  SimuladorPasoAPaso sim = new SimuladorPasoAPaso(miAutomata,lienzo); 
    sim.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
       Automata actual = lienzo.getAutomata();
    String[][] datos = actual.obtenerTabla();
    String[] columnas = new String[datos[0].length];
    columnas[0] = "Estado";
    for(int i=1; i<columnas.length; i++) columnas[i] = "Simp " + i; 

    javax.swing.JTable tabla = new javax.swing.JTable(datos, columnas);
    javax.swing.JOptionPane.showMessageDialog(this, new javax.swing.JScrollPane(tabla), "Tabla de Transiciones", javax.swing.JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jButton14ActionPerformed

    private Estado obtenerInicial() {
    for (Estado e : lienzo.estados) {
        if (e.esInicial) return e;
    }
    return null;
}
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new Simulador().setVisible(true));
    }
 
    private void abrirTestAutomata() {

        if (lienzo.estados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay estados");
            return;
        }

        Estado inicial = obtenerInicialSeguro();

        if (inicial == null) {
            JOptionPane.showMessageDialog(this, "No hay estado inicial");
            return;
        }

        Automata aut = new Automata(lienzo.estados, lienzo.transiciones, inicial);
        TestAutomataFrame frame = new TestAutomataFrame(aut);

        frame.agregarFila();
    }
    private void validarEstadosFinales() {
    boolean hayFinal = false;

    for (Estado e : lienzo.estados) {
        if (e.esFinal) {
            hayFinal = true;
        }
    }

    if (!hayFinal) {
        System.out.println("⚠ No hay estados finales");
    }
}
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
