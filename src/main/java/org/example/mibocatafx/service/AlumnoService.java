package org.example.mibocatafx.service;

import jakarta.persistence.Query;
import org.example.mibocatafx.dao.AlumnoDAO;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlumnoService {
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private List<Usuario> listadoUsuarios = new ArrayList<>();

    public void add(Alumno alumno) {
        if (alumno.getUsuario() == null || alumno.getCurso() == null) {
            throw new IllegalArgumentException("Un alumno debe tener un usuario y un curso asociados");
        }

        alumnoDAO.add(alumno);
    }

    public List<Alumno> getAll() {
        return alumnoDAO.getAll();
    }

    public Alumno getAlumnoByUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        return alumnoDAO.getAlumnoByUsuario(usuario);
    }

    public void update(Alumno alumno) {
        alumnoDAO.update(alumno);
    }

    public void delete(Alumno alumno) {
        alumnoDAO.delete(alumno);
    }

    public List<Alumno> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return alumnoDAO.getPaginated(page, offset, filtros);
    }

    public Long countAlumnos(HashMap<String, String> filtros) {
        return alumnoDAO.countAlumnos(filtros);
    }
}
