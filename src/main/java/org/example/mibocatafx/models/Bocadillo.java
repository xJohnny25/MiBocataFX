package org.example.mibocatafx.models;

import jakarta.persistence.*;
import org.example.mibocatafx.util.DiaBocadillo;
import org.example.mibocatafx.util.TipoBocadillo;

@Entity
@Table(name = "bocadillo")
public class Bocadillo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo", nullable = false)
    private TipoBocadillo tipo;

    @Column(name = "dia")
    private DiaBocadillo dia;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    public Bocadillo(int id, String nombre, TipoBocadillo tipo, DiaBocadillo dia, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.dia = dia;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Bocadillo() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    public DiaBocadillo getDia() {
        return dia;
    }
    public void setDia(DiaBocadillo dia) {
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
