package com.mycompany.lenguajes.IGU;

import java.util.ArrayList;

public class Automata {

    public ArrayList<Estado> estados;
    public ArrayList<Transicion> transiciones;
    public Estado estadoInicial;

    public Automata(ArrayList<Estado> estados, ArrayList<Transicion> transiciones, Estado inicial) {
        this.estados = estados;
        this.transiciones = transiciones;
        this.estadoInicial = inicial;
    }
    public boolean probarCadena(String cadena) {
        // Aprovechamos la lógica que ya escribiste en simular
        ResultadoSimulacion resultado = simular(cadena);
        return resultado.isAceptada(); 
    }

    // =========================================
    // 🔵 SIMULACIÓN CON λ
    // =========================================
    public ResultadoSimulacion simular(String cadena) {

        if (estadoInicial == null) {
            return new ResultadoSimulacion(false, "");
        }

        ArrayList<Estado> actuales = new ArrayList<>();
        actuales.add(estadoInicial);

        // 🔥 ε-closure inicial
        actuales = epsilonClosure(actuales);

        StringBuilder recorrido = new StringBuilder();

        recorrido.append("Paso 0: ").append(formato(actuales)).append("\n");
        recorrido.append("Clausura λ: ").append(formato(actuales)).append("\n\n");

        for (int i = 0; i < cadena.length(); i++) {

            String simbolo = String.valueOf(cadena.charAt(i));

            ArrayList<Estado> anteriores = new ArrayList<>(actuales);

            recorrido.append("Transiciones:\n");

            for (Estado e : anteriores) {
                for (Transicion t : transiciones) {
                    if (t.origen == e && t.simbolo.equals(simbolo)) {
                        recorrido.append("  ")
                                .append(e.nombre)
                                .append(" --")
                                .append(simbolo)
                                .append("--> ")
                                .append(t.destino.nombre)
                                .append("\n");
                    }
                }
            }

            actuales = mover(actuales, simbolo);
            actuales = epsilonClosure(actuales);

            recorrido.append("\nPaso ")
                    .append(i + 1)
                    .append(" (").append(simbolo).append("): ")
                    .append(formato(actuales))
                    .append("\n");

            recorrido.append("Clausura λ: ")
                    .append(formato(actuales))
                    .append("\n\n");
        }

        boolean aceptada = false;

        for (Estado e : actuales) {
            if (e.esFinal) {
                aceptada = true;
                break;
            }
        }

        recorrido.append("Resultado: ")
                .append(aceptada ? "ACEPTADA" : "RECHAZADA")
                .append("\n");

        return new ResultadoSimulacion(aceptada, recorrido.toString());
    }

    // =========================================
    // 🔵 MOVIMIENTO (sin λ)
    // =========================================
    public ArrayList<Estado> mover(ArrayList<Estado> estados, String simbolo) {

        ArrayList<Estado> resultado = new ArrayList<>();

        for (Estado e : estados) {
            for (Transicion t : transiciones) {
                if (t.origen == e && t.simbolo.equals(simbolo)) {
                    if (!resultado.contains(t.destino)) {
                        resultado.add(t.destino);
                    }
                }
            }
        }

        return resultado;
    }

    // =========================================
    // 🔵 ε-CLOSURE
    // =========================================
    public ArrayList<Estado> epsilonClosure(ArrayList<Estado> estados) {

        ArrayList<Estado> closure = new ArrayList<>(estados);
        ArrayList<Estado> stack = new ArrayList<>(estados);

        while (!stack.isEmpty()) {

            Estado actual = stack.remove(stack.size() - 1);

            for (Transicion t : transiciones) {

               if (t.origen == actual && (t.simbolo.equals("") || t.simbolo.equals("λ") || t.simbolo.equals("ε"))) {
    if (!closure.contains(t.destino)) {
        closure.add(t.destino);
        stack.add(t.destino);
    }
}
            }
        }

        return closure;
    }
    public java.util.Set<Estado> calcularClausuraIndividual(Estado inicial) {
    ArrayList<Estado> lista = new ArrayList<>();
    lista.add(inicial);
    ArrayList<Estado> resultado = epsilonClosure(lista);
    return new java.util.HashSet<>(resultado);
}

    // =========================================
    // 🔵 FORMATO DE ESTADOS
    // =========================================
    private String formato(ArrayList<Estado> estados) {

        StringBuilder sb = new StringBuilder("{");

        for (int i = 0; i < estados.size(); i++) {
            sb.append(estados.get(i).nombre);
            if (i < estados.size() - 1) sb.append(",");
        }

        sb.append("}");
        return sb.toString();
    }

    // =========================================
    // 🔵 MOSTRAR CLOSURE INDIVIDUAL
    // =========================================
    public String mostrarClausura(Estado estado) {

        ArrayList<Estado> lista = new ArrayList<>();
        lista.add(estado);

        ArrayList<Estado> closure = epsilonClosure(lista);

        return "Clausuraλ(" + estado.nombre + ") = " + formato(closure);
    }
    public String[][] obtenerTabla() {

    ArrayList<String> simbolos = new ArrayList<>();

    for (Transicion t : transiciones) {
        if (!t.simbolo.equals("") && !simbolos.contains(t.simbolo)) {
            simbolos.add(t.simbolo);
        }
    }

    String[][] tabla = new String[estados.size()][simbolos.size() + 1];

    for (int i = 0; i < estados.size(); i++) {

        Estado e = estados.get(i);
        tabla[i][0] = e.nombre;

        for (int j = 0; j < simbolos.size(); j++) {

            String s = simbolos.get(j);
            StringBuilder dest = new StringBuilder();

            for (Transicion t : transiciones) {
                if (t.origen.nombre.equals(e.nombre) && t.simbolo.equals(s)) {
                    dest.append(t.destino.nombre).append(" ");
                }
            }

            tabla[i][j + 1] = dest.toString().trim();
        }
    }

    return tabla;
}
    
}