package com.mycompany.lenguajes.IGU;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Transiciones extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private Lienzo lienzoDestino;

    public Transiciones(Automata automata, Lienzo lienzoDestino) {
        super("Tabla de Transiciones");
        this.lienzoDestino = lienzoDestino;

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"Estado origen", "Símbolo", "Estado destino"}, 0);
        tabla = new JTable(modelo);

        if (automata != null) {
            for (Transicion t : automata.transiciones) {
                modelo.addRow(new Object[]{t.origen.nombre, t.simbolo, t.destino.nombre});
            }
        }

        JButton btnAgregar = new JButton("Agregar fila");
        JButton btnGenerarAutomata = new JButton("Generar Automata");

        btnAgregar.addActionListener(e -> modelo.addRow(new Object[]{"", "", ""}));

        btnGenerarAutomata.addActionListener(e -> {
            Automata autNuevo = generarAutomataDesdeTabla();

        
            JOptionPane.showMessageDialog(this, "Autómata generado con "
                    + autNuevo.estados.size() + " estados y "
                    + autNuevo.transiciones.size() + " transiciones.");

            lienzoDestino.estados = autNuevo.estados;
            lienzoDestino.transiciones = autNuevo.transiciones;
            lienzoDestino.repaint();

        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnGenerarAutomata);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private Automata generarAutomataDesdeTabla() {
        ArrayList<Estado> estados = new ArrayList<>();
        ArrayList<Transicion> transiciones = new ArrayList<>();
        Estado estadoInicial = null;

        int cols = 3;
        int anchoLienzo = 700;
        int altoLienzo = 500;
        int margen = 50;

        // Buscar todos los estados únicos y asignar posiciones circulares
        ArrayList<String> nombres = new ArrayList<>();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String origen = String.valueOf(modelo.getValueAt(i, 0));
            String destino = String.valueOf(modelo.getValueAt(i, 2));

            if (!nombres.contains(origen) && !origen.isEmpty()) nombres.add(origen);
            if (!nombres.contains(destino) && !destino.isEmpty()) nombres.add(destino);
        }

        int n = nombres.size();
        double angulo = 2 * Math.PI / n;
        int cx = anchoLienzo / 2;
        int cy = altoLienzo / 2;
        int radio = Math.min(anchoLienzo, altoLienzo) / 2 - margen;

        for (int i = 0; i < nombres.size(); i++) {
            int x = cx + (int)(radio * Math.cos(i * angulo)) - 25; 
            int y = cy + (int)(radio * Math.sin(i * angulo)) - 25;
            Estado e = new Estado(x, y, nombres.get(i));
            estados.add(e);
        }

        if (!estados.isEmpty()) estados.get(0).esInicial = true;

        // Crear transiciones
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String origenNombre = String.valueOf(modelo.getValueAt(i, 0));
            String simbolo = String.valueOf(modelo.getValueAt(i, 1));
            String destinoNombre = String.valueOf(modelo.getValueAt(i, 2));

            if (origenNombre.isEmpty() || destinoNombre.isEmpty() || simbolo.isEmpty()) continue;

            Estado origen = getEstado(estados, origenNombre);
            Estado destino = getEstado(estados, destinoNombre);

            if (origen != null && destino != null) {
                transiciones.add(new Transicion(origen, destino, simbolo));
            }
        }

        estadoInicial = estados.isEmpty() ? null : estados.get(0);
        return new Automata(estados, transiciones, estadoInicial);
    }

    private Estado getEstado(ArrayList<Estado> estados, String nombre) {
        return estados.stream().filter(e -> e.nombre.equals(nombre)).findFirst().orElse(null);
    }
}