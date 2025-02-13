package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class PedidoDAO {
    public void save(Pedido pedido) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            if (pedido.getId() == 0) {
                session.persist(pedido);
            } else {
                session.merge(pedido);
            }

            tx.commit();
        } catch (HibernateException e) {
            System.out.println("Error al insertar el pedido.");
        }
    }

    public boolean tienePedido(Alumno alumno) {
        LocalDate diaHoy = LocalDate.now();
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            List<Pedido> pedidos = session.createQuery("from Pedido p where p.alumno = :alumno and p.fecha = :fecha")
                .setParameter("alumno", alumno)
                .setParameter("fecha", diaHoy)
                .getResultList();

            return !pedidos.isEmpty();
        } catch (NoResultException e) {
            return false;
        }
    }

    public Pedido getPedidoHoy(Alumno alumno) {
        LocalDate diaHoy = LocalDate.now();

        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Pedido p where p.alumno = :alumno and p.fecha = :fecha");
            query.setParameter("alumno", alumno);
            query.setParameter("fecha", diaHoy);

            return (Pedido) query.getSingleResult();
        }
    }
}
