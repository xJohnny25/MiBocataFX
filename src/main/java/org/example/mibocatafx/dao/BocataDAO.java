package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.mibocatafx.models.Bocata;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

public class BocataDAO {
    public List<Bocata> getBocatas() {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            return session.createQuery("from Bocata ", Bocata.class).getResultList();
        }
    }

    public Bocata getBocataByName(String name) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Bocata b where b.nombre = :nombre");
            query.setParameter("nombre", name);

            return (Bocata) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error al obtener el bocadillo.");
        }

        return null;
    }
}
