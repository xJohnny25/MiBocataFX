package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.AlumnoDAO;
import org.example.mibocatafx.models.Alumno;

import java.util.ArrayList;
import java.util.List;

public class AlumnoService {
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private List<Alumno> listadoAlumnos = new ArrayList<>();

    //TODO

//    public boolean comprobarCredenciales(String mail, String password) {
//        listadoAlumnos = alumnoDAO.getAll();
//
//        for (Alumno alumno1 : listadoAlumnos) {
//            if (alumno.getIdUsuario() == alumno1.getIdUsuario()) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public int getIdAlumno(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        return alumnoDAO.getIdAlumno(id);
    }
}
