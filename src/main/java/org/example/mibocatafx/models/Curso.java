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
}
