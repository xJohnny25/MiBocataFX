package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.CursoDAO;
import org.example.mibocatafx.models.Curso;

import java.util.List;

public class CursoService {
    private static final CursoDAO cursoDao = new CursoDAO();

    public List<Curso> getAll() {
        return cursoDao.getAll();
    }
}
