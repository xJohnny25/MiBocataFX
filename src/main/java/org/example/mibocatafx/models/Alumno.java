package org.example.mibocatafx.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    @Column(name = "id_curso", nullable = false)
    private int idCurso;

    @Column(name = "fecha_baja")
    private Date fechaBaja;

    @Column(name = "motivo_baja")
    private String motivoBaja;

    public Alumno(int id, String nombre, int idUsuario, int idCurso, Date fechaBaja, String motivoBaja) {
        this.id = id;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
        this.fechaBaja = fechaBaja;
        this.motivoBaja = motivoBaja;
    }

    public Alumno() {}

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

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCurso() {
        return idCurso;
    }
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }
    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }
}
