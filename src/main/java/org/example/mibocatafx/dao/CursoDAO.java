package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
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
}
