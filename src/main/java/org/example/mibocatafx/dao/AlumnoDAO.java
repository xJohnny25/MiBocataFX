package org.example.mibocatafx.dao;

import jakarta.persistence.Query;
import org.example.mibocatafx.models.Alumno;
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

    public int getIdAlumno(int id) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            Query query = session.createQuery("select Alumno.id from Alumno a where a.id = :id"); //necesito el usuario para conseguir el id segun el correo del usuario??
            query.setParameter("id", id);

            return query.getFirstResult();
        } catch (Exception e) {
            System.out.println("Id del alumno no encontrado.");
        }

        return 0;
    }

    //TODO -> Crear método getId del alumno en la bd ¿Vale el getter?
}
