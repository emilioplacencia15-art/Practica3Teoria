/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lenguajes.IGU;
public class Estado {
    public int x, y;
    public String nombre;
    public boolean esInicial = false;
    public boolean esFinal = false;

    public Estado(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
    }
    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Estado other = (Estado) obj;
    return nombre.equals(other.nombre); 
}

@Override
public int hashCode() {
    return nombre.hashCode();
}
}