package com.mycompany.lenguajes.IGU;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TestAutomataFrame extends JFrame {

    private Automata automata;
    private JTable tabla;
    private DefaultTableModel modelo;

    public TestAutomataFrame(Automata aut) {
        super("Prueba de Autómata");
        this.automata = aut;

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modelo = new DefaultTableModel(new Object[]{"Cadena", "Estados", "Resultado"}, 0);
        tabla = new JTable(modelo);
tabla.setDefaultRenderer(Object.class, new javax.swing.table.TableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JTextArea area = new JTextArea(value != null ? value.toString() : "");
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        if (isSelected) {
            area.setBackground(table.getSelectionBackground());
            area.setForeground(table.getSelectionForeground());
        } else {
            area.setBackground(Color.WHITE);
            area.setForeground(Color.BLACK);
        }

        return area;
    }
});
        tabla.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int fila = tabla.getSelectedRow();
                    evaluarFila(fila);
                    e.consume();
                }
            }
        });

        JButton btnAgregar = new JButton("Agregar fila");
        JButton btnEvaluar = new JButton("Evaluar");

        btnAgregar.addActionListener(e -> agregarFila());
        btnEvaluar.addActionListener(e -> evaluarFilas());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEvaluar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        for (int i = 0; i < 3; i++) agregarFila();

        setVisible(true);
    }
private void evaluarFila(int fila) {
    String cadena = (String) modelo.getValueAt(fila, 0);
    if (cadena == null) cadena = "";

    ResultadoSimulacion res = automata.simular(cadena);

    modelo.setValueAt(res.getEstados(), fila, 1);
    modelo.setValueAt(res.isAceptada() ? "Aceptada" : "Rechazada", fila, 2);

    ajustarAlturaFilas(); // 🔥 IMPORTANTE
}
    public void agregarFila() {
    modelo.addRow(new Object[]{"", "", ""});
}

    private void evaluarFilas() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            evaluarFila(i);
        }
    }

    
    private void ajustarAlturaFilas() {
    for (int row = 0; row < tabla.getRowCount(); row++) {
        int altura = 20;

        for (int col = 0; col < tabla.getColumnCount(); col++) {
            Component comp = tabla.prepareRenderer(tabla.getCellRenderer(row, col), row, col);
            altura = Math.max(altura, comp.getPreferredSize().height);
        }

        tabla.setRowHeight(row, altura);
    }
}
}