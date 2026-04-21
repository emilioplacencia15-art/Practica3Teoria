package com.mycompany.lenguajes.IGU;

import java.util.*;
import java.util.stream.Collectors;

public class Minimizar {
    private Automata automata;

    public Minimizar(Automata automata) {
        this.automata = automata;
    }

    public Automata minimizarAFD() {
        Set<Estado> alcanzables = encontrarAlcanzables();
        List<Estado> estadosLimpios = automata.estados.stream()
                .filter(alcanzables::contains)
                .collect(Collectors.toList());
        
        List<Transicion> transicionesLimpias = automata.transiciones.stream()
                .filter(t -> alcanzables.contains(t.origen) && alcanzables.contains(t.destino))
                .collect(Collectors.toList());

        Map<Estado, Integer> particionActual = new HashMap<>();
        for (Estado e : estadosLimpios) {
            particionActual.put(e, e.esFinal ? 1 : 0);
        }

        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Map<Estado, String> firmas = new HashMap<>();
            
            for (Estado e : estadosLimpios) {
                StringBuilder firma = new StringBuilder(particionActual.get(e).toString());
                Set<String> alfabeto = transicionesLimpias.stream()
                        .map(t -> t.simbolo).collect(Collectors.toSet());
                
                for (String simbolo : alfabeto.stream().sorted().collect(Collectors.toList())) {
                    Estado destino = transicionesLimpias.stream()
                        .filter(t -> t.origen == e && t.simbolo.equals(simbolo))
                        .map(t -> t.destino).findFirst().orElse(null);
                    
                    firma.append("-").append(destino != null ? particionActual.get(destino) : "null");
                }
                firmas.put(e, firma.toString());
            }

            List<String> firmasUnicas = firmas.values().stream().distinct().collect(Collectors.toList());
            Map<Estado, Integer> nuevaParticion = new HashMap<>();
            for (Estado e : estadosLimpios) {
                nuevaParticion.put(e, firmasUnicas.indexOf(firmas.get(e)));
            }

            if (!nuevaParticion.equals(particionActual)) {
                particionActual = nuevaParticion;
                cambio = true;
            }
        }

        return reconstruirAutomata(particionActual, transicionesLimpias);
    }

    private Set<Estado> encontrarAlcanzables() {
        Set<Estado> alcanzados = new HashSet<>();
        if (automata.estadoInicial == null) return alcanzados;
        
        Stack<Estado> pila = new Stack<>();
        pila.push(automata.estadoInicial);
        alcanzados.add(automata.estadoInicial);

        while (!pila.isEmpty()) {
            Estado actual = pila.pop();
            for (Transicion t : automata.transiciones) {
                if (t.origen == actual && !alcanzados.contains(t.destino)) {
                    alcanzados.add(t.destino);
                    pila.push(t.destino);
                }
            }
        }
        return alcanzados;
    }
 
    private Automata reconstruirAutomata(Map<Estado, Integer> particion, List<Transicion> transiciones) {
    Map<Integer, Estado> nuevosEstadosMap = new HashMap<>();
    ArrayList<Estado> nuevosEstadosLista = new ArrayList<>();

    particion.values().stream().distinct().forEach(id -> {
        // Buscamos un representante para la posición X, Y
        Estado representante = particion.entrySet().stream()
                .filter(entry -> entry.getValue().equals(id))
                .map(Map.Entry::getKey).findFirst().get();
        
        Estado nuevo = new Estado(representante.x, representante.y, "M" + id);

        // 🔥 CORRECCIÓN AQUÍ: 
        // Si ALGÚN estado del grupo original era final, el nuevo estado es final.
        nuevo.esFinal = particion.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(id) && entry.getKey().esFinal);
        
        // Lo mismo para el inicial (este ya lo tenías bien)
        nuevo.esInicial = particion.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(id) && entry.getKey().esInicial);
        
        nuevosEstadosMap.put(id, nuevo);
        nuevosEstadosLista.add(nuevo);
    });

    // ... (el resto del código de transiciones se queda igual)
    ArrayList<Transicion> nuevasTransiciones = new ArrayList<>(); 
    for (Transicion t : transiciones) {
        Estado orig = nuevosEstadosMap.get(particion.get(t.origen));
        Estado dest = nuevosEstadosMap.get(particion.get(t.destino));
        
        if (nuevasTransiciones.stream().noneMatch(nt -> nt.origen == orig && nt.destino == dest && nt.simbolo.equals(t.simbolo))) {
            nuevasTransiciones.add(new Transicion(orig, dest, t.simbolo));
        }
    }

    Estado inicial = nuevosEstadosLista.stream().filter(e -> e.esInicial).findFirst().orElse(null);
    return new Automata(nuevosEstadosLista, nuevasTransiciones, inicial);
}
}
