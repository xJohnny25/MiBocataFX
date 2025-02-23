package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import javax.naming.Referenceable.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlumnoDAO {
    public void add(Alumno alumno) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.persist(alumno);

            tx.commit();
        }
    }

    public void update(Alumno alumno) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.merge(alumno);

            tx.commit();
        }
    }

    public void delete(Alumno alumno) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.remove(alumno);

            tx.commit();
        }
    }

    public List<Alumno> getAll() {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            return session.createQuery("from Alumno", Alumno.class).getResultList();
        }
    }

    public Alumno getAlumnoByUsuario(Usuario usuario) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            Query query = session.createQuery("from Alumno a where a.usuario = :usuario");
            query.setParameter("usuario", usuario);

            return (Alumno) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se ha encontrado el alumno");
            return null;
        }
    }

    public List<Alumno> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Alumno a WHERE true");

            if (filtros != null) {
                for (String key : filtros.keySet()) {
                    switch (key) {
                        case "nombre" -> hql.append(" AND a.nombre like :").append(key);
                        case "correo" -> hql.append(" AND a.usuario.correo like :").append(key);
                        case "curso" -> hql.append(" AND a.curso.nombre = :").append(key);
                    }
                }
            }

            Query<Alumno> query = session.createQuery(hql.toString(), Alumno.class);

            if (filtros != null) {
                for (Map.Entry<String, String> filtro : filtros.entrySet()) {
                    switch (filtro.getKey()) {
                        case "nombre", "correo" -> query.setParameter(filtro.getKey(), "%" + filtro.getValue() + "%");
                        case "curso" -> query.setParameter(filtro.getKey(), filtro.getValue());
                    }
                }
            }

            query.setFirstResult((page - 1) * offset);
            query.setMaxResults(offset);

            return query.getResultList();
        }
    }

    public long countAlumnos(HashMap<String, String> filtros) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM Alumno a WHERE true");

            if (filtros != null) {
                for (String key : filtros.keySet()) {
                    switch (key) {
                        case "nombre" -> hql.append(" AND a.nombre like :").append(key);
                        case "correo" -> hql.append(" AND a.usuario.correo like :").append(key);
                        case "curso" -> hql.append(" AND a.curso.nombre = :").append(key);
                    }
                }
            }

            Query<Long> query = session.createQuery(hql.toString(), Long.class);

            if (filtros != null) {
                for (Map.Entry<String, String> filtro : filtros.entrySet()) {
                    switch (filtro.getKey()) {
                        case "nombre", "correo" -> query.setParameter(filtro.getKey(), "%" + filtro.getValue() + "%");
                        case "curso" -> query.setParameter(filtro.getKey(), filtro.getValue());
                    }
                }
            }

            return query.getSingleResult();
        }
    }
}
