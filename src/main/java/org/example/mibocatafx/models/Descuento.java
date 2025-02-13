package org.example.mibocatafx.models;

import jakarta.persistence.*;
import org.example.mibocatafx.util.TipoDescuento;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "descuento")
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDescuento tipo;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "alumno_descuento",
            joinColumns = @JoinColumn(name = "id_descuento"),
            inverseJoinColumns = @JoinColumn(name = "id_alumno")
    )
    private List<Alumno> alumnos;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    public Descuento(String descripcion, TipoDescuento tipo, int cantidad, List<Alumno> alumnos, LocalDate fechaBaja) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.alumnos = alumnos;
        this.fechaBaja = fechaBaja;
    }

    public Descuento() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoDescuento getTipo() {
        return tipo;
    }
    public void setTipo(TipoDescuento tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}
