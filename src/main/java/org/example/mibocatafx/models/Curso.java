package org.example.mibocatafx.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    public Curso(String nombre, LocalDate fechaBaja) {
        this.nombre = nombre;
        this.fechaBaja = fechaBaja;
    }

    public Curso() {}

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}
