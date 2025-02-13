package org.example.mibocatafx.service;

import org.example.mibocatafx.dao.UsuarioDAO;
import org.example.mibocatafx.models.Usuario;

public class UsuarioService {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario getUsuarioByMail(String mail, String password) {
        if ((mail.isEmpty() || mail.isBlank()) || (password.isBlank() || password.isEmpty())) {
            throw new IllegalArgumentException("El correo está vacío o es nulo");
        }

        return usuarioDAO.getUsuarioByMail(mail, password);
    }
}