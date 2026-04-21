package com.mycompany.lenguajes.IGU;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class EliminarLambda {

    private Automata automata;
    private StringBuilder pasos = new StringBuilder();
    private int posX = 100; // Para no amontonar los estados
    private int posY = 100;

    public EliminarLambda(Automata automata) {
        this.automata = automata;
    }

    public Automata eliminar() {
        ArrayList<Estado> nuevosEstados = new ArrayList<>();
        ArrayList<Transicion> nuevasTransiciones = new ArrayList<>();
        // Mapa para no duplicar conjuntos de estados
        HashMap<String, Estado> mapa = new HashMap<>();

        Estado inicialOriginal = automata.estadoInicial;
        if (inicialOriginal == null) {
            return new Automata(new ArrayList<>(), new ArrayList<>(), null);
        }

        pasos.append("Iniciando eliminación de Lambdas...\n");

        // 1. Calcular cierre inicial
        ArrayList<Estado> inicialLista = new ArrayList<>();
        inicialLista.add(inicialOriginal);
        ArrayList<Estado> cierreInicial = automata.epsilonClosure(inicialLista);

        Estado inicialAFD = obtenerEstado(cierreInicial, mapa);
        inicialAFD.esInicial = true;
        nuevosEstados.add(inicialAFD);

        // 2. Construcción de transiciones (Subconjuntos)
        for (int i = 0; i < nuevosEstados.size(); i++) {
            Estado actual = nuevosEstados.get(i);
            
            // Recuperamos los estados originales que forman este conjunto
            ArrayList<Estado> estadosOriginales = recuperarEstadosDesdeNombre(actual.nombre);

            for (String simbolo : obtenerSimbolos()) {
                ArrayList<Estado> mov = automata.mover(estadosOriginales, simbolo);
                ArrayList<Estado> clausuraMov = automata.epsilonClosure(mov);

                if (!clausuraMov.isEmpty()) {
                    Estado destino = obtenerEstado(clausuraMov, mapa);

                    if (!nuevosEstados.contains(destino)) {
                        nuevosEstados.add(destino);
                    }

                    nuevasTransiciones.add(new Transicion(actual, destino, simbolo));
                }
            }
        }

        // 3. Marcar finales de forma segura
        for (Estado nuevo : nuevosEstados) {
            ArrayList<Estado> componentes = recuperarEstadosDesdeNombre(nuevo.nombre);
            for (Estado comp : componentes) {
                if (comp.esFinal) {
                    nuevo.esFinal = true;
                    break;
                }
            }
        }

        return new Automata(nuevosEstados, nuevasTransiciones, inicialAFD);
    }

    // --- MEJORA: Busca los objetos Estado originales para no depender solo de Strings ---
    private ArrayList<Estado> recuperarEstadosDesdeNombre(String nombreConjunto) {
        ArrayList<Estado> encontrados = new ArrayList<>();
        String limpio = nombreConjunto.replace("{", "").replace("}", "");
        String[] nombres = limpio.split(",");
        
        for (String n : nombres) {
            for (Estado e : automata.estados) {
                if (e.nombre.equals(n)) {
                    encontrados.add(e);
                    break;
                }
            }
        }
        return encontrados;
    }

    private ArrayList<String> obtenerSimbolos() {
        ArrayList<String> simbolos = new ArrayList<>();
        for (Transicion t : automata.transiciones) {
            if (t.simbolo != null && !t.simbolo.isEmpty() && 
                !t.simbolo.equals("λ") && !t.simbolo.equals("ε") &&
                !simbolos.contains(t.simbolo)) {
                simbolos.add(t.simbolo);
            }
        }
        return simbolos;
    }

    private Estado obtenerEstado(ArrayList<Estado> conjunto, HashMap<String, Estado> mapa) {
        String nombre = nombreConjuntoFormateado(conjunto);

        if (!mapa.containsKey(nombre)) {
            // MEJORA: Posicionamiento dinámico para que no salgan amontonados
            Estado nuevo = new Estado(posX, posY, nombre);
            posX += 120;
            if (posX > 700) {
                posX = 100;
                posY += 120;
            }
            mapa.put(nombre, nuevo);
        }
        return mapa.get(nombre);
    }

    private String nombreConjuntoFormateado(ArrayList<Estado> conjunto) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Estado e : conjunto) {
            if (e != null) nombres.add(e.nombre);
        }
        Collections.sort(nombres); // Ordenar para que {q0,q1} sea igual a {q1,q0}

        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < nombres.size(); i++) {
            sb.append(nombres.get(i));
            if (i < nombres.size() - 1) sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    public String getPasos() {
        return pasos.toString();
    }
}