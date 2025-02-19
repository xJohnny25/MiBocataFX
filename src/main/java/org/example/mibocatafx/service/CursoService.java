package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.CursoDAO;
import org.example.mibocatafx.models.Curso;

import java.util.List;

public class CursoService {
    private static final CursoDAO cursoDao = new CursoDAO();

    public List<Curso> getAll() {
        return cursoDao.getAll();
    }

    public Curso getCursoByName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso no puede estar vacío");
        }

        return cursoDao.getCursoByName(name);
    }
}
