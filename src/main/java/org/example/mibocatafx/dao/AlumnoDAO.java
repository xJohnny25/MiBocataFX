package org.example.mibocatafx.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;
import javax.naming.Referenceable.*;

import java.util.List;

public class AlumnoDAO {

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

    //TODO -> Crear método getId del alumno en la bd ¿Vale el getter?
}
