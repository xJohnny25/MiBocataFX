package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.BocataDAO;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Bocata;

import java.util.List;

public class BocataService {
    private final BocataDAO bocataDAO = new BocataDAO();

    public List<Bocata> getBocatas() {
        return bocataDAO.getBocatas();
    }

    public Bocata getBocataByName(String name) {
        if (!name.isEmpty() || !name.isBlank()) {
            return bocataDAO.getBocataByName(name);
        }

        return null;
    }

    public List<Bocata> getBocataToday() {
        return bocataDAO.getBocataToday();
    }

    public Bocata getBocataPedido(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("Se necesita un alumno para conseguir sus pedidos");
        }

        return bocataDAO.getBocataPedido(alumno);
    }
}
