package com.mycompany.lenguajes.IGU;

public class ResultadoSimulacion {
    private boolean aceptada;
    private String estados;

    public ResultadoSimulacion(boolean aceptada, String estados) {
        this.aceptada = aceptada;
        this.estados = estados;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public String getEstados() {
        return estados;
    }
}