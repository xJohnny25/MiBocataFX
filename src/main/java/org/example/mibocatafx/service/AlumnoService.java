package org.example.mibocatafx.service;

import jakarta.persistence.Query;
import org.example.mibocatafx.dao.AlumnoDAO;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class AlumnoService {
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private List<Usuario> listadoUsuarios = new ArrayList<>();

    public boolean validarCredenciales(String correoUsuario, String contrasena) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()){
            listadoUsuarios = session.createQuery("from Usuario").getResultList();
        } catch (Exception e) {
            System.out.println("");
        }

        for (Usuario usuario : listadoUsuarios) {
            if (usuario.getCorreo().equals(correoUsuario) && usuario.getContrasena().equals(contrasena)) {
                return true;
            }
        }

        return false;
    }

    public Alumno getAlumnoByUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        return alumnoDAO.getAlumnoByUsuario(usuario);
    }
}
