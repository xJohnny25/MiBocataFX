package org.example.mibocatafx.models;

import jakarta.persistence.*;
import org.example.mibocatafx.util.DiaBocadillo;
import org.example.mibocatafx.util.TipoBocadillo;

@Entity
@Table(name = "bocadillo")
public class Bocata {
    @Id
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoBocadillo tipo;

    @Column(name = "dia")
    private int dia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    public Bocata(String nombre, TipoBocadillo tipo, int dia, String descripcion, double precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.dia = dia;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Bocata() {}

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoBocadillo getTipo() {
        return tipo;
    }
    public void setTipo(TipoBocadillo tipo) {
        this.tipo = tipo;
    }

    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
