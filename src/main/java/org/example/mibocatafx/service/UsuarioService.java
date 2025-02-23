package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.UsuarioDAO;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.util.HibernateConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.ObjectStreamException;

public class UsuarioService {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void add(Usuario usuario) {
        if (usuario.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El usuario debe tener un correo.");
        }

        usuarioDAO.add(usuario);
    }

    public Usuario getUsuarioByMail(String mail, String password) {
        if ((mail.isEmpty() || mail.isBlank()) || (password.isBlank() || password.isEmpty())) {
            throw new IllegalArgumentException("El correo está vacío o es nulo");
        }

        return usuarioDAO.getUsuarioByMail(mail, password);
    }

    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
    }
}