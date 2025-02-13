package org.example.mibocatafx.models;

import jakarta.persistence.*;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.models.Curso;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", nullable = false, referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_curso", nullable = false, referencedColumnName = "nombre")
    private Curso curso;

    @Column(name = "fecha_baja")
    private Date fechaBaja;

    @Column(name = "motivo_baja")
    private String motivoBaja;

    @ManyToMany(mappedBy = "alumnos")
    private List<Descuento> descuentos;

    public Alumno(String motivoBaja, Date fechaBaja, Curso curso, Usuario usuario, String nombre, int id) {
        this.motivoBaja = motivoBaja;
        this.fechaBaja = fechaBaja;
        this.curso = curso;
        this.usuario = usuario;
        this.nombre = nombre;
        this.id = id;
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

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
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
