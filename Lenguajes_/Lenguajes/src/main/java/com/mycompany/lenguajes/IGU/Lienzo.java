    package com.mycompany.lenguajes.IGU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*; // Importante para QuadCurve2D
import java.util.*;

public class Lienzo extends JPanel {

    public ArrayList<Estado> estados = new ArrayList<>();
    public ArrayList<Transicion> transiciones = new ArrayList<>();
    private Set<Estado> estadosActivos = new HashSet<>(); 
    private boolean modoClausura = false;
    private Estado seleccionado = null;
    private Estado arrastrando = null;
    private int offsetX, offsetY;
    private int contador = 0;
    public String modo = "nada";

    public Lienzo() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Estado sel = getEstadoEn(e.getX(), e.getY());
                if (sel != null && modo.equals("nada")) {
                    arrastrando = sel;
                    seleccionado = sel;
                    offsetX = e.getX() - sel.x;
                    offsetY = e.getY() - sel.y;
                    repaint();
                } else if (sel == null) {
                    seleccionado = null;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                arrastrando = null;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Estado est = getEstadoEn(e.getX(), e.getY());
                    if (est != null) {
                        seleccionado = est;
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem itemIni = new JMenuItem(est.esInicial ? "Quitar Inicial" : "Hacer Inicial");
                        itemIni.addActionListener(al -> {
                            estados.forEach(estado -> estado.esInicial = false);
                            est.esInicial = !est.esInicial;
                            repaint();
                        });
                        JMenuItem itemFin = new JMenuItem(est.esFinal ? "Quitar Final" : "Hacer Final");
                        itemFin.addActionListener(al -> {
                            est.esFinal = !est.esFinal;
                            repaint();
                        });
                        menu.add(itemIni);
                        menu.add(itemFin);
                        menu.show(Lienzo.this, e.getX(), e.getY());
                    }
                }

                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (modo.equals("estado")) {
                        estados.add(new Estado(e.getX() - 25, e.getY() - 25, "q" + contador++));
                        repaint();
                    }
                    if (modo.equals("eliminar")) {
                        Estado est = getEstadoEn(e.getX(), e.getY());
                        if (est != null) {
                            estados.remove(est);
                            transiciones.removeIf(t -> t.origen == est || t.destino == est);
                        } else {
                            Transicion t = getTransicionEn(e.getX(), e.getY());
                            if (t != null) transiciones.remove(t);
                        }
                        repaint();
                    }
                    if (modo.equals("transicion")) {
                        Estado clic = getEstadoEn(e.getX(), e.getY());
                        if (clic != null) {
                            if (seleccionado == null) {
                                seleccionado = clic;
                            } else {
                                String simbolo = JOptionPane.showInputDialog(Lienzo.this, "Símbolo (vacío para λ):");
                                if (simbolo != null) {
                                    if (simbolo.equals("ε") || simbolo.trim().isEmpty()) {
                                        simbolo = ""; 
                                    }
                                    transiciones.add(new Transicion(seleccionado, clic, simbolo));
                                }
                                seleccionado = null;
                                repaint();
                            }
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (arrastrando != null) {
                    arrastrando.x = e.getX() - offsetX;
                    arrastrando.y = e.getY() - offsetY;
                    repaint();
                }
            }
        });
    }

    public Estado getEstadoSeleccionado() {
        return seleccionado;
    }

    public void setEstadoSeleccionado(Estado e) {
        this.seleccionado = e;
        repaint();
    }

    public void setEstadosActivos(Set<Estado> activos) {
        this.estadosActivos = (activos != null) ? activos : new HashSet<>();
        repaint();
    }

    public Automata getAutomata() {
        Estado inicial = null;
        for (Estado e : estados) {
            if (e.esInicial) { inicial = e; break; }
        }
        return new Automata(new ArrayList<>(estados), new ArrayList<>(transiciones), inicial);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke trazoNormal = new BasicStroke(1.2f);
        float[] dash = {5.0f};
        Stroke trazoPunteado = new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

        // --- LÓGICA DE AGRUPACIÓN Y DIBUJO DE TRANSICIONES ---
        Map<String, String> grupos = new HashMap<>();
        for (Transicion t : transiciones) {
            String clave = t.origen.nombre + "->" + t.destino.nombre;
            String s = (t.simbolo.isEmpty()) ? "λ" : t.simbolo;
            grupos.put(clave, grupos.containsKey(clave) ? grupos.get(clave) + "," + s : s);
        }

        for (String clave : grupos.keySet()) {
            String[] partes = clave.split("->");
            Estado o = encontrarEstado(partes[0]);
            Estado d = encontrarEstado(partes[1]);
            String txt = grupos.get(clave);
            boolean esPunteado = txt.contains("λ");

            g2.setStroke(esPunteado ? trazoPunteado : trazoNormal);
            g2.setColor(esPunteado ? new Color(100, 100, 250) : Color.BLACK);

            int x1 = o.x + 25, y1 = o.y + 25;
            int x2 = d.x + 25, y2 = d.y + 25;

            if (o == d) { // BUCLE
                g2.drawArc(o.x + 10, o.y - 30, 30, 40, 0, 360);
                g2.drawString(txt, o.x + 20, o.y - 35);
            } else {
                // Verificar si existe la transición inversa para curvar
                boolean inversa = grupos.containsKey(d.nombre + "->" + o.nombre);
                double angulo = Math.atan2(y2 - y1, x2 - x1);

                if (inversa) {
                    // Dibujar Curva QuadCurve2D
                    int mx = (x1 + x2) / 2;
                    int my = (y1 + y2) / 2;
                    int dX = x2 - x1;
                    int dY = y2 - y1;
                    double len = Math.sqrt(dX * dX + dY * dY);
                    
                    // Punto de control para la curva
                    double factorCurva = 35.0;
                    int cx = (int) (mx + factorCurva * (y1 - y2) / len);
                    int cy = (int) (my + factorCurva * (x2 - x1) / len);

                    // Puntos de los bordes del círculo
                    int xStart = (int) (x1 + 25 * Math.cos(angulo + 0.4));
                    int yStart = (int) (y1 + 25 * Math.sin(angulo + 0.4));
                    int xEnd = (int) (x2 - 25 * Math.cos(angulo - 0.4));
                    int yEnd = (int) (y2 - 25 * Math.sin(angulo - 0.4));

                    QuadCurve2D curva = new QuadCurve2D.Double(xStart, yStart, cx, cy, xEnd, yEnd);
                    g2.draw(curva);
                    
                    // Flecha para curva
                    dibujarPunta(g2, cx, cy, xEnd, yEnd);
                    g2.drawString(txt, cx, cy);
                } else {
                    // Línea Recta
                    int xA = (int) (x1 + 25 * Math.cos(angulo));
                    int yA = (int) (y1 + 25 * Math.sin(angulo));
                    int xB = (int) (x2 - 25 * Math.cos(angulo));
                    int yB = (int) (y2 - 25 * Math.sin(angulo));
                    
                    g2.drawLine(xA, yA, xB, yB);
                    dibujarPunta(g2, xA, yA, xB, yB);
                    g2.drawString(txt, (xA + xB) / 2, (yA + yB) / 2 - 5);
                }
            }
        }

        // 2. Dibujar Estados
        for (Estado e : estados) {
            if (e.esInicial) {
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(e.x - 20, e.y + 25, e.x, e.y + 25);
                g2.drawLine(e.x - 10, e.y + 15, e.x, e.y + 25);
                g2.drawLine(e.x - 10, e.y + 35, e.x, e.y + 25);
            }

            g2.setColor(estadosActivos.contains(e) ? new Color(255, 255, 150) : Color.WHITE);
            g2.fillOval(e.x, e.y, 50, 50);
            
            g2.setStroke(new BasicStroke(seleccionado == e ? 3 : 1));
            g2.setColor(seleccionado == e ? Color.BLUE : Color.BLACK);
            g2.drawOval(e.x, e.y, 50, 50);
            g2.drawString(e.nombre, e.x + 15, e.y + 30);

            if (e.esFinal) {
                g2.drawOval(e.x + 5, e.y + 5, 40, 40);
            }
            g2.setColor(estadosActivos.contains(e) ? 
    (modoClausura ? new Color(150, 255, 150) : new Color(255, 255, 150)) // Verde si es clausura, amarillo si es simulación
    : Color.WHITE);
        }
    }
    public void setModoClausura(boolean valor) {
    this.modoClausura = valor;
}

    private void dibujarPunta(Graphics2D g2, int xO, int yO, int xD, int yD) {
        double ang = Math.atan2(yD - yO, xD - xO);
        int largo = 12;
        g2.drawLine(xD, yD, (int) (xD - largo * Math.cos(ang - Math.PI / 6)), (int) (yD - largo * Math.sin(ang - Math.PI / 6)));
        g2.drawLine(xD, yD, (int) (xD - largo * Math.cos(ang + Math.PI / 6)), (int) (yD - largo * Math.sin(ang + Math.PI / 6)));
    }

    private Estado encontrarEstado(String nombre) {
        return estados.stream().filter(e -> e.nombre.equals(nombre)).findFirst().orElse(null);
    }

    public Estado getEstadoEn(int x, int y) {
        for (Estado e : estados) {
            if (x >= e.x && x <= e.x + 50 && y >= e.y && y <= e.y + 50) return e;
        }
        return null;
    }
    public void setAutomata(Automata aut) {
    if (aut == null) return;
    this.estados = new ArrayList<>(aut.estados);
    this.transiciones = new ArrayList<>(aut.transiciones);
    
    // Buscamos el estado inicial en la lista que nos pasaron
    this.estados.forEach(e -> {
        if (aut.estadoInicial != null && e.nombre.equals(aut.estadoInicial.nombre)) {
            e.esInicial = true;
        }
    });
    
    repaint();
}

    private Transicion getTransicionEn(int x, int y) {
        for (Transicion t : transiciones) {
            int mx = (t.origen.x + t.destino.x) / 2 + 25;
            int my = (t.origen.y + t.destino.y) / 2 + 25;
            if (Math.abs(x - mx) < 20 && Math.abs(y - my) < 20) return t;
        }
        return null;
    }
}