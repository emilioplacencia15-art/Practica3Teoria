package com.mycompany.lenguajes.IGU;

import java.util.ArrayList;
import java.util.HashMap;

public class Convertir {

    private Automata automata;

    public Convertir(Automata automata) {
        this.automata = automata;
    }

    public Automata convertirAFNtoAFD() {

        ArrayList<ArrayList<Estado>> nuevosEstados = new ArrayList<>();
        ArrayList<Transicion> nuevasTransiciones = new ArrayList<>();
        HashMap<String, Estado> mapaEstados = new HashMap<>();

        // 🔥 estado inicial con epsilon closure
        ArrayList<Estado> inicial = new ArrayList<>();
        inicial.add(automata.estadoInicial);
        inicial = automata.epsilonClosure(inicial);

        nuevosEstados.add(inicial);

        ArrayList<ArrayList<Estado>> pendientes = new ArrayList<>();
        pendientes.add(inicial);

        while (!pendientes.isEmpty()) {

            ArrayList<Estado> actual = pendientes.remove(0);

            ArrayList<String> simbolos = obtenerSimbolos();

            for (String simbolo : simbolos) {

                ArrayList<Estado> mover = automata.mover(actual, simbolo);
                mover = automata.epsilonClosure(mover);

                if (mover.isEmpty()) continue;

                if (!contieneEstado(nuevosEstados, mover)) {
                    nuevosEstados.add(mover);
                    pendientes.add(mover);
                }

                Estado origen = obtenerOcrearEstado(actual, mapaEstados);
                Estado destino = obtenerOcrearEstado(mover, mapaEstados);

                nuevasTransiciones.add(new Transicion(origen, destino, simbolo));
            }
        }

        // 🔥 marcar finales
        for (ArrayList<Estado> conjunto : nuevosEstados) {

            Estado afdEstado = obtenerOcrearEstado(conjunto, mapaEstados);

            for (Estado e : conjunto) {
                if (e.esFinal) {
                    afdEstado.esFinal = true;
                    break;
                }
            }
        }

        // 🔥 inicial AFD
        Estado inicialAFD = obtenerOcrearEstado(inicial, mapaEstados);
        inicialAFD.esInicial = true;

        return new Automata(
                new ArrayList<>(mapaEstados.values()),
                nuevasTransiciones,
                inicialAFD
        );
    }

    // =========================
    // 🔧 AUXILIARES
    // =========================

    private ArrayList<String> obtenerSimbolos() {

        ArrayList<String> simbolos = new ArrayList<>();

        for (Transicion t : automata.transiciones) {
            if (t.simbolo != null &&
                !t.simbolo.equals("") &&
                !simbolos.contains(t.simbolo)) {

                simbolos.add(t.simbolo);
            }
        }

        return simbolos;
    }

    private boolean contieneEstado(ArrayList<ArrayList<Estado>> lista,
                                   ArrayList<Estado> buscado) {

        for (ArrayList<Estado> conjunto : lista) {
            if (conjunto.containsAll(buscado) &&
                buscado.containsAll(conjunto)) {
                return true;
            }
        }
        return false;
    }

    private int contadorX = 50;
    private int contadorY = 50;

    private Estado obtenerOcrearEstado(ArrayList<Estado> conjunto,
                                       HashMap<String, Estado> mapa) {

        String nombre = nombreConjunto(conjunto);

        if (!mapa.containsKey(nombre)) {

            Estado nuevo = new Estado(contadorX, contadorY, nombre);

            contadorX += 100;
            if (contadorX > 600) {
                contadorX = 50;
                contadorY += 100;
            }

            mapa.put(nombre, nuevo);
        }

        return mapa.get(nombre);
    }

    private String nombreConjunto(ArrayList<Estado> conjunto) {
    // 1. Extraer nombres y ordenarlos para que {q0,q1} sea igual a {q1,q0}
    java.util.List<String> nombres = new ArrayList<>();
    for (Estado e : conjunto) {
        if (e != null) nombres.add(e.nombre);
    }
    java.util.Collections.sort(nombres);

    // 2. Construir el nombre único
    StringBuilder sb = new StringBuilder("{");
    for (int i = 0; i < nombres.size(); i++) {
        sb.append(nombres.get(i));
        if (i < nombres.size() - 1) sb.append(",");
    }
    sb.append("}");
    return sb.toString();
}
}