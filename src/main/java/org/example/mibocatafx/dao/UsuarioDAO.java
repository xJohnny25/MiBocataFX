package org.example.mibocatafx.dao;

import jakarta.persistence.Query;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {
    public void update(Usuario usuario) {
        Transaction tx;

        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            tx = session.beginTransaction();

            session.merge(usuario);

            tx.commit();
        }
    }

    public Usuario getUsuarioByMail(String mail, String password) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            Query query = session.createQuery("from Usuario u where u.correo = :correo and u.contrasena = :password");
            query.setParameter("correo", mail);
            query.setParameter("password", password);

            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Id del alumno no encontrado.");
        }

        return null;
    }
}
