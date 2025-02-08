package org.example.mibocatafx.dao;

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


}
