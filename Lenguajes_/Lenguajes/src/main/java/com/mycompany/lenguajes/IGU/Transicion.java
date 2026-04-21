package com.mycompany.lenguajes.IGU;

public class Transicion {
    public Estado origen;
    public Estado destino;
    public String simbolo;

    public Transicion(Estado origen, Estado destino, String simbolo) {
        this.origen = origen;
        this.destino = destino;
        this.simbolo = simbolo;
    }
}