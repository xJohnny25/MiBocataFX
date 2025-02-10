package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.PedidoDAO;
import org.example.mibocatafx.models.Pedido;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public void save(Pedido pedido) {
        if (pedido.getFecha() == null || pedido.getIdAlumno() == 0 || pedido.getIdBocadillo() == 0) {
            throw new IllegalArgumentException("Un pedido debe tener un alumno asociado, un bocadillo y una fecha de creación");
        }

        pedidoDAO.save(pedido);
    }
}
