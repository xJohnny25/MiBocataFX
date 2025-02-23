package org.example.mibocatafx.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "id_bocadillo", nullable = false)
    private Bocata bocadillo;

    @Column(name = "fecha_retirada")
    private LocalDateTime fechaRetirada;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "costo_final")
    private double precioFinal;

    @ManyToOne
    @JoinColumn(name = "id_descuento")
    private Descuento descuento;

    public Pedido(Alumno alumno, Bocata bocadillo, LocalDateTime fechaRetirada, LocalDate fecha, double precioFinal, Descuento descuento) {
        this.alumno = alumno;
        this.bocadillo = bocadillo;
        this.fechaRetirada = fechaRetirada;
        this.fecha = fecha;
        this.precioFinal = precioFinal;
        this.descuento = descuento;
    }

    public Pedido() {}

    public Pedido(int id, Alumno alumno, Bocata bocadillo, LocalDateTime fechaRetirada, LocalDate fecha, double precioFinal, Descuento descuento) {
        this.id = id;
        this.alumno = alumno;
        this.bocadillo = bocadillo;
        this.fechaRetirada = fechaRetirada;
        this.fecha = fecha;
        this.precioFinal = precioFinal;
        this.descuento = descuento;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Bocata getBocadillo() {
        return bocadillo;
    }
    public void setBocadillo(Bocata bocadillo) {
        this.bocadillo = bocadillo;
    }

    public LocalDateTime getFechaRetirada() {
        return fechaRetirada;
    }
    public void setFechaRetirada(LocalDateTime fechaRetirada) {
        this.fechaRetirada = fechaRetirada;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }
    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Descuento getDescuento() {
        return descuento;
    }
    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }
}
