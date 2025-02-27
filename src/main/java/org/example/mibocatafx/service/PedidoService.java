package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.PedidoDAO;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Pedido;

import java.util.HashMap;
import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public void save(Pedido pedido) {
        if (pedido.getFecha() == null || pedido.getAlumno() == null || pedido.getBocadillo() == null) {
            throw new IllegalArgumentException("Un pedido debe tener un alumno asociado, un bocadillo y una fecha de creación");
        }

        pedidoDAO.save(pedido);
    }

    public boolean tienePedido(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("No se ha encontrado al alumno para ver sus pedidos");
        }

        return pedidoDAO.tienePedido(alumno);
    }

    public Pedido getPedidoHoy(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("No se pudo obtener el pedido de hoy del alumno");
        }

        return pedidoDAO.getPedidoHoy(alumno);
    }

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return pedidoDAO.getPaginated(page, offset, filtros);
    }

    public Long countPedidos(HashMap<String, String> filtros) {
        return pedidoDAO.countPedidos(filtros);
    }

    public void updateFechaRetirada(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Se necesita un pedido y una fecha válida");
        }

        pedidoDAO.updateFechaRetirada(pedido);
    }

    public long countPedidosCalientes(HashMap<String, String> filtros) {
        return pedidoDAO.countPedidosCalientes(filtros);
    }

    public long countPedidosFrios(HashMap<String, String> filtros) {
        return pedidoDAO.countPedidosFrios(filtros);
    }
}
