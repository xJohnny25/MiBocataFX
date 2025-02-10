package org.example.mibocatafx.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;

    @Column(name = "id_alumno", nullable = false)
    private int idAlumno;

    @Column(name = "id_bocadillo", nullable = false)
    private int idBocadillo;

    @Column(name = "fecha_retirada")
    private LocalDateTime fechaRetirada;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "costo_final")
    private double precioFinal;

    @Column(name = "id_descuento")
    private int idDescuento;

    public Pedido(int idAlumno, int idBocadillo, LocalDateTime fechaRetirada, LocalDateTime fecha, double precioFinal, int idDescuento) {
        this.idAlumno = idAlumno;
        this.idBocadillo = idBocadillo;
        this.fechaRetirada = fechaRetirada;
        this.fecha = fecha;
        this.precioFinal = precioFinal;
        this.idDescuento = idDescuento;
    }

    public Pedido() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdBocadillo() {
        return idBocadillo;
    }
    public void setIdBocadillo(int idBocadillo) {
        this.idBocadillo = idBocadillo;
    }

    public LocalDateTime getFechaRetirada() {
        return fechaRetirada;
    }
    public void setFechaRetirada(LocalDateTime fechaRetirada) {
        this.fechaRetirada = fechaRetirada;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }
    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public int getIdDescuento() {
        return idDescuento;
    }
    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }
}
