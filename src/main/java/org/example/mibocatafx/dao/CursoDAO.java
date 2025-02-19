package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;

import java.util.List;

public class CursoDAO {
    public List<Curso> getAll() {
        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            return session.createQuery("from Curso").getResultList();
        }
    }

    public Curso getCursoByName(String name) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Curso c where c.nombre = :name");
            query.setParameter("name", name);

            return (Curso) query.getSingleResult();
        }
    }
}
