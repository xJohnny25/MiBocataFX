package org.example.mibocatafx.dao;

import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PedidoDAO {
    public void save (Pedido pedido) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.persist(pedido);

            tx.commit();
        } catch (HibernateException e) {
            System.out.println("Error al insertar el pedido.");
        }
    }
}
