package org.example.mibocatafx.models;

import jakarta.persistence.*;
import org.example.mibocatafx.util.Rol;

import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private int id;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "mac")
    private String mac;

    @Column(name = "fecha_baja")
    private Date fechaBaja;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    public Usuario(String correo, String contrasena, String mac, Date fechaBaja, Rol rol) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.mac = mac;
        this.fechaBaja = fechaBaja;
        this.rol = rol;
    }

    public Usuario() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
